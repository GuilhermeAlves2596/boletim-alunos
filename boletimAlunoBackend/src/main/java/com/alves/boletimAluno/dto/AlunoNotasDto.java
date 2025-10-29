package com.alves.boletimAluno.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlunoNotasDto {

    private Long alunoId;
    private String alunoNome;
    private Map<Long, Double> notas;
    private Double media;
}
