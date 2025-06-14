<?php

class PostApiUsers
{
    private string $baseUrl;

    public function __construct()
    {
        $this->baseUrl = 'http://localhost:3001';
    }
  public function logar($email, $senha)
    {   
        $data = [
            'email' => $email,
            'senha' => $senha
        ];
    }
    public function cadastrar($nome, $email, $cpf, $senha)
    {   
        $data = [
            'nome' => $nome,
            'email' => $email,
            'cpf' => $cpf,
            'senha' => $senha
        ];

        $url = $this->baseUrl . '/usuarios';

        $curl = curl_init($url);
        curl_setopt_array($curl, [
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_TIMEOUT => 10,
            CURLOPT_POST => true,
            CURLOPT_POSTFIELDS => json_encode($data),
            CURLOPT_HTTPHEADER => [
                'Accept: application/json',
                'Content-Type: application/json'
            ]
        ]);

        $response = curl_exec($curl);

        if (curl_errno($curl)) {
            $error = curl_error($curl);
            curl_close($curl);
            return [];
        }

        curl_close($curl);

        if (!$response) {
            return ['mensagem' => 'Sem resposta da API'];
        }

        return json_decode($response, true);
    }
}
