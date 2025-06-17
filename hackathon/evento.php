<?php
include "header.php";
require_once 'classes/CursoEvento.php';

$evento = new CursoEvento();
$response = $evento->eventoSlug($_GET['slug']);
$evento = $response['evento'];

if (!$evento) {
    echo "Evento não encontrado.";
    exit;
}

function formatarDataHora($data, $hora) {
    return date('d/m/Y', strtotime($data)) . ' às ' . substr($hora, 0, 5);
}
?>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title><?= htmlspecialchars($evento['titulo']) ?></title>
</head>
<body>
    <br></br>
<section class="py-5">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6">
                    <div class="card shadow">
                        <div class="card-body">
                            
<h1><?= htmlspecialchars($evento['titulo']) ?></h1>

<p><strong>Descrição:</strong> <?= nl2br(htmlspecialchars($evento['descricao'])) ?></p>
<p><strong>Data e horário:</strong> <?= formatarDataHora($evento['data'], $evento['hora']) ?></p>
<p><strong>Curso:</strong> <?= htmlspecialchars($evento['nome_curso']) ?></p>
                            <?php if (isset($evento['foto_palestrante_b64']) && !empty($evento['foto_palestrante_b64'])): ?>
                                <div class="text-center my-3">
                                    <img src="<?= htmlspecialchars($evento['foto_palestrante_b64']) ?>" 
                                        alt="Foto do Palestrante: <?= htmlspecialchars($evento['nome_palestrante']) ?>" 
                                    style="max-width: 200px; height: auto; border-radius: 8%; object-fit: cover;">
                                </div>
                            <?php else: ?>
                                <p><em>Foto do palestrante não disponível.</em></p>
                            <?php endif; ?>
<p><strong>Palestrante:</strong> <?= htmlspecialchars($evento['nome_palestrante']) ?></p>
<p><strong>Formação e demais informações:</strong> <?= htmlspecialchars($evento['mc_palestrante']) ?></p>
<p><strong>Contato:</strong> <?= htmlspecialchars($evento['email_palestrante']) ?></p>



<?php if (isset($_SESSION['token']) && isset($_SESSION['usuario']['id'])): ?>

    <form  method="POST" action="inscricao.php">
        <input type="hidden" name="id_evento" value="<?= $evento['id'] ?>">
        <button type="submit" onclick="return confirm('Tem certeza que deseja se inscrever neste evento?')">Inscrever-se</button>
    </form>
<?php else: ?>
    <p> <a class="btn" href="formulario_login.php">Faça login para se inscrever</a></p>
<?php endif; ?>

<p><a class="btn" href="index.php">Voltar</a></p>

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
