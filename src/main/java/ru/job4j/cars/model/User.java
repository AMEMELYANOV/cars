package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель данных пользователь
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    /**
     * Список объявлений пользователя
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Advert> adverts = new ArrayList<>();

    /**
     * Статус пользователя
     */
    private boolean active;

    /**
     * Создает новый объект пользователя User.
     *
     * @param username имя пользователя
     * @param email адрес электронной почты пользователя
     * @param password пароль пользователя
     * @param phonenumber телефонный номер пользователя
     * @return возвращает объект пользователя
     */
    public static User of(String username, String email, String password, String phonenumber) {
        User user = new User();
        user.username = username;
        user.email = email;
        user.password = password;
        user.phonenumber = phonenumber;
        return user;
    }

    /**
     * Добавляет объявление в список объявлений пользователя User.
     *
     * @param advert объявление
     */
    public void addAdvert(Advert advert) {
        this.adverts.add(advert);
        advert.setUser(this);
    }
}
