package ru.job4j.cars;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Application {

	static final Logger log = LoggerFactory.getLogger(Application.class);

	@Bean(destroyMethod = "close")
	public SessionFactory sf() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure().build();
		return new MetadataSources(registry).buildMetadata().buildSessionFactory();
	}

	public static void main(String[] args) {
		log.info("Before Starting application");
		SpringApplication.run(Application.class, args);
		log.debug("Starting my application in debug with {} args", args.length);
		log.info("Starting my application with {} args.", args.length);
		System.out.println("Go to http://localhost:8080");
	}

}
