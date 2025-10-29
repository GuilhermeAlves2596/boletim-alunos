package com.alves.boletimAluno.repository;

import com.alves.boletimAluno.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    Optional<Nota> findByAlunoIdAndAvaliacaoId(Long alunoId, Long avaliacaoId);
    List<Nota> findByAlunoIdInAndAvaliacaoIdIn(List<Long> alunoIds, List<Long> avaliacaoIds);
}
