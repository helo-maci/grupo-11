CREATE DATABASE IF NOT EXISTS ies_eventos;
USE ies_eventos;

CREATE TABLE IF NOT EXISTS palestrantes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS cursos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    email VARCHAR(100) NOT NULL,
    curso_id INT NOT NULL,
    FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

CREATE TABLE IF NOT EXISTS eventos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    data DATE NOT NULL,
    hora TIME NOT NULL,
    id_curso INT NOT NULL,
    id_palestrante INT NOT NULL,
    slug VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_palestrante) REFERENCES palestrantes(id),
    FOREIGN KEY (id_curso) REFERENCES cursos(id)
);

CREATE TABLE IF NOT EXISTS inscricoes (
    id_usuario INT NOT NULL,
    id_evento INT NOT NULL,
    PRIMARY KEY (id_usuario, id_evento),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_evento) REFERENCES eventos(id)
); 