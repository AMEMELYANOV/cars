package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cars.dto.UserCreateEditDto;
import ru.job4j.cars.dto.UserReadDto;
import ru.job4j.cars.service.UserService;

/**
 * Контроллер пользователя
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Controller
@Slf4j
@AllArgsConstructor
public class UserController {

    /**
     * Объект для доступа к методам UserService
     */
    private final UserService userService;

    /**
     * Обрабатывает GET запрос, возвращает страницу редактирования пользователя.
     * В зависимости от параметра password на страницу будут выведены
     * сообщения для пользователя о необходимости исправить вводимые данные.
     *
     * @param userId   идентификатор пользователя
     * @param model    модель
     * @param password параметр GET запроса, true, если есть ошибка валидации пароля
     * @return страница редактирования пользователя
     */
    @GetMapping("/userEdit{userId}")
    public String getEdit(@RequestParam(value = "userId") Integer userId, Model model,
                          @RequestParam(value = "password", required = false) String password) {
        String errorMessage = null;
        if (password != null) {
            errorMessage = "Неверно введен старый пароль!";
        }
        model.addAttribute("errorMessage", errorMessage);
        UserReadDto userDto = userService.findUserById(userId);
        model.addAttribute("user", userDto);

        log.info("Method {} run", "getEdit");
        return "userEdit";
    }

    /**
     * Обрабатывает POST запрос, выполняется перенаправление на страницу объявлений.
     * При удачной валидации пользователя, пользователь обновляется в базе,
     * при неудачной валидации выполняется переадресация
     * на страницу редактирования пользователя с соответствующими параметрами.
     *
     * @param userDto     объект DTO пользователя сформированный из данных формы редактирования
     * @param oldPassword старый пароль пользователя
     * @return перенаправление на страницу объявлений
     */
    @PostMapping("/userEdit")
    public String userEdit(
            @RequestParam(value = "oldPassword") String oldPassword,
            @ModelAttribute UserCreateEditDto userDto
    ) {
        UserReadDto userDtoFromDB = userService.findUserByEmail(userDto.getEmail());
        if (oldPassword != null && oldPassword.equals(userDtoFromDB.getPassword())) {
            userService.update(userDto);
            return "redirect:/ads";
        }

        log.info("Method {} run", "userEdit");
        return "redirect:/userEdit?userId=" + userDtoFromDB.getId() + "&password=true";
    }
}
