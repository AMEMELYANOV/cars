package ru.job4j.cars.repository;

import org.assertj.core.api.Assertions;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import ru.job4j.cars.model.Advert;
import ru.job4j.cars.model.User;

import java.util.List;

/**
 * Тест класс реализации хранилища объявлений
 * @see ru.job4j.cars.repository.UserRepository
 * @author Alexander Emelyanov
 * @version 1.0
 */
class UserRepositoryTest {

    /**
     * Пользователь 1
     */
    private User userOne;

    /**
     * Пользователь 2
     */
    private User userTwo;

    /**
     * Объект для доступа к методам UserRepository
     */
    private UserRepository userRepository;

    /**
     * Создание объекта (bean), используемого для
     * подключения к базе данных приложения,
     * параметры считываются из файла /resources/hibernate.cfg.xml
     *
     * @return объект (фабрика сессий)
     */
    @Bean(destroyMethod = "close")
    SessionFactory sf() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        userRepository = new HibernateUserRepository(sf());
        userOne = User.of("user1", "email1",
                "password1", "+79051111111");
        userTwo = User.of("user2", "email2",
                "password2", "+79051111112");
    }

    /**
     * Сохраняется пользователь, вызовом метода {@link UserRepository#save(User)},
     * далее из базы получаем пользователя методом
     * {@link UserRepository#findUserByEmail(String)}.
     * Выполняем проверку эквивалентности сохраненного объекта и полученного из базы.
     */
    @Test
    void whenUserAdd() {
        userRepository.save(userOne);
        User rsl = userRepository.findUserByEmail("email1");

        Assertions.assertThat(userOne).isEqualTo(rsl);
    }

    /**
     * Сохраняется два пользователя, вызовами метода {@link UserRepository#save(User)},
     * далее из базы получаем список всех пользователей методом
     * {@link UserRepository#findAllUsers()}.
     * Выполняем проверку размера списка на соответствие количеству сохраненных
     * пользователей.
     */
    @Test
    void whenFindUsersAll() {
        userRepository.save(userOne);
        userRepository.save(userTwo);
        List<User> users = userRepository.findAllUsers();

        Assertions.assertThat(users).hasSize(2);
    }

    /**
     * Сохраняется пользователя, вызовом метода {@link UserRepository#save(User)},
     * далее из базы получаем пользователя методом {@link UserRepository#findUserByEmail(String)}.
     * Выполняем проверку полученного объекта на эквивалентность сохраненному объекту.
     */
    @Test
    void whenFindUserByEmailSuccess() {
        userRepository.save(userOne);
        User rsl = userRepository.findUserByEmail(userOne.getEmail());

        Assertions.assertThat(userOne).isEqualTo(rsl);
    }

    /**
     * Сохраняется пользователя, вызовом метода {@link UserRepository#save(User)},
     * далее из базы получаем пользователя методом {@link UserRepository#findUserByEmail(String)}
     * с другим email.
     * Выполняем проверку полученного объекта на эквивалентность null.
     */
    @Test
    void whenFindUserByEmailFail() {
        userRepository.save(userOne);
        User rsl = userRepository.findUserByEmail(userTwo.getEmail());

        Assertions.assertThat(rsl).isNull();
    }

    /**
     * Сохраняется пользователя, вызовом метода {@link UserRepository#save(User)},
     * далее из базы получаем пользователя методом {@link UserRepository#findUserById(Integer)}.
     * Выполняем проверку полученного объекта на эквивалентность сохраненному объекту.
     */
    @Test
    void whenFindUserByIdSuccess() {
        User user = userRepository.save(userOne);
        User rsl = userRepository.findUserById(user.getId());

        Assertions.assertThat(userOne).isEqualTo(rsl);
    }

    /**
     * Сохраняется пользователя, вызовом метода {@link UserRepository#save(User)},
     * далее из базы получаем пользователя методом {@link UserRepository#findUserById(Integer)}.
     * с другим id.
     * Выполняем проверку полученного объекта на эквивалентность null.
     */
    @Test
    void whenFindUserByIdFail() {
        User user = userRepository.save(userOne);
        User rsl = userRepository.findUserById(user.getId() + 1);

        Assertions.assertThat(rsl).isNull();
    }

    /**
     * Сохраняется пользователь, вызовом метода {@link UserRepository#save(User)},
     * далее изменяется поле email и вызывается метод {@link UserRepository#update(User)}.
     * Возвращенный пользователь проверяется на эквивалентность начальному пользователю.
     */
    @Test
    void whenUserUpdate() {
        User updatedUser = userRepository.save(userOne);
        updatedUser.setEmail("updatedEmail");
        User rsl = userRepository.update(updatedUser);

        Assertions.assertThat(rsl).isEqualTo(updatedUser);
    }
}