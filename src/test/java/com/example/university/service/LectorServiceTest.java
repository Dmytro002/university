package com.example.university.service;

import com.example.university.domain.model.Lector;
import com.example.university.repository.LectorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;

import static com.example.university.util.ObjectMapperUtils.toObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest(classes = LectorService.class)
class LectorServiceTest {

    private static final String LECTOR_1 = "lector-1";
    private static final String LECTOR_2 = "lector-2";
    private static final String LECTOR_HEAD = "lector-head";

    @MockBean
    private LectorRepository lectorRepository;

    @Autowired
    private LectorService lectorService;

    @Test
    void testGlobalSearch_LectorsFound() {
        String template = "lector";

        given(lectorRepository.findByNameContainingIgnoreCase(template))
                .willReturn(List.of(
                        toObject(LECTOR_1, Lector.class),
                        toObject(LECTOR_2, Lector.class),
                        toObject(LECTOR_HEAD, Lector.class)
                ));

        String result = lectorService.globalSearch(template);

        then(lectorRepository).should().findByNameContainingIgnoreCase(template);
        assertEquals("Lector One, Lector Two, Head Lector", result);
    }

    @Test
    void testGlobalSearch_NoLectorsFound() {
        String template = "unknown";

        given(lectorRepository.findByNameContainingIgnoreCase(template))
                .willReturn(Collections.emptyList());

        String result = lectorService.globalSearch(template);

        then(lectorRepository).should().findByNameContainingIgnoreCase(template);
        assertEquals("No lectors found.", result);
    }

    @Test
    void testGlobalSearch_PartialMatchFound() {
        String template = "one";

        given(lectorRepository.findByNameContainingIgnoreCase(template))
                .willReturn(List.of(
                        toObject(LECTOR_1, Lector.class)
                ));

        String result = lectorService.globalSearch(template);

        then(lectorRepository).should().findByNameContainingIgnoreCase(template);
        assertEquals("Lector One", result);
    }

    @Test
    void testGlobalSearch_CaseInsensitive() {
        String template = "TWO";

        given(lectorRepository.findByNameContainingIgnoreCase(template))
                .willReturn(List.of(
                        toObject(LECTOR_2, Lector.class)
                ));

        String result = lectorService.globalSearch(template);

        then(lectorRepository).should().findByNameContainingIgnoreCase(template);
        assertEquals("Lector Two", result);
    }
}
