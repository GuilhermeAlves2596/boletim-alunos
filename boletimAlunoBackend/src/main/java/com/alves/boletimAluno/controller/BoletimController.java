package com.alves.boletimAluno.controller;

import com.alves.boletimAluno.dto.*;
import com.alves.boletimAluno.model.Aluno;
import com.alves.boletimAluno.model.Avaliacao;
import com.alves.boletimAluno.model.Nota;
import com.alves.boletimAluno.service.BoletimService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/boletim")
@AllArgsConstructor
public class BoletimController {

    private final BoletimService boletimService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BoletimResponseDto> buscarBoletim(
            @RequestParam Long turmaId,
            @RequestParam Long disciplinaId) {

        List<Aluno> alunos = boletimService.listarAlunosPorTurma(turmaId);
        List<Avaliacao> avaliacoes = boletimService.listarAvaliacoes(turmaId, disciplinaId);

        List<Long> alunoIds = alunos.stream().map(Aluno::getId).toList();
        List<Long> avaliacaoIds = avaliacoes.stream().map(Avaliacao::getId).toList();
        Map<Long, List<Nota>> notasPorAluno = boletimService.buscarNotas(alunoIds, avaliacaoIds);

        List<AlunoNotasDto> alunosDto = alunos.stream().map(a -> {
            AlunoNotasDto dto = new AlunoNotasDto();
            dto.setAlunoId(a.getId());
            dto.setAlunoNome(a.getNome());

            Map<Long, Double> notasMap = new HashMap<>();
            List<Nota> notasDoAluno = notasPorAluno.getOrDefault(a.getId(), List.of());
            for (Avaliacao av : avaliacoes) {
                notasMap.put(
                        av.getId(),
                        notasDoAluno.stream()
                                .filter(n -> n.getAvaliacaoId().equals(av.getId()))
                                .map(Nota::getValor)
                                .findFirst()
                                .orElse(null)
                );
            }

            dto.setNotas(notasMap);
            dto.setMedia(boletimService.calcularMediaPonderadaParaAluno(notasDoAluno, avaliacoes));
            return dto;
        }).toList();

        List<AvaliacaoDto> avaliacoesDto = avaliacoes.stream().map(av -> {
            AvaliacaoDto dto = new AvaliacaoDto();
            dto.setId(av.getId());
            dto.setNome(av.getNome());
            dto.setPeso(av.getPeso());
            return dto;
        }).toList();

        BoletimResponseDto resp = new BoletimResponseDto();
        resp.setAlunos(alunosDto);
        resp.setAvaliacoes(avaliacoesDto);

        return ResponseEntity.ok(resp);
    }


    @PostMapping("/save")
    public ResponseEntity<?> salvarNotas(@Valid @RequestBody SaveGradesRequestDto req) {

        List<Nota> notasToSave = req.getGrades().stream().map(g -> {
            if (g.getValor() < 0 || g.getValor() > 10) throw new IllegalArgumentException("Nota fora do intervalo 0-10");
            Nota nota = new Nota();
            nota.setAlunoId(g.getAlunoId());
            nota.setAvaliacaoId(g.getAvaliacaoId());
            nota.setValor(g.getValor());
            return nota;
        }).collect(Collectors.toList());

        boletimService.salvarNotasEmLote(notasToSave);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/turmas")
    public ResponseEntity<List<TurmaDto>> listarTurmas() {
        List<TurmaDto> out = boletimService.listarTurmas().stream().map(t -> {
            TurmaDto dto = new TurmaDto();
            dto.setId(t.getId());
            dto.setNome(t.getNome());
            return dto;
        }).toList();
        return ResponseEntity.ok(out);
    }

    @GetMapping("/disciplinas")
    public ResponseEntity<List<DisciplinaDto>> listarDisciplinas(@RequestParam(required = false) Long turmaId) {
        List<DisciplinaDto> out = (turmaId == null
                ? boletimService.listarDisciplinas()
                : boletimService.listarDisciplinasPorTurma(turmaId))
                .stream()
                .map(d -> {
                    DisciplinaDto dto = new DisciplinaDto();
                    dto.setId(d.getId());
                    dto.setNome(d.getNome());
                    return dto;
                }).toList();

        return ResponseEntity.ok(out);
    }
}
