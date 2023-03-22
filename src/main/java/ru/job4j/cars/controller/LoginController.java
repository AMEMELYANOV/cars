package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Контроллер для страницы входа пользователя
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Controller
@Slf4j
@AllArgsConstructor
public class LoginController {

    /**
     * Объект для доступа к методам UserService
     */
    private final UserService userService;

    /**
     * Обрабатывает GET запрос, возвращает страницу входа пользователя.
     * В зависимости от параметров error и logout на страницу будут выведены сообщения
     * для пользователя о необходимости исправить вводимые данные.
     *
     * @param error параметр GET запроса, true, если есть ошибка при заполнении формы
     * @param logout параметр GET запроса, true, если пользователь разлогинился
     * @param model модель
     * @return страница входа пользователя
     */
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        String errorMessage = null;
        if (error != null) {
            errorMessage = "Имя аккаунта или пароль введены неправильно!";
        }
        if (logout != null) {
            errorMessage = "Вы вышли!";
        }
        model.addAttribute("errorMessage", errorMessage);

        log.info("Method {} run", "loginPage");
        return "login";
    }

    /**
     * Обрабатывает POST запрос, возвращает страницу со списком объявлений.
     * При удачной валидации пользователя, пользователь записывается
     * в аттрибуты сессии, при неудачной валидации выполняется переадресация
     * на страницу входа с параметром error=true.
     *
     * @param user параметр GET запроса, true, если есть ошибка при заполнении формы
     * @param request запрос пользователя
     * @return список задач
     */
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, HttpServletRequest request) {
        User userFromDB = userService.findUserByEmail(user.getEmail());
        if (userFromDB != null && userFromDB.getPassword().equals(user.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", userFromDB);
            return "redirect:/ads";
        }

        log.info("Method {} run", "loginUser");
        return "redirect:/login?error=true";
    }

    /**
     * Обрабатывает GET запрос, перенаправляет на страницу входа.
     * Выполняется выход пользователя из приложения и
     * очистка сессии.
     *
     * @param session сессия клиента
     * @return перенаправление на страницу входа с параметром logout=true
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpSession session) {
        session.invalidate();

        log.info("Method {} run", "logoutPage");
        return "redirect:/login?logout=true";
    }
}
