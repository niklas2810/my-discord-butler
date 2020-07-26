package com.niklasarndt.discordbutler;

import com.niklasarndt.discordbutler.listener.DirectMessageReceived;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Niklas on 2020/07/24
 */
public class Butler {

    private final long startupTimestamp = System.currentTimeMillis();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final JDA jda;
    private final long ownerId;

    private final ModuleManager moduleManager;

    protected Butler() throws Exception {
        logger.info("Startup is in progress");

        try {
            ownerId = Long.parseLong(System.getenv("OWNER_ID"));
        } catch (Exception e) {
            throw new IllegalArgumentException("You must provide the owner's Discord ID via OWNER_ID.");
        }
        logger.info("Owner ID has been stored.");

        jda = setUpJda();
        logger.info("JDA has been set up!");

        moduleManager = new ModuleManager(this);
        moduleManager.loadAll();
        logger.info("Module manager has been set up!");

        logger.info("SUCCESS: SETUP COMPLETE");
    }

    public JDA getJda() {
        return jda;
    }

    public long getOwnerId() {
        return ownerId;
    }

    /**
     * @return The completely initialized JDA instance.
     * @throws Exception Will cause a shutdown + sentry log.
     */
    private JDA setUpJda() throws Exception {
        final JDABuilder builder = JDABuilder.create(System.getenv("TOKEN_DISCORD"),
                GatewayIntent.DIRECT_MESSAGES, GatewayIntent.DIRECT_MESSAGE_REACTIONS);
        builder.setActivity(Activity.of(Activity.ActivityType.WATCHING, "via direct messages"));
        builder.addEventListeners(new DirectMessageReceived(this));
        builder.disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.EMOTE, CacheFlag.CLIENT_STATUS);
        return builder.build().awaitReady();
    }

    public long getStartupTimestamp() {
        return startupTimestamp;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }
}
