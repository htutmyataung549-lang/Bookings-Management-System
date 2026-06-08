package com.example.event_booking_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.event_booking_system")
public class EventBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventBookingSystemApplication.class, args);
	}

}
