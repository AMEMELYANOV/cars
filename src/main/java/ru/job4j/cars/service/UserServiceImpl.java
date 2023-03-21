package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User add(User user) {
        return userRepository.add(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByUserEmail(email);
    }

    @Override
    public User findUserById(Integer userId) {
        return userRepository.findUserByUserId(userId);
    }

    @Override
    public User update(User user) {
        return userRepository.update(user);
    }
}
