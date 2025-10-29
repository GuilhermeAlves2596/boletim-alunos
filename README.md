# Boletim de Alunos

Aplicação Fullstack para gerenciamento de notas, turmase e avaliações.
Desenvolvida com **Spring Boot (Java 17)** no backend e **Angular 16** no frontend.

---

## Tecnologias Utilizadas

### Backend (`boletimAlunoBackend`)
- Java 17
- Spring Boot 3.x
  - Spring Web
  - Spring Data JPA
  - H2 Database (banco em memória)
  - Spring Validation
- Lombok
- Swagger/OpenAPI 3
- JUnit 5 & Mockito (para testes unitários)
- Maven (gerenciador de dependências)

### Frontend (`boletimFrontend`)
- Angular 16
- Angular Material (tema customizado em amarelo)
- TypeScript
- Nginx (para servir o build no container Docker)

---

## Estrutura de Pastas

```
boletim-alunos/
│
├── boletimAlunoBackend/      # API REST (Spring Boot)
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
│
├── boletimFrontend/          # Interface Web (Angular)
│   ├── src/
│   ├── package.json
│   ├── angular.json
│   ├── Dockerfile
│   └── nginx.conf
│
├── docker-compose.yml        # Orquestra ambos os serviços
└── README.md                 # Documentação do projeto
```

---

## Funcionalidades Principais

- Listagem de turmas e disciplinas
- Listagem de avaliações (provas, trabalhos etc.)
- Registro e consulta de notas dos alunos
- Cálculo automático da média ponderada
- Exibição do boletim consolidado por turma e disciplina
- Validação de intervalos de notas (0–10)
- Tratamento global de exceções com `@RestControllerAdvice`
- Interface moderna com Angular Material

---

## Testes Unitários

O backend possui testes de controladores e serviços, utilizando:

- JUnit 5
- Mockito
- @WebMvcTest para endpoints REST
- Cobertura de casos de sucesso e erro (400 Bad Request, etc.)

Rodar testes manualmente:
```bash
cd boletimAlunoBackend
mvn test
```

---

## Documentação da API

A aplicação expõe uma interface Swagger para exploração e testes dos endpoints.

Após iniciar o backend, acesse:

http://localhost:8080/swagger-ui/index.html

---

## Como Rodar Localmente (sem Docker)

### Backend

1. Requisitos:
   - Java 17+
   - Maven 3.9+

2. Executar via terminal ou IDE (IntelliJ / Eclipse / VS Code):
   ```bash
   cd boletimAlunoBackend
   mvn spring-boot:run
   ```

3. API iniciará em:
   http://localhost:8080

### Frontend

1. Requisitos:
   - Node.js 18+
   - Angular CLI 16+

2. Instalar dependências:
   ```bash
   cd boletimFrontend
   npm install
   ```

3. Rodar localmente:
   ```bash
   ng serve
   ```

4. Acesse:
   http://localhost:4200

---

## Como Rodar com Docker Compose

1. Na raiz do projeto (`boletim-alunos`), execute:

   ```bash
   docker compose up --build
   ```

2. O Docker irá:
   - Construir e subir o backend (Spring Boot) na porta 8080
   - Construir e servir o frontend (Angular) via Nginx na porta 80

3. Acesse no navegador:
   - Frontend: http://localhost:4200
   - Swagger (API): http://localhost:8080/swagger-ui/index.html

4. Parar containers:
   ```bash
   docker compose down
   ```

---

## Autenticação e Controle de Acesso (cenário real)

Em um cenário de produção, a aplicação incluiria:
- Autenticação JWT (JSON Web Token) via Spring Security
- Criação de usuários e perfis (ROLE_ADMIN, ROLE_PROFESSOR, ROLE_ALUNO)
- Proteção dos endpoints sensíveis com anotações como:
  ```java
  @PreAuthorize("hasRole('PROFESSOR')")
  ```
- O frontend Angular integraria o fluxo de login, armazenando o token em `sessionStorage` e adicionando o header `Authorization: Bearer <token>` nas requisições HTTP.

Essa camada garantiria:
- Controle de acesso baseado em papéis.
- Sessões seguras sem persistência de senha no frontend.
- Rotas protegidas no Angular com Route Guards.

---

## Comandos Úteis

| Ação | Comando |
|------|----------|
| Build backend | `mvn clean package -DskipTests` |
| Rodar backend | `mvn spring-boot:run` |
| Build frontend | `ng build --configuration production` |
| Subir tudo com Docker | `docker compose up --build` |
| Parar containers | `docker compose down` |
| Rodar testes | `mvn test` |

---

## Autor

**Guilherme de Andrade Alves**  
Desenvolvedor Fullstack  


