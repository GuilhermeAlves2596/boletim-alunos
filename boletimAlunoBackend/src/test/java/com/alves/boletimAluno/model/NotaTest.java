package com.alves.boletimAluno.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotaTest {

    @Test
    void testNotaGettersAndSetters() {
        Nota nota = new Nota();

        nota.setId(1L);
        nota.setValor(9.5);
        nota.setAlunoId(2L);
        nota.setAvaliacaoId(3L);

        assertEquals(1L, nota.getId());
        assertEquals(9.5, nota.getValor());
        assertEquals(2L, nota.getAlunoId());
        assertEquals(3L, nota.getAvaliacaoId());
    }

}