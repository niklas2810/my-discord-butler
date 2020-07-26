package com.niklasarndt.discordbutler.modules.core.command;

import com.niklasarndt.discordbutler.modules.ButlerCommand;
import com.niklasarndt.discordbutler.modules.ButlerContext;
import com.niklasarndt.discordbutler.util.BuildInfo;
import net.dv8tion.jda.api.EmbedBuilder;

/**
 * Created by Niklas on 2020/07/26
 */
public class VersionCommand extends ButlerCommand {

    public VersionCommand() {
        super("version", 0, 0, "Displays some info about the current build.");
    }

    @Override
    public void execute(ButlerContext context) {
        EmbedBuilder embed = context.resultBuilder().useEmbed();
        embed.setTitle(String.format("%s v%s", BuildInfo.NAME, BuildInfo.VERSION));
        embed.addField("Build Timestamp", BuildInfo.TIMESTAMP, false);
        embed.addField("Target JDK", BuildInfo.TARGET_JDK, true);
        embed.addField("Repository URL", BuildInfo.URL, true);
    }
}
