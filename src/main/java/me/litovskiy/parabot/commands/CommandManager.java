package me.litovskiy.parabot.commands;

import me.litovskiy.parabot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandManager {
    @Autowired
    public final List<AbstractCommand> commands = new ArrayList<>();

    public String execute(String input, User user) {
        for (AbstractCommand command : commands)
            if (command.getCommand().equals(input))
                return command.execute(user);

        return "error";
    }
}
