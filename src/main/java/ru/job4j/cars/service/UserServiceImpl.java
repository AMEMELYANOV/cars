package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.UserRepository;

/**
 * Реализация сервиса по работе с пользователями
 * @see ru.job4j.cars.service.UserService
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Объект для доступа к методам UserRepository
     */
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Выполняет поиск и возврат пользователя по адресу электронной
     * почты пользователя.
     *
     * @param email адрес электронной почты пользователя
     * @return user пользователь
     */
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    /**
     * Выполняет сохранение пользователя. Возвращает
     * пользователя с проинициализированным идентификатором.
     *
     * @param user пользователь
     * @return user пользователь с проинициализированным идентификатором
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Выполняет обновление и возвращение пользователя.
     *
     * @param user пользователь
     * @return user обновленный пользователь
     */
    @Override
    public User update(User user) {
        return userRepository.update(user);
    }

    /**
     * Выполняет поиск и возврат пользователя идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return user пользователь
     */
    @Override
    public User findUserById(Integer userId) {
        return userRepository.findUserById(userId);
    }
}
