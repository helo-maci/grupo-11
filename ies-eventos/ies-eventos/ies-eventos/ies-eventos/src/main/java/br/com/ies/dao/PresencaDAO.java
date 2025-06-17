package br.com.ies.dao;

import br.com.ies.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PresencaDAO extends ConexaoDAO {

    public void registrarPresenca(int idUsuario, int idEvento) throws SQLException {
        String sql = "INSERT INTO presenca_evento (id_usuario, id_evento) VALUES (?, ?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idEvento);
            stmt.executeUpdate();
        }
    }

    public void removerPresenca(int idUsuario, int idEvento) throws SQLException {
        String sql = "DELETE FROM presenca_evento WHERE id_usuario = ? AND id_evento = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idEvento);
            stmt.executeUpdate();
        }
    }

    public List<Usuario> listarPresencasPorEvento(int idEvento) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.id, u.nome, u.cpf, u.email, c.id as id_curso, c.nome as nome_curso " +
                "FROM presenca_evento p " +
                "INNER JOIN usuarios u ON p.id_usuario = u.id " +
                "INNER JOIN eventos ev ON p.id_evento = ev.id " +
                "INNER JOIN cursos c ON ev.id_curso = c.id " +
                "WHERE p.id_evento = ? ORDER BY u.nome";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idEvento);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(new Usuario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getInt("id_curso"),
                            rs.getString("nome_curso")
                    ));
                }
            }
        }

        return usuarios;
    }

    public boolean usuarioPresente(int idUsuario, int idEvento) throws SQLException {
        String sql = "SELECT COUNT(*) FROM presenca_evento WHERE id_usuario = ? AND id_evento = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idEvento);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public int contarPresencasPorEvento(int idEvento) throws SQLException {
        String sql = "SELECT COUNT(*) FROM presenca_evento WHERE id_evento = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idEvento);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }
}