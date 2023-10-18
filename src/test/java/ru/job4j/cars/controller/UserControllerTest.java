package ru.job4j.cars.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cars.dto.UserCreateEditDto;
import ru.job4j.cars.dto.UserReadDto;
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
     * Выполняется проверка возвращения страницы редактирования профиля пользователя.
     */
    @Test
    void whenGetEditPageSuccess() {
        String errorMessage = null;
        String password = null;
        int id = 1;
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        doReturn(userReadDto).when(userService).findUserById(id);

        UserController userController = new UserController(userService);
        String template = userController.getEdit(id, model, password);

        verify(model).addAttribute("errorMessage", errorMessage);
        verify(model).addAttribute("user", userReadDto);
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
        doReturn(userReadDto).when(userService).findUserById(id);

        UserController userController = new UserController(userService);
        String template = userController.getEdit(id, model, password);

        verify(model).addAttribute("errorMessage", errorMessage);
        verify(model).addAttribute("user", userReadDto);
        Assertions.assertThat(template).isEqualTo("userEdit");
    }

    /**
     * Выполняется проверка возвращения страницы списка объявлений,
     * при удачном обновлении пользователя.
     */
    @Test
    void whenUserEditSuccess() {
        String oldPassword = "pass";
        UserService userService = mock(UserService.class);
        doReturn(userReadDto).when(userService).findUserByEmail(userCreateEditDto.getEmail());

        UserController userController = new UserController(userService);
        String template = userController.userEdit(oldPassword, userCreateEditDto);

        verify(userService, times(1)).update(userCreateEditDto);
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
        doReturn(userReadDto).when(userService).findUserByEmail(userCreateEditDto.getEmail());

        UserController userController = new UserController(userService);
        String template = userController.userEdit(oldPassword, userCreateEditDto);

        verify(userService, times(0)).update(userCreateEditDto);
        Assertions.assertThat(template)
                .isEqualTo("redirect:/userEdit?userId=" + userReadDto.getId() + "&password=true");
    }
}