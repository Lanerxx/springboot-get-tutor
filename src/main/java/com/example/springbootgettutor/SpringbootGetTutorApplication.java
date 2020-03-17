package com.example.springbootgettutor;

import com.example.springbootgettutor.repository.BaseRepository;
import com.example.springbootgettutor.repository.impl.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class SpringbootGetTutorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootGetTutorApplication.class, args);
    }

}
