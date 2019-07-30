package ru.flexconstructor.AMZScout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * AMSZScout test application.
 */
@SpringBootApplication
@EnableScheduling
public class AmzScoutApplication {

	/**
	 * Starts app.
	 *
	 * @param args some args.
	 */
	public static void main(String[] args) {
		SpringApplication.run(AmzScoutApplication.class, args);
	}

}
