package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.function.Function;

/**
 * Реализация хранилища пользователей
 * @see ru.job4j.cars.repository.UserRepository
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
@Repository
public class HibernateUserRepository implements UserRepository {

    /**
     * Объект для выполнения подключения к базе данных приложения
     */
    private final SessionFactory sessionFactory;

    /**
     * Выполняет переданный метод оборачиваю в транзакцию
     *
     * @param command выполняемый метод
     * @return объект результат выполнения переданного метода
     */
    private <T> T execute(final Function<Session, T> command) {
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            transaction.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * Выполняет поиск и возврат пользователя по адресу электронной
     * почты пользователя.
     *
     * @param email адрес электронной почты пользователя
     * @return user пользователь
     */
    @Override
    public User findUserByEmail(String email) {
        return this.execute(
                session -> {
                    Query query = session.createQuery("from User where email = :email");
                    query.setParameter("email", email);
                    return (User) query.uniqueResult();
                }
        );
    }

    /**
     * Выполняет сохранение пользователя. Возвращает
     * пользователя с проинициализированным идентификатором.
     *
     * @param user пользователь
     * @return user пользователь с проинициализированным идентификатором
     */
    @Override
    public User save(User user) {
        return this.execute(
                session -> {
                    session.save(user);
                    return user;
                }
        );
    }

    /**
     * Выполняет обновление и возвращение пользователя.
     *
     * @param user пользователь
     * @return user обновленный пользователь
     */
    @Override
    public User update(User user) {
        return this.execute(
                session -> {
                    session.update(user);
                    return user;
                }
        );
    }

    /**
     * Выполняет поиск и возврат пользователя идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return user пользователь
     */
    @Override
    public User findUserById(Integer userId) {
        return this.execute(
                session -> {
                    Query query = session.createQuery("from User where id = :userId");
                    query.setParameter("userId", userId);
                    return (User) query.uniqueResult();
                }
        );
    }

    /**
     * Выполняет возврат всех пользователей.
     *
     * @return список пользователей
     */
    @Override
    public List<User> findAllUsers() {
        return this.execute(
                session -> {
                    Query query = session.createQuery("from User", User.class);
                    return query.list();
                }
        );
    }
}
