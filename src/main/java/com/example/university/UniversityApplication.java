package com.example.university;

import com.example.university.console.ConsoleHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class UniversityApplication implements CommandLineRunner {

    private final ConsoleHandler consoleHandler;

    public static void main(String[] args) {
        SpringApplication.run(UniversityApplication.class, args);
    }

    @Override
    public void run(String... args) {
        consoleHandler.startConsole();
    }
}
