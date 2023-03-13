package me.litovskiy.parabot.commands;

import me.litovskiy.parabot.model.User;
import me.litovskiy.parabot.util.StringWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class Week extends AbstractCommand {
    @Autowired
    private StringWorker stringWorker;
    public Week() {
        super("неделя");
    }

    @Override
    public String execute(User user) {
        return stringWorker.getLessonsOnWeek(user.getGroup());
    }
}
