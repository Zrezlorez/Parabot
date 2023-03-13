package me.litovskiy.parabot.service;

import lombok.RequiredArgsConstructor;
import me.litovskiy.parabot.model.Group;
import me.litovskiy.parabot.repository.GroupRepository;
import me.litovskiy.parabot.service.interfaces.GroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(value = "groupService")
@RequiredArgsConstructor
public class GroupServiceImp implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public void add(Group group) {
        groupRepository.save(group);
    }

    @Override
    public boolean update(Long id, Group group) {
        if (groupRepository.findById(id).isPresent()) {
            group.setId(id);
            groupRepository.save(group);
            return true;
        }
        return false;
    }

    @Override
    public Group findById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public boolean deleteById(Long id) {
        if (groupRepository.findById(id).isPresent()) {
            groupRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Group getByName(String name) {
        return groupRepository.getByName(name.toUpperCase());
    }
}
