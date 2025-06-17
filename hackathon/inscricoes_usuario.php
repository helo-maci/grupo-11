<?php
include "header.php";
require_once 'classes/Inscricao.php';


if (!isset($_SESSION['token']) || !isset($_SESSION['usuario']['id'])) {
    header('Location: formulario_login.php');
    exit;
}

$idUsuario = $_SESSION['usuario']['id'];
$inscricaoService = new Inscricao();

$eventosInscritos = $inscricaoService->listarInscricoesUsuario($idUsuario);

function formatarDataHora($data, $hora) {
    return date('d/m/Y', strtotime($data)) . ' às ' . substr($hora, 0, 5);
}

function limitarTexto($texto, $limite = 150) {
    return strlen($texto) > $limite ? substr($texto, 0, $limite) . '...' : $texto;
}
?>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Minhas Inscrições</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <section class="py-5">
        <div class="container">
            <h2 class="text-center mb-5">Meus Eventos Inscritos</h2>

            <?php if (empty($eventosInscritos)): ?>
                <div class="alert alert-info text-center" role="alert">
                    <p>Você ainda não se inscreveu em nenhum evento.</p>
                </div>
            <?php else: ?>
                <div class="row">
                    <?php foreach ($eventosInscritos as $evento): ?>
                        <div class="col-md-6 mb-4">
                            <div class="card shadow h-100">
                                <div class="card-body">
                                    <h3 class="card-title"><?= htmlspecialchars($evento['titulo']) ?></h3>
                                    <p class="card-text">
                                        <strong>Descrição:</strong> <?= nl2br(htmlspecialchars(limitarTexto($evento['descricao'], 150))) ?>
                                    </p>
                                    <p class="card-text">
                                        <strong>Quando:</strong> <?= formatarDataHora($evento['data'], $evento['hora']) ?>
                                    </p>
                                    <p class="card-text">
                                        <strong>Curso:</strong> <?= htmlspecialchars($evento['nome_curso']) ?>
                                    </p>
                                    <p class="card-text">
                                        <strong>Palestrante:</strong> <?= htmlspecialchars($evento['nome_palestrante']) ?>
                                    </p>
                                    <a href="evento.php?slug=<?= urlencode($evento['slug']) ?>" class="btn btn-primary mt-2">Ver Detalhes</a>

                                    <?php
                                    if (isset($evento['tem_presenca']) && $evento['tem_presenca']):
                                    ?>
                                        <a href="download_certificado.php?id_evento=<?= $evento['id'] ?>&id_usuario=<?= $idUsuario ?>" class="btn btn-success mt-2" target="_blank"> Certificado (PDF)</a>
                                    <?php endif; ?>
                                </div>
                            </div>
                        </div>
                    <?php endforeach; ?>
                </div>
            <?php endif; ?>

            <div class="text-center mt-5">
                <a href="index.php" class="btn btn-secondary">Voltar para a lista de eventos</a>
            </div>
        </div>
    </section>

    <?php include "footer.php"; ?>
</body>
</html>
