package ru.job4j.cars.service;

import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.UserReadDto;
import ru.job4j.cars.model.Advert;

import java.io.IOException;
import java.util.List;

/**
 * Сервис по работе с объявлениями
 * @see ru.job4j.cars.model.Advert
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface AdvertService {

    /**
     * Выполняет возврат всех объявлений.
     *
     * @return список объявлений
     */
    List<Advert> findAllAdverts();

    /**
     * Выполняет сохранение объявления. Возвращает
     * объявления с проинициализированным идентификатором.
     *
     * @param advert объявление
     * @param file файл изображения
     * @param userDto объект DTO пользователя
     * @return advert объявление с проинициализированным идентификатором
     * @exception IOException если при сохранении файла изображения произошли ошибки
     */
    Advert save(Advert advert, MultipartFile file, UserReadDto userDto) throws IOException;

    /**
     * Выполняет поиск и возврат объявления по идентификатору.
     *
     * @param advertId идентификатор
     * @return advert объявление
     */
    Advert findAdvertById(Integer advertId);

    /**
     * Выполняет поиск и возврат объявления по идентификатору пользователя.
     *
     * @param id идентификатор
     * @return advert объявление
     */
    List<Advert> findAdvertsByUserId(int id);

    /**
     * Выполняет удаление объявления по идентификатору.
     *
     * @param advertId идентификатор
     */
    void deleteAdvertById(Integer advertId);

    /**
     * Выполняет обновление объявления. Возвращает
     * обновленное объявление.
     *
     * @param advert объявление
     * @return advert обновленное объявление
     */
    Advert update(Advert advert);

    /**
     * Выполняет фильтрацию объявлений по переданным параметрам.
     * Вернет список объявлений соответствующих фильтру.
     *
     * @param bodyType тип кузова
     * @param transmissionname трансмиссия
     * @param drive тип привода
     * @param fuel тип топлива
     * @return список объявлений
     */
    List<Advert> filterAdverts(String bodyType, String transmissionname,
                                      String drive, String fuel);
}
