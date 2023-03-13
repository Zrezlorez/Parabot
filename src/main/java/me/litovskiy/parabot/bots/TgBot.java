package me.litovskiy.parabot.bots;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.GetChat;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.SneakyThrows;
import me.litovskiy.parabot.commands.CommandManager;
import me.litovskiy.parabot.service.interfaces.GroupService;
import me.litovskiy.parabot.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TgBot implements AbstractBot {
    private final CommandManager commandManager;
    private final UserService userService;
    private final GroupService groupService;
    public final TelegramBot bot;

    @Autowired
    public TgBot(CommandManager commandManager, UserService userService, GroupService groupService) {
        this.commandManager = commandManager;
        this.userService = userService;
        this.groupService = groupService;
        bot = new TelegramBot(getToken());
    }

    @Override
    public void run(){
        bot.setUpdatesListener(updates -> {
            updates.forEach(this::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void process(Update update) {
        Message message = update.message();
        if(message==null) return;
        //if(message.chat().id()!=1827409284)
        //send(String.format("%s написал %s", getName(message.chat().id()), message.text()), 1827409284);
        bot(message.text(), message.messageId(), message.chat().id());
    }

    private static String getToken() {
        String testGroup =  "1116496780:AAH8HZ8kDNoSQW3LNXKM8ladh434hCJfEls";
        String mainGroup = "5524044091:AAHe9Wt7GHqfjf4_mEVKG-WbaDcIEUkF2IY";
        return testGroup;

    }
    @Override
    public void send(String mes, long userId) {
        BaseRequest<SendMessage, SendResponse> request = new SendMessage(userId, mes);
        bot.execute(request);
    }

    @Override
    public void send(String mes, int messageId, long userId, String[]... lines) {
        BaseRequest<SendMessage, SendResponse> request = new SendMessage(userId, mes).
                replyToMessageId(messageId).
                replyMarkup(createKeyboard(lines[0], lines[1]));
        bot.execute(request);
    }

    public Keyboard createKeyboard(String[] line1, String[] line2) {
        for (int i=0; i< line1.length; i++) {
            line1[i] = "/" + line1[i];
        }
        for (int i=0; i< line2.length; i++) {
            line2[i] = "/" + line2[i];
        }
        if(line1[0].equals("/0")) {
            return new ReplyKeyboardRemove(true);
        }
        return new ReplyKeyboardMarkup(line1, line2)
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true);
    }
    @SneakyThrows
    @Override
    public String getName(long userId) {
        var result = bot.execute(new GetChat(userId));
        if (result.chat().username()!=null)
            return result.chat().username();
        if (result.chat().title()!=null)
            return "беседа тг: " + result.chat().title();
        return "тг: " +
                result.chat().firstName() +
                " " +
                (result.chat().lastName()==null ? "" : result.chat().lastName());
    }

    @Override
    public CommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public UserService getUserService() {
        return userService;
    }

    @Override
    public GroupService getGroupService() {
        return groupService;
    }
}
