package me.litovskiy.parabot.bots;

import me.litovskiy.parabot.commands.CommandManager;
import me.litovskiy.parabot.model.User;
import me.litovskiy.parabot.service.interfaces.GroupService;
import me.litovskiy.parabot.service.interfaces.UserService;
import org.springframework.stereotype.Component;

@Component
public interface AbstractBot {
    void run();
    void send(String mes, long userId);
    void send(String mes, int messageId, long userId, String[]... lines);
    String getName(long userId);
    CommandManager getCommandManager();
    UserService getUserService();
    GroupService getGroupService();

    default void bot(String mes, int messageId, long userId) {
        if (mes == null) return;
        mes = mes.toLowerCase()
                .replace("[club216410844|@parabots] ", "")
                .replace("/", "")
                .trim();
        User user = getUserService().findByUserId(userId);
        String[] line1 = {"Сегодня", "Завтра", "Неделя"};
        String[] line2 = {"Сбросить", "Донат", "Удалить"};

        // обработка регистрации пользователя
        if (user == null) {
            user = new User().setUserId(userId).setName(getName(userId));
            getUserService().add(user);
        }

        if (user.getGroup() == null) {
            send(changeGroup(mes, user), messageId, userId, line1, line2);
            return;
        }
        // обработка команд
        String result = getCommandManager().execute(mes, user);
        if (result == null) return;
        user.setMessageCount(user.getMessageCount()+1);
        if (result.isEmpty()) {
            send("По вашему запросу нет пар", messageId, userId, line1, line2);
            return;
        }
        send(result, messageId, userId, line1, line2);
    }

    private String changeGroup(String message, User user) {
        if (!message.isEmpty() && getGroupService()
                .findAll()
                .stream()
                .anyMatch(g -> g.getName().toLowerCase().contains(message.replace("\\s", "")))) {

            getUserService().update(user.getId(), user.setGroup(getGroupService().getByName(message.replace("\\s", ""))));
            return "Ваша группа установлена";
        }
        return "Перед использованием бота, вам нужно ввести свою группу (например: ис211)";
    }
}