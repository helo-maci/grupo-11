package br.com.ies.dao;

import br.com.ies.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscricaoDAO extends ConexaoDAO {

    public void inscreverUsuario(int idUsuario, int idEvento) throws SQLException {
        String sql = "INSERT INTO inscricoes (id_usuario, id_evento) VALUES (?, ?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idEvento);
            stmt.executeUpdate();
        }
    }

    public void cancelarInscricao(int idUsuario, int idEvento) throws SQLException {
        String sql = "DELETE FROM inscricoes WHERE id_usuario = ? AND id_evento = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idEvento);
            stmt.executeUpdate();
        }
    }

    public List<Usuario> listarInscritosPorEvento(int idEvento) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.id, u.nome, u.cpf, u.email, c.id as id_curso, c.nome as nome_curso " +
                "FROM inscricoes i " +
                "INNER JOIN usuarios u ON i.id_usuario = u.id " +
                "INNER JOIN eventos ev ON i.id_evento = ev.id " +
                "INNER JOIN cursos c ON ev.id_curso = c.id " +
                "WHERE i.id_evento = ? ORDER BY u.nome";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idEvento);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(new Usuario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getInt("id_curso"), // Usando id_curso do evento/curso
                            rs.getString("nome_curso")
                    ));
                }
            }
        }

        return usuarios;
    }

    public boolean usuarioInscrito(int idUsuario, int idEvento) throws SQLException {
        String sql = "SELECT COUNT(*) FROM inscricoes WHERE id_usuario = ? AND id_evento = ?";

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

    public int contarInscritosPorEvento(int idEvento) throws SQLException {
        String sql = "SELECT COUNT(*) FROM inscricoes WHERE id_evento = ?";

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