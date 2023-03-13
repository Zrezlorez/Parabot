package me.litovskiy.parabot;

import me.litovskiy.parabot.bots.TgBot;
import me.litovskiy.parabot.bots.VkBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@SpringBootApplication
@EnableJpaRepositories
public class ParabotApplication {

    private static TgBot tgBot;
    private static VkBot vkBot;
    public static void main(String[] args) {

        SpringApplication.run(ParabotApplication.class, args);
        System.out.println("Bot enabled!");

        TimeZone.setDefault(TimeZone.getTimeZone("GMT+3"));
        ExecutorService tgThread = Executors.newSingleThreadExecutor();
        ExecutorService vkThread = Executors.newSingleThreadExecutor();
        tgThread.execute(tgBot::run);
        vkThread.execute(vkBot::run);
    }
    @Autowired
    private void setTgBot(TgBot tgBot) {
        ParabotApplication.tgBot = tgBot;
    }
    @Autowired
    public void setVkBot(VkBot vkBot) {
        ParabotApplication.vkBot = vkBot;
    }

}
