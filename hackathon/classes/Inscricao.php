<?php
require_once 'ApiServices.php';

class Inscricao extends ApiServices {
    public function inscreverUsuario($idUsuario, $idEvento) {
        return $this->intermediario('/inscricoes', 'POST', [
            'id_usuario' => $idUsuario,
            'id_evento' => $idEvento
        ]);
    }
}
