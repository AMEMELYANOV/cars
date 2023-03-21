package ru.job4j.cars.repository;

import org.assertj.core.api.Assertions;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import ru.job4j.cars.model.User;

import java.util.List;

class UserRepositoryTest {
    private User userOne;
    private User userTwo;

    private UserRepository userRepository;

    @Bean(destroyMethod = "close")
    SessionFactory sf() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository(sf());
        userOne = User.of("user1", "email1",
                "password1", "+79051111111");
        userTwo = User.of("user2", "email2",
                "password2", "+79051111112");
    }

    @Test
    void whenUserAdd() {
        userRepository.add(userOne);
        User rsl = userRepository.findUserByEmail("email1");
        List<User> users = userRepository.getAllUsers();

        Assertions.assertThat(users).hasSize(1);
        Assertions.assertThat(userOne).isEqualTo(rsl);
    }

    @Test
    void whenGetUsersAll() {
        userRepository.add(userOne);
        userRepository.add(userTwo);
        List<User> users = userRepository.getAllUsers();

        Assertions.assertThat(users).hasSize(2);
    }

    @Test
    void whenFindUserByEmailSuccess() {
        userRepository.add(userOne);
        User rsl = userRepository.findUserByEmail(userOne.getEmail());

        Assertions.assertThat(userOne).isEqualTo(rsl);
    }

    @Test
    void whenFindUserByEmailFail() {
        userRepository.add(userOne);
        User rsl = userRepository.findUserByEmail(userTwo.getEmail());

        Assertions.assertThat(rsl).isNull();
    }

    @Test
    void whenFindUserByIdSuccess() {
        User user = userRepository.add(userOne);
        User rsl = userRepository.findUserById(user.getId());

        Assertions.assertThat(userOne).isEqualTo(rsl);
    }

    @Test
    void whenFindUserByIdFail() {
        User user = userRepository.add(userOne);
        User rsl = userRepository.findUserById(user.getId() + 1);

        Assertions.assertThat(rsl).isNull();
    }

    @Test
    void whenUserUpdate() {
        User updatedUser = userRepository.add(userOne);
        updatedUser.setEmail("updatedEmail");
        User rsl = userRepository.update(updatedUser);

        Assertions.assertThat(rsl).isEqualTo(updatedUser);
    }
}