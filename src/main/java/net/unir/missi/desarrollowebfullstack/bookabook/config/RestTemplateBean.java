package net.unir.missi.desarrollowebfullstack.bookabook.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateBean {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
