package com.alves.boletimAluno.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoletimResponseDto {

    private List<AlunoNotasDto> alunos;
    private List<AvaliacaoDto> avaliacoes;
}
