package ru.job4j.cars.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.job4j.cars.dto.UserCreateEditDto;
import ru.job4j.cars.dto.UserReadDto;
import ru.job4j.cars.mapper.UserMapper;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.HibernateUserRepository;
import ru.job4j.cars.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

/**
 * Тест класс реализации сервисов
 * @see ru.job4j.cars.service.UserService
 * @author Alexander Emelyanov
 * @version 1.0
 */
class UserServiceImplTest {

    /**
     Моск объекта UserRepository
     */
    private UserRepository userRepository;

    /**
     * Объект для доступа к методам UserService
     */
    private UserService userService;

    /**
     * Объект для доступа к методам UserMapper
     */
    private UserMapper userMapper;

    /**
     * Пользователь
     */
    private User user;

    /**
     * Объект DTO пользователя
     */
    private UserCreateEditDto userCreateEditDto;

    /**
     * Объект DTO пользователя
     */
    private UserReadDto userReadDto;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(HibernateUserRepository.class);
        userMapper = Mockito.mock(UserMapper.class);
        userService = new UserServiceImpl(userRepository, userMapper);
        user = User.of("username", "email", "pass", "+9077777777");
        userCreateEditDto = UserCreateEditDto.builder()
                .username("username")
                .email("email")
                .password("pass")
                .phonenumber("+9077777777")
                .build();
        userReadDto = UserReadDto.builder()
                .active(true)
                .email("email")
                .password("pass")
                .phonenumber("+9077777777")
                .build();
    }

    /**
     * Выполняется проверка возвращения пользователя при сохранении.
     */
    @Test
    void whenAddUserThenReturnUser() {
        user.setActive(true);
        doReturn(user).when(userRepository).save(user);
        doReturn(user).when(userMapper).getUserFromUserCreateEditDto(userCreateEditDto);
        doReturn(userReadDto).when(userMapper).getUserReadDtoFromUser(user);

        UserReadDto result = userService.save(userCreateEditDto);

        verify(userRepository).save(user);
        verify(userMapper).getUserFromUserCreateEditDto(userCreateEditDto);
        verify(userMapper).getUserReadDtoFromUser(user);
        assertThat(result).isEqualTo(userReadDto);
    }

    /**
     * Выполняется проверка возвращения пользователя
     * при поиске по email.
     */
    @Test
    void whenFindUserByEmailThenReturnUser() {
        doReturn(user).when(userRepository).findUserByEmail(user.getEmail());
        doReturn(userReadDto).when(userMapper).getUserReadDtoFromUser(user);

        UserReadDto result = userService.findUserByEmail(user.getEmail());

        verify(userRepository).findUserByEmail(user.getEmail());
        verify(userMapper).getUserReadDtoFromUser(user);
        assertThat(result).isEqualTo(userReadDto);
    }

    /**
     * Выполняется проверка возвращения пользователя
     * при поиске по идентификатору.
     */
    @Test
    void whenFindUserByIdThenReturnUser() {
        doReturn(user).when(userRepository).findUserById(user.getId());
        doReturn(userReadDto).when(userMapper).getUserReadDtoFromUser(user);

        UserReadDto result = userService.findUserById(user.getId());

        verify(userRepository).findUserById(user.getId());
        verify(userMapper).getUserReadDtoFromUser(user);
        assertThat(result).isEqualTo(userReadDto);
    }

    /**
     * Выполняется проверка возвращения пользователя при обновлении.
     */
    @Test
    void whenUpdateUserThenReturnUser() {
        doReturn(user).when(userRepository).findUserByEmail(user.getEmail());
        doReturn(user).when(userRepository).update(user);
        doReturn(user).when(userMapper).updateUserFromUserCreateEditDto(userCreateEditDto, user);
        doReturn(userReadDto).when(userMapper).getUserReadDtoFromUser(user);

        UserReadDto result = userService.update(userCreateEditDto);

        verify(userRepository).findUserByEmail(user.getEmail());
        verify(userRepository).update(user);
        verify(userMapper).updateUserFromUserCreateEditDto(userCreateEditDto, user);
        assertThat(result).isEqualTo(userReadDto);
    }
}