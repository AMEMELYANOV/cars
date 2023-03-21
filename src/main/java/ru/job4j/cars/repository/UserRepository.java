package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.function.Function;

@Repository
public class UserRepository implements UserRep {

    private final SessionFactory sf;

    public UserRepository(SessionFactory sf) {
        this.sf = sf;
    }

    private <T> T execute(final Function<Session, T> command) {
        final Session session = sf.openSession();
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

    @Override
    public User add(User user) {
        return this.execute(
                session -> {
                    session.save(user);
                    return user;
                }
        );
    }

    @Override
    public User update(User user) {
        return this.execute(
                session -> {
                    session.update(user);
                    return user;
                }
        );
    }

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

    @Override
    public List<User> getAllUsers() {
        return this.execute(
                session -> {
                    Query query = session.createQuery("from User", User.class);
                    return query.list();
                }
        );
    }
}
