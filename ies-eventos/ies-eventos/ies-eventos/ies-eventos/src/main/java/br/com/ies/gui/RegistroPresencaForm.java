package br.com.ies.gui;

import br.com.ies.model.Evento;
import br.com.ies.model.Usuario;
import br.com.ies.dao.InscricaoDAO;
import br.com.ies.dao.PresencaDAO;
import br.com.ies.dao.UsuarioDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class RegistroPresencaForm extends JDialog {

    private Evento evento;
    private JTable tableInscritos;
    private DefaultTableModel tableModel;
    private List<Usuario> inscritos;
    private JLabel lblEvento;
    private JLabel lblTotalInscritos;
    private JLabel lblTotalPresencas;
    private JButton btnRegistrarPresenca;
    private JButton btnRemoverPresenca;
    private JButton btnFechar;

    public RegistroPresencaForm(Frame parent, Evento evento) {
        super(parent, "Registro de Presença - " + evento.getTitulo(), true);
        this.evento = evento;
        setSize(900, 700);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        carregarInscritos();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Painel superior
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblEvento = new JLabel("Evento: " + evento.getTitulo());
        lblEvento.setFont(new Font("Arial", Font.BOLD, 18));
        lblEvento.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(lblEvento, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        lblTotalInscritos = new JLabel("Total Inscritos: 0");
        lblTotalInscritos.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTotalPresencas = new JLabel("Total Presenças: 0");
        lblTotalPresencas.setFont(new Font("Arial", Font.PLAIN, 14));
        statsPanel.add(lblTotalInscritos);
        statsPanel.add(lblTotalPresencas);
        topPanel.add(statsPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Tabela
        String[] colunas = {"ID", "CPF", "Nome", "Curso", "Status Presença"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableInscritos = new JTable(tableModel);
        tableInscritos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableInscritos.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
        tableInscritos.getColumnModel().getColumn(1).setPreferredWidth(100); // CPF
        tableInscritos.getColumnModel().getColumn(2).setPreferredWidth(200); // Nome
        tableInscritos.getColumnModel().getColumn(3).setPreferredWidth(150); // Curso
        tableInscritos.getColumnModel().getColumn(4).setPreferredWidth(120); // Status Presença


        JScrollPane scrollPane = new JScrollPane(tableInscritos);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRegistrarPresenca = new JButton("Registrar Presença");
        btnRemoverPresenca = new JButton("Remover Presença");
        btnFechar = new JButton("Fechar");

        buttonPanel.add(btnRegistrarPresenca);
        buttonPanel.add(btnRemoverPresenca);
        buttonPanel.add(btnFechar);
        add(buttonPanel, BorderLayout.SOUTH);

        btnRegistrarPresenca.addActionListener(e -> registrarPresenca());
        btnRemoverPresenca.addActionListener(e -> removerPresenca());
        btnFechar.addActionListener(e -> dispose());
    }

    private void carregarInscritos() {
        tableModel.setRowCount(0);
        try {
            InscricaoDAO inscricaoDAO = new InscricaoDAO();
            PresencaDAO presencaDAO = new PresencaDAO();

            inscritos = inscricaoDAO.listarInscritosPorEvento(evento.getId());

            for (Usuario usuario : inscritos) {
                boolean presente = presencaDAO.usuarioPresente(usuario.getId(), evento.getId());
                Object[] row = {
                        usuario.getId(),
                        usuario.getCpf(),
                        usuario.getNome(),
                        usuario.getNomeCurso(), // Pega o nome do curso do objeto Usuario
                        presente ? "✅ Presente" : "❌ Ausente"
                };
                tableModel.addRow(row);
            }
            atualizarContadores();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar inscritos: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarContadores() throws SQLException {
        InscricaoDAO inscricaoDAO = new InscricaoDAO();
        PresencaDAO presencaDAO = new PresencaDAO();

        int totalInscritos = inscricaoDAO.contarInscritosPorEvento(evento.getId());
        int totalPresencas = presencaDAO.contarPresencasPorEvento(evento.getId());

        lblTotalInscritos.setText("Total Inscritos: " + totalInscritos);
        lblTotalPresencas.setText("Total Presenças: " + totalPresencas);
    }

    private void registrarPresenca() {
        int selectedRow = tableInscritos.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário da lista.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cpf = (String) tableModel.getValueAt(selectedRow, 1);

        try {
            Usuario usuario = null;
            for (Usuario u : inscritos) {
                if (u.getCpf().equals(cpf)) {
                    usuario = u;
                    break;
                }
            }

            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PresencaDAO presencaDAO = new PresencaDAO();
            if (presencaDAO.usuarioPresente(usuario.getId(), evento.getId())) {
                JOptionPane.showMessageDialog(this, "Usuário já está com presença registrada.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            presencaDAO.registrarPresenca(usuario.getId(), evento.getId());

            tableModel.setValueAt("✅ Presente", selectedRow, 4); // Coluna do Status Presença

            atualizarContadores();

            JOptionPane.showMessageDialog(this, "Presença registrada com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar presença: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerPresenca() {
        int selectedRow = tableInscritos.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário da lista.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cpf = (String) tableModel.getValueAt(selectedRow, 1);

        try {
            Usuario usuario = null;
            for (Usuario u : inscritos) {
                if (u.getCpf().equals(cpf)) {
                    usuario = u;
                    break;
                }
            }

            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PresencaDAO presencaDAO = new PresencaDAO();
            presencaDAO.removerPresenca(usuario.getId(), evento.getId());

            tableModel.setValueAt("❌ Ausente", selectedRow, 4); // Coluna do Status Presença

            atualizarContadores();

            JOptionPane.showMessageDialog(this, "Presença removida com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao remover presença: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}