package ru.job4j.cars.mapper;

import org.mapstruct.Mapper;
import ru.job4j.cars.dto.UserCreateEditDto;
import ru.job4j.cars.dto.UserReadDto;
import ru.job4j.cars.model.User;

/**
 * Класс реализация отображений объектов DTO в пользователя
 * и пользователя в объекты DTO
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Выполняет преобразование объекта пользователя в UserReadDto.
     *
     * @param user пользователь
     * @return userDto объект DTO
     */
    UserReadDto getUserReadDtoFromUser(User user);

    /**
     * Выполняет преобразование объекта UserReadDto в пользователя.
     *
     * @param userDto объект DTO
     * @return user пользователь
     */
    User getUserFromUserCreateEditDto(UserCreateEditDto userDto);

    /**
     * Выполняет обновление объекта пользователя на основе объекта UserReadDto.
     *
     * @param fromObject объект DTO
     * @param toObject пользователь
     * @return user пользователь
     */
    default User updateUserFromUserCreateEditDto(UserCreateEditDto fromObject, User toObject) {
        toObject.setUsername(fromObject.getUsername());
        toObject.setEmail(fromObject.getEmail());
        toObject.setPassword(fromObject.getPassword());
        toObject.setPhonenumber(fromObject.getPhonenumber());
        return toObject;
    }
}
