package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.UserService;

import javax.validation.Valid;

/**
 * Контроллер страницы регистрации пользователя
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Controller
@Slf4j
@RequestMapping("/reg")
@AllArgsConstructor
public class RegController {

    /**
     * Объект для доступа к методам UserService
     */
    private final UserService userService;

    /**
     * Обрабатывает GET запрос, возвращает страницу регистрации пользователя.
     * В зависимости от параметров errors и account на страницу будут выведены
     * сообщения для пользователя о необходимости исправить вводимые данные.
     *
     * @param password параметр GET запроса, true, если есть ошибка валидации пароля
     * @param account параметр GET запроса, true, если ошибка валидации
     * @param model модель
     * @return страница регистрации пользователя
     */
    @GetMapping
    public String regPage(@RequestParam(value = "password", required = false) String password,
                          @RequestParam(value = "account", required = false) String account,
                          Model model) {
        String errorMessage = null;
        if (password != null) {
            errorMessage = "Пароли должны совпадать!";
        }
        if (account != null) {
            errorMessage = "Аккаунт уже существует!";
        }
        model.addAttribute("errorMessage", errorMessage);

        log.info("Method {} run", "regPage");
        return "reg";
    }

    /**
     * Обрабатывает POST запрос, выполняется перенаправление на страницу входа.
     * При удачной валидации пользователя, пользователь сохраняется в базе,
     * при неудачной валидации выполняется переадресация на страницу регистрации
     * с параметрами account=true или password=true.
     *
     * @param user пользователь сформированный из данных формы регистрации
     * @param errors список ошибок полученных при валидации модели пользователя
     * @param repassword повторно набранный пароль
     * @return страница входа пользователя
     */
    @PostMapping
    public String regSave(@Valid @ModelAttribute User user, Errors errors,
                          @RequestParam String repassword) {
        if (errors.hasErrors()) {
            return "reg";
        }
        User userFromDB = userService.findUserByEmail(user.getEmail());
        if (userFromDB != null) {
            return "redirect:/reg?account=true";
        }
        if (!user.getPassword().equals(repassword)) {
            return "redirect:/reg?password=true";
        }
        user.setActive(true);
        userService.save(user);

        log.info("Method {} run", "regSave");
        return "redirect:/login";
    }

    /**
     * Добавляет нового пустого пользователя в модель.
     * @return пользователь
     */
    @ModelAttribute
    public User user() {
        return new User();
    }
}