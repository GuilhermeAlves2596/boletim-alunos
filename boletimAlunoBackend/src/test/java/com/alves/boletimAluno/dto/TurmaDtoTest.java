package com.alves.boletimAluno.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurmaDtoTest {

    @Test
    void testTurmaDto() {
        TurmaDto turma = new TurmaDto();

        turma.setId(1L);
        turma.setNome("Turma A");

        assertEquals(1L, turma.getId());
        assertEquals("Turma A", turma.getNome());
    }

}