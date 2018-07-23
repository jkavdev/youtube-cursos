package br.com.jkavdev.angularjs.listatelefonicaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ListaTelefonicaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListaTelefonicaApiApplication.class, args);
	}
}
