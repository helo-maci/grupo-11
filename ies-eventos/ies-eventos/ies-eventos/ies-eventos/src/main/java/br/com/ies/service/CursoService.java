package br.com.ies.service;

import br.com.ies.dao.CursoDAO;
import br.com.ies.model.Curso;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CursoService {
    private static final List<Curso> cursosIniciais = new ArrayList<>();

    private static final CursoDAO cursoDAO = new CursoDAO();

    static {

        cursosIniciais.add(new Curso(0, "Administração"));
        cursosIniciais.add(new Curso(0, "Ciências Contábeis"));
        cursosIniciais.add(new Curso(0, "Direito"));
        cursosIniciais.add(new Curso(0, "Gestão Hospitalar"));
        cursosIniciais.add(new Curso(0, "Marketing"));
        cursosIniciais.add(new Curso(0, "Pedagogia"));
        cursosIniciais.add(new Curso(0, "Processos Gerenciais"));
        cursosIniciais.add(new Curso(0, "Psicologia"));
        cursosIniciais.add(new Curso(0, "Recursos Humanos"));
        cursosIniciais.add(new Curso(0, "Sistemas para Internet"));

        try {
            List<Curso> cursosExistentes = cursoDAO.listarTodos();
            if (cursosExistentes.isEmpty()) {
                for (Curso curso : cursosIniciais) {
                    cursoDAO.salvar(curso);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar cursos: " + e.getMessage());
        }
    }

    public static List<Curso> getCursos() {
        try {
            return cursoDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Erro ao listar cursos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static Curso getCursoById(int id) {
        try {
            return cursoDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar curso por ID: " + e.getMessage());
            return null;
        }
    }
}