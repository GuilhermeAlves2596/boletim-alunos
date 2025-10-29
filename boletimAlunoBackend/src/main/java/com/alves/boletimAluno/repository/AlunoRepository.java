package com.alves.boletimAluno.repository;

import com.alves.boletimAluno.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByTurmaId(Long turmaId);
}
