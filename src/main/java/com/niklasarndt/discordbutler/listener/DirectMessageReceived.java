package com.niklasarndt.discordbutler.listener;

import com.niklasarndt.discordbutler.Butler;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

/**
 * Created by Niklas on 2020/07/24
 */
public class DirectMessageReceived extends ListenerAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Butler butler;

    public DirectMessageReceived(Butler butler) {
        this.butler = butler;
    }

    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
        if (event.getAuthor().getIdLong() != butler.getOwnerId()) return; //This message was not sent by the bot owner.

        logger.info("Message text: {}", event.getMessage().getContentDisplay());
        event.getMessage().addReaction("\u2705").queue();
        event.getChannel().sendMessage("You are authorized to send messages.").queue();
    }
}
