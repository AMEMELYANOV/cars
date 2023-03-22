package ru.job4j.cars;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Основной класс для запуска приложения
 * @author Alexander Emelyanov
 * @version 1.0
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Application {

	/**
	 * Логгер
	 */
	static final Logger log = LoggerFactory.getLogger(Application.class);

	/**
	 * Выполняет запуск приложения
	 * @param args аргументы командной строки
	 */
	public static void main(String[] args) {
		log.info("Before Starting application");
		SpringApplication.run(Application.class, args);
		log.debug("Starting my application in debug with {} args", args.length);
		log.info("Starting my application with {} args.", args.length);
		System.out.println("Go to http://localhost:8080");
	}

}
