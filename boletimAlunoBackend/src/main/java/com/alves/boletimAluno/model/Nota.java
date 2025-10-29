package com.alves.boletimAluno.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nota", uniqueConstraints = @UniqueConstraint(columnNames = {"aluno_id", "avaliacao_id"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aluno_id")
    private Long alunoId;

    @Column(name = "avaliacao_id")
    private Long avaliacaoId;

    private Double valor;

}
