package com.niklasarndt.discordbutler.modules.core.command;

import com.niklasarndt.discordbutler.modules.ButlerCommand;
import com.niklasarndt.discordbutler.modules.ButlerContext;
import com.niklasarndt.discordbutler.util.ButlerUtils;
import java.util.Arrays;

/**
 * Created by Niklas on 2020/08/01.
 */
public class RemindCommand extends ButlerCommand {

    public RemindCommand() {
        super("remind", 2, Integer.MAX_VALUE,
                "Be reminded in a certain time. Syntax: " +
                        "remind <days>d<hours>h<minutes>m<seconds>s <message>. " +
                        "Leave out some values for the duration if " +
                        "these should be zero anyway (e.g. days).");
    }

    @Override
    public void execute(ButlerContext context) {
        long duration = ButlerUtils.parseTimeString(context.args()[0]);

        if (duration <= 0) {
            context.resultBuilder()
                    .error("Invalid duration: %s. Please make sure that the format " +
                            "is correct and you specified a positive duration. " +
                            "Use \"help remind\" for more details.");
            return;
        }

        String message = String.join(" ",
                Arrays.copyOfRange(context.args(), 1, context.args().length));
        context.instance().getScheduleManager().scheduleMessage(message, duration);
        context.resultBuilder().success("You will be reminded in %s.",
                ButlerUtils.prettyPrintTime(duration));
    }
}
