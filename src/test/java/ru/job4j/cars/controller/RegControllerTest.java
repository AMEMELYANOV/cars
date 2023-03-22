package ru.job4j.cars.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.UserService;

import static org.mockito.Mockito.*;

/**
 * Тест класс реализации контроллеров
 * @see ru.job4j.cars.controller.RegController
 * @author Alexander Emelyanov
 * @version 1.0
 */
class RegControllerTest {

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
     * Выполняется проверка удачной регистрации пользователя и перенаправление
     * пользователя на страницу входа.
     */
    @Test
    void whenRegSaveSuccess() {
        String repassword = "password";
        UserService userService = mock(UserService.class);
        Errors errors = mock(Errors.class);

        RegController regController = new RegController(userService);
        String template = regController.regSave(user, errors, repassword);

        verify(userService, times(1)).save(user);
        Assertions.assertThat(template).isEqualTo("redirect:/login");
    }

    /**
     * Выполняется проверка неудачной регистрации пользователя и перенаправление
     * пользователя на страницу регистрации с параметрами ошибки, что пользователь
     * существует.
     */
    @Test
    void whenRegSaveIfUserExist() {
        String repassword = "password";
        UserService userService = mock(UserService.class);
        Errors errors = mock(Errors.class);
        doReturn(new User()).when(userService).findUserByEmail(user.getEmail());

        RegController regController = new RegController(userService);
        String template = regController.regSave(user, errors, repassword);

        verify(userService, times(0)).save(user);
        Assertions.assertThat(template).isEqualTo("redirect:/reg?account=true");
    }

    /**
     * Выполняется проверка неудачной регистрации пользователя и перенаправление
     * пользователя на страницу регистрации с параметрами ошибки, что пользователь
     * ввел повторно неправильный пароль в форме регистрации.
     */
    @Test
    void whenRegSaveIfPasswordNotEqual() {
        String repassword = "pwd";
        UserService userService = mock(UserService.class);
        Errors errors = mock(Errors.class);

        RegController regController = new RegController(userService);
        String template = regController.regSave(user, errors, repassword);

        verify(userService, times(0)).save(user);
        Assertions.assertThat(template).isEqualTo("redirect:/reg?password=true");
    }

    /**
     * Выполняется проверка возвращения страницы регистрации,
     * параметры ошибок отсутствуют.
     */
    @Test
    void whenRegPageSuccess() {
        String password = null;
        String account = null;
        String errorMessage = null;
        UserService userService = mock(UserService.class);
        Model model = mock(Model.class);

        RegController regController = new RegController(userService);
        String template = regController.regPage(password, account, model);

        verify(model).addAttribute("errorMessage", errorMessage);
        Assertions.assertThat(template).isEqualTo("reg");
    }

    /**
     * Выполняется проверка возвращения страницы регистрации,
     * с параметром ошибки неправильного повторения пароля.
     */
    @Test
    void whenRegPageIfPasswordParameterNotNull() {
        String password = "true";
        String account = null;
        String errorMessage = "Пароли должны совпадать!";
        UserService userService = mock(UserService.class);
        Model model = mock(Model.class);

        RegController regController = new RegController(userService);
        String template = regController.regPage(password, account, model);

        verify(model).addAttribute("errorMessage", errorMessage);
        Assertions.assertThat(template).isEqualTo("reg");
    }

    /**
     * Выполняется проверка возвращения страницы регистрации,
     * с параметром ошибки уже зарегистрированного пользователя с такой почтой.
     */
    @Test
    void whenRegPageIfAccountParameterNotNull() {
        String password = null;
        String account = "true";
        String errorMessage = "Аккаунт уже существует!";
        UserService userService = mock(UserService.class);
        Model model = mock(Model.class);

        RegController regController = new RegController(userService);
        String template = regController.regPage(password, account, model);

        verify(model).addAttribute("errorMessage", errorMessage);
        Assertions.assertThat(template).isEqualTo("reg");
    }

}