<?php
include "header.php";
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
        <section class="about-section py-5" aria-labelledby="about-heading">
        <div class="container">
            <h2 id="about-heading" class="text-center mb-5">Login</h2>
</section>


<section class="py-5">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6">
                    <div class="card shadow">
                        <div class="card-body">
                            <h2 class="title-center card-title mb-4"></h2>

    <?php if (!empty($errors)): ?>
        <ul style="list-style:none;">
            <?php foreach ($errors as $erro): ?>
                <li style="color: red;"><?= $erro ?></li>
            <?php endforeach; ?>
        </ul>
    <?php endif; ?>
                            <form method="post">
                                            <div class="mb-3">
                                            <label for="email">Email:</label>
                                             <input class="label1"   type="email" name="email" value="<?= $_POST['email'] ?? '' ?>" required>
                                            
                                <div class="mb-3">
                                    <label for="senha">Senha:</label>
                                    <input class="label1"   type="password" name="senha" value="<?= $_POST['senha'] ?? '' ?>" required>
                                  


                                </div>
                                <br></br>
                                
                                <div class="button grid mt-3">
                                    <button type="submit" name="submit">Entrar</button>
                                </div>
                                <br></br>

                                <div class="btn grid mt-3"> 
                                    <p><a href="formulario_cadastro.php">Cadastre-se</a></p>
                                    <a href="index.php"></div>
                                          <div class="btn grid mt-3">
                                         <a href="index.php">Voltar para Eventos</a>

                            </form>
                        </div>
                    </div>
                    </div>
                </div>
            </div>
        </div>
    </section>










<?php include "footer.php"; ?>
</body>
</html>