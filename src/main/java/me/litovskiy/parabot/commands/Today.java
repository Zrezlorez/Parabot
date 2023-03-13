package me.litovskiy.parabot.commands;

import me.litovskiy.parabot.model.User;
import me.litovskiy.parabot.util.StringWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Today extends AbstractCommand {
    @Autowired
    private StringWorker stringWorker;

    public Today() {
        super("сегодня");
    }

    @Override
    public String execute(User user) {
        return stringWorker.getLessonsByDay(user.getGroup(), LocalDate.now().getDayOfWeek().getValue() - 1);
    }
}
