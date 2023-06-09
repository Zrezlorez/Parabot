package me.litovskiy.parabot.repository;

import me.litovskiy.parabot.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Group getByName(String name);
}
