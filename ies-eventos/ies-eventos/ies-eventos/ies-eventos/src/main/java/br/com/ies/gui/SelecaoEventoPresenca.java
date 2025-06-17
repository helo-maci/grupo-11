package br.com.ies.gui;

import br.com.ies.model.Curso;
import br.com.ies.model.Evento;
import br.com.ies.service.CursoService;
import br.com.ies.dao.EventoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SelecaoEventoPresenca extends JDialog {
    
    private JComboBox<Curso> comboCurso;
    private JTable tableEventos;
    private DefaultTableModel tableModel;
    private List<Evento> eventos;
    private JButton btnSelecionar;
    private JButton btnFechar;
    private Evento eventoSelecionado;
    
    public SelecaoEventoPresenca(Frame parent) {
        super(parent, "Selecionar Evento para Registro de Presença", true);
        
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        initComponents();
        carregarCursos();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Panel superior para seleção de curso
        JPanel panelCurso = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCurso.setBorder(BorderFactory.createTitledBorder("Selecionar Curso"));
        
        JLabel lblCurso = new JLabel("Curso:");
        comboCurso = new JComboBox<>();
        comboCurso.setPreferredSize(new Dimension(300, 25));
        
        panelCurso.add(lblCurso);
        panelCurso.add(comboCurso);
        
        add(panelCurso, BorderLayout.NORTH);
        
        // Panel central para lista de eventos
        JPanel panelEventos = new JPanel(new BorderLayout());
        panelEventos.setBorder(BorderFactory.createTitledBorder("Eventos do Curso"));
        
        String[] colunas = {"Título", "Descrição", "Data", "Hora", "Palestrante"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableEventos = new JTable(tableModel);
        tableEventos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tableEventos.getColumnModel().getColumn(0).setPreferredWidth(200);
        tableEventos.getColumnModel().getColumn(1).setPreferredWidth(300);
        tableEventos.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableEventos.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableEventos.getColumnModel().getColumn(4).setPreferredWidth(150);
        
        JScrollPane scrollPane = new JScrollPane(tableEventos);
        panelEventos.add(scrollPane, BorderLayout.CENTER);
        
        add(panelEventos, BorderLayout.CENTER);
        
        // Panel inferior para botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnSelecionar = new JButton("Selecionar Evento");
        btnFechar = new JButton("Fechar");
        
        panelBotoes.add(btnSelecionar);
        panelBotoes.add(btnFechar);
        
        add(panelBotoes, BorderLayout.SOUTH);
        
        // Event listeners
        comboCurso.addActionListener(e -> {
            if (comboCurso.getSelectedItem() != null) {
                carregarEventosPorCurso();
            }
        });
        
        btnSelecionar.addActionListener(e -> selecionarEvento());
        btnFechar.addActionListener(e -> dispose());
        
        // Inicialmente desabilitar botão selecionar
        btnSelecionar.setEnabled(false);
    }
    
    private void carregarCursos() {
        try {
            List<Curso> cursos = CursoService.getCursos();
            comboCurso.removeAllItems();
            
            // Adicionar item vazio
            comboCurso.addItem(new Curso(0, "Selecione um curso..."));
            
            for (Curso curso : cursos) {
                comboCurso.addItem(curso);
            }
            
            comboCurso.setSelectedIndex(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar cursos: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void carregarEventosPorCurso() {
        Curso cursoSelecionado = (Curso) comboCurso.getSelectedItem();
        
        if (cursoSelecionado == null || cursoSelecionado.getId() == 0) {
            tableModel.setRowCount(0);
            btnSelecionar.setEnabled(false);
            return;
        }
        
        try {
            EventoDAO eventoDAO = new EventoDAO();
            eventos = eventoDAO.listarPorCurso(cursoSelecionado.getId());
            
            tableModel.setRowCount(0);
            DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");
            
            for (Evento evento : eventos) {
                Object[] row = {
                    evento.getTitulo() != null ? evento.getTitulo() : "",
                    evento.getDescricao() != null ? evento.getDescricao() : "",
                    evento.getData() != null ? evento.getData().format(dataFormatter) : "",
                    evento.getHora() != null ? evento.getHora().format(horaFormatter) : "",
                    evento.getNomePalestrante() != null ? evento.getNomePalestrante() : "Palestrante não informado"
                };
                tableModel.addRow(row);
            }
            
            btnSelecionar.setEnabled(!eventos.isEmpty());
            
            if (eventos.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Nenhum evento encontrado para o curso '" + cursoSelecionado.getNome() + "'.",
                    "Informação", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar eventos: " + ex.getMessage() + "\n\nDetalhes técnicos: " + ex.toString(),
                "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro inesperado ao carregar eventos: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void selecionarEvento() {
        int selectedRow = tableEventos.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento da lista.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        eventoSelecionado = eventos.get(selectedRow);
        
        // Abrir tela de registro de presença
        RegistroPresencaForm form = new RegistroPresencaForm((Frame) getOwner(), eventoSelecionado);
        form.setVisible(true);
        
        dispose();
    }
    
    public Evento getEventoSelecionado() {
        return eventoSelecionado;
    }
} 