<?php
require_once 'ApiServices.php';

class Usuario extends ApiServices {

    public function logar($email, $senha) {
        return $this->intermediario('/veriUsuarios', 'POST', [
            'email' => $email,
            'senha' => $senha
        ]);
    }    
    
    public function cadastrar($nome, $email, $cpf, $senha) {
        return $this->intermediario('/usuarios', 'POST', [
            'nome' => $nome,
            'email' => $email,
            'cpf' => $cpf,
            'senha' => $senha
        ]);
    }
}