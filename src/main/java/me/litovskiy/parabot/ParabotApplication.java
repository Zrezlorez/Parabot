package me.litovskiy.parabot;

import me.litovskiy.parabot.commands.CommandManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class ParabotApplication {

    private static CommandManager commandManager;

    public static void main(String[] args) {

        SpringApplication.run(ParabotApplication.class, args);
        for (var c : commandManager.commands) {
            System.out.println(c.getCommand());
        }
    }

    @Autowired
    private void setCommandManager(CommandManager commandManager) {
        ParabotApplication.commandManager = commandManager;
    }
}
