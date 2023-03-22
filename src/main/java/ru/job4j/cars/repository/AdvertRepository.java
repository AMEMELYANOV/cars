package ru.job4j.cars.repository;

import ru.job4j.cars.model.Advert;

import java.util.List;

/**
 * Хранилище объявлений
 * @see ru.job4j.cars.model.Advert
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface AdvertRepository {

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
     * @return advert объявление с проинициализированным идентификатором
     */
    Advert save(Advert advert);

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
     * Если удаление состоялось, вернет true, иначе false.
     *
     * @param advertId идентификатор
     * @return true, если удаление состоялось, иначе false
     */
    boolean deleteAdvertById(Integer advertId);

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
