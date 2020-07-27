package com.niklasarndt.discordbutler.listener;

import com.niklasarndt.discordbutler.Butler;
import com.niklasarndt.discordbutler.enums.ResultType;
import com.niklasarndt.discordbutler.util.Emojis;
import com.niklasarndt.discordbutler.util.ExecutionFlags;
import com.niklasarndt.discordbutler.util.ResultBuilder;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Nonnull;

/**
 * Created by Niklas on 2020/07/24.
 */
public class DirectMessageReceived extends ListenerAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Butler butler;

    public DirectMessageReceived(Butler butler) {
        this.butler = butler;
    }

    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
        //This message was not sent by the bot owner.
        if (event.getAuthor().getIdLong() != butler.getOwnerId()) return;

        if (butler.hasFlag(ExecutionFlags.NO_MODULE_MANAGER)) {
            event.getMessage().addReaction(Emojis.LOCK).queue();
            return;
        }

        ResultBuilder result = butler.getModuleManager().execute(event.getMessage());

        if (result.hasOutput()) {
            try {
                result.produceIntoChannel(event.getChannel());
            } catch (Exception e) {
                logger.error("Unexpected exception while producing the command result of \"{}\"",
                        event.getMessage().getContentRaw(), e);
                event.getChannel().sendMessage(String.format("%s Could not produce a response: " +
                                "**%s** - %s", ResultType.ERROR.emoji,
                        e.getClass().getSimpleName(), e.getMessage())).queue();
            }
        } else {
            event.getMessage().addReaction(result.getType().emoji).queue();
        }
    }
}
