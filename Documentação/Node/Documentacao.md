# Documentação da API Node.js

## 1. Estrutura Geral e Arquitetura
A API foi desenvolvida em Node.js com o framework Express.js, seguindo uma arquitetura em camadas para garantir a separação de responsabilidades. Isso torna o projeto escalável e de fácil manutenção.

### Diretórios Principais
- **`/middlewares`**: Contém middlewares, como o de autenticação, para verificar tokens JWT.
  - Arquivo principal: `autenticacao.ts`.
  - Função: Gerenciar a validação de tokens JWT para proteger as rotas da API.

- **`/routes`**: Define todos os endpoints da API. Cada ficheiro agrupa as rotas de um recurso específico.
  - Exemplos:
    - `usuarios.ts`: Gerencia os usuários (registro, autenticação).
    - `eventos.ts`: Gerencia os eventos e suas consultas.
    - `inscricoes.ts`: Gerencia inscrições de usuários em eventos.

- **`/db`**: Configuração do banco de dados e integração via Knex.js.
  - Arquivo principal: `knex.ts`.
  - Função: Configurar as credenciais e conectar-se ao banco de dados MySQL.

- **`server.ts`**: Arquivo de entrada da aplicação.
  - Inicializa o servidor Express, aplica os middlewares e conecta as rotas da API.

---

## 2. Execução da API

### Passo a Passo para Configuração

1. **Clonar o Repositório**
   ```bash
   git clone [URL_DO_SEU_REPOSITORIO]
   cd node
   ```

2. **Instalar Dependências**
   ```bash
   npm install
   ```

3. **Configurar o Banco de Dados**
   - Certifique-se de que o banco de dados está em execução.
   - Configure as credenciais no arquivo `knexfile.js` ou similar, usando as variáveis de ambiente configuradas no `.env`.

4. **Executar Migrações**
   ```bash
   npm run migrate:up
   ```

5. **Iniciar o Servidor**
   ```bash
   npm run dev
   ```
   A API estará disponível em [http://localhost:3001](http://localhost:3001).

---

## 3. Estrutura dos Dados

### Banco de Dados

A API utiliza um banco de dados relacional gerenciado pelo Knex.js. A estrutura de tabelas é composta por:

1. **Tabela `usuarios`**
   - `id`: Identificador único do usuário.
   - `nome`: Nome completo.
   - `email`: Email válido e único.
   - `cpf`: CPF único (11 caracteres).
   - `senha`: Hash da senha do usuário (criptografado com bcrypt).

2. **Tabela `cursos`**
   - `id`: Identificador único do curso.
   - `nome`: Nome do curso.

3. **Tabela `eventos`**
   - `id`: Identificador único do evento.
   - `nome`: Nome do evento.
   - `descricao`: Detalhes sobre o evento.
   - `slug`: Identificador amigável na URL.
   - `id_curso`: Chave estrangeira para o curso associado.
   - `id_palestrante`: Chave estrangeira para o palestrante associado.

4. **Tabela `palestrantes`**
   - `id`: Identificador único do palestrante.
   - `nome`: Nome completo.
   - `email`: Contato do palestrante.
   - `minicurriculo`: Breve descrição do palestrante.

5. **Tabela `inscricoes`**
   - `id`: Identificador único da inscrição.
   - `id_usuario`: Chave estrangeira para o usuário inscrito.
   - `id_evento`: Chave estrangeira para o evento inscrito.

---

## 4. Endpoints e Operações

### **Usuários** (`/usuarios`)
- **GET /**
  - **Descrição:** Lista todos os usuários registrados.
  - **Exemplo de Resposta:**
    ```json
    {
      "usuarios": [
        {
          "id": 1,
          "nome": "João Silva",
          "email": "joao@email.com",
          "cpf": "12345678901"
        }
      ]
    }
    ```

- **POST /**
  - **Descrição:** Registra um novo usuário com validação de dados e senha criptografada.
  - **Exemplo de Payload:**
    ```json
    {
      "nome": "Maria Oliveira",
      "email": "maria@email.com",
      "cpf": "98765432100",
      "senha": "senha123"
    }
    ```
  - **Exemplo de Resposta:**
    ```json
    {
      "usuario": {
        "id": 2,
        "nome": "Maria Oliveira",
        "email": "maria@email.com"
      }
    }
    ```

### **Autenticação** (`/veriUsuarios`)
- **POST /**
  - **Descrição:** Autentica o usuário e retorna um token JWT válido por 5 horas.
  - **Exemplo de Payload:**
    ```json
    {
      "email": "joao@email.com",
      "senha": "senha123"
    }
    ```
  - **Exemplo de Resposta:**
    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
      "usuario": {
        "id": 1,
        "nome": "João Silva"
      }
    }
    ```

### **Cursos** (`/cursos`)
- **GET /**
  - **Descrição:** Retorna uma lista de todos os cursos disponíveis.
  - **Exemplo de Resposta:**
    ```json
    {
      "cursos": [
        {
          "id": 1,
          "nome": "Engenharia de Software"
        }
      ]
    }
    ```

### **Eventos** (`/eventos`)
- **GET /**
  - **Descrição:** Lista todos os eventos cadastrados, com opção de filtro por curso.
  - **Exemplo de Resposta:**
    ```json
    {
      "eventos": [
        {
          "id": 1,
          "nome": "Semana da Tecnologia",
          "descricao": "Evento sobre inovações tecnológicas.",
          "slug": "semana-tecnologia",
          "id_curso": 1,
          "id_palestrante": 1
        }
      ]
    }
    ```

- **GET /:slug**
  - **Descrição:** Retorna detalhes de um evento específico com base no slug.
  - **Exemplo de Resposta:**
    ```json
    {
      "evento": {
        "id": 1,
        "nome": "Semana da Tecnologia",
        "descricao": "Evento sobre inovações tecnológicas.",
        "slug": "semana-tecnologia",
        "curso": "Engenharia de Software",
        "palestrante": {
          "nome": "Dr. Carlos Silva",
          "email": "carlos@exemplo.com",
          "minicurriculo": "Especialista em IA."
        }
      }
    }
    ```

### **Inscrições** (`/inscricoes`)
- **POST /**
  - **Descrição:** Registra um usuário autenticado em um evento, verificando dados obrigatórios.
  - **Exemplo de Payload:**
    ```json
    {
      "id_usuario": 1,
      "id_evento": 1
    }
    ```
  - **Exemplo de Resposta:**
    ```json
    {
      "mensagem": "Inscrição realizada com sucesso."
    }
    ```

---

## 5. Tratamento de Erros

1. **Validação de Dados (Zod)**
   - Retorna erro 400 com detalhes dos campos inválidos.
   - **Exemplo:**
     ```json
     {
       "status": "erro",
       "message": "Validação falhou",
       "errors": [
         {
           "field": "email",
           "message": "Email inválido."
         }
       ]
     }
     ```

2. **Erros de Banco de Dados**
   - Retorna erro 400 para duplicidade de CPF ou email.
   - **Exemplo:**
     ```json
     {
       "errors": [
         {
           "message": "Email e/ou CPF já cadastrados."
         }
       ]
     }
     ```

3. **Erro Interno**
   - Retorna erro 500 para falhas inesperadas no servidor.
   - **Exemplo:**
     ```json
     {
       "status": "erro",
       "message": "Erro no servidor."
     }
     ```

4. **Autenticação Inválida**
   - Retorna erro 401 quando o token JWT é inválido ou ausente.
   - **Exemplo:**
     ```json
     {
       "status": "erro",
       "message": "Token inválido."
     }
     ```

---

## 6. Fluxo de Autenticação

A autenticação utiliza JWT para proteger rotas. O middleware `autenticacao.ts` verifica o token em cada requisição protegida:

1. O cliente envia o token no cabeçalho `Authorization`.
2. O middleware valida o token e passa os dados do usuário para as próximas etapas.
3. Caso o token seja inválido, a API retorna erro 401.

---

## 7. Boas Práticas e Organização

- **Versionamento:** Utilize Git e mantenha o histórico de commits claro e informativo.
- **Branches:** Trabalhe em branches específicas para cada funcionalidade.
- **Pull Requests:** Revise o código antes de mesclar alterações na branch principal.
- **Logs:** Registre erros no servidor para facilitar a depuração.

---

Este documento descreve detalhadamente a execução e a estrutura dos dados da API, atendendo aos requisitos do projeto.
