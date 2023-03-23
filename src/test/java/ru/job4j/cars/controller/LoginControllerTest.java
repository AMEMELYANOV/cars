package ru.job4j.cars.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

/**
 * Тест класс реализации контроллеров
 * @see ru.job4j.cars.controller.LoginController
 * @author Alexander Emelyanov
 * @version 1.0
 */
class LoginControllerTest {

    /**
     * Пользователь
     */
    private User user;

    /**
     * Пользователь
     */
    private User userFromDB;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        user = User.of("user", "email",
                "password", "+79051111111");
        userFromDB = User.of("user", "email",
                "pwd", "+79051111111");
    }

    /**
     * Выполняется проверка загрузки страницы входа.
     */
    @Test
    void whenRegLogin() {
        String error = null;
        String logout = null;
        String errorMessage = null;

        UserService userService = mock(UserService.class);
        Model model = mock(Model.class);

        LoginController loginController = new LoginController(userService);
        String template = loginController.loginPage(error, logout, model);

        verify(model).addAttribute("errorMessage", errorMessage);
        Assertions.assertThat(template).isEqualTo("login");
    }

    /**
     * Выполняется проверка загрузки страницы входа, с параметром
     * error, пароль или логин пользователя введены неверно.
     */
    @Test
    void whenLoginPageIfPasswordParameterNotNull() {
        String error = "true";
        String logout = null;
        String errorMessage = "Имя аккаунта или пароль введены неправильно!";
        UserService userService = mock(UserService.class);
        Model model = mock(Model.class);

        LoginController loginController = new LoginController(userService);
        String template = loginController.loginPage(error, logout, model);

        verify(model).addAttribute("errorMessage", errorMessage);
        Assertions.assertThat(template).isEqualTo("login");
    }

    /**
     * Выполняется проверка загрузки страницы входа, с параметром
     * logout, пользователь вышел из приложения.
     */
    @Test
    void whenLoginPageIfAccountParameterNotNull() {
        String error = null;
        String logout = "true";
        String errorMessage = "Вы вышли!";
        UserService userService = mock(UserService.class);
        Model model = mock(Model.class);

        LoginController loginController = new LoginController(userService);
        String template = loginController.loginPage(error, logout, model);

        verify(model).addAttribute("errorMessage", errorMessage);
        Assertions.assertThat(template).isEqualTo("login");
    }

    /**
     * Выполняется проверка удачного входа пользователя и перенаправление
     * пользователя на страницу объявлений.
     */
    @Test
    void whenLoginUserExist() {
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        doReturn(user).when(userService).findUserByEmail(user.getEmail());
        doReturn(session).when(request).getSession();

        LoginController loginController = new LoginController(userService);
        String template = loginController.loginUser(user, request);

        verify(session).setAttribute("user", user);
        Assertions.assertThat(template).isEqualTo("redirect:/ads");
    }

    /**
     * Выполняется проверка неудачного входа пользователя (логин не найден)
     * и перенаправление пользователя на страницу входа.
     */
    @Test
    void whenLoginUserNotExist() {
        UserService userService = mock(UserService.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        doReturn(null).when(userService).findUserByEmail(user.getEmail());

        LoginController loginController = new LoginController(userService);
        String template = loginController.loginUser(user, request);

        Assertions.assertThat(template).isEqualTo("redirect:/login?error=true");
    }

    /**
     * Выполняется проверка неудачного входа пользователя (логин найден,
     * но пароль не совпал) и перенаправление пользователя на страницу входа.
     */
    @Test
    void whenLoginUserPasswordNotEqual() {
        UserService userService = mock(UserService.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        doReturn(userFromDB).when(userService).findUserByEmail(user.getEmail());

        LoginController loginController = new LoginController(userService);
        String template = loginController.loginUser(user, request);

        Assertions.assertThat(template).isEqualTo("redirect:/login?error=true");
    }

    /**
     * Выполняется проверка выхода пользователя из приложения
     * с перенаправлением пользователя на страницу входа с параметром выхода.
     */
    @Test
    void whenLogoutPage() {
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);

        LoginController loginController = new LoginController(userService);
        String template = loginController.logoutPage(session);

        Assertions.assertThat(template).isEqualTo("redirect:/login?logout=true");
    }
}