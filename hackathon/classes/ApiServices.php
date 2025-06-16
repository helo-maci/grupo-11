<?php

class ApiServices {
    private string $baseUrl;

    public function __construct() {
        $this->baseUrl = 'http://localhost:3001';
    }

private function request(string $endpoint, string $method = 'GET', array $data = []) {
    $url = $this->baseUrl . $endpoint;

    $curl = curl_init($url);
    $options = [
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_TIMEOUT => 10,
        CURLOPT_HTTPHEADER => ['Accept: application/json']
    ];

    if ($method === 'POST') {
        $options[CURLOPT_POST] = true;
        $options[CURLOPT_POSTFIELDS] = json_encode($data);
        $options[CURLOPT_HTTPHEADER][] = 'Content-Type: application/json';
    }

    curl_setopt_array($curl, $options);
    $response = curl_exec($curl);
    $error = curl_error($curl);
    curl_close($curl);

    if (!$response) {
        return ['erro' => 'Erro na requisição: ' . $error];
    }

    return json_decode($response, true);
}
    public function intermediario(string $endpoint, string $method = 'GET', array $data = []) {
    return $this->request($endpoint, $method, $data);
}
}
