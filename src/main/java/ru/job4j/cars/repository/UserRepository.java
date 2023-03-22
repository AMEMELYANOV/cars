package ru.job4j.cars.repository;

import ru.job4j.cars.model.User;

import java.util.List;

/**
 * Хранилище пользователей
 * @see ru.job4j.cars.model.User
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface UserRepository {

 /**
  * Выполняет поиск и возврат пользователя по адресу электронной
  * почты пользователя.
  *
  * @param email адрес электронной почты пользователя
  * @return user пользователь
  */
 User findUserByEmail(String email);

 /**
  * Выполняет сохранение пользователя. Возвращает
  * пользователя с проинициализированным идентификатором.
  *
  * @param user пользователь
  * @return user пользователь с проинициализированным идентификатором
  */
 User save(User user);

 /**
  * Выполняет обновление и возвращение пользователя.
  *
  * @param user пользователь
  * @return user обновленный пользователь
  */
 User update(User user);

 /**
  * Выполняет поиск и возврат пользователя идентификатору.
  *
  * @param userId идентификатор пользователя
  * @return user пользователь
  */
 User findUserById(Integer userId);

 /**
  * Выполняет возврат всех пользователей.
  *
  * @return список пользователей
  */
 List<User> findAllUsers();
}
