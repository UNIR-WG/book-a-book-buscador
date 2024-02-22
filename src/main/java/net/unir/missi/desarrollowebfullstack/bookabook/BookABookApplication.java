package net.unir.missi.desarrollowebfullstack.bookabook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class BookABookApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookABookApplication.class, args);
	}

}
