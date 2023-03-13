package me.litovskiy.parabot.service;

import lombok.RequiredArgsConstructor;
import me.litovskiy.parabot.model.Group;
import me.litovskiy.parabot.model.Lesson;
import me.litovskiy.parabot.repository.LessonRepository;
import me.litovskiy.parabot.service.interfaces.GroupService;
import me.litovskiy.parabot.service.interfaces.LessonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Transactional
@Service(value = "lessonService")
@RequiredArgsConstructor
public class LessonServiceImp implements LessonService {
    private final LessonRepository lessonRepository;
    private final GroupService groupService;
    @Override
    public void add(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    @Override
    public boolean update(Long id, Lesson Lesson) {
        if (lessonRepository.findById(id).isPresent()) {
            Lesson.setId(id);
            lessonRepository.save(Lesson);
            return true;
        }
        return false;
    }

    @Override
    public Lesson findById(Long id) {
        return lessonRepository.findById(id).orElse(null);
    }

    @Override
    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    @Override
    public boolean deleteById(Long id) {
        if (lessonRepository.findById(id).isPresent()) {
            lessonRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Lesson> getByDay(int day, boolean numerator, Group group) {
        return lessonRepository.getAllByDayAndNumeratorAndGroup(day, numerator, group);
    }

    @Override
    public List<Lesson> getAllWeek(boolean numerator, Group group) {
        return lessonRepository.getAllByNumeratorAndGroup(numerator, group);
    }

    @PostConstruct
    public void fillDataBase() {
//        add(new Lesson()
//                .setDay(0)
//                .setName("подготовка лошков")
//                .setStartTime(new Time(10, 30, 0))
//                .setEndTime(new Time(11, 40, 0))
//                .setNumerator(true)
//                .setGroup(groupService.getByName("ИС214")));
    }
}
