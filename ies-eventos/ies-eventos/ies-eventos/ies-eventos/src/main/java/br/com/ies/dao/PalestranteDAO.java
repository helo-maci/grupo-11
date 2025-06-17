package br.com.ies.dao;

import br.com.ies.model.Palestrante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PalestranteDAO extends ConexaoDAO {

    public void salvar(Palestrante palestrante) throws SQLException {
        String sql = palestrante.getId() == 0
                ? "INSERT INTO palestrantes (nome, email, minicurriculo, foto) VALUES (?, ?, ?, ?)"
                : "UPDATE palestrantes SET nome = ?, email = ?, minicurriculo = ?, foto = ? WHERE id = ?";
        
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
                            rs.getString("minicurriculo"),
                            rs.getBytes("foto")
                    );
                }
            }
        }
        
        return null;
    }


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
                        rs.getString("minicurriculo"),
                        rs.getBytes("foto")
                ));
            }
        }
        
        return palestrantes;
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM palestrantes WHERE id = ?";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
} 