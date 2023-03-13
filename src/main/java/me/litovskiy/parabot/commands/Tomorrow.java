package me.litovskiy.parabot.commands;

import me.litovskiy.parabot.model.User;
import me.litovskiy.parabot.util.StringWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Tomorrow extends AbstractCommand {
    @Autowired
    private StringWorker stringWorker;

    public Tomorrow() {
        super("завтра");
    }


    @Override
    public String execute(User user) {
        int today = LocalDate.now().getDayOfWeek().getValue() - 1;
        if (today == 5)
            return "Завтра воскресенье, пар нет";
        if (today == 6)
            return stringWorker.getLessonsByDay(user.getGroup(), 0);
        return stringWorker.getLessonsByDay(user.getGroup(), today + 1);
    }
}
