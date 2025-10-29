package com.alves.boletimAluno.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AvaliacaoTest {

    @Test
    void testAvaliacaoGettersAndSetters() {
        Avaliacao avaliacao = new Avaliacao();

        avaliacao.setId(1L);
        avaliacao.setNome("Prova 1");
        avaliacao.setPeso(2);
        avaliacao.setTurmaId(101L);
        avaliacao.setDisciplinaId(202L);

        assertEquals(1L, avaliacao.getId());
        assertEquals("Prova 1", avaliacao.getNome());
        assertEquals(2, avaliacao.getPeso());
        assertEquals(101L, avaliacao.getTurmaId());
        assertEquals(202L, avaliacao.getDisciplinaId());
    }

}