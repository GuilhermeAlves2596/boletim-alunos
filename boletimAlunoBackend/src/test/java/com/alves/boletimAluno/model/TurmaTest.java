package com.alves.boletimAluno.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurmaTest {

    @Test
    void testTurmaGettersAndSetters() {
        Turma turma = new Turma();

        turma.setId(1L);
        turma.setNome("Turma A");

        assertEquals(1L, turma.getId());
        assertEquals("Turma A", turma.getNome());
    }

}