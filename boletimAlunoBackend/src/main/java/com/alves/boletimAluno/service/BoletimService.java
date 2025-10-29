package com.alves.boletimAluno.service;

import com.alves.boletimAluno.model.*;
import com.alves.boletimAluno.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BoletimService {

    private final AlunoRepository alunoRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final NotaRepository notaRepository;
    private final TurmaRepository turmaRepository;
    private final DisciplinaRepository disciplinaRepository;


    public List<Aluno> listarAlunosPorTurma(Long turmaId) {
        return alunoRepository.findByTurmaId(turmaId);
    }


    public List<Avaliacao> listarAvaliacoes(Long turmaId, Long disciplinaId) {
        return avaliacaoRepository.findByTurmaIdAndDisciplinaIdOrderById(turmaId, disciplinaId);
    }

    public List<Turma> listarTurmas() {
        return turmaRepository.findAll();
    }

    public List<Disciplina> listarDisciplinas() {
        return disciplinaRepository.findAll();
    }

    public Map<Long, List<Nota>> buscarNotas(List<Long> alunoIds, List<Long> avaliacaoIds) {
        List<Nota> notas = notaRepository.findByAlunoIdInAndAvaliacaoIdIn(alunoIds, avaliacaoIds);
        return notas.stream().collect(Collectors.groupingBy(Nota::getAlunoId));
    }

    @Transactional
    public void salvarNotasEmLote(List<Nota> notasToSave) {
        for (Nota n : notasToSave) {
            Optional<Nota> existente = notaRepository.findByAlunoIdAndAvaliacaoId(n.getAlunoId(), n.getAvaliacaoId());
            if (existente.isPresent()) {
                Nota e = existente.get();
                e.setValor(n.getValor());
                notaRepository.save(e);
            } else {
                notaRepository.save(n);
            }
        }
    }

    public List<Disciplina> listarDisciplinasPorTurma(Long turmaId) {
        List<Avaliacao> avals = avaliacaoRepository.findByTurmaId(turmaId);
        List<Long> disciplinaIds = avals.stream()
                .map(Avaliacao::getDisciplinaId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (disciplinaIds.isEmpty()) return Collections.emptyList();
        return disciplinaRepository.findAllById(disciplinaIds);
    }


    public Double calcularMediaPonderadaParaAluno(List<Nota> notasDoAluno, List<Avaliacao> avaliacoes) {
        if (notasDoAluno == null || notasDoAluno.isEmpty()) return null;
        Map<Long, Integer> pesoByAvaliacao = avaliacoes.stream()
                .collect(Collectors.toMap(Avaliacao::getId, Avaliacao::getPeso));


        double somaPesos = 0.0;
        double somaPonderada = 0.0;


        for (Nota n : notasDoAluno) {
            Integer peso = pesoByAvaliacao.get(n.getAvaliacaoId());
            if (peso == null) continue;
            if (n.getValor() == null) continue;
            somaPonderada += n.getValor() * peso;
            somaPesos += peso;
        }


        if (somaPesos == 0.0) return null;
        return Math.round((somaPonderada / somaPesos) * 10.0) / 10.0;
    }
}
