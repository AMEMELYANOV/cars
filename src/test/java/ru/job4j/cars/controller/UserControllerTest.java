package ru.job4j.cars.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.UserService;

import static org.mockito.Mockito.*;

/**
 * Тест класс реализации контроллеров
 * @see ru.job4j.cars.controller.LoginController
 * @author Alexander Emelyanov
 * @version 1.0
 */
class UserControllerTest {

    /**
     * Пользователь
     */
    private User user;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        user = User.of("user", "email",
                "password", "+79051111111");
    }

    /**
     * Выполняется проверка возвращения страницы редактирования профиля пользователя.
     */
    @Test
    void whenGetEditPageSuccess() {
        String errorMessage = null;
        String password = null;
        int id = 1;
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        doReturn(user).when(userService).findUserById(id);

        UserController userController = new UserController(userService);
        String template = userController.getEdit(id, model, password);

        verify(model).addAttribute("errorMessage", errorMessage);
        verify(model).addAttribute("user", user);
        Assertions.assertThat(template).isEqualTo("userEdit");
    }

    /**
     * Выполняется проверка возвращения страницы редактирования профиля пользователя,
     * при наличии ошибки ввода паролей.
     */
    @Test
    void whenGetEditPageFail() {
        String errorMessage = "Неверно введен старый пароль!";
        String password = "true";
        int id = 1;
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        doReturn(user).when(userService).findUserById(id);

        UserController userController = new UserController(userService);
        String template = userController.getEdit(id, model, password);

        verify(model).addAttribute("errorMessage", errorMessage);
        verify(model).addAttribute("user", user);
        Assertions.assertThat(template).isEqualTo("userEdit");
    }

    /**
     * Выполняется проверка возвращения страницы списка объявлений,
     * при удачном обновлении пользователя.
     */
    @Test
    void whenUserEditSuccess() {
        String oldPassword = "password";
        UserService userService = mock(UserService.class);
        doReturn(user).when(userService).findUserById(user.getId());

        UserController userController = new UserController(userService);
        String template = userController.userEdit(user, oldPassword);

        verify(userService, times(1)).update(user);
        Assertions.assertThat(template).isEqualTo("redirect:/ads");
    }

    /**
     * Выполняется проверка возвращения страницы редактирования профиля пользователя,
     * при неудачном обновлении пользователя из-за пароля.
     */
    @Test
    void whenUserEditFail() {
        String oldPassword = "pwd";
        UserService userService = mock(UserService.class);
        doReturn(user).when(userService).findUserById(user.getId());

        UserController userController = new UserController(userService);
        String template = userController.userEdit(user, oldPassword);

        verify(userService, times(0)).update(user);
        Assertions.assertThat(template)
                .isEqualTo("redirect:/userEdit?userId=" + user.getId() + "&password=true");
    }
}