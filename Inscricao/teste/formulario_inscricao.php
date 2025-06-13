<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Formulário de Inscrição</title>
</head>
<body>
    <h1>Inscrição</h1>

    <form method="POST" action="">
        <label for="nome">Nome:</label><br>
        <input type="text" name="nome" required><br><br>

        <label for="cpf">CPF:</label><br>
        <input type="text" name="cpf" required><br><br>

        <label for="email">Email:</label><br>
        <input type="email" name="email" required><br><br>

        <label for="telefone">Telefone:</label><br>
        <input type="text" name="telefone" required><br><br>

        <button type="submit" name="submit" onclick="clique()">Enviar</button>
    </form>

    <?php
    if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['submit'])) {
        require_once 'classes/PostApiInscricoes.php';

        $nome = $_POST['nome'];
        $cpf = $_POST['cpf'];
        $email = $_POST['email'];
        $telefone = $_POST['telefone'];

        $api = new PostApiInscricoes();
        $resultado = $api->inscrever($nome, $cpf, $email, $telefone);

        echo "<div>";

        if (isset($resultado['inscricao'])) {
            echo "<p style='color:green;'>Inscrição realizada com sucesso!</p>";
        } elseif (isset($resultado['issues']) && is_array($resultado['issues'])) {
            echo "<p style='color:red;'>Erros de validação:</p>";
            echo "<ul style='color:red;'>";
            foreach ($resultado['issues'] as $erro) {
                echo "<li>" . htmlspecialchars($erro['message']) . "</li>";
            }
            echo "</ul>";
        } elseif (isset($resultado['mensagem'])) {
            echo "<p style='color:red;'>Erro: " . htmlspecialchars($resultado['mensagem']) . "</p>";
        } 
        echo "</div>";
    }
    ?>
</body>
</html>
