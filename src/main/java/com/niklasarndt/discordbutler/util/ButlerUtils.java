package com.niklasarndt.discordbutler.util;

import com.niklasarndt.discordbutler.modules.ButlerCommandInformation;
import net.dv8tion.jda.api.entities.MessageEmbed;

/**
 * Created by Niklas on 2020/07/25.
 */
public class ButlerUtils {

    private ButlerUtils() {
    }

    public static String prettyPrintTime(long ms) {
        StringBuilder builder = new StringBuilder();

        int days = (int) (ms / 86400000); //1.000 * 60 * 60 * 24
        appendTime(builder, days, "day");

        int hours = (int) (ms / 3600000) % 24; //1.000 * 60 * 60
        appendTime(builder, hours, "hour");

        int minutes = (int) (ms / 60000) % 60; //1.000 * 60;
        appendTime(builder, minutes, "minute");

        int seconds = (int) (ms / 1000) % 60; //1.000 * 60;
        appendTime(builder, seconds, "second");

        return builder.substring(0, builder.length() - 2);
    }

    private static void appendTime(StringBuilder builder, int num, String singular) {
        if (num > 0) builder.append(timeDisplay(num, singular));
    }

    private static String timeDisplay(int num, String singular) {
        return String.format("%02d %s, ", num, num == 1 ? singular : singular + "s");
    }

    public static String buildShortCommandInfo(ButlerCommandInformation info, int maxCommandLength,
                                               int maxDescLength) {

        String desc = trimString(info.getDescription(), maxDescLength);

        return String.format(String.format("`%%-%ds`: `%%-%ds`", maxCommandLength, maxDescLength),
                info.getName(),
                desc);
    }

    public static String buildShortCommandInfo(ButlerCommandInformation info) {
        return buildShortCommandInfo(info, 16, 50);
    }

    public static String trimString(String input, int maxLength) {
        if (maxLength < 1) throw new IllegalArgumentException(
                "The maximum string length must be larger than zero.");

        if (maxLength < 4) {
            return ".".repeat(maxLength);
        }

        return input.length() > maxLength ?
                input.substring(0, maxLength - 3) + "..." :
                input;
    }

    public static MessageEmbed.Field buildEmbedCommandInfo(
            ButlerCommandInformation info, int maxDescLength, boolean inline) {
        return new MessageEmbed.Field(info.getName(),
                trimString(info.getDescription(), maxDescLength), inline);
    }

    public static MessageEmbed.Field buildEmbedCommandInfo(ButlerCommandInformation info) {
        return buildEmbedCommandInfo(info, 50, true);
    }

    public static int parseInt(String input, int defaultValue) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
