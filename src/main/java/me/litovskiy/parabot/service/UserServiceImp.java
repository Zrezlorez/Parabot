package me.litovskiy.parabot.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import me.litovskiy.parabot.model.Group;
import me.litovskiy.parabot.model.User;
import me.litovskiy.parabot.repository.GroupRepository;
import me.litovskiy.parabot.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(value = "userService")
@RequiredArgsConstructor
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    @Override
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean update(Long id, User user) {
        if (userRepository.findById(id).isPresent()) {
            user.setId(id);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean deleteById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @PostConstruct
    public void fillDataBase() {
        findAll().forEach(user -> deleteById(user.getId()));
        groupRepository.save(new Group().setName("ИС214"));

        add(new User()
                .setUserId(123123123)
                .setGroup(groupRepository.findByName("ИС214")));
    }

}
