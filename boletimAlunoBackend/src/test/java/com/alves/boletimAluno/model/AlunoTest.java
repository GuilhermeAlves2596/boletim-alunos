package com.alves.boletimAluno.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlunoTest {

    @Test
    void testAluno() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("João Silva");
        aluno.setTurmaId(101L);

        assertEquals(1L, aluno.getId());
        assertEquals("João Silva", aluno.getNome());
        assertEquals(101L, aluno.getTurmaId());
    }

}