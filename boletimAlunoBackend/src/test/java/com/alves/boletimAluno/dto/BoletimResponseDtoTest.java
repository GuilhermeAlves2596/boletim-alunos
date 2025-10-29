package com.alves.boletimAluno.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoletimResponseDtoTest {

    AlunoNotasDto alunoNotasDto;
    AvaliacaoDto avaliacao;

    @BeforeEach
    void setUp() {
        Long alunoId = 1L;
        String alunoNome = "João Silva";
        Map<Long, Double> notas = Map.of(1L, 8.5, 2L, 7.0, 3L, 9.0);
        Double media = 8.17;

        alunoNotasDto = new AlunoNotasDto(alunoId, alunoNome, notas, media);

        avaliacao = new AvaliacaoDto(1L, "Prova Final", 5);
    }

    @Test
    void testBoletimResponseDto() {
        BoletimResponseDto boletim = new BoletimResponseDto();

        boletim.setAlunos(List.of(alunoNotasDto));
        boletim.setAvaliacoes(List.of(avaliacao));

        assertEquals(1, boletim.getAlunos().size());
        assertEquals(1, boletim.getAvaliacoes().size());

    }


}