package ru.job4j.cars.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Модель данных двигатель
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Entity
@Table(name = "engines")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class Engine {

    /**
     * Идентификатор двигателя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Объем двигателя
     */
    private double capacity;

    /**
     * Количество лошадиных сил двигателя
     */
    private int hp;

    /**
     * Тип топлива двигателя
     */
    private String fuel;

    /**
     * Создает новый объект двигатель Engine.
     *
     * @param capacity объем двигателя
     * @param hp количество лошадиных сил двигателя
     * @param fuel тип топлива двигателя
     * @return возвращает объект двигателя
     */
    public static Engine of(double capacity, int hp, String fuel) {
        Engine engine = new Engine();
        engine.capacity = capacity;
        engine.hp = hp;
        engine.fuel = fuel;
        return engine;
    }
}
