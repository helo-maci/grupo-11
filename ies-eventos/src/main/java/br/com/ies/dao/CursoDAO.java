package br.com.ies.dao;

import br.com.ies.model.Curso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por realizar operações de banco de dados relacionadas aos cursos
 * Herda da classe ConexaoDAO para utilizar a conexão com o banco de dados
 */
public class CursoDAO extends ConexaoDAO {
    
    /**
     * Salva um curso no banco de dados
     * Se o curso não tiver ID, será criado um novo registro
     * Se o curso já tiver ID, o registro será atualizado
     * 
     * @param curso Curso a ser salvo
     * @throws SQLException Se ocorrer algum erro no banco de dados
     */
    public void salvar(Curso curso) throws SQLException {
        String sql = curso.getId() == 0
                ? "INSERT INTO cursos (nome, descricao) VALUES (?, ?)"
                : "UPDATE cursos SET nome = ?, descricao = ? WHERE id = ?";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getDescricao());
            
            if (curso.getId() != 0) {
                stmt.setInt(3, curso.getId());
            }
            
            stmt.executeUpdate();
            
            if (curso.getId() == 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        curso.setId(rs.getInt(1));
                    }
                }
            }
        }
    }
    
    /**
     * Busca um curso pelo seu ID
     * 
     * @param id ID do curso a ser buscado
     * @return Curso encontrado ou null se não existir
     * @throws SQLException Se ocorrer algum erro no banco de dados
     */
    public Curso buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM cursos WHERE id = ?";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Curso(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("descricao")
                    );
                }
            }
        }
        
        return null;
    }
    
    /**
     * Lista todos os cursos cadastrados, ordenados por nome
     * 
     * @return Lista de cursos
     * @throws SQLException Se ocorrer algum erro no banco de dados
     */
    public List<Curso> listarTodos() throws SQLException {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM cursos ORDER BY nome";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                cursos.add(new Curso(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao")
                ));
            }
        }
        
        return cursos;
    }
    
    /**
     * Exclui um curso do banco de dados
     * 
     * @param id ID do curso a ser excluído
     * @throws SQLException Se ocorrer algum erro no banco de dados
     */
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM cursos WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
} 