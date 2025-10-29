package com.alves.boletimAluno.service;


import com.alves.boletimAluno.model.*;
import com.alves.boletimAluno.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoletimServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @Mock
    private NotaRepository notaRepository;

    @Mock
    private TurmaRepository turmaRepository;

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @InjectMocks
    private BoletimService service;

    @BeforeEach
    void setup() {
    }

    private Aluno createAluno(Long id, String nome, Long turmaId) {
        Aluno a = new Aluno();
        a.setId(id);
        a.setNome(nome);
        a.setTurmaId(turmaId);
        return a;
    }

    private Avaliacao createAvaliacao(Long id, String nome, Integer peso, Long turmaId, Long disciplinaId) {
        Avaliacao a = new Avaliacao();
        a.setId(id);
        a.setNome(nome);
        a.setPeso(peso);
        a.setTurmaId(turmaId);
        a.setDisciplinaId(disciplinaId);
        return a;
    }

    private Nota createNota(Long id, Long alunoId, Long avaliacaoId, Double valor) {
        Nota n = new Nota();
        n.setId(id);
        n.setAlunoId(alunoId);
        n.setAvaliacaoId(avaliacaoId);
        n.setValor(valor);
        return n;
    }

    private Turma createTurma(Long id, String nome) {
        Turma t = new Turma();
        t.setId(id);
        t.setNome(nome);
        return t;
    }

    private Disciplina createDisciplina(Long id, String nome) {
        Disciplina d = new Disciplina();
        d.setId(id);
        d.setNome(nome);
        return d;
    }

    @Test
    void testListarAlunosPorTurma() {
        List<Aluno> lista = List.of(createAluno(1L, "A", 1L));
        when(alunoRepository.findByTurmaId(1L)).thenReturn(lista);

        List<Aluno> result = service.listarAlunosPorTurma(1L);

        assertThat(result).isSameAs(lista);
        verify(alunoRepository, times(1)).findByTurmaId(1L);
    }

    @Test
    void testListarAvaliacoes() {
        List<Avaliacao> avals = List.of(createAvaliacao(1L, "P", 5, 1L, 1L));
        when(avaliacaoRepository.findByTurmaIdAndDisciplinaIdOrderById(1L, 1L)).thenReturn(avals);

        List<Avaliacao> result = service.listarAvaliacoes(1L, 1L);

        assertThat(result).isSameAs(avals);
        verify(avaliacaoRepository, times(1)).findByTurmaIdAndDisciplinaIdOrderById(1L, 1L);
    }

    @Test
    void testListarTurmas() {
        List<Turma> turmas = List.of(createTurma(1L, "T1"));
        when(turmaRepository.findAll()).thenReturn(turmas);

        List<Turma> result = service.listarTurmas();

        assertThat(result).isSameAs(turmas);
        verify(turmaRepository).findAll();
    }

    @Test
    void testListarDisciplinas() {
        List<Disciplina> disciplinas = List.of(createDisciplina(1L, "Mat"));
        when(disciplinaRepository.findAll()).thenReturn(disciplinas);

        List<Disciplina> result = service.listarDisciplinas();

        assertThat(result).isSameAs(disciplinas);
        verify(disciplinaRepository).findAll();
    }

    @Test
    void testBuscarNotas() {
        Nota n1 = createNota(null, 1L, 1L, 8.0);
        Nota n2 = createNota(null, 2L, 1L, 7.0);
        when(notaRepository.findByAlunoIdInAndAvaliacaoIdIn(List.of(1L,2L), List.of(1L))).thenReturn(List.of(n1, n2));

        Map<Long, List<Nota>> result = service.buscarNotas(List.of(1L,2L), List.of(1L));

        assertThat(result).hasSize(2);
        assertThat(result.get(1L)).contains(n1);
        assertThat(result.get(2L)).contains(n2);
        verify(notaRepository).findByAlunoIdInAndAvaliacaoIdIn(List.of(1L,2L), List.of(1L));
    }

    @Test
    void testSalvarNotasEmLote() {
        Nota incomingExisting = createNota(null, 1L, 1L, 9.5);
        Nota existing = createNota(100L, 1L, 1L, 7.0);
        Nota incomingNew = createNota(null, 2L, 1L, 6.0);

        when(notaRepository.findByAlunoIdAndAvaliacaoId(1L, 1L)).thenReturn(Optional.of(existing));
        when(notaRepository.findByAlunoIdAndAvaliacaoId(2L, 1L)).thenReturn(Optional.empty());

        service.salvarNotasEmLote(List.of(incomingExisting, incomingNew));

        verify(notaRepository, times(1)).save(existing);
        assertEquals(9.5, existing.getValor());

        verify(notaRepository, times(1)).save(incomingNew);
    }

    @Test
    void testListarDisciplinasPorTurma() {
        Avaliacao a1 = createAvaliacao(1L, "P", 5, 1L, 1L);
        Avaliacao a2 = createAvaliacao(2L, "T", 2, 1L, 2L);
        when(avaliacaoRepository.findByTurmaId(1L)).thenReturn(List.of(a1, a2));
        Disciplina d1 = createDisciplina(1L, "Mat");
        Disciplina d2 = createDisciplina(2L, "Port");
        when(disciplinaRepository.findAllById(List.of(1L,2L))).thenReturn(List.of(d1, d2));

        List<Disciplina> result = service.listarDisciplinasPorTurma(1L);

        assertThat(result).containsExactlyInAnyOrder(d1, d2);
        verify(avaliacaoRepository).findByTurmaId(1L);
        verify(disciplinaRepository).findAllById(List.of(1L,2L));
    }

    @Test
    void testListarDisciplinasPorTurmaVazioQuandoSemAval() {
        when(avaliacaoRepository.findByTurmaId(5L)).thenReturn(Collections.emptyList());

        List<Disciplina> result = service.listarDisciplinasPorTurma(5L);

        assertThat(result).isEmpty();
        verify(avaliacaoRepository).findByTurmaId(5L);
        verifyNoInteractions(disciplinaRepository);
    }

    @Test
    void testCalcularMediaPonderadaParaAluno() {
        Avaliacao a1 = createAvaliacao(1L, "P", 5, 1L, 1L);
        Avaliacao a2 = createAvaliacao(2L, "T", 2, 1L, 1L);
        Avaliacao a3 = createAvaliacao(3L, "A", 1, 1L, 1L);
        List<Avaliacao> avals = List.of(a1, a2, a3);

        Nota n1 = createNota(null, 1L, 1L, 8.0);
        Nota n2 = createNota(null, 2L, 1L, 6.0);
        Nota n3 = createNota(null, 3L, 1L, 10.0);

        Double media = service.calcularMediaPonderadaParaAluno(List.of(n1, n2, n3), avals);
        assertNotNull(media);
        assertEquals(8.0, media);
    }

    @Test
    void testCalcularMediaPonderadaParaAlunoNull() {

        assertNull(service.calcularMediaPonderadaParaAluno(null, List.of()));
        assertNull(service.calcularMediaPonderadaParaAluno(Collections.emptyList(), List.of()));

        Avaliacao a1 = createAvaliacao(1L, "P", 5, 1L, 1L);
        Nota n1 = createNota(null, 1L, 99L, 7.0);
        Double media = service.calcularMediaPonderadaParaAluno(List.of(n1), List.of(a1));

        assertNull(media);
    }

}