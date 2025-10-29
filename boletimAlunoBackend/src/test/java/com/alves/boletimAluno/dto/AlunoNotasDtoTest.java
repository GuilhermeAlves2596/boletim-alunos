package com.alves.boletimAluno.dto;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AlunoNotasDtoTest {

    @Test
    void testAlunoNotasDto() {
        Long alunoId = 1L;
        String alunoNome = "João Silva";
        Map<Long, Double> notas = Map.of(1L, 8.5, 2L, 7.0, 3L, 9.0);
        Double media = 8.17;

        AlunoNotasDto dto = new AlunoNotasDto(alunoId, alunoNome, notas, media);

        assertEquals(alunoId, dto.getAlunoId());
        assertEquals(alunoNome, dto.getAlunoNome());
        assertEquals(notas, dto.getNotas());
        assertEquals(media, dto.getMedia());
    }

}