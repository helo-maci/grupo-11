<?php
session_start();

require('fpdf186/fpdf.php');

require_once 'classes/Inscricao.php';
require_once 'classes/CursoEvento.php';
require_once 'classes/Usuario.php';

if (!isset($_SESSION['token']) || !isset($_SESSION['usuario']['id'])) {
    header('Location: formulario_login.php');
    exit;
}

$idEvento = $_GET['id_evento'] ?? null;
$idUsuario = $_GET['id_usuario'] ?? null;

if (!$idEvento || !$idUsuario) {
    die("Erro: ID do evento ou ID do usuário não fornecido.");
}

$inscricaoService = new Inscricao();
$cursoEventoService = new CursoEvento();
$usuarioService = new Usuario();

$inscricoesUsuario = $inscricaoService->listarInscricoesUsuario($idUsuario);
$eventoInscrito = null;

foreach ($inscricoesUsuario as $inscricao) {
    if (isset($inscricao['id']) && $inscricao['id'] == $idEvento && isset($inscricao['tem_presenca']) && $inscricao['tem_presenca']) {
        $eventoInscrito = $inscricao;
        break;
    }
}

if (!$eventoInscrito) {
    die("Você não está inscrito neste evento ou sua presença não foi confirmada.");
}

$nomeParticipante = $_SESSION['usuario']['nome'] ?? 'Participante';
$cpfParticipante = $_SESSION['usuario']['cpf'] ?? 'CPF não informado';


$tituloEvento = $eventoInscrito['titulo'] ?? 'Evento Desconhecido';
$dataEvento = date('d/m/Y', strtotime($eventoInscrito['data'])) ?? 'Data não informada';
$horaEvento = substr($eventoInscrito['hora'], 0, 5) ?? 'Hora não informada';
$nomePalestrante = $eventoInscrito['nome_palestrante'] ?? 'Palestrante não informado';
$nomeCurso = $eventoInscrito['nome_curso'] ?? 'Curso não informado';

class PDF extends FPDF {

    function Footer() {

        $this->SetY(-90); 
        $this->SetFont('Arial', 'I', 8);

        $logoPath ='./img/Logo.png';

        if (file_exists($logoPath)) {
            $imageWidth = 40;
            $xPos = (210 - $imageWidth) / 2;
            $this->Image($logoPath, $xPos, $this->GetY() + 5, $imageWidth);
        } else {
                $this->Cell(0, 10, mb_convert_encoding('Logo não encontrada: ' . $logoPath, 'ISO-8859-1', 'UTF-8'), 0, 0, 'C');
        }
    }
}

$pdf = new PDF();
$pdf->AliasNbPages();
$pdf->AddPage();

$pdf->SetFont('Arial', 'B', 24);
$pdf->Cell(0, 40, mb_convert_encoding('CERTIFICADO DE PARTICIPAÇÃO', 'ISO-8859-1', 'UTF-8'), 0, 1, 'C');

$pdf->SetFont('Arial', '', 14);
$pdf->SetLeftMargin(20);
$pdf->SetRightMargin(20);

$pdf->MultiCell(0, 10, mb_convert_encoding("Certificamos que ", 'ISO-8859-1', 'UTF-8'), 0, 'C');
$pdf->SetFont('Arial', 'B', 16);
$pdf->MultiCell(0, 10, mb_convert_encoding($nomeParticipante, 'ISO-8859-1', 'UTF-8'), 0, 'C');
$pdf->SetFont('Arial', '', 12);
$pdf->MultiCell(0, 10, mb_convert_encoding("Inscrito no CPF: $cpfParticipante", 'ISO-8859-1', 'UTF-8'), 0, 'C');

$pdf->SetFont('Arial', '', 14);
$pdf->MultiCell(0, 10, mb_convert_encoding("Participou do evento", 'ISO-8859-1', 'UTF-8'), 0, 'C');
$pdf->SetFont('Arial', 'B', 16);
$pdf->MultiCell(0, 10, mb_convert_encoding($tituloEvento, 'ISO-8859-1', 'UTF-8'), 0, 'C');

$pdf->SetFont('Arial', '', 14);
$pdf->MultiCell(0, 10, mb_convert_encoding("Relacionado ao curso de ", 'ISO-8859-1', 'UTF-8'), 0, 'C');
$pdf->SetFont('Arial', 'B', 16);
$pdf->MultiCell(0, 10, mb_convert_encoding($nomeCurso, 'ISO-8859-1', 'UTF-8'), 0, 'C');

$pdf->SetFont('Arial', '', 14);
$pdf->MultiCell(0, 10, mb_convert_encoding("Ministrado por ", 'ISO-8859-1', 'UTF-8'), 0, 'C');
$pdf->SetFont('Arial', 'B', 16);
$pdf->MultiCell(0, 10, mb_convert_encoding($nomePalestrante, 'ISO-8859-1', 'UTF-8'), 0, 'C');

$pdf->SetFont('Arial', '', 14);
$pdf->MultiCell(0, 10, mb_convert_encoding("Em $dataEvento às $horaEvento.", 'ISO-8859-1', 'UTF-8'), 0, 'C');

$pdf->SetFont('Arial', '', 14);
$pdf->MultiCell(0, 10, mb_convert_encoding("Unialfa, Umuarama - PR", 'ISO-8859-1', 'UTF-8'), 0, 'C');

$pdf->Output('I', mb_convert_encoding('Certificado_', 'ISO-8859-1', 'UTF-8') . str_replace(' ', '_', $tituloEvento) . '.pdf');

?>