<?php
require_once 'ApiServices.php';

class CursoEvento extends ApiServices {

    public function listarCursos() {
        return $this->request('/cursos');
    }

    public function listarEventos() {
        return $this->request('/eventos');
    }

    public function eventoSlug(string $slug) {
        return $this->request("/eventos/$slug");
    }
}
