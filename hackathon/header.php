<?php
session_start();
require_once 'classes/CursoEvento.php';
?>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HACKGP11 - Eventos de Tecnologia</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <header class="header" role="banner" aria-label="Cabeçalho do site">
        <div  class="container">
            <a href="index.php" ><img class="logol" src="img/Logo.png" alt="Logo do Hackathon"></a>
            <h1 class="logo" img src="css/img/logo.png" style="color:rgb(255, 255, 255);">Hackathon</h1>
            <nav class="nav" role="navigation" aria-label="Navegação principal">
                <?php if (isset($_SESSION['token']) && isset($_SESSION['usuario']['id'])): ?>
                <ul>
                    <li><a href="index.php" class="nav-link" aria-current="page">HOME</a></li>
                    <li><a href="inscricoes_usuario.php" class="nav-link">Minhas Inscrições</a></li>
                    <li>
                        <form action="logout.php" method="POST"> 
                          <li><a href="logout.php" type="submit" class="nav-link">Sair</a> </li>
                        </form>
                    </li>
                </ul>
                <?php else: ?>
                <ul>
                    <li><a href="index.php" class="nav-link" aria-current="page">HOME</a></li>
                    <li><a href="formulario_login.php" class="nav-link">Login</a></li>
                    <li><a href="formulario_cadastro.php" class="nav-link">Cadastre-se</a></li>
                </ul>
                <?php endif; ?>
            </nav>
            <button class="menu-toggle" aria-label="Abrir menu" aria-expanded="false">
            </button>
        </div>
    </header>


