package me.litovskiy.parabot.service.interfaces;

import me.litovskiy.parabot.model.User;

import java.util.List;

public interface UserService {

    void add(User user);

    boolean update(Long id, User user);

    User findById(Long id);

    List<User> findAll();

    boolean deleteById(Long id);

}