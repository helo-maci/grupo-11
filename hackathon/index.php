<?php
include "header.php";
require_once 'classes/CursoEvento.php';

$api = new CursoEvento();

$cursos = $api->listarCursos()['cursos'] ;
$eventos = $api->listarEventos()['eventos'] ?? [];

$idCursoSelecionado = $_GET['curso'] ?? null;

if ($idCursoSelecionado) {
    $eventos = array_filter($eventos, fn($evento) => $evento['id_curso'] == $idCursoSelecionado);
}

function limitarTexto($texto, $limite = 100) {
    return strlen($texto) > $limite ? substr($texto, 0, $limite) . '...' : $texto;
}
?>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Eventos</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/index.js"></script>
</head>
<body>
<section>
    <div class="fullscreen-container">
        <img src="" alt="Banner Evento">
        <div class="texto-central">EVENTOS UNIALFA</div>
        <div class="texto-central2"><h1>Semanais e mensais </h1> </div>
    </div>
</section>



    <section class="">
        <div class="container2">
            <h2 id="about-heading" class="text-center mb-5">EVENTOS</h2>
</section>

    <form method="GET">
        <h1>Filtrar por curso</h1>
        <select class="btn"   name="curso" id="curso" onchange="this.form.submit()">
            <option class="label1" value="">-- Todos os cursos --</option>
            <?php foreach ($cursos as $curso): ?>
                <option value="<?= $curso['id'] ?>" <?= $idCursoSelecionado == $curso['id'] ? 'selected' : '' ?>>
                    <?= htmlspecialchars($curso['nome']) ?>
                </option>
            <?php endforeach; ?>
        </select>
    </form>
    

    <div class="label1">
        <?php foreach ($eventos as $evento): ?>
            <div class="">
                <h3><?= htmlspecialchars($evento['titulo']) ?></h3>
                <p><?= limitarTexto($evento['descricao']) ?></p>
                <a class="btn" href="evento.php?slug=<?= urlencode($evento['slug']) ?>">Ver detalhes</a>
            </div>
            
        <?php endforeach; ?>
    </div>
<?php include "footer.php"; ?>
</body>
</html>