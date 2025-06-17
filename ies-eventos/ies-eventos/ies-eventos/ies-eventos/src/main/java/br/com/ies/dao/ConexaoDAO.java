package br.com.ies.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConexaoDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/agora?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    private Connection connection;

    public ConexaoDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            criarTabelas();
        } catch (Exception e) {
            System.out.println("[DAO Connection] " + e.getMessage());
        }
    }


    private void criarTabelas() {
        try (Statement stmt = connection.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS palestrantes (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    email VARCHAR(100) NOT NULL,
                    minicurriculo TEXT,
                    foto LONGBLOB
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS cursos (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    descricao TEXT
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS eventos (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    titulo VARCHAR(255) NOT NULL,
                    slug VARCHAR(255) NOT NULL UNIQUE,
                    descricao TEXT,
                    data DATE NOT NULL,
                    hora TIME NOT NULL,
                    id_curso INT NOT NULL,
                    id_palestrante INT,
                    FOREIGN KEY (id_curso) REFERENCES cursos(id),
                    FOREIGN KEY (id_palestrante) REFERENCES palestrantes(id)
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS presenca_evento (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    id_usuario INT UNSIGNED NOT NULL,
                    id_evento INT NOT NULL,
                    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
                    FOREIGN KEY (id_evento) REFERENCES eventos(id)
                )
            """);

        } catch (Exception e) {
            System.out.println("[DAO Create Tables] " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}