package ru.job4j.cars.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Модель данных брэнд
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Entity
@Table(name = "brands")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class Brand {

    /**
     * Идентификатор брэнда
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Наименование брэнда
     */
    private String brandname;

    /**
     * Создает новый объект брэнда Brand.
     *
     * @param name наименование брэнда
     * @return возвращает объект брэнда
     */
    public static Brand of(String name) {
        Brand brand = new Brand();
        brand.brandname = name;
        return brand;
    }
}