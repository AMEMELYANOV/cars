package ru.job4j.cars.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
     * Пользователь
     */
    private User user;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(HibernateUserRepository.class);
        userService = new UserServiceImpl(userRepository);
        user = User.of("username", "email", "password", "+79051111111");
    }

    /**
     * Выполняется проверка возвращения пользователя при сохранении.
     */
    @Test
    void whenAddUserThenReturnUser() {
        doReturn(user).when(userRepository).save(user);

        User result = userService.save(user);

        verify(userRepository).save(user);
        assertThat(result).isEqualTo(user);
    }

    /**
     * Выполняется проверка возвращения пользователя
     * при поиске по email.
     */
    @Test
    void whenFindUserByEmailThenReturnUser() {
        doReturn(user).when(userRepository).findUserByEmail(user.getEmail());

        User result = userService.findUserByEmail(user.getEmail());

        verify(userRepository).findUserByEmail(user.getEmail());
        assertThat(result).isEqualTo(user);
    }

    /**
     * Выполняется проверка возвращения пользователя
     * при поиске по идентификатору.
     */
    @Test
    void whenFindUserByIdThenReturnUser() {
        doReturn(user).when(userRepository).findUserById(user.getId());

        User result = userService.findUserById(user.getId());

        verify(userRepository).findUserById(user.getId());
        assertThat(result).isEqualTo(user);
    }

    /**
     * Выполняется проверка возвращения пользователя при обновлении.
     */
    @Test
    void whenUpdateUserThenReturnUser() {
        doReturn(user).when(userRepository).update(user);

        User result = userService.update(user);

        verify(userRepository).update(user);
        assertThat(result).isEqualTo(user);
    }
}