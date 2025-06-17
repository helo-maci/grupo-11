# Documentação do BackOffice Java

## 1. Estrutura Geral e Arquitetura
O sistema foi desenvolvido em Java, utilizando **Swing** para interfaces gráficas e **JDBC com MySQL** para persistência. Segue o padrão MVC, com separação clara entre modelos, DAOs, serviços e GUI.

### Pacotes Principais
- **`/dao`**: Camada de acesso a dados.
  - Exemplo: `CursoDAO`, `EventoDAO`, `PalestranteDAO`, `ConexaoDAO`.
  - Função: Executar operações SQL e conectar-se ao banco via JDBC.

- **`/model`**: Define as entidades do sistema.
  - Exemplo: `Curso`, `Evento`, `Palestrante`.
  - Função: Representar os dados com atributos e métodos.

- **`/service`**: Lógica de negócio e inicialização.
  - Exemplo: `CursoService`.
  - Função: Gerenciar regras de inicialização e chamadas de DAO.

- **`/gui`**: Interfaces Swing.
  - Exemplo: `MainFrame`, `EventoForm`, `PalestranteList`.
  - Função: Interação com o usuário.

- **`/util`**: Utilitários.
  - Exemplo: `SlugUtil` para gerar slugs amigáveis.

---

## 2. Execução do Sistema

### Passo a Passo

1. **Criar o Banco de Dados**
```sql
CREATE DATABASE e_ua;
```

2. **Compilar e Executar**
```bash
mvn clean install
java -cp target/classes br.com.ies.gui.MainFrame
```

> Atenção: Verifique o usuário/senha configurados na classe `ConexaoDAO.java`.

---

## 3. Estrutura dos Dados

### Banco de Dados
As tabelas são criadas automaticamente se não existirem, por `ConexaoDAO.java`.

#### Tabela `palestrantes`
- `id`: INT, PK
- `nome`: VARCHAR(100)
- `email`: VARCHAR(100)
- `mini_curriculo`: TEXT
- `foto`: LONGBLOB

#### Tabela `cursos`
- `id`: INT, PK
- `nome`: VARCHAR(100)
- `descricao`: TEXT

#### Tabela `eventos`
- `id`: INT, PK
- `titulo`: VARCHAR(100)
- `slug`: VARCHAR(100)
- `descricao`: TEXT
- `data`: DATE
- `hora`: TIME
- `id_curso`: INT (FK para `cursos`)
- `id_palestrante`: INT (FK para `palestrantes`)

---

## 4. Funcionalidades e Telas

### Eventos (`/gui/EventForm`, `/gui/EventList`)
- Cadastro, edição, exclusão e visualização de eventos.
- Atribuição de curso e palestrante.
- Interface amigável com validação de data e hora.

### Palestrantes (`/gui/PalestranteForm`, `/gui/PalestranteList`)
- CRUD completo.
- Suporte a upload de foto e minicurrículo.

### Cursos (`/service/CursoService`)
- Cursos iniciais populados na primeira execução.
- Inclui "Sistemas para Internet", "Direito", etc.

### Tela Principal (`MainFrame`)
- Menu com navegação para todas as telas.
- Janela principal fixa com boas práticas de layout.

---

## 5. Utilitários

### SlugUtil
Converte o título do evento para uma versão amigável de URL.
```java
SlugUtil.gerarSlug("Semana de Tecnologia")
```

---

## 6. Boas Práticas
- Uso extensivo de POO (construtores, encapsulamento, toString).
- DAO genérico com PreparedStatements.
- Interface desacoplada da lógica de dados.
- Validações de entrada (ex: campos obrigatórios, data/hora).

---

## 7. Observações Finais
- Aplicação local standalone com Swing.
- Backoffice ideal para uso interno da UniALFA.
- Compatível com demais camadas do projeto (Node.js e PHP).

---

**Projeto Hackathon — 3º Período — UniALFA**
