package com.alves.boletimAluno.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaveGradesRequestDtoTest {

    GradeDto gradeDto;

    @BeforeEach
    void setUp() {
        gradeDto = new GradeDto(1L, 1L, 8.5);
    }

    @Test
    void testSaveGradesRequestDto(){
        SaveGradesRequestDto requestDto = new SaveGradesRequestDto();
        requestDto.setTurmaId(1L);
        requestDto.setDisciplinaId(1L);
        requestDto.setGrades(java.util.List.of(gradeDto));

        assertEquals(1L, requestDto.getTurmaId());
        assertEquals(1L, requestDto.getDisciplinaId());
        assertNotNull(requestDto.getGrades());
        assertEquals(1, requestDto.getGrades().size());
        assertEquals(8.5, requestDto.getGrades().get(0).getValor());
    }
}