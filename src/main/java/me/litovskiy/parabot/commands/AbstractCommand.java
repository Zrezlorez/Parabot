package me.litovskiy.parabot.commands;

import lombok.Getter;
import me.litovskiy.parabot.model.User;

public abstract class AbstractCommand {
    @Getter
    private final String command;

    public AbstractCommand(String command) {
        this.command = command;
    }

    public abstract String execute(User user);
}
