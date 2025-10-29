package com.alves.boletimAluno.dto;

import com.alves.boletimAluno.model.Avaliacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeDtoTest {

    Avaliacao avaliacao;

    @BeforeEach
    void setUp() {
        avaliacao = new Avaliacao();
        avaliacao.setId(1L);
        avaliacao.setNome("Prova Bimestral");
        avaliacao.setPeso(3);
    }

    @Test
    void testGradeDto() {
        GradeDto grade = new GradeDto();

        grade.setAlunoId(1L);
        grade.setAvaliacaoId(avaliacao.getId());
        grade.setValor(9.5);

        assertEquals(1L, grade.getAlunoId());
        assertEquals(1L, grade.getAvaliacaoId());
    }

}