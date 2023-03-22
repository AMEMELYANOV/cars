package ru.job4j.cars.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Модель данных тип кузова
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Entity
@Table(name = "bodytypes")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class BodyType {

    /**
     * Идентификатор типа кузова
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Наименование типа кузова
     */
    private String bodytypename;

    /**
     * Создает новый объект тип кузова BodyType.
     *
     * @param bodytypename наименование кузова
     * @return возвращает объект тип кузова
     */
    public static BodyType of(String bodytypename) {
        BodyType bodyType = new BodyType();
        bodyType.bodytypename = bodytypename;
        return bodyType;
    }
}
