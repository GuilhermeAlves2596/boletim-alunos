export interface Avaliacao {
  id: number;
  nome: string;
  peso: number;
}

export interface AlunoRow {
  alunoId: number;
  alunoNome: string;
  notas: Record<string, number | null>;
  media: number | null;
}

export interface BoletimResponse {
  alunos: AlunoRow[];
  avaliacoes: Avaliacao[];
}

export interface GradeDto {
  alunoId: number;
  avaliacaoId: number;
  valor: number;
}

export interface SaveGradesRequest {
  turmaId: number;
  disciplinaId: number;
  grades: GradeDto[];
}

export interface Turma {
  id: number;
  nome: string;
}

export interface Disciplina {
  id: number;
  nome: string;
}