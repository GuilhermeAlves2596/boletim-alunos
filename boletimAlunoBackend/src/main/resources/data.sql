-- =================================================
-- Turmas
-- =================================================
INSERT INTO turma (id, nome) VALUES (1, 'Turma A');
INSERT INTO turma (id, nome) VALUES (2, 'Turma B');
INSERT INTO turma (id, nome) VALUES (3, 'Turma C');
INSERT INTO turma (id, nome) VALUES (4, 'Turma D');

-- =================================================
-- Disciplinas
-- =================================================
INSERT INTO disciplina (id, nome) VALUES (1, 'Matemática');
INSERT INTO disciplina (id, nome) VALUES (2, 'Português');
INSERT INTO disciplina (id, nome) VALUES (3, 'História');
INSERT INTO disciplina (id, nome) VALUES (4, 'Ciências');
INSERT INTO disciplina (id, nome) VALUES (5, 'Inglês');

-- =================================================
-- Alunos
-- =================================================
INSERT INTO aluno (id, nome, turma_id) VALUES (1, 'João Silva', 1);
INSERT INTO aluno (id, nome, turma_id) VALUES (2, 'Maria Santos', 1);
INSERT INTO aluno (id, nome, turma_id) VALUES (3, 'Pedro Oliveira', 1);
INSERT INTO aluno (id, nome, turma_id) VALUES (4, 'Lucas Pereira', 1);
INSERT INTO aluno (id, nome, turma_id) VALUES (14, 'Rafael Pinto', 1);
INSERT INTO aluno (id, nome, turma_id) VALUES (18, 'Diego Nunes', 1);

INSERT INTO aluno (id, nome, turma_id) VALUES (5, 'Laura Gomez', 2);
INSERT INTO aluno (id, nome, turma_id) VALUES (6, 'Ana Costa', 2);
INSERT INTO aluno (id, nome, turma_id) VALUES (7, 'Maristela Estoco', 2);
INSERT INTO aluno (id, nome, turma_id) VALUES (15, 'Paula Ferreira', 2);
INSERT INTO aluno (id, nome, turma_id) VALUES (19, 'Isabela Moura', 2);

INSERT INTO aluno (id, nome, turma_id) VALUES (8, 'Carlos Souza', 3);
INSERT INTO aluno (id, nome, turma_id) VALUES (9, 'Beatriz Lima', 3);
INSERT INTO aluno (id, nome, turma_id) VALUES (10, 'Ricardo Alves', 3);
INSERT INTO aluno (id, nome, turma_id) VALUES (16, 'Andre Lima', 3);
INSERT INTO aluno (id, nome, turma_id) VALUES (20, 'Felipe Castro', 3);

INSERT INTO aluno (id, nome, turma_id) VALUES (11, 'Sofia Mendes', 4);
INSERT INTO aluno (id, nome, turma_id) VALUES (12, 'Bruno Costa', 4);
INSERT INTO aluno (id, nome, turma_id) VALUES (13, 'Fernanda Rocha', 4);
INSERT INTO aluno (id, nome, turma_id) VALUES (17, 'Camila Rocha', 4);

-- =================================================
-- Avaliações
-- =================================================
INSERT INTO avaliacao (id, nome, peso, turma_id, disciplina_id) VALUES (1, 'Prova', 5, 1, 1);
INSERT INTO avaliacao (id, nome, peso, turma_id, disciplina_id) VALUES (2, 'Trabalho', 2, 1, 1);
INSERT INTO avaliacao (id, nome, peso, turma_id, disciplina_id) VALUES (3, 'Atividade', 1, 1, 1);

INSERT INTO avaliacao (id, nome, peso, turma_id, disciplina_id) VALUES (4, 'Prova', 5, 1, 2);
INSERT INTO avaliacao (id, nome, peso, turma_id, disciplina_id) VALUES (5, 'Trabalho', 2, 1, 2);
INSERT INTO avaliacao (id, nome, peso, turma_id, disciplina_id) VALUES (6, 'Redação', 1, 1, 2);

INSERT INTO avaliacao (id, nome, peso, turma_id, disciplina_id) VALUES (7, 'Prova', 5, 2, 1);
INSERT INTO avaliacao (id, nome, peso, turma_id, disciplina_id) VALUES (8, 'Trabalho', 2, 2, 1);
INSERT INTO avaliacao (id, nome, peso, turma_id, disciplina_id) VALUES (9, 'Atividade', 1, 2, 1);

INSERT INTO avaliacao (id, nome, peso, turma_id, disciplina_id) VALUES (10, 'Prova', 5, 2, 3);
INSERT INTO avaliacao (id, nome, peso, turma_id, disciplina_id) VALUES (11, 'Trabalho', 3, 2, 3);

INSERT INTO avaliacao (id, nome, peso, turma_id, disciplina_id) VALUES (12, 'Prova', 5, 3, 4);
INSERT INTO avaliacao (id, nome, peso, turma_id, disciplina_id) VALUES (13, 'Trabalho', 2, 3, 4);
INSERT INTO avaliacao (id, nome, peso, turma_id, disciplina_id) VALUES (14, 'Laboratório', 2, 3, 4);

INSERT INTO avaliacao (id, nome, peso, turma_id, disciplina_id) VALUES (15, 'Prova', 5, 4, 5);
INSERT INTO avaliacao (id, nome, peso, turma_id, disciplina_id) VALUES (16, 'Trabalho', 2, 4, 5);
INSERT INTO avaliacao (id, nome, peso, turma_id, disciplina_id) VALUES (17, 'Apresentação', 1, 4, 5);