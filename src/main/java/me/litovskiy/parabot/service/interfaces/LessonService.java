package me.litovskiy.parabot.service.interfaces;

import me.litovskiy.parabot.model.Group;
import me.litovskiy.parabot.model.Lesson;

import java.util.List;

public interface LessonService {
    void add(Lesson lesson);

    boolean update(Long id, Lesson Lesson);

    Lesson findById(Long id);

    List<Lesson> findAll();

    boolean deleteById(Long id);

    List<Lesson> getByDay(int day, boolean numerator, Group group);

    List<Lesson> getAllWeek(boolean numerator, Group group);
}
