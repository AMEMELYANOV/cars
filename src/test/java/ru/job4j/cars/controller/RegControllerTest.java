package ru.job4j.cars.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import ru.job4j.cars.dto.UserCreateEditDto;
import ru.job4j.cars.dto.UserReadDto;
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
    void setUp() {
        userCreateEditDto = UserCreateEditDto.builder()
                .email("email")
                .password("pass")
                .phonenumber("+9077777777")
                .build();
        userReadDto = UserReadDto.builder()
                .active(true)
                .email("email")
                .id(1)
                .password("pass")
                .phonenumber("+9077777777")
                .build();
    }

    /**
     * Выполняется проверка удачной регистрации пользователя и перенаправление
     * пользователя на страницу входа.
     */
    @Test
    void whenRegSaveSuccess() {
        String repassword = "pass";
        UserService userService = mock(UserService.class);
        Errors errors = mock(Errors.class);
        doReturn(null).when(userService).findUserByEmail(userCreateEditDto.getEmail());

        RegController regController = new RegController(userService);
        String template = regController.regSave(userCreateEditDto, errors, repassword);

        verify(userService, times(1)).save(userCreateEditDto);
        Assertions.assertThat(template).isEqualTo("redirect:/login");
    }

    /**
     * Выполняется проверка неудачной регистрации пользователя и перенаправление
     * пользователя на страницу регистрации с параметрами ошибки, что пользователь
     * существует.
     */
    @Test
    void whenRegSaveIfUserExist() {
        String repassword = "pass";
        UserService userService = mock(UserService.class);
        Errors errors = mock(Errors.class);
        doReturn(userReadDto).when(userService).findUserByEmail(userCreateEditDto.getEmail());

        RegController regController = new RegController(userService);
        String template = regController.regSave(userCreateEditDto, errors, repassword);

        verify(userService, times(0)).save(userCreateEditDto);
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
        String template = regController.regSave(userCreateEditDto, errors, repassword);

        verify(userService, times(0)).save(userCreateEditDto);
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