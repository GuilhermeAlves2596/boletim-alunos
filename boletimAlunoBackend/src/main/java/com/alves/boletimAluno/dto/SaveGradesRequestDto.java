package com.alves.boletimAluno.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveGradesRequestDto {
    @NotNull
    private Long turmaId;
    @NotNull
    private Long disciplinaId;
    @NotNull
    private List<GradeDto> grades;

}
