package ru.job4j.cars.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.AdvertRepository;
import ru.job4j.cars.repository.HibernateAdvertRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Тест класс реализации сервисов
 * @see ru.job4j.cars.service.AdvertService
 * @author Alexander Emelyanov
 * @version 1.0
 */
class AdvertServiceImplTest {

    /**
     Моск объекта AdRepository
     */
    private AdvertRepository advertRepository;

    /**
     * Объект для доступа к методам AdvertService
     */
    private AdvertService advertService;

    /**
     * Объявление
     */
    private Advert advert;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    public void setup() {
        advertRepository = Mockito.mock(HibernateAdvertRepository.class);
        advertService = new AdvertServiceImpl(advertRepository);
        advert = Advert.of("title",
                "description",
                true,
                Car.of("model", Brand.of("brand"), BodyType.of("bodytype"),
                        Engine.of(2000.0, 150, "fuel"),
                        Transmission.of("transmission"), 2010, 100000),
                1000000, "city");
        advert.setUser(User.of("username", "email", "password", "+79051111111"));
    }

    /**
     * Выполняется проверка возвращения списка всех объявления,
     * если в списке есть элементы.
     */
    @Test
    void whenFindAllAdvertsThenReturnList() {
        List<Advert> adverts = List.of(advert);
        doReturn(adverts).when(advertRepository).findAllAdverts();

        List<Advert> result = advertRepository.findAllAdverts();

        assertThat(result.size()).isEqualTo(1);
    }

    /**
     * Выполняется проверка возвращения списка всех объявлений,
     * если список пустой.
     */
    @Test
    void whenFindAllAdvertsThenReturnEmptyList() {
        doReturn(Collections.emptyList()).when(advertRepository).findAllAdverts();

        List<Advert> result = advertRepository.findAllAdverts();

        assertThat(result).isEmpty();
    }

    /**
     * Выполняется проверка возврата объявления и выбора обновления объявления
     * при ненулевом идентификаторе.
     */
    @Test
    void whenSaveAdvertWithNotZeroIdThenUpdateAdvert() throws IOException {
        advert.setId(1);
        MultipartFile file = mock(MultipartFile.class);
        doReturn(advert).when(advertRepository).update(advert);
        doReturn(true).when(file).isEmpty();

        Advert result = advertService.save(advert, file);

        verify(advertRepository).update(advert);
        Assertions.assertThat(result).isEqualTo(advert);
    }

    /**
     * Выполняется проверка возврата объявления и выбора сохранения объявления
     * при нулевом идентификаторе.
     */
    @Test
    void whenSaveAdvertWithZeroIdThenSaveAdvert() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        doReturn(advert).when(advertRepository).save(advert);
        doReturn(true).when(file).isEmpty();

        Advert result = advertService.save(advert, file);

        verify(advertRepository).save(advert);
        Assertions.assertThat(result).isEqualTo(advert);
    }

    /**
     * Выполняется проверка возвращения объявления,
     * если объявление найдено по идентификатору.
     */
    @Test
    void whenFindAdvertByIdThenReturnAdvert() {
        doReturn(advert).when(advertRepository).findAdvertById(advert.getId());

        Advert result = advertService.findAdvertById(advert.getId());

        verify(advertRepository).findAdvertById(advert.getId());
        Assertions.assertThat(result).isEqualTo(advert);
    }

    /**
     * Выполняется проверка возвращения списка объявлений,
     * если объявление найдено по идентификатору пользователя.
     */
    @Test
    void whenFindAdvertByUserIdThenList() {
        List<Advert> adverts = List.of(advert);
        doReturn(adverts).when(advertRepository).findAdvertsByUserId(advert.getUser().getId());

        List<Advert> result = advertService.findAdvertsByUserId(advert.getUser().getId());

        verify(advertRepository).findAdvertsByUserId(advert.getUser().getId());
        Assertions.assertThat(result).isEqualTo(adverts);
    }

    /**
     * Выполняется проверка вызова метода
     * {@link HibernateAdvertRepository#deleteAdvertById(Integer)}.
     */
    @Test
    void whenDeleteAdvertById() {
        advertService.deleteAdvertById(advert.getId());

        verify(advertRepository).deleteAdvertById(advert.getId());
    }

    /**
     * Выполняется проверка возвращения объявления,
     * если объявление обновлено.
     */
    @Test
    void whenUpdateAdvertThenAdvert() {
        doReturn(advert).when(advertRepository).update(advert);

        Advert result = advertService.update(advert);

        verify(advertRepository).update(advert);
        assertThat(result).isEqualTo(advert);
    }

    /**
     * Выполняется проверка возвращения объявления,
     * если объявление обновлено.
     */
    @Test
    void whenFilterAdvertsThenList() {
        List<Advert> adverts = List.of(advert);
        String bodyType = "bodytype";
        String transmissionname = "transmissionname";
        String drive = "drive";
        String fuel = "fuel";
        doReturn(adverts).when(advertRepository).filterAdverts(bodyType, transmissionname,
                drive, fuel);

        List<Advert> result = advertService.filterAdverts(bodyType, transmissionname,
                drive, fuel);

        verify(advertRepository).filterAdverts(bodyType, transmissionname,
                drive, fuel);
        assertThat(result).isEqualTo(adverts);
    }
}