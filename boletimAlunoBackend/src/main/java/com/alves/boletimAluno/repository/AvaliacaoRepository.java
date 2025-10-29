package com.alves.boletimAluno.repository;

import com.alves.boletimAluno.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    List<Avaliacao> findByTurmaIdAndDisciplinaIdOrderById(Long turmaId, Long disciplinaId);

    List<Avaliacao> findByTurmaId(Long turmaId);
}
