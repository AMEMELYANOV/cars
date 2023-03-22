package ru.job4j.cars.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * Модель данных объявление
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Entity
@Table(name = "adverts")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class Advert {

    /**
     * Идентификатор объявления
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Заголовок объявления
     */
    private String title;

    /**
     * Описание объявления
     */
    private String description;

    /**
     * Статус объявления
     */
    private boolean active;

    /**
     * Цена автомобиля
     */
    private int price;

    /**
     * Наименование файла изображения автомобиля
     */
    private String filename;

    /**
     * Город объявления
     */
    private String city;

    /**
     * Автомобиль объявления
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", unique = true)
    private Car car;

    /**
     * Пользователь объявления
     */
    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    /**
     * Дата и время создания объявления
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date(System.currentTimeMillis());

    /**
     * Создает новый объект объявление Advert.
     *
     * @param title заголовок объявления
     * @param description описание объявления
     * @param active статус объявления
     * @param car автомобиль объявления
     * @param price цена автомобиля
     * @param city город объявления
     * @return возвращает объект объявление
     */
    public static Advert of(String title, String description,
                            boolean active, Car car, int price, String city) {
        Advert advert = new Advert();
        advert.title = title;
        advert.description = description;
        advert.active = active;
        advert.car = car;
        advert.created = new Date(System.currentTimeMillis());
        advert.price = price;
        advert.city = city;
        return advert;
    }
}
