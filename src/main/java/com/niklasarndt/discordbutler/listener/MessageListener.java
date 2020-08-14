package com.niklasarndt.discordbutler.listener;

import com.niklasarndt.discordbutler.Butler;
import com.niklasarndt.discordbutler.enums.ResultType;
import com.niklasarndt.discordbutler.util.Emojis;
import com.niklasarndt.discordbutler.util.ExecutionFlags;
import com.niklasarndt.discordbutler.util.ResultBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Nonnull;

/**
 * Created by Niklas on 2020/07/24.
 */
public class MessageListener extends ListenerAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Butler butler;

    public MessageListener(Butler butler) {
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

        handleCommand(event.getMessage(), event.getChannel());
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getChannel().getTopic() == null ||
                !event.getChannel().getTopic().contains("allow-butler")) return;
        if (event.getAuthor().getIdLong() != butler.getOwnerId()) return;

        if (butler.hasFlag(ExecutionFlags.NO_MODULE_MANAGER)) {
            event.getMessage().addReaction(Emojis.LOCK).queue();
            return;
        }

        handleCommand(event.getMessage(), event.getChannel());
    }

    private void handleCommand(Message message, MessageChannel channel) {
        try {
            ResultBuilder result = butler.getModuleManager().execute(message);

            if (result.hasOutput()) {
                try {
                    result.produceIntoChannel(channel);
                } catch (Exception e) {
                    logger.error("Unexpected exception while producing " +
                                    "the command result of \"{}\"",
                            message.getContentRaw(), e);
                    channel.sendMessage(String.format("%s Could not produce a response: " +
                                    "**%s** - %s", ResultType.ERROR.emoji,
                            e.getClass().getSimpleName(), e.getMessage())).queue();
                }
            } else {
                message.addReaction(result.getType().emoji).queue();
            }
        } catch (InsufficientPermissionException permissionException) {
            channel.sendMessage(Emojis.X + "There's a permission missing. " +
                    "Please make sure the bot has the permission **\"" +
                    permissionException.getPermission().getName() + "\"**.").queue();
            logger.warn("Lack of permission " + permissionException.getPermission().getName());
        } catch (Exception e) {
            channel.sendMessage("Oh no! A critical internal error has occurred. **" +
                    e.getClass().getSimpleName() + "**: " + e.getMessage()).queue();
            logger.error("Internal message processing error", e);
        }

    }
}
