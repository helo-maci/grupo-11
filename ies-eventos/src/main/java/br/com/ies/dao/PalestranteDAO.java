package br.com.ies.dao;

import br.com.ies.model.Palestrante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por realizar operações de banco de dados relacionadas aos palestrantes
 * Herda da classe ConexaoDAO para utilizar a conexão com o banco de dados
 */
public class PalestranteDAO extends ConexaoDAO {
    
    /**
     * Salva um palestrante no banco de dados
     * Se o palestrante não tiver ID, será criado um novo registro
     * Se o palestrante já tiver ID, o registro será atualizado
     * 
     * @param palestrante Palestrante a ser salvo
     * @throws SQLException Se ocorrer algum erro no banco de dados
     */
    public void salvar(Palestrante palestrante) throws SQLException {
        String sql = palestrante.getId() == 0
                ? "INSERT INTO palestrantes (nome, email, mini_curriculo, foto) VALUES (?, ?, ?, ?)"
                : "UPDATE palestrantes SET nome = ?, email = ?, mini_curriculo = ?, foto = ? WHERE id = ?";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, palestrante.getNome());
            stmt.setString(2, palestrante.getEmail());
            stmt.setString(3, palestrante.getMiniCurriculo());
            stmt.setBytes(4, palestrante.getFoto());
            
            if (palestrante.getId() != 0) {
                stmt.setInt(5, palestrante.getId());
            }
            
            stmt.executeUpdate();
            
            if (palestrante.getId() == 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        palestrante.setId(rs.getInt(1));
                    }
                }
            }
        }
    }
    
    /**
     * Busca um palestrante pelo seu ID
     * 
     * @param id ID do palestrante a ser buscado
     * @return Palestrante encontrado ou null se não existir
     * @throws SQLException Se ocorrer algum erro no banco de dados
     */
    public Palestrante buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM palestrantes WHERE id = ?";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Palestrante(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("mini_curriculo"),
                            rs.getBytes("foto")
                    );
                }
            }
        }
        
        return null;
    }
    
    /**
     * Lista todos os palestrantes cadastrados, ordenados por nome
     * 
     * @return Lista de palestrantes
     * @throws SQLException Se ocorrer algum erro no banco de dados
     */
    public List<Palestrante> listarTodos() throws SQLException {
        List<Palestrante> palestrantes = new ArrayList<>();
        String sql = "SELECT * FROM palestrantes ORDER BY nome";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                palestrantes.add(new Palestrante(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("mini_curriculo"),
                        rs.getBytes("foto")
                ));
            }
        }
        
        return palestrantes;
    }
    
    /**
     * Exclui um palestrante do banco de dados
     * 
     * @param id ID do palestrante a ser excluído
     * @throws SQLException Se ocorrer algum erro no banco de dados
     */
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM palestrantes WHERE id = ?";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
} 