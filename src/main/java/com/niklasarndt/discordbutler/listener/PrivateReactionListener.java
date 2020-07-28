package com.niklasarndt.discordbutler.listener;

import com.niklasarndt.discordbutler.Butler;
import com.niklasarndt.discordbutler.util.Emojis;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Niklas on 2020/07/28.
 */
public class PrivateReactionListener extends ListenerAdapter {

    private static final List<String> REACT_EMOJIS = List.of(Emojis.WASTEBASKET);
    private final Butler butler;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ExpiringMap<Long, String> removed = ExpiringMap.builder()
            .expirationPolicy(ExpirationPolicy.CREATED)
            .expiration(5, TimeUnit.SECONDS).build();

    public PrivateReactionListener(Butler butler) {
        this.butler = butler;
    }

    @Override
    public void onPrivateMessageReactionAdd(@Nonnull PrivateMessageReactionAddEvent event) {
        if (event.getUserIdLong() != butler.getOwnerId()) return;

        if (!REACT_EMOJIS.contains(event.getReactionEmote().getEmoji())) return;
        logger.debug("Received emote: " + event.getMessageIdLong());


        event.getChannel()
                .retrieveMessageById(event.getMessageIdLong())
                .delay(3, TimeUnit.SECONDS)
                .queue(message -> {
                    logger.debug("Executing reaction processing");
                    if (message == null || !message.getAuthor().isBot()) {
                        logger.debug("Not suitable for reaction processing");
                        return;
                    }

                    if (removed.containsKey(event.getMessageIdLong()) &&
                            removed.get(event.getMessageIdLong())
                                    .equals(event.getReactionEmote().getEmoji())) {
                        logger.debug("User removed reaction");
                        return;
                    }

                    String emoji = event.getReactionEmote().getEmoji();
                    if (emoji.equals(Emojis.WASTEBASKET)) {
                        message.delete().queue();
                    }
                });
    }

    @Override
    public void onPrivateMessageReactionRemove(@Nonnull PrivateMessageReactionRemoveEvent event) {
        if (!REACT_EMOJIS.contains(event.getReactionEmote().getEmoji())) return;

        logger.debug("Removed emote: " + event.getMessageIdLong());
        removed.put(event.getMessageIdLong(), event.getReactionEmote().getEmoji());
    }
}
