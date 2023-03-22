package ru.job4j.cars.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.AdvertService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Тест класс реализации контроллеров
 * @see ru.job4j.cars.controller.AdvertController
 * @author Alexander Emelyanov
 * @version 1.0
 */
class AdvertControllerTest {

    /**
     * Пользователь
     */
    private User user;

    /**
     * Объявление
     */
    private Advert advert;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        user = User.of("user", "email",
                "password", "+79051111111");
        advert = Advert.of("title1", "description1", true,
                Car.of("model1",
                        Brand.of("brand1"),
                        BodyType.of("bodyType1"),
                        Engine.of(1.0, 100, "fuel1"),
                        Transmission.of("transmission1"),
                        1, 1),
                1, "city1");
        advert.setUser(user);
    }

    /**
     * Выполняется проверка возвращения страницы приветствия.
     */
    @Test
    void whenGetGreeting() {
        AdvertService advertService = mock(AdvertService.class);

        AdvertController advertController = new AdvertController(advertService);
        String template = advertController.getGreeting();

        assertThat(template).isEqualTo("index");
    }

    /**
     * Выполняется проверка возвращения страницы списка объявлений.
     */
    @Test
    void whenGetAdverts() {
        List<Advert> adverts = new ArrayList<>();
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        AdvertService advertService = mock(AdvertService.class);

        doReturn(user).when(session).getAttribute("user");

        AdvertController advertController = new AdvertController(advertService);
        String template = advertController.getAdverts(model, session);

        verify(model).addAttribute("adverts", adverts);
        verify(model).addAttribute("user", user);
        verify(advertService, times(1)).findAllAdverts();
        assertThat(template).isEqualTo("ads");
    }

    /**
     * Выполняется проверка возвращения страницы добавления объявления.
     */
    @Test
    void whenGetAdd() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        AdvertService advertService = mock(AdvertService.class);

        doReturn(user).when(session).getAttribute("user");

        AdvertController advertController = new AdvertController(advertService);
        String template = advertController.addAdvert(any(Advert.class), model, session);

        verify(model).addAttribute("user", user);
        assertThat(template).isEqualTo("add");
    }

    /**
     * Выполняется проверка возвращения страницы подробных данных объявления
     */
    @Test
    void whenGetDetailsAdvert() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        AdvertService advertService = mock(AdvertService.class);

        doReturn(user).when(session).getAttribute("user");
        doReturn(advert).when(advertService).findAdvertById(anyInt());

        AdvertController advertController = new AdvertController(advertService);
        String template = advertController.getDetails(anyInt(), model, session);

        verify(model).addAttribute("user", user);
        verify(model).addAttribute("advert", advert);
        verify(advertService, times(1)).findAdvertById(anyInt());
        assertThat(template).isEqualTo("details");
    }

    /**
     * Выполняется проверка возвращения страницы списка пользовательских объявлений.
     */
    @Test
    void whenGetMyAdverts() {
        List<Advert> adverts = new ArrayList<>();
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        AdvertService advertService = mock(AdvertService.class);

        doReturn(user).when(session).getAttribute("user");
        doReturn(adverts).when(advertService).findAdvertsByUserId(anyInt());

        AdvertController advertController = new AdvertController(advertService);
        String template = advertController.getMyAds(model, session);

        verify(model).addAttribute("user", user);
        verify(model).addAttribute("adverts", adverts);
        verify(advertService, times(1)).findAdvertsByUserId(anyInt());
        assertThat(template).isEqualTo("myAds");
    }

    /**
     * Выполняется проверка возвращения страницы списка пользовательских объявлений,
     * при удачном удалении объявления.
     */
    @Test
    void whenDeleteMyAdvert() {
        AdvertService advertService = mock(AdvertService.class);

        AdvertController advertController = new AdvertController(advertService);
        String template = advertController.deleteMyAds(anyInt());

        verify(advertService, times(1)).deleteAdvertById(anyInt());
        assertThat(template).isEqualTo("redirect:/myAds");
    }

    /**
     * Выполняется проверка возвращения страницы редактирования объявления.
     */
    @Test
    void whenGetEditAdvert() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        AdvertService advertService = mock(AdvertService.class);

        doReturn(user).when(session).getAttribute("user");
        doReturn(advert).when(advertService).findAdvertById(anyInt());

        AdvertController advertController = new AdvertController(advertService);
        String template = advertController.getEditAds(anyInt(), model, session);

        verify(model).addAttribute("user", user);
        verify(model).addAttribute("advert", advert);
        verify(advertService, times(1)).findAdvertById(anyInt());
        assertThat(template).isEqualTo("editAds");
    }

    /**
     * Выполняется проверка возвращения страницы списка пользовательских объявлений,
     * при удачном редактировании объявления.
     */
    @Test
    void whenEditAdvert() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        AdvertService advertService = mock(AdvertService.class);

        doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");

        AdvertController advertController = new AdvertController(advertService);
        String template = advertController.editAds(model, advert,
               file, request);

        verify(model).addAttribute("user", user);
        verify(advertService, times(1)).save(any(Advert.class),
                any(MultipartFile.class));
        assertThat(template).isEqualTo("redirect:/myAds");
    }

    /**
     * Выполняется проверка возвращения страницы списка объявлений,
     * при удачном применении фильтров.
     */
    @Test
    void whenPostFilterAdverts() {
        List<Advert> adverts = new ArrayList<>();
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        AdvertService advertService = mock(AdvertService.class);

        doReturn(user).when(session).getAttribute("user");
        doReturn(adverts).when(advertService).filterAdverts(anyString(),
                anyString(), anyString(),
                anyString());

        AdvertController advertController = new AdvertController(advertService);
        String template = advertController.filterAdverts(anyString(),
                anyString(), anyString(),
                anyString(), model, session);

        verify(model).addAttribute("user", user);
        verify(model).addAttribute("adverts", adverts);
        verify(advertService, times(1)).filterAdverts(anyString(),
                anyString(), anyString(),
                anyString());
        assertThat(template).isEqualTo("ads");
    }
}