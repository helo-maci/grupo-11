# Documentação do Sistema PHP + CSS

# Documentação do Frontend (PHP e CSS)

## 1. `style.css`
*Define o visual da interface*

- `.header`: Estiliza o topo da página (cor, sombra, fixo ao rolar).
- `.container`: Controla o layout responsivo em colunas e grids.
- `.btn`, `button`: Estilizam botões de ações.
- `.card`, `.shadow`: Estilizam blocos com borda, sombra e organização.
- `.label1`: Estilo de inputs e campos de formulário.

---

## 2. `index.php`
*Página principal com a lista de eventos*

- Carrega os eventos da base de dados.
- Mostra cada evento com título, resumo e link para "Ver mais".
- Se o usuário estiver logado, mostra o botão "Sair".

**Trechos importantes:**
- `CursoEvento->listar()`: Busca todos os eventos.
- `foreach ($eventos)`: Mostra cada evento em um card.
- `href="evento.php?slug=..."`: Link para detalhes do evento.

---

## 3. `evento.php`
*Exibe os detalhes de um evento específico*

- Mostra título, descrição, data/hora, curso, palestrante, contato.
- Se o usuário estiver logado: botão "Inscrever-se".
- Se não estiver logado: botão para fazer login.

**Trechos importantes:**
- `eventoSlug($_GET['slug'])`: Busca evento pelo slug.
- `$_SESSION['usuario']`: Verifica se o usuário está logado.
- `<form action="inscricao.php">`: Formulário de inscrição.

---

## 4. `formularioCadastro.php`
*Formulário para novo usuário se cadastrar*

- Campos: nome, email, CPF, senha.
- Ao enviar, chama `Usuario->cadastrar()` e trata erros.

**Trechos importantes:**
- `$_POST['...']`: Coleta os dados do formulário.
- `Usuario->cadastrar()`: Cria novo usuário.
- `header(...)`: Redireciona para login se cadastro for bem-sucedido.

---

## 5. `formularioLogin.php`
*Página de login*

- Coleta email e senha.
- Verifica credenciais com `Usuario->logar()`.
- Se válidas, salva sessão e redireciona.

**Trechos importantes:**
- `Usuario->logar()`: Verifica login.
- `$_SESSION['token']`: Salva autenticação.

---

## 6. `inscricao.php`
*Inscreve um usuário logado em um evento*

- Recebe `id_evento` via POST.
- Usa `Inscricao->inscreverUsuario()` para salvar a inscrição.

**Trechos importantes:**
- `$_SESSION['usuario']['id']`: ID do usuário.
- `$_POST['id_evento']`: Evento selecionado.
- `Inscricao->inscreverUsuario()`: Efetua inscrição.

---

## 7. `logout.php`
*Realiza o logout do usuário*

- Limpa a sessão e redireciona.

**Trechos importantes:**
- `unset($_SESSION['...'])`: Apaga dados.
- `header(...)`: Redireciona para login.

---

## 8. Fluxo do Site

1. Usuário acessa `index.php` → vê eventos.
2. Clica em um evento → `evento.php?slug=...`.
3. Se não estiver logado → `formularioLogin.php`.
4. Autentica com `Usuario->logar()` → volta ao `index.php`.
5. Acessa novamente o evento e realiza inscrição via `inscricao.php`.
6. Pode encerrar sessão em `logout.php`.

---

**Projeto Hackathon — Frontend com PHP e CSS — UniALFA**


---

# Documentação do Projeto PHP

## 1. Estrutura Geral
O projeto está organizado seguindo o princípio da separação de responsabilidades, o que torna o código mais limpo, sustentável e fácil de manter.

### Diretórios Principais
- **`/classes`**: Contém as classes PHP que representam a lógica de negócio e as regras do sistema. Inclui moldes para objetos (utilizadores, eventos) e serviços que executam ações, como a comunicação com a API.
- **`/css`**: Pasta dedicada aos ficheiros de estilo (CSS). Isolar o CSS garante que a aparência do site seja independente da sua estrutura e funcionalidade.
- **`/js`**: Pasta para os ficheiros de script (JavaScript). Utilizada para adicionar interatividade e melhorar a experiência do utilizador no lado do cliente, sem necessidade de recarregar a página.
- **Ficheiros `.php` na raiz**: São os "controladores" ou pontos de entrada. Responsáveis por receber as requisições, utilizar as classes para processar os dados e renderizar o HTML final.

---

## 2. Descrição Detalhada dos Ficheiros Principais

### `index.php`: Página Inicial
**Função:** Atua como a vitrine do site, listando todos os eventos disponíveis de forma atrativa.

**Conteúdo:**
- **PHP**:
  - Inicia a sessão com `session_start()`.
  - Inclui ficheiros essenciais, como `header.php` e a classe `classes/ApiServices.php`.
  - Cria uma instância de `ApiServices` e chama o método `getEventos()` para buscar os dados na API. É importante tratar possíveis erros (ex.: API offline).
- **PHP/HTML**:
  - Usa um `foreach` para percorrer a lista de eventos. Dentro do loop, cria um "cartão" para cada evento, exibindo nome, data e uma breve descrição.
  - Adiciona um botão ou link "Ver Detalhes" que aponta para `evento.php?id=[ID_DO_EVENTO]`.

### `evento.php`: Página de Detalhes do Evento
**Função:** Oferece uma visão detalhada de um único evento, incentivando a inscrição.

**Conteúdo:**
- **PHP**:
  - Inicia a sessão e verifica se um `id` válido foi passado pela URL (`$_GET['id']`). Redireciona para `index.php` se o ID for inválido.
  - Usa o `id` para chamar o método `getEventoPorId($id)` de `ApiServices`.
- **HTML**:
  - Exibe todos os dados do evento, incluindo nome, descrição completa, palestrante, horário e local.
  - Inclui um botão "Inscrever-se neste Evento", que leva para `inscricao.php?id=[ID_DO_EVENTO]`.

### `formulario_login.php`: Página de Login
**Função:** Autenticar utilizadores existentes de forma segura.

**Conteúdo:**
- **PHP**:
  - Inicia a sessão. Redireciona utilizadores já logados para `index.php`.
- **HTML**:
  - Formulário com `method="POST"`, contendo campos para "email" e "password".
- **PHP**:
  - Valida credenciais usando o método `loginUtilizador($email, $password)` de `ApiServices`.
  - Armazena os dados do utilizador na sessão e redireciona para `index.php` em caso de sucesso.

### `inscricao.php`: Página de Inscrição
**Função:** Processar e confirmar a inscrição de um utilizador num evento.

**Conteúdo:**
- **PHP**:
  - Verifica se o utilizador está logado. Se não, redireciona para `formulario_login.php`.
  - Obtém o ID do evento da URL (`$_GET['id']`) e o ID do utilizador da sessão.
- **HTML/PHP**:
  - Exibe uma mensagem de confirmação e um formulário com campos escondidos para IDs necessários.
- **PHP**:
  - Submete os dados para a API usando `realizarInscricao($idUtilizador, $idEvento)`.

### `inscricoes_usuario.php`: Página "Minhas Inscrições"
**Função:** Permite que o utilizador visualize e gerencie os eventos em que está inscrito.

**Conteúdo:**
- **PHP**:
  - Verifica o login e obtém o ID do utilizador.
  - Usa o método `getInscricoesPorUsuario($id)` de `ApiServices` para buscar inscrições.
- **HTML**:
  - Exibe as inscrições em uma lista ou tabela, com opções como "Gerar Certificado" ou "Ver Detalhes".

---

## 3. Descrição Detalhada das Classes (`/classes`)

### `ApiServices.php`: Serviço da API
**Função:** Centralizar toda a comunicação com a API Node.js.

**Conteúdo:**
- **Propriedade:** `private $apiUrl = "http://localhost:3000";`.
- **Métodos:**
  - `getEventos()`: Faz uma chamada GET para `$apiUrl/eventos`.
  - `getEventoPorId($id)`: Faz uma chamada GET para `$apiUrl/eventos/$id`.
  - `loginUtilizador($email, $password)`: Faz uma chamada POST para `$apiUrl/login`.
  - `realizarInscricao($idUtilizador, $idEvento)`: Faz uma chamada POST para `$apiUrl/inscricoes`.

**Tecnologia:** Usa cURL para requisições HTTP.

### Classes de Modelo: `Usuario.php`, `CursoEvento.php`, `Inscricao.php`
**Função:** Moldes para os dados.

**Conteúdo:**
- **Propriedades:** Correspondem aos campos dos dados.
- **Construtor:** Recebe um array de dados (ex.: `json_decode`) e preenche as propriedades da classe.

---
