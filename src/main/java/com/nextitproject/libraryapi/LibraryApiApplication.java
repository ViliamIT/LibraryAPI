package com.nextitproject.libraryapi;

import com.nextitproject.libraryapi.backend.repository.UserInfoRepository;
import com.nextitproject.libraryapi.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication//(exclude = SecurityAutoConfiguration.class)
@PropertySource("classpath:application.properties")
//@Import(AppConfig.class)
//@ComponentScan(basePackages = {"com.nextitproject.libraryapi.backend.repository.memory", "com.nextitproject.libraryapi.backend.filter", "com.nextitproject.libraryapi.backend.service"})
@ComponentScan(basePackages = {"com.nextitproject.libraryapi.backend.auth", "com.nextitproject.libraryapi.backend.entity", "com.nextitproject.libraryapi.backend.filter", "com.nextitproject.libraryapi.backend.repository", "com.nextitproject.libraryapi.backend.service", "com.nextitproject.libraryapi.backend.storage", "com.nextitproject.libraryapi.config", "com.nextitproject.libraryapi.frontend"})
//@EnableJpaRepositories(basePackageClasses = UserInfoRepository.class)
        public class LibraryApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApiApplication.class, args);
    }
    @Bean
    public AppProperties appProperties(){
        return new AppProperties();
    }
}
