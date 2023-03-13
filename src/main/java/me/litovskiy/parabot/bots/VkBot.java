package me.litovskiy.parabot.bots;

import api.longpoll.bots.LongPollBot;
import api.longpoll.bots.model.events.messages.MessageNew;
import api.longpoll.bots.model.objects.additional.Keyboard;
import api.longpoll.bots.model.objects.additional.buttons.Button;
import api.longpoll.bots.model.objects.additional.buttons.TextButton;
import api.longpoll.bots.model.objects.basic.Message;
import api.longpoll.bots.model.objects.basic.User;
import lombok.SneakyThrows;
import me.litovskiy.parabot.commands.CommandManager;
import me.litovskiy.parabot.service.interfaces.GroupService;
import me.litovskiy.parabot.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class VkBot extends LongPollBot implements AbstractBot{

    private final CommandManager commandManager;
    private final UserService userService;
    private final GroupService groupService;
    private final Random RANDOM;

    @Autowired
    public VkBot(CommandManager commandManager, UserService userService, GroupService groupService) {
        this.commandManager = commandManager;
        this.userService = userService;
        this.groupService = groupService;
        RANDOM = new Random();
    }

    @SneakyThrows
    @Override
    public void run() {
        this.startPolling();
    }


    @SneakyThrows
    @Override
    public void onMessageNew(MessageNew messageNew) {
        Message message = messageNew.getMessage();
        if(message.hasText()) {
            bot(message.getText(), message.getConversationMessageId(), message.getPeerId());
        }
    }

    @Override
    public String getAccessToken() {
        String testGroup = "vk1.a.Pka1R03PDVhnW8YRw6IAecvMLCYehLqZuapGYTUMzy-fBO8Dpx_3ilh-Xz20MSFerL9MctIuuWjUjkGhgJ7_4ZXfQrdllcjnS4z-ISd0jsiLHsJ60DQzaCpjGHXPh8f-T91mybW6X6cgR0Gc22DQQ2De8bThfox3NalzkFrSWAvHjbmatKa_XbP4FWza6r-I";
        String mainGroup = "vk1.a.9AQs2ozEmJPOItH-rRjTytJRVmgJkoWkeAWHzVZWUXubclk4B-InLeqbEBYKrYVu44__jISNwYGyKCruFl9TBPF3tGDXAnRZm-YQkBLndKRcI7_wKZR6LlFNcY2N_i5A7z2kEuTpnzqhlJ2bMya1XNlfXoBWv9Y3Aw8cuKJXUMeoHcQcBz_1D6OgmjOYM7qd";
        return testGroup;
    }
    @SneakyThrows
    @Override
    public void send(String mes, long userId) {
        vk.messages
                .send()
                .setMessage(mes)
                .setPeerId((int)userId)
                .setRandomId(RANDOM.nextInt(10000))
                .execute();
    }
    @SneakyThrows
    @Override
    public void send(String mes, int messageId, long userId, String[]... lines) {
        vk.messages
                .send()
                .setMessage(mes)
                .setPeerId((int)userId)
                .setRandomId(RANDOM.nextInt(10000))
                .setKeyboard(createKeyboard(lines[0], lines[1]))
                .execute();
    }

    public Keyboard createKeyboard(String[] line1, String[] line2) {
        List<List<Button>> allKey = new ArrayList<>();
        List<Button> listLine1 = new ArrayList<>();
        List<Button> listLine2 = new ArrayList<>();
        for(var z: line1)
            listLine1.add(new TextButton(Button.Color.PRIMARY, new TextButton.Action(z)));
        for(var z: line2)
            listLine2.add(new TextButton(Button.Color.PRIMARY, new TextButton.Action(z)));
        allKey.add(listLine1);
        allKey.add(listLine2);
        if(line1[0].equals("0"))
            return new Keyboard(new ArrayList<>());
        return new Keyboard(allKey);
    }

    @SneakyThrows
    @Override
    public String getName(long userId) {
        if (userId >= 2000000000 && userId < 2000001000)
            return vk.messages
                    .getConversationsById()
                    .setPeerIds((int)userId)
                    .execute().getResponse()
                    .getItems()
                    .get(0)
                    .getChatSettings()
                    .getTitle();
        User user = vk.users
                .get()
                .setUserIds(String.valueOf(userId))
                .execute()
                .getResponse()
                .get(0);
        return user.getFirstName() + " " + user.getLastName();
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
