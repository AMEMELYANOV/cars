package ru.job4j.cars.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Модель данных автомобиль
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Entity
@Table(name = "cars")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class Car {

    /**
     * Идентификатор автомобиля
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Модель автомобиля
     */
    private String carModel;

    /**
     * Год выпуска автомобиля
     */
    private int year;

    /**
     * Пробег автомобиля
     */
    private int mileage;

    /**
     * Цвет автомобиля
     */
    private String color;

    /**
     * Привод автомобиля
     */
    private String drive;

    /**
     * Брэнд автомобиля
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    /**
     * Тип кузова автомобиля
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bodytype_id")
    private BodyType bodyType;

    /**
     * Трансмиссия автомобиля
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;

    /**
     * Двигатель автомобиля
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "engine_id")
    private Engine engine;

    /**
     * Создает новый объект автомобиль Car.
     *
     * @param model модель автомобиля
     * @param brand брэнд автомобиля
     * @param bodyType тип кузова автомобиля
     * @param engine двигатель автомобиля
     * @param transmission трансмиссия автомобиля
     * @param year год выпуска автомобиля
     * @param mileage пробег автомобиля
     * @return возвращает объект автомобиль
     */
    public static Car of(String model, Brand brand, BodyType bodyType, Engine engine,
                         Transmission transmission, int year, int mileage) {
        Car car = new Car();
        car.carModel = model;
        car.brand = brand;
        car.bodyType = bodyType;
        car.engine = engine;
        car.transmission = transmission;
        car.year = year;
        car.mileage = mileage;
        return car;
    }
}
