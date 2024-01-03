package com.practice.ts.PracticeTwo;

import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;

@SpringBootApplication
public class PracticeTwoApplication {

    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .modulesToInstall(new ParameterNamesModule());
        var converter = new MappingJackson2HttpMessageConverter(builder.build());
        restTemplate.getMessageConverters().add(converter);

        return restTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(PracticeTwoApplication.class, args);
    }


}
