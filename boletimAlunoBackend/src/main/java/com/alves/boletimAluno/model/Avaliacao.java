package com.alves.boletimAluno.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "avaliacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Avaliacao {

    @Id
    private Long id;
    private String nome;
    private Integer peso;
    @Column(name = "turma_id")
    private Long turmaId;
    @Column(name = "disciplina_id")
    private Long disciplinaId;

}
