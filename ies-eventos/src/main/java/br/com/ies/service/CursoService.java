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
        // Lista inicial de cursos
        cursosIniciais.add(new Curso(0, "Administração", "Curso de Administração"));
        cursosIniciais.add(new Curso(0, "Ciências Contábeis", "Curso de Ciências Contábeis"));
        cursosIniciais.add(new Curso(0, "Direito", "Curso de Direito"));
        cursosIniciais.add(new Curso(0, "Gestão Hospitalar", "Curso de Gestão Hospitalar"));
        cursosIniciais.add(new Curso(0, "Marketing", "Curso de Marketing"));
        cursosIniciais.add(new Curso(0, "Pedagogia", "Curso de Pedagogia"));
        cursosIniciais.add(new Curso(0, "Processos Gerenciais", "Curso de Processos Gerenciais"));
        cursosIniciais.add(new Curso(0, "Psicologia", "Curso de Psicologia"));
        cursosIniciais.add(new Curso(0, "Recursos Humanos", "Curso de Recursos Humanos"));
        cursosIniciais.add(new Curso(0, "Sistemas para Internet", "Curso de Sistemas para Internet"));
        
        // Inicializa os cursos no banco de dados
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
            System.err.println("Erro ao buscar curso: " + e.getMessage());
            return null;
        }
    }
} 