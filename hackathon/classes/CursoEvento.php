<?php
require_once 'ApiServices.php';

class CursoEvento extends ApiServices {

    public function listarCursos() {
        return $this->intermediario('/cursos');
    }

    public function listarEventos() {
        return $this->intermediario('/eventos');
    }

    public function eventoSlug(string $slug) {
        return $this->intermediario("/eventos/$slug");
    }
}
