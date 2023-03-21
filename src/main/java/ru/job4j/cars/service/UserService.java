package ru.job4j.cars.service;

import ru.job4j.cars.model.User;

public interface UserService {

    User add(User user);

    User findUserByEmail(String email);

    User findUserById(Integer userId);

    User update(User user);
}
