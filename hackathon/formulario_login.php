<?php
session_start();
require_once 'classes/Usuario.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['submit'])) {
    $email = $_POST['email'];
    $senha = $_POST['senha'];

    $usuario = new Usuario();
    $response = $usuario->logar($email, $senha);

    if (isset($response['token'])) {

        $_SESSION['token'] = $response['token'];
        $_SESSION['usuario'] = $response['usuario'];

        header("Location: ./index.php");
        exit;
    } else {
        $errors[] = $response['message'] ?? '';
    }
}
?>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
    <h1>Login</h1>

    <?php if (!empty($errors)): ?>
        <ul>
            <?php foreach ($errors as $erro): ?>
                <li style="color: red;"><?= $erro ?></li>
            <?php endforeach; ?>
        </ul>
    <?php endif; ?>

    <form method="POST">

        <label for="email">Email:</label><br>
        <input type="email" name="email" value="<?= $_POST['email'] ?? '' ?>" required>
        <br><br>

        <label for="senha">Senha:</label><br>
        <input type="password" name="senha" value="<?= $_POST['senha'] ?? ''?>" required>
        <br><br>

        <button type="submit" name="submit">Login</button>
        <p>Não é Cadastrado? <a href="formulario_cadastro.php">Cadastre-se</a></p>
        <a href="index.php"><p>Não quero fazer login </a></p>
    </form>


</body>
</html>