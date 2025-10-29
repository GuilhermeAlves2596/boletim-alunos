package com.alves.boletimAluno.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DisciplinaTest {

    @Test
    void testDisciplinaGettersAndSetters() {
        Disciplina disciplina = new Disciplina();

        disciplina.setId(1L);
        disciplina.setNome("Matemática");

        assertEquals(1L, disciplina.getId());
        assertEquals("Matemática", disciplina.getNome());
    }

}