<?php
session_start();
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
</head>
<body>
    <h1>Lista de Eventos</h1>

    <form method="GET">
        <label for="curso">Filtrar por curso:</label>
        <select name="curso" id="curso" onchange="this.form.submit()">
            <option value="">-- Todos os cursos --</option>
            <?php foreach ($cursos as $curso): ?>
                <option value="<?= $curso['id'] ?>" <?= $idCursoSelecionado == $curso['id'] ? 'selected' : '' ?>>
                    <?= htmlspecialchars($curso['nome']) ?>
                </option>
            <?php endforeach; ?>
        </select>
    </form>

    <div class="grid">
        <?php foreach ($eventos as $evento): ?>
            <div class="card">
                <h3><?= htmlspecialchars($evento['titulo']) ?></h3>
                <p><?= limitarTexto($evento['descricao']) ?></p>
                <a href="evento.php?slug=<?= urlencode($evento['slug']) ?>">Ver detalhes</a>
            </div>
        <?php endforeach; ?>
    </div>
</body>
</html>
