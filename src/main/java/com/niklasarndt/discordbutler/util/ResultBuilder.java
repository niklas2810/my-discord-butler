package com.niklasarndt.discordbutler.util;

import com.niklasarndt.discordbutler.enums.ResultType;
import com.niklasarndt.discordbutler.modules.ButlerModuleInformation;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;

/**
 * Created by Niklas on 2020/07/25
 */
public class ResultBuilder {

    public static final ResultBuilder NOT_FOUND = new ResultBuilder(null, ResultType.NOT_FOUND, null);
    private final ButlerModuleInformation currentModule;

    private ResultType type = ResultType.SUCCESS;
    private String output;

    private boolean usesEmbed = false;
    private EmbedBuilder embedBuilder;

    public ResultBuilder(ButlerModuleInformation currentModule) {
        this.currentModule = currentModule;
    }

    public ResultBuilder(ButlerModuleInformation currentModule, ResultType type, String output) {
        this.currentModule = currentModule;
        this.type = type;
        this.output = output;
    }

    public ButlerModuleInformation getCurrentModule() {
        return currentModule;
    }

    public ResultType getType() {
        return type;
    }

    public void setType(ResultType type) {
        this.type = type;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setOutput(ResultType type, String output) {
        this.type = type;
        this.output = output;
    }

    public void invalidArgsLength(int min, int max, int actual) {
        type = ResultType.ERROR;
        if (min == max || min == 0) {
            output = String.format("This command does not accept parameters (your provided %d).", actual);
        } else {
            output = String.format("This command only accepts %d-%d parameters (you provided %d).", min, max, actual);
        }
    }

    public boolean hasOutput() {
        return (output != null && output.length() > 0) || usesEmbed && !embedBuilder.isEmpty();
    }

    public EmbedBuilder useEmbed() {
        usesEmbed = true;
        if (embedBuilder == null) embedBuilder = new EmbedBuilder();
        return embedBuilder;
    }

    public String produceString(boolean addEmoji) {
        return (addEmoji ? type.emoji + " " : "") + (usesEmbed ? "" : getOutput());
    }

    public String produceString() {
        return produceString(true);
    }

    public MessageEmbed produceEmbed() {
        return usesEmbed ? embedBuilder.build() : null;
    }

    public void produceIntoChannel(MessageChannel channel) {
        if (usesEmbed) {
            MessageEmbed result = produceEmbed();
            if (!result.isSendable()) {
                throw new RuntimeException(
                        String.format("The produced result is too long to be sent (length: %s, max allowed: %s).",
                                result.getLength(), MessageEmbed.EMBED_MAX_LENGTH_BOT));
            } else channel.sendMessage(produceEmbed()).queue();
        } else channel.sendMessage(produceString()).queue();
    }

    public void error(String output) {
        type = ResultType.ERROR;
        this.output = output;
    }

    public void error(Exception e) {
        error(String.format("The command could not be executed. Reason: **%s** - %s",
                e.getClass().getSimpleName(), e.getMessage()));
    }

    public void notFound() {
        type = ResultType.NOT_FOUND;
    }

    public void notFound(String output) {
        notFound();
        setOutput(output);
    }
}
