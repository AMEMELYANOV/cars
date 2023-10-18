package ru.job4j.cars.service;

import ru.job4j.cars.dto.UserCreateEditDto;
import ru.job4j.cars.dto.UserReadDto;

/**
 * Реализация сервиса по работе с пользователями
 * @see ru.job4j.cars.model.User
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface UserService {

    /**
     * Выполняет поиск и возврат пользователя по адресу электронной
     * почты пользователя.
     *
     * @param email адрес электронной почты пользователя
     * @return DTO объект пользователя
     */
    UserReadDto findUserByEmail(String email);

    /**
     * Выполняет сохранение пользователя. Возвращает
     * пользователя с проинициализированным идентификатором.
     *
     * @param userDto пользователь
     * @return user пользователь с проинициализированным идентификатором
     */
    UserReadDto save(UserCreateEditDto userDto);

    /**
     * Выполняет обновление и возвращение пользователя.
     *
     * @param userDto пользователь
     * @return DTO объект обновленного пользователя
     */
    UserReadDto update(UserCreateEditDto userDto);

    /**
     * Выполняет поиск и возврат пользователя идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return DTO объект пользователя
     */
    UserReadDto findUserById(Integer userId);
}
