package ru.job4j.cars.repository;

import org.assertj.core.api.Assertions;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import ru.job4j.cars.model.*;

import java.util.List;

/**
 * Тест класс реализации хранилища объявлений
 * @see ru.job4j.cars.repository.AdvertRepository
 * @author Alexander Emelyanov
 * @version 1.0
 */
class HibernateAdvertRepositoryTest {

    /**
     * Объявление 1
     */
    private Advert advertOne;

    /**
     * Объявление 2
     */
    private Advert advertTwo;

    /**
     * Пользователь
     */
    private User user;

    /**
     * Объект для доступа к методам AdvertRepository
     */
    private AdvertRepository advertRepository;

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
    SessionFactory sessionFactory() {
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
        advertRepository = new HibernateAdvertRepository(sessionFactory());
        userRepository = new HibernateUserRepository(sessionFactory());
        advertOne = Advert.of("title1", "description1", true,
                Car.of("model1",
                        Brand.of("brand1"),
                        BodyType.of("bodyType1"),
                        Engine.of(1.0, 100, "fuel1"),
                        Transmission.of("transmission1"),
                        1, 1),
                1, "city1");
        advertTwo = Advert.of("title2", "description2", true,
                Car.of("model2",
                        Brand.of("brand2"),
                        BodyType.of("bodyType2"),
                        Engine.of(2.0, 200, "fuel2"),
                        Transmission.of("transmission2"),
                        2, 2),
                2, "city2");
        user = User.of("username", "email", "password", "+79059991111");
    }

    /**
     * Сохраняется объявление, вызовом метода {@link AdvertRepository#save(Advert)},
     * далее из базы получаем список всех объявлений методом
     * {@link AdvertRepository#findAllAdverts()}.
     * Выполняем проверку размера списка и содержание элементов
     * на эквивалентность сохраненному объекту.
     */
    @Test
    public void whenSaveAdvert() {
        userRepository.save(user);
        advertOne.setUser(user);
        advertRepository.save(advertOne);
        List<Advert> adverts = advertRepository.findAllAdverts();

        Assertions.assertThat(adverts).hasSize(1);
        Assertions.assertThat(adverts.get(0)).isEqualTo(advertOne);
    }

    /**
     * Сохраняется два объявления, вызовами метода
     * {@link AdvertRepository#save(Advert)},
     * далее из базы получаем список всех объявлений методом
     * {@link AdvertRepository#findAllAdverts()}.
     * Выполняем проверку размера списка на соответствие количеству сохраненных
     * объявлений.
     */
    @Test
    public void whenFindAllAdverts() {
        userRepository.save(user);
        advertOne.setUser(user);
        advertRepository.save(advertOne);
        advertTwo.setUser(user);
        advertRepository.save(advertTwo);
        List<Advert> adverts = advertRepository.findAllAdverts();

        Assertions.assertThat(adverts).hasSize(2);
    }

    /**
     * Сохраняется объявление, вызовом метода {@link AdvertRepository#save(Advert)},
     * далее из базы получаем объявление методом {@link AdvertRepository#findAdvertById(Integer)}.
     * Выполняем проверку на эквивалентность.
     */
    @Test
    public void whenFindAdvertById() {
        userRepository.save(user);
        advertOne.setUser(user);
        Advert advert = advertRepository.save(advertOne);
        Advert rsl = advertRepository.findAdvertById(advert.getId());

        Assertions.assertThat(rsl).isEqualTo(advert);
    }

    /**
     * Сохраняется объявление, вызовом метода {@link AdvertRepository#save(Advert)},
     * далее изменяется поле title и вызывается метод {@link AdvertRepository#update(Advert)}.
     * Возвращенное объявление проверяется на эквивалентность первоначальному.
     */
    @Test
    public void whenUpdateAdvert() {
        userRepository.save(user);
        advertOne.setUser(user);
        Advert advert = advertRepository.save(advertOne);
        advert.setTitle("updatedTitle1");
        Advert rsl = advertRepository.update(advert);

        Assertions.assertThat(rsl).isEqualTo(advert);
    }

    /**
     * Сохраняется два объявления, вызовами методов {@link AdvertRepository#save(Advert)},
     * далее из базы получаем список объявлений методом {@link AdvertRepository#findAllAdverts()}.
     * Выполняем проверку списка на эквивалентность количеству сохраненных в базе объявлений.
     */
    @Test
    public void whenFindAdvertByUserId() {
        User storedUser = userRepository.save(user);
        advertOne.setUser(user);
        advertRepository.save(advertOne);
        advertTwo.setUser(user);
        advertRepository.save(advertTwo);
        List<Advert> adverts = advertRepository.findAdvertsByUserId(storedUser.getId());

        Assertions.assertThat(adverts).hasSize(2);
    }

    /**
     * Сохраняется объявление, вызовом метода {@link AdvertRepository#save(Advert)},
     * далее вызывается метод {@link AdvertRepository#deleteAdvertById(Integer)} и
     * метод {@link AdvertRepository#findAllAdverts()}, который возвращает список объявлений.
     * Размер списка объявлений проверяется на эквивалентность 0.
     */
    @Test
    public void whenDeleteAdvertById() {
        userRepository.save(user);
        advertOne.setUser(user);
        Advert advert = advertRepository.save(advertOne);
        advertRepository.deleteAdvertById(advert.getId());
        List<Advert> adverts = advertRepository.findAllAdverts();

        Assertions.assertThat(adverts).hasSize(0);
    }

    /**
     * Сохраняется два объявления, вызовом метода {@link AdvertRepository#save(Advert)},
     * далее вызывается метод
     * {@link AdvertRepository#filterAdverts(String, String, String, String)},
     * который возвращает список объявлений.
     * Размер списка объявлений проверяется на эквивалентность 1, элемент на эквивалентность
     * объявлению, по параметрам которого выполнялось фильтрование.
     */
    @Test
    public void whenFilterAdverts() {
        userRepository.save(user);
        advertOne.setUser(user);
        Advert advert = advertRepository.save(advertOne);
        advertTwo.setUser(user);
        advertRepository.save(advertTwo);
        List<Advert> adverts = advertRepository.filterAdverts(advert
                .getCar()
                .getBodyType()
                .getBodytypename(), "", "", "");

        Assertions.assertThat(adverts.get(0).getTitle()).isEqualTo("title1");
        Assertions.assertThat(adverts).hasSize(1);
    }
}