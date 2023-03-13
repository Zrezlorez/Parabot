package me.litovskiy.parabot.service.interfaces;

import me.litovskiy.parabot.model.Group;

import java.util.List;

public interface GroupService {
    void add(Group group);

    boolean update(Long id, Group group);

    Group findById(Long id);

    List<Group> findAll();

    boolean deleteById(Long id);
    Group getByName(String name);
}
