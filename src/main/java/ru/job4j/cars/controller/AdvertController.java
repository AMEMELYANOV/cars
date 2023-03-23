package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Advert;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.AdvertService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Контроллер объявлений
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Controller
@Slf4j
@AllArgsConstructor
public class AdvertController {

    /**
     * Объект для доступа к методам AdvertService
     */
    private final AdvertService advertService;

    /**
     * Обрабатывает GET запрос, возвращает стартовую страницу
     *
     * @return стартовая страница
     */
    @GetMapping("/")
    public String getGreeting() {
        log.info("Method {} run", "getGreeting");
        return "index";
    }

    /**
     * Обрабатывает GET запрос, возвращает страницу списка всех объявлений.
     *
     * @param model модель
     * @param session сессия пользователя
     * @return страница списка всех объявлений
     */
    @GetMapping("/ads")
    public String getAdverts(Model model, HttpSession session) {
        List<Advert> adverts = advertService.findAllAdverts();
        model.addAttribute("adverts", adverts);
        model.addAttribute("user", session.getAttribute("user"));

        log.info("Method {} run", "getAdverts");
        return "ads";
    }

    /**
     * Обрабатывает GET запрос, возвращает страницу добавления объявления.
     *
     * @param advert объявление
     * @param model модель
     * @param session сессия пользователя
     * @return страница добавления объявления
     */
    @GetMapping("/add")
    public String addAdvert(@ModelAttribute("advert") Advert advert,
                            Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));

        log.info("Method {} run", "addAdvert");
        return "add";
    }

    /**
     * Обрабатывает GET запрос, возвращает страницу с подробным описанием объявления,
     * выбор объявления происходит по идентификатору.
     *
     * @param advertId идентификатор объявления
     * @param model модель
     * @param session сессия пользователя
     * @return страница с подробным описанием объявления
     */
    @GetMapping("/details{advertId}")
    public String getDetails(@RequestParam(value = "advertId") Integer advertId,
                             Model model, HttpSession session) {
        Advert advert = advertService.findAdvertById(advertId);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("advert", advert);

        log.info("Method {} run", "getDetails");
        return "details";
    }

    /**
     * Обрабатывает GET запрос, возвращает страницу со списком объявлений пользователя.
     *
     * @param model модель
     * @param session сессия пользователя
     * @return страница со списком объявлений пользователя
     */
    @GetMapping("/myAds")
    public String getMyAds(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Advert> adverts = advertService.findAdvertsByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("adverts", adverts);

        log.info("Method {} run", "getMyAds");
        return "myAds";
    }

    /**
     * Обрабатывает GET запрос, возвращает страницу со списком объявлений пользователя.
     * Вызывает метод удаления объявления по идентификатору.
     *
     * @param advertId модель
     * @return страница со списком объявлений пользователя
     */
    @GetMapping("/del{advertId}")
    public String deleteMyAds(@RequestParam(value = "advertId") Integer advertId) {
        advertService.deleteAdvertById(advertId);

        log.info("Method {} run", "deleteMyAds");
        return "redirect:/myAds";
    }

    /**
     * Обрабатывает GET запрос, возвращает страницу с формой редактирования объявления,
     * выбор объявления происходит по идентификатору.
     *
     * @param advertId модель
     * @param model модель
     * @param session сессия пользователя
     * @return страница со списком объявлений пользователя
     */
    @GetMapping("/editAds{advertId}")
    public String getEditAds(@RequestParam(value = "advertId") Integer advertId,
                             Model model,
                             HttpSession session
    ) {
        Advert advert = advertService.findAdvertById(advertId);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("advert", advert);

        log.info("Method {} run", "getEditAds");
        return "editAds";
    }

    /**
     * Обрабатывает POST запрос, возвращает страницу со списком объявлений пользователя,
     * после сохранения объявления.
     *
     * @param model модель
     * @param advert объявление
     * @param file файл изображения автомобиля
     * @param request запрос
     * @throws IOException при работе по сохранению файла изображения автомобиля
     * @return страница со списком объявлений пользователя
     */
    @PostMapping("/saveAds")
    public String editAds(Model model,
                          @ModelAttribute Advert advert,
                          @RequestParam("file") MultipartFile file,
                          HttpServletRequest request) throws IOException {
        HttpSession sc = request.getSession();
        User user = (User) sc.getAttribute("user");
        model.addAttribute("user", user);
        advert.setUser(user);
        advertService.save(advert, file);

        log.info("Method {} run", "editAds");
        return "redirect:/myAds";
    }

    /**
     * Обрабатывает POST запрос, возвращает страницу со списком объявлений,
     * после применения выбранного пользователем фильтра.
     *
     * @param bodyType тип кузова
     * @param transmissionname трансмиссия
     * @param drive тип привода
     * @param fuel топливо
     * @param model модель
     * @param session сессия пользователя
     * @return страница со списком объявлений пользователя
     */
    @PostMapping("/find")
    public String filterAdverts(@RequestParam("bodyType") String bodyType,
                                @RequestParam("transmissionname") String transmissionname,
                                @RequestParam("drive") String drive,
                                @RequestParam("fuel") String fuel,
                                Model model, HttpSession session) {
        System.out.println(bodyType);
        List<Advert> adverts = advertService.filterAdverts(bodyType, transmissionname,
                drive, fuel);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("adverts", adverts);

        log.info("Method {} run", "filterAdverts");
        return "ads";
    }
}
