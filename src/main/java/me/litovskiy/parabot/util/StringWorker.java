package me.litovskiy.parabot.util;

import lombok.RequiredArgsConstructor;
import me.litovskiy.parabot.model.Group;
import me.litovskiy.parabot.model.Lesson;
import me.litovskiy.parabot.service.interfaces.LessonService;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class StringWorker {
    private final LessonService lessonService;

    public String getLessonsByDay(Group group, int day) {
        StringBuilder result = new StringBuilder();
        List<Lesson> info = lessonService.getByDay(day, getNumerator(), group);
        for (Lesson lesson : info) {
            addLesson(result, lesson);
        }
        return result.toString();
    }

    private void addLesson(StringBuilder result, Lesson lesson) {
        result.append(String.format("%s - %s: %s\n\n",
                lesson.getStartTime(),
                lesson.getEndTime(),
                lesson.getName()));
    }

    private boolean getNumerator() {
        LocalDateTime date2 = LocalDate.now().atStartOfDay();
        return Duration.between(LocalDate.of(2022, 9, 5).atStartOfDay(), date2).toDays() / 7 % 2 != 0;
    }
}
