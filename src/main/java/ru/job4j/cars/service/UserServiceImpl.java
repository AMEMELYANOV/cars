package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.UserCreateEditDto;
import ru.job4j.cars.dto.UserReadDto;
import ru.job4j.cars.mapper.UserMapper;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.UserRepository;

/**
 * Реализация сервиса по работе с пользователями
 * @see ru.job4j.cars.service.UserService
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    /**
     * Объект для доступа к методам UserRepository
     */
    private final UserRepository userRepository;

    /**
     * Объект для доступа к методам UserMapper
     */
    private final UserMapper userMapper;

    /**
     * Выполняет поиск и возврат пользователя по адресу электронной
     * почты пользователя.
     *
     * @param email адрес электронной почты пользователя
     * @return DTO объект пользователя
     */
    @Override
    public UserReadDto findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        return userMapper.getUserReadDtoFromUser(user);
    }

    /**
     * Выполняет сохранение пользователя. Возвращает
     * пользователя с проинициализированным идентификатором.
     *
     * @param userDto пользователь
     * @return DTO объект пользователя с проинициализированным идентификатором
     */
    @Override
    public UserReadDto save(UserCreateEditDto userDto) {
        User user = userMapper.getUserFromUserCreateEditDto(userDto);
        user.setActive(true);
        userRepository.save(user);
        return userMapper.getUserReadDtoFromUser(user);
    }

    /**
     * Выполняет обновление и возвращение пользователя.
     *
     * @param userDto пользователь
     * @return DTO объект обновленного пользователя
     */
    @Override
    public UserReadDto update(UserCreateEditDto userDto) {
        User user = userRepository.findUserByEmail(userDto.getEmail());
        user = userMapper.updateUserFromUserCreateEditDto(userDto, user);
        user = userRepository.update(user);
        return userMapper.getUserReadDtoFromUser(user);
    }

    /**
     * Выполняет поиск и возврат пользователя идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return DTO объект пользователя
     */
    @Override
    public UserReadDto findUserById(Integer userId) {
        User user = userRepository.findUserById(userId);
        return userMapper.getUserReadDtoFromUser(user);
    }
}
