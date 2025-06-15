<?php
session_start();
require_once 'classes/Inscricao.php';

if (!isset($_SESSION['token']) || !isset($_SESSION['usuario'])) {
    header('Location: formulario_login.php');
    exit;
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $idUsuario = $_SESSION['usuario']['id'];
    $idEvento = $_POST['id_evento'];

    $inscricao = new Inscricao();
    $resposta = $inscricao->inscreverUsuario($idUsuario, $idEvento);

    if ($resposta['success']) {
        echo "<script>alert('Inscrição realizada com sucesso!'); window.location.href='index.php';</script>";
    } else {
        echo "<script>alert('Você já se inscreveu nesse curso'); window.history.back();</script>";
    }
}
