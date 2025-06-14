<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Formulário de Login</title>
    <script src="js/script.js" defer ></script>
</head>
<body>
    <h1>Login</h1>

    <?php 

    if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['submit'])) {
        require_once 'classes/PostApiUsers.php';
        $email = $_POST['email'];
        $senha = $_POST['senha'];

        $api = new PostApiUsers();
        $response = $api->logar($email, $senha);

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