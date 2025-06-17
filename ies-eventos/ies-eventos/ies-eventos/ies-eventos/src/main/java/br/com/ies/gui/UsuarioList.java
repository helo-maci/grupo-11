package br.com.ies.gui;

import br.com.ies.dao.UsuarioDAO;
import br.com.ies.model.Curso;
import br.com.ies.model.Usuario;
import br.com.ies.service.CursoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class UsuarioList extends JDialog {
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<Curso> cmbFiltroCurso;
    private UsuarioDAO usuarioDAO;

    public UsuarioList(Frame owner) {
        super(owner, "Lista de Usuários", true);
        usuarioDAO = new UsuarioDAO();
        initComponents();
        carregarUsuarios();

        setSize(800, 500);
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Painel de filtros
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Filtrar por curso:"));
        cmbFiltroCurso = new JComboBox<>();

        cmbFiltroCurso.addItem(new Curso(0, "Todos os cursos"));
        carregarCursosFiltro();
        cmbFiltroCurso.addActionListener(e -> carregarUsuarios());
        filterPanel.add(cmbFiltroCurso);

        add(filterPanel, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "CPF", "Email", "Curso"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnFechar = new JButton("Fechar");

        btnFechar.addActionListener(e -> dispose());

        buttonPanel.add(btnFechar);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void carregarCursosFiltro() {
        List<Curso> cursos = CursoService.getCursos();
        for (Curso curso : cursos) {
            cmbFiltroCurso.addItem(curso);
        }
    }

    private void carregarUsuarios() {
        tableModel.setRowCount(0);
        try {
            List<Usuario> usuarios;
            Curso cursoSelecionado = (Curso) cmbFiltroCurso.getSelectedItem();

            if (cursoSelecionado != null && cursoSelecionado.getId() > 0) {
                usuarios = usuarioDAO.listarPorCurso(cursoSelecionado.getId());
            } else {
                usuarios = usuarioDAO.listarTodos();
            }

            for (Usuario usuario : usuarios) {
                Object[] row = {
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getCpf(),
                        usuario.getEmail(),
                        usuario.getNomeCurso()
                };
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuários: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}