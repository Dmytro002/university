package com.example.university.service;

import com.example.university.domain.model.Lector;
import com.example.university.repository.LectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectorService {

    private final LectorRepository lectorRepository;

    public String globalSearch(String template) {
        return lectorRepository.findByNameContainingIgnoreCase(template)
                .stream()
                .map(Lector::getName)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        lectors -> lectors.isEmpty()
                                ? "No lectors found."
                                : String.join(", ", lectors)
                ));
    }
}