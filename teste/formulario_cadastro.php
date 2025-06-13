<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Formulário de Cadastro</title>
</head>
<body>
    <h1>Cadastro</h1>

    <?php 

    if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['submit'])) {
        require_once 'classes/PostApiUsers.php';

        $nome = $_POST['nome'];
        $email = $_POST['email'];
        $senha = $_POST['senha'];

        $api = new PostApiUsers();
        $response = $api->cadastrar($nome, $email, $senha);

        if (isset($response['errors'])) {
            $errors = $response['errors'];
        }
    }
    ?>
    <?php if (!empty($errors)): ?>
        <ul>
            <?php foreach ($errors as $error): ?>
                <li style="color: red;">
                    <?= "{$error['message']}"; ?>
                </li>
            <?php endforeach; ?>
        </ul>
    <?php endif; ?>

    <form method="POST">
        <label for="nome">Nome:</label><br>
        <input type="text" name="nome" value="<?= $_POST['nome'] ?? ''?>" required>
        <br><br>

        <label for="email">Email:</label><br>
        <input type="email" name="email" value="<?= $_POST['email'] ?? '' ?>" required>
        <br><br>

        <label for="senha">Senha:</label><br>
        <input type="password" name="senha" value="<?= $_POST['senha'] ?? ''?>" required>
        <br><br>

        <button type="submit" name="submit">Enviar</button>
    </form>

</body>
</html>
