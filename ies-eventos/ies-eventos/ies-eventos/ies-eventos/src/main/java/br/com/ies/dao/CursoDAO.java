package br.com.ies.dao;

import br.com.ies.model.Curso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO extends ConexaoDAO {

    public void salvar(Curso curso) throws SQLException {
        String sql = curso.getId() == 0
                ? "INSERT INTO cursos (nome) VALUES (?)"
                : "UPDATE cursos SET nome = ? WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, curso.getNome());

            if (curso.getId() != 0) {
                stmt.setInt(2, curso.getId());
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

    public Curso buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM cursos WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Curso(
                            rs.getInt("id"),
                            rs.getString("nome")
                    );
                }
            }
        }

        return null;
    }

    public List<Curso> listarTodos() throws SQLException {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM cursos ORDER BY nome";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                cursos.add(new Curso(
                        rs.getInt("id"),
                        rs.getString("nome")
                ));
            }
        }

        return cursos;
    }

}