package com.yetihaulingfreight.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(
		exclude = {
				org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
				org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class
		}
)
@EnableScheduling
@EnableAsync
public class BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}
