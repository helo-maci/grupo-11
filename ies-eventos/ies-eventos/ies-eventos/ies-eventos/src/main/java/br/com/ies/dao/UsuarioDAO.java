// Conteúdo de UsuarioDAO.java

package br.com.ies.dao;

import br.com.ies.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends ConexaoDAO {

    public void salvar(Usuario usuario) throws SQLException {

        String sql = usuario.getId() == 0
                ? "INSERT INTO usuarios (nome, cpf, email) VALUES (?, ?, ?)"
                : "UPDATE usuarios SET nome = ?, cpf = ?, email = ? WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getEmail());

            if (usuario.getId() != 0) {
                stmt.setInt(4, usuario.getId());
            }

            stmt.executeUpdate();

            if (usuario.getId() == 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuario.setId(rs.getInt(1));
                    }
                }
            }
        }
    }

    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT u.id, u.nome, u.cpf, u.email, c.id as id_curso, c.nome as nome_curso " +
                "FROM usuarios u " +
                "LEFT JOIN inscricoes i ON u.id = i.id_usuario " +
                "LEFT JOIN eventos ev ON i.id_evento = ev.id " +
                "LEFT JOIN cursos c ON ev.id_curso = c.id " +
                "WHERE u.id = ? LIMIT 1";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getInt("id_curso"),
                            rs.getString("nome_curso")
                    );
                }
            }
        }

        return null;
    }

    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.id, u.nome, u.cpf, u.email, c.id as id_curso, c.nome as nome_curso " +
                "FROM usuarios u " +
                "LEFT JOIN inscricoes i ON u.id = i.id_usuario " +
                "LEFT JOIN eventos ev ON i.id_evento = ev.id " +
                "LEFT JOIN cursos c ON ev.id_curso = c.id " +
                "GROUP BY u.id, u.nome, u.cpf, u.email, c.id, c.nome " +
                "ORDER BY u.nome";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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

        return usuarios;
    }

    public List<Usuario> listarPorCurso(int cursoId) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.id, u.nome, u.cpf, u.email, c.id as id_curso, c.nome as nome_curso " +
                "FROM usuarios u " +
                "INNER JOIN inscricoes i ON u.id = i.id_usuario " +
                "INNER JOIN eventos ev ON i.id_evento = ev.id " +
                "INNER JOIN cursos c ON ev.id_curso = c.id " +
                "WHERE c.id = ? " +
                "GROUP BY u.id, u.nome, u.cpf, u.email, c.id, c.nome " +
                "ORDER BY u.nome";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, cursoId);

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

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public boolean existeCpf(String cpf) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE cpf = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }
}