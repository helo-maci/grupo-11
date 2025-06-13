<?php

class PostApiInscricoes
{
    private string $baseUrl;

    public function __construct()
    {
        $this->baseUrl = 'http://localhost:3001';
    }

    public function inscrever($nome, $cpf, $email, $telefone)
    {
        $data = [
            'nome' => $nome,
            'cpf' => $cpf,
            'email' => $email,
            'telefone' => $telefone
        ];

        $url = $this->baseUrl . '/inscricoes';

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
            return ['mensagem' => 'Erro cURL: ' . $error];
        }

        curl_close($curl);

        if (!$response) {
            return ['mensagem' => 'Sem resposta da API'];
        }

        return json_decode($response, true);
    }
}
