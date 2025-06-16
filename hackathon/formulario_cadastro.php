    <?php 
include "header.php";
require_once 'classes/Usuario.php';

    if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['submit'])) {
 
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


    <section class="about-section py-5" aria-labelledby="about-heading">
        <div class="container">
            <h2 id="about-heading" class="text-center mb-5">CADASTRO</h2>
</section>

<section class="py-5">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6">
                    <div class="card shadow">
                        <div class="card-body">
                            <h2 class="title-center card-title mb-4"></h2>
                            
                                    <?php if (!empty($errors)): ?>
                                    <ul style="list-style: none">
                                        <?php foreach ($errors as $error): ?>
                                            <li style="color: red;">
                                                <?= "{$error['message']}"; ?>
                                            </li>
                                        <?php endforeach; ?>
                                    </ul>
                                <?php endif; ?>

                            <form method="post">
                                <div class="ins mb-3">
                                    <label   for="nome">Nome:</label>
                                    <input   class="label1"   type="text" name="nome" value="<?= $_POST['nome'] ?? '' ?>" required>
                                    
                                
                                <div class="mb-3">
                                            <label for="email">Email:</label>
                                             <input class="label1"   type="email" name="email" value="<?= $_POST['email'] ?? '' ?>" required>
                                            

                                <div class="mb-3">
                                    <label for="cpf">CPF:</label>
                                    <input  class="label1"   type="text" name="cpf" value="<?= $_POST['cpf'] ?? '' ?>" required>
                            
                                <div class="mb-3">
                                    <label for="senha">Senha:</label>
                                    <input class="label1"   type="password" name="senha" value="<?= $_POST['senha'] ?? '' ?>" required>
                                </div>
                                <br></br>
                                
                                <div class="button grid mt-3">
                                    <button type="submit" name="submit">Cadastrar</button>
                                </div>
                                <br></br>   

                                <div class="btn text-end grid mt-3"> 
                                    <p><a href="formulario_login.php">Ir para Login</a></p>
                                    <a href="index.php"></div>
                                          <div class="btn text-end grid mt-3">
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