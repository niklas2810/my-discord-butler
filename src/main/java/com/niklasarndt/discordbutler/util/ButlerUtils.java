package com.niklasarndt.discordbutler.util;

import com.niklasarndt.discordbutler.modules.ButlerCommandInformation;
import net.dv8tion.jda.api.entities.MessageEmbed;

/**
 * Created by Niklas on 2020/07/25
 */
public class ButlerUtils {


    public static String prettyPrintTime(long duration) {
        StringBuilder builder = new StringBuilder();

        int days = (int) (duration / 86400000); //1.000 * 60 * 60 * 24
        int hours = (int) (duration / 3600000) % 24; //1.000 * 60 * 60
        int minutes = (int) (duration / 60000) % 60; //1.000 * 60;
        int seconds = (int) (duration / 1000) % 60; //1.000 * 60;

        if (days > 0) {
            builder.append(String.format("%02d %s, ", days, days == 1 ? "day" : "days"));
        }
        if (hours > 0) {
            builder.append(String.format("%02d %s, ", hours, hours == 1 ? "hour" : "hours"));
        }
        if (minutes > 0) {
            builder.append(String.format("%02d %s, ", minutes, minutes == 1 ? "minute" : "minutes"));
        }
        if (seconds > 0) {
            builder.append(String.format("%02d %s, ", seconds, seconds == 1 ? "second" : "seconds"));
        }
        return builder.substring(0, builder.length() - 2);
    }

    public static String buildShortCommandInfo(ButlerCommandInformation info, int maxCommandLength, int maxDescLength) {

        String desc = trimString(info.getDescription(), maxDescLength);

        return String.format(String.format("`%%-%ds`: `%%-%ds`\n", maxCommandLength, maxDescLength),
                info.getName(),
                desc);
    }

    public static String buildShortCommandInfo(ButlerCommandInformation info) {
        return buildShortCommandInfo(info, 16, 50);
    }

    public static String trimString(String input, int maxLength) {
        return input.length() > maxLength ?
                input.substring(0, maxLength - 4) + " ..." :
                input;
    }

    public static MessageEmbed.Field buildEmbedCommandInfo(ButlerCommandInformation info, int maxDescLength, boolean inline) {
        return new MessageEmbed.Field(info.getName(), trimString(info.getDescription(), maxDescLength), inline);
    }

    public static MessageEmbed.Field buildEmbedCommandInfo(ButlerCommandInformation info) {
        return buildEmbedCommandInfo(info, 50, true);
    }
}
