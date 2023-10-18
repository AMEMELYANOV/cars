package ru.job4j.cars.dto;

import lombok.*;

/**
 * DTO пользователя для чтения/отправки в/из веб-форм
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserReadDto {

    /**
     * Идентификатор пользователя
     */
    private int id;

    /**
     * Имя пользователя
     */
    private String username;

    /**
     *  Адрес электронной почты пользователя
     */
    private String email;

    /**
     * Пароль пользователя
     */
    private String password;

    /**
     * Телефонный номер пользователя
     */
    private String phonenumber;

    /**
     * Статус пользователя
     */
    private boolean active;
}
