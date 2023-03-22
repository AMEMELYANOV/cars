package ru.job4j.cars.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Модель данных трансмиссия
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Entity
@Table(name = "transmissions")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class Transmission {

    /**
     * Идентификатор трансмиссии
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Наименование трансмиссии
     */
    private String transmissionname;

    /**
     * Создает новый объект трансмиссии Transmission.
     *
     * @param transmissionname наименование трансмиссии
     * @return возвращает объект трансмиссии
     */
    public static Transmission of(String transmissionname) {
        Transmission transmission = new Transmission();
        transmission.transmissionname = transmissionname;
        return transmission;
    }
}
