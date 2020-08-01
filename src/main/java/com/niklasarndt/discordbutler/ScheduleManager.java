package com.niklasarndt.discordbutler;

import com.niklasarndt.discordbutler.util.ButlerUtils;
import com.niklasarndt.discordbutler.util.Emojis;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Niklas on 2020/08/01.
 * <p>
 * WARNING: Automating a task without explicit user consent is considered API abuse.
 * A message should only be scheduled if the user to you to do so. (e.g. via the remind command)
 */
public class ScheduleManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ScheduledExecutorService executorService = Executors
            .newScheduledThreadPool(1, runnable -> new Thread(null, runnable,
                    "ScheduleThread-" + System.currentTimeMillis()));
    private final Butler butler;

    public ScheduleManager(Butler butler) {
        this.butler = butler;
    }

    public void schedule(Runnable runnable, long waitTimeInMs) {
        executorService.schedule(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                logger.error("Failed to run scheduled task", e);
            }
        }, waitTimeInMs, TimeUnit.MILLISECONDS);
    }

    public void scheduleMessage(String message, long waitTimeInMs) {
        schedule(() -> {
            User user = butler.getJda().retrieveUserById(butler.getOwnerId()).complete();

            String duration = ButlerUtils.prettyPrintTime(waitTimeInMs);

            String intro = String.format("Hey there %s Here's what you " +
                            "asked me to **remind** you of **%s ago**!", Emojis.WAVE,
                    duration);
            MessageEmbed embed = new EmbedBuilder()
                    .addField("Your Reminder", message, false)
                    .setFooter(String.format("Requested %s ago", duration)).build();

            user.openPrivateChannel()
                    .queue(channel -> channel.sendMessage(intro).embed(embed).queue());
        }, waitTimeInMs);
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            logger.warn("Executor service did not shut down automatically, forcing shutdown", e);
            executorService.shutdownNow();
        }
    }

    public boolean isShutdown() {
        return executorService.isShutdown();
    }
}
