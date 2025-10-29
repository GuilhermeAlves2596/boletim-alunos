package com.alves.boletimAluno.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DisciplinaDtoTest {

    @Test
    void testDisciplinaDto() {
        DisciplinaDto disciplina = new DisciplinaDto();

        disciplina.setId(1L);
        disciplina.setNome("Matemática");

        assertEquals(1L, disciplina.getId());
        assertEquals("Matemática", disciplina.getNome());
    }

}