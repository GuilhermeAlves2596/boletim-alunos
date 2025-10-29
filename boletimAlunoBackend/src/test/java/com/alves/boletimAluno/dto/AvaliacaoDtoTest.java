package com.alves.boletimAluno.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AvaliacaoDtoTest {

    @Test
    void testAvaliacaoDto() {
        AvaliacaoDto avaliacao = new AvaliacaoDto();

        avaliacao.setId(1L);
        avaliacao.setNome("Prova Final");
        avaliacao.setPeso(5);

        assertEquals(1L, avaliacao.getId());
        assertEquals("Prova Final", avaliacao.getNome());
        assertEquals(5, avaliacao.getPeso());
    }

}