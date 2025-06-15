    <?php 

    if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['submit'])) {
        require_once 'classes/Usuario.php';
        $nome = $_POST['nome'];
        $email = $_POST['email'];
        $cpf = $_POST['cpf'];
        $senha = $_POST['senha'];

        $usuario = new Usuario();
        $response = $usuario->cadastrar($nome, $email, $cpf, $senha);

        if (isset($response['errors'])) {
            $errors = $response['errors'];
        }
        if(!$errors){
            header("location: ./formulario_login.php");
            exit; 
        }
    }
    ?>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Formulário de Cadastro</title>
    <script src="js/script.js" defer ></script>
</head>
<body>
    <h1>Cadastro</h1>

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
        <input type="text" name="nome" value="<?= $_POST['nome'] ?? '' ?>" required>
        <br><br>

        <label for="email">Email:</label><br>
        <input type="email" name="email" value="<?= $_POST['email'] ?? '' ?>" required>
        <br><br>

        <label for="cpf">CPF:</label><br>
        <input type="text" name="cpf" value="<?= $_POST['cpf'] ?? '' ?>" required>
        <br><br>

        <label for="senha">Senha:</label><br>
        <input type="password" name="senha" value="<?= $_POST['senha'] ?? ''?>" required>
        <br><br>

        <button type="submit" name="submit">Cadastrar</button>
        <p>Já sou Cadastrado. <a href="formulario_login.php">Ir para Login</a></p>
        <a href="index.php"><p>Não quero me cadastrar </a></p>
    </form>


</body>
</html>