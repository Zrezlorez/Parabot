package me.litovskiy.parabot.bots;

import me.litovskiy.parabot.commands.CommandManager;
import me.litovskiy.parabot.model.User;
import me.litovskiy.parabot.service.interfaces.GroupService;
import me.litovskiy.parabot.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractBot {
    abstract void send(String mes, long userId);

    abstract void send(String mes, int messageId, long userId, String[]... lines);

    abstract String getName(long userId);

    @Autowired
    CommandManager commandManager;
    @Autowired
    UserService userService;
    @Autowired
    GroupService groupService;

    public void bot(String mes, int messageId, long userId) {
        if (mes == null) return;
        mes = mes.toLowerCase();
        User user = userService.findById(userId);
        String[] line1 = {"Сегодня", "Завтра", "Неделя"};
        String[] line2 = {"Сбросить", "Донат", "Удалить"};

        // обработка регистрации пользователя
        if (user == null) {
            user = new User()
                    .setUserId(userId);
            userService.add(user);
        }
        if (user.getGroup() == null) {
            send(changeGroup(mes, userId, user), messageId, userId, line1, line2);
            return;
        }


        // обработка команд
        String result = commandManager.execute(mes, user);
        if (result == null) return;
        if (result.isEmpty()) {
            send("По вашему запросу нет пар", messageId, userId, line1, line2);
            return;
        }
        send(result, messageId, userId, line1, line2);
    }

    private String changeGroup(String message, long userId, User user) {
        if (!message.isEmpty() && groupService
                .findAll()
                .stream()
                .anyMatch(g -> g.getName().contains(message.replace("\\s", "")))) {

            userService.update(userId, user.setGroup(groupService.getByName(message.replace("\\s", ""))));
            return "Ваша группа установлена";
        }
        return "Перед использованием бота, вам нужно ввести свою группу (например: ис211)";
    }
}
