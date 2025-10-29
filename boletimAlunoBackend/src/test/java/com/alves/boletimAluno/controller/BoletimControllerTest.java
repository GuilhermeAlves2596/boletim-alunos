package com.alves.boletimAluno.controller;

import com.alves.boletimAluno.dto.GradeDto;
import com.alves.boletimAluno.dto.SaveGradesRequestDto;
import com.alves.boletimAluno.exception.GlobalExceptionHandler;
import com.alves.boletimAluno.model.*;
import com.alves.boletimAluno.service.BoletimService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class BoletimControllerStandaloneTest {

    private MockMvc mockMvc;

    @Mock
    private BoletimService boletimService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private BoletimController controller;

    private final MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setMessageConverters(jacksonConverter)
                .build();
    }

    private Aluno aluno(Long id, String nome, Long turmaId) {
        Aluno a = new Aluno();
        a.setId(id);
        a.setNome(nome);
        a.setTurmaId(turmaId);
        return a;
    }

    private Avaliacao avaliacao(Long id, String nome, Integer peso, Long turmaId, Long disciplinaId) {
        Avaliacao a = new Avaliacao();
        a.setId(id);
        a.setNome(nome);
        a.setPeso(peso);
        a.setTurmaId(turmaId);
        a.setDisciplinaId(disciplinaId);
        return a;
    }

    private Turma turma(Long id, String nome) {
        Turma t = new Turma();
        t.setId(id);
        t.setNome(nome);
        return t;
    }

    private Disciplina disciplina(Long id, String nome) {
        Disciplina d = new Disciplina();
        d.setId(id);
        d.setNome(nome);
        return d;
    }

    private Nota nota(Long id, Long alunoId, Long avaliacaoId, Double valor) {
        Nota n = new Nota();
        n.setId(id);
        n.setAlunoId(alunoId);
        n.setAvaliacaoId(avaliacaoId);
        n.setValor(valor);
        return n;
    }


    @Test
    void testBuscarBoletim() throws Exception {
        Long turmaId = 1L;
        Long disciplinaId = 1L;

        List<Aluno> alunos = List.of(aluno(1L, "João Silva", turmaId), aluno(2L, "Maria", turmaId));
        List<Avaliacao> avaliacoes = List.of(
                avaliacao(1L, "Prova", 5, turmaId, disciplinaId),
                avaliacao(2L, "Trabalho", 2, turmaId, disciplinaId)
        );

        List<Nota> notas = List.of(nota(null, 1L, 1L, 8.0), nota(null, 1L, 2L, 6.0));

        when(boletimService.listarAlunosPorTurma(turmaId)).thenReturn(alunos);
        when(boletimService.listarAvaliacoes(turmaId, disciplinaId)).thenReturn(avaliacoes);
        when(boletimService.buscarNotas(
                List.of(1L, 2L),
                List.of(1L, 2L)
        )).thenReturn(Map.of(1L, List.of(notas.get(0), notas.get(1))));

        when(boletimService.calcularMediaPonderadaParaAluno(anyList(), anyList())).thenReturn(7.4);

        mockMvc.perform(get("/api/boletim")
                        .param("turmaId", String.valueOf(turmaId))
                        .param("disciplinaId", String.valueOf(disciplinaId))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.alunos", hasSize(2)))
                .andExpect(jsonPath("$.alunos[0].alunoId", is(1)))
                .andExpect(jsonPath("$.alunos[0].alunoNome", is("João Silva")));
    }

    @Test
    void testSalvarNotas() throws Exception {

        SaveGradesRequestDto req = new SaveGradesRequestDto();
        req.setTurmaId(1L);
        req.setDisciplinaId(1L);

        GradeDto g1 = new GradeDto();
        g1.setAlunoId(1L);
        g1.setAvaliacaoId(1L);
        g1.setValor(8.0);

        GradeDto g2 = new GradeDto();
        g2.setAlunoId(2L);
        g2.setAvaliacaoId(1L);
        g2.setValor(7.0);

        req.setGrades(List.of(g1, g2));


        mockMvc.perform(post("/api/boletim/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testSalvarNotasInvalid() throws Exception {
        SaveGradesRequestDto req = new SaveGradesRequestDto();
        req.setTurmaId(1L);
        req.setDisciplinaId(1L);

        GradeDto g1 = new GradeDto();
        g1.setAlunoId(1L);
        g1.setAvaliacaoId(1L);
        g1.setValor(8.0);

        GradeDto g2 = new GradeDto();
        g2.setAlunoId(2L);
        g2.setAvaliacaoId(1L);
        g2.setValor(12.0);

        req.setGrades(List.of(g1, g2));

        mockMvc.perform(post("/api/boletim/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Nota fora do intervalo")));
    }

    @Test
    void testListarTurmas() throws Exception {
        List<Turma> turmas = List.of(turma(1L, "Turma A"), turma(2L, "Turma B"));
        when(boletimService.listarTurmas()).thenReturn(turmas);

        mockMvc.perform(get("/api/boletim/turmas")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nome", is("Turma A")));
    }

    @Test
    void testListarDisciplinas() throws Exception {
        List<Disciplina> disciplinas = List.of(disciplina(1L, "Matemática"), disciplina(2L, "Português"));
        when(boletimService.listarDisciplinas()).thenReturn(disciplinas);

        mockMvc.perform(get("/api/boletim/disciplinas")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].nome", is("Português")));
    }

    @Test
    void testListarDisciplinasByTurma() throws Exception {
        List<Disciplina> disciplinas = List.of(disciplina(1L, "Matemática"));
        when(boletimService.listarDisciplinasPorTurma(1L)).thenReturn(disciplinas);

        mockMvc.perform(get("/api/boletim/disciplinas")
                        .param("turmaId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Matemática")));
    }
}

