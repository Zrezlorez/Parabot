package me.litovskiy.parabot.repository;

import me.litovskiy.parabot.model.Group;
import me.litovskiy.parabot.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    // найти на день
    List<Lesson> getAllByDayAndNumeratorAndGroup(int day, boolean numerator, Group group);

    // найти на всю неделю
    List<Lesson> getAllByNumeratorAndGroup(boolean numerator, Group group);

}
