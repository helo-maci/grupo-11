package br.com.ies.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados MySQL
 * Esta classe implementa o padrão Singleton para garantir uma única instância de conexão
 */
public class ConexaoDAO {
    // Configurações de conexão com o banco de dados
    private static final String URL = "jdbc:mysql://localhost:3306/e_ua?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "root";     // Usuário padrão do XAMPP
    private static final String PASSWORD = "";     // Senha padrão do XAMPP (vazia)
    
    // Objeto que mantém a conexão ativa com o banco de dados
    private Connection connection;
    
    /**
     * Construtor da classe
     * Inicializa a conexão com o banco de dados e cria as tabelas necessárias
     */
    public ConexaoDAO() {
        try {
            // Carrega o driver JDBC do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Estabelece a conexão com o banco de dados usando as credenciais configuradas
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Cria as tabelas necessárias se elas não existirem
            criarTabelas();
            
        } catch (Exception e) {
            // Em caso de erro na conexão, exibe a mensagem de erro
            System.out.println("[DAO Connection] " + e.getMessage());
        }
    }
    
    /**
     * Método responsável por criar as tabelas necessárias no banco de dados
     * As tabelas são criadas apenas se não existirem (usando CREATE TABLE IF NOT EXISTS)
     */
    private void criarTabelas() {
        try (Statement stmt = connection.createStatement()) {
            // Cria a tabela de palestrantes com os campos:
            // id: identificador único auto-incrementado
            // nome: nome do palestrante (não pode ser nulo)
            // email: email do palestrante (não pode ser nulo)
            // mini_curriculo: mini currículo do palestrante
            // foto: foto do palestrante
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS palestrantes (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    email VARCHAR(100) NOT NULL,
                    mini_curriculo TEXT,
                    foto LONGBLOB
                )
            """);
            
            // Cria a tabela de cursos com os campos:
            // id: identificador único auto-incrementado
            // nome: nome do curso (não pode ser nulo)
            // descricao: descrição detalhada do curso
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS cursos (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    descricao TEXT
                )
            """);
            
            // Cria a tabela de eventos com os campos:
            // id: identificador único auto-incrementado
            // titulo: título do evento (não pode ser nulo)
            // slug: identificador único para URLs (não pode ser nulo)
            // descricao: descrição detalhada do evento
            // data: data do evento (não pode ser nulo)
            // hora: horário do evento (não pode ser nulo)
            // id_curso: referência ao curso (não pode ser nulo)
            // id_palestrante: referência ao palestrante (não pode ser nulo)
            // FOREIGN KEY: garante que o palestrante existe na tabela palestrantes
            // FOREIGN KEY: garante que o curso existe na tabela cursos
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS eventos (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    titulo VARCHAR(100) NOT NULL,
                    slug VARCHAR(100) NOT NULL,
                    descricao TEXT,
                    data DATE NOT NULL,
                    hora TIME NOT NULL,
                    id_curso INT NOT NULL,
                    id_palestrante INT NOT NULL,
                    FOREIGN KEY (id_palestrante) REFERENCES palestrantes(id),
                    FOREIGN KEY (id_curso) REFERENCES cursos(id)
                )
            """);
            
        } catch (Exception e) {
            // Em caso de erro na criação das tabelas, exibe a mensagem de erro
            System.out.println("[DAO Create Tables] " + e.getMessage());
        }
    }
    
    /**
     * Método que retorna a conexão ativa com o banco de dados
     * @return Connection objeto de conexão com o banco de dados
     */
    public Connection getConnection() {
        return connection;
    }
} 