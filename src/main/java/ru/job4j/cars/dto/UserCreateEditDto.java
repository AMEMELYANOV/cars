package ru.job4j.cars.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * DTO пользователя для создания или обновления
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateEditDto {

    /**
     * Имя пользователя
     */
    @NotBlank(message = "Поле не должно быть пустым")
    @Size(min = 5, message = "Имя пользователя должно быть не менее 5 символов")
    private String username;

    /**
     *  Адрес электронной почты пользователя
     */
    @Column(unique = true)
    @NotBlank(message = "Поле не должно быть пустым")
    private String email;

    /**
     * Пароль пользователя
     */
    @NotBlank(message = "Поле не должно быть пустым")
    @Size(min = 4, message = "Пароль пользователя должен быть не менее 4 символов")
    private String password;

    /**
     * Телефонный номер пользователя
     */
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "\\+\\d{11}",
            message = "Должно быть в формате \"+\" и 11 значный номер")
    private String phonenumber;

}
