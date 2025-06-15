<?php
require_once 'ApiServices.php';

class Usuario extends ApiServices {

    public function logar($email, $senha) {
        return $this->request('/veriUsuarios', 'POST', [
            'email' => $email,
            'senha' => $senha
        ]);
    }    
    
    public function cadastrar($nome, $email, $cpf, $senha) {
        return $this->request('/usuarios', 'POST', [
            'nome' => $nome,
            'email' => $email,
            'cpf' => $cpf,
            'senha' => $senha
        ]);
    }
}





