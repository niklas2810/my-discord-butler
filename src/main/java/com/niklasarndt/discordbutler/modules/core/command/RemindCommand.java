package com.niklasarndt.discordbutler.modules.core.command;

import com.niklasarndt.discordbutler.modules.ButlerCommand;
import com.niklasarndt.discordbutler.modules.ButlerContext;
import com.niklasarndt.discordbutler.scheduler.ScheduleManager;
import com.niklasarndt.discordbutler.scheduler.ScheduledTask;
import com.niklasarndt.discordbutler.util.ButlerUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Niklas on 2020/08/01.
 */
public class RemindCommand extends ButlerCommand {

    public RemindCommand() {
        super("remind", 0, Integer.MAX_VALUE,
                "Be reminded in a certain time. Type \"remind\" to receive a list" +
                        "of pending reminders. Syntax: " +
                        "remind <days>d<hours>h<minutes>m<seconds>s <message>. " +
                        "Leave out some values for the duration if " +
                        "these should be zero anyway (e.g. days).");
    }

    @Override
    public void execute(ButlerContext context) {
        if (context.args().length == 0) {
            displayOverview(context);
        } else if (context.args().length == 1) {
            context.resultBuilder().error("Please specify what the bot should remind" +
                    "you of!");
        } else {
            createReminder(context);
        }
    }

    private void createReminder(ButlerContext context) {
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

    private void displayOverview(ButlerContext context) {
        StringBuilder builder = new StringBuilder("Pending reminders:\n\n");
        List<ScheduledTask> tasks = context.instance().getScheduleManager().getScheduledTasks().stream()
                .filter(i -> i.getName().equals(ScheduleManager.MESSAGE_REMINDER_NAME))
                .collect(Collectors.toList());
        for (ScheduledTask task : tasks) {
            builder.append(String.format("Reminder **#%s**: In %s.\n", task.getFancyIndex(),
                    task.getFancyTimeUntilExecution()));
        }
        if (tasks.size() == 0) {
            builder.append("There are **no scheduled reminders**.\n");
        }

        List<ScheduledTask> failedTasks = context.instance().getScheduleManager().getFailedTasks(true).stream()
                .filter(i -> i.getName().equals(ScheduleManager.MESSAGE_REMINDER_NAME))
                .collect(Collectors.toList());

        if (failedTasks.size() > 0) {
            builder.append("\n\nFailed reminders:");

            for (ScheduledTask task : tasks) {
                builder.append(String.format("Reminder **#%s**: %s ago.\n", task.getFancyIndex(),
                        task.getTimeSinceExecution()));
            }
        }
        context.resultBuilder().success(builder.toString().trim());
    }
}
