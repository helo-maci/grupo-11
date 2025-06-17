package br.com.ies.gui;

import br.com.ies.dao.EventoDAO;
import br.com.ies.model.Curso;
import br.com.ies.model.Evento;
import br.com.ies.model.Palestrante;
import br.com.ies.service.CursoService;
import br.com.ies.util.SlugUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


public class EventoForm extends JDialog {
    private JTextField txtTitulo;
    private JTextArea txtDescricao;
    private JFormattedTextField txtData;
    private JFormattedTextField txtHora;
    private JComboBox<Curso> cmbCurso;
    private JComboBox<Palestrante> cmbPalestrante;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private boolean confirmado = false;
    private Evento evento;
    
    public EventoForm(Frame owner, Evento evento) {

        super(owner, evento == null ? "Novo Evento" : "Editar Evento", true);
        this.evento = evento;
        
        initComponents();
        carregarDados();
        
        setSize(500, 450);
        setLocationRelativeTo(owner);
    }
    
    private void initComponents() {

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Título:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtTitulo = new JTextField(20);
        formPanel.add(txtTitulo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Descrição:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtDescricao = new JTextArea(2, 20);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setFont(txtTitulo.getFont());
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        scrollDescricao.setPreferredSize(new Dimension(300, 50));
        formPanel.add(scrollDescricao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Data (dd/MM/yyyy):"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        try {
            MaskFormatter dataMask = new MaskFormatter("##/##/####");
            dataMask.setPlaceholderCharacter('_');
            txtData = new JFormattedTextField(dataMask);
            txtData.setColumns(20);
        } catch (ParseException e) {
            txtData = new JFormattedTextField();
        }
        formPanel.add(txtData, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Hora (HH:mm):"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        try {
            MaskFormatter horaMask = new MaskFormatter("##:##");
            horaMask.setPlaceholderCharacter('_');
            txtHora = new JFormattedTextField(horaMask);
            txtHora.setColumns(20);
        } catch (ParseException e) {
            txtHora = new JFormattedTextField();
        }
        formPanel.add(txtHora, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Curso:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cmbCurso = new JComboBox<>();
        carregarCursos();
        formPanel.add(cmbCurso, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Palestrante:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cmbPalestrante = new JComboBox<>();
        carregarPalestrantes();
        formPanel.add(cmbPalestrante, gbc);
        
        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        
        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);
        
        add(buttonPanel, BorderLayout.SOUTH);

        txtTitulo.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                gerarSlug();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                gerarSlug();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                gerarSlug();
            }
        });
    }
    
    private void carregarCursos() {

        List<Curso> cursos = CursoService.getCursos();
        for (Curso curso : cursos) {
            cmbCurso.addItem(curso);
        }
    }
    
    private void carregarPalestrantes() {

        try {
            List<Palestrante> palestrantes = new br.com.ies.dao.PalestranteDAO().listarTodos();
            for (Palestrante palestrante : palestrantes) {
                cmbPalestrante.addItem(palestrante);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar palestrantes: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void carregarDados() {

        if (evento != null) {
            txtTitulo.setText(evento.getTitulo());
            txtDescricao.setText(evento.getDescricao());

            txtData.setText(evento.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            txtHora.setText(evento.getHora().format(DateTimeFormatter.ofPattern("HH:mm")));

            for (int i = 0; i < cmbCurso.getItemCount(); i++) {
                Curso curso = cmbCurso.getItemAt(i);
                if (curso.getId() == evento.getIdCurso()) {
                    cmbCurso.setSelectedIndex(i);
                    break;
                }
            }

            for (int i = 0; i < cmbPalestrante.getItemCount(); i++) {
                Palestrante palestrante = cmbPalestrante.getItemAt(i);
                if (palestrante.getId() == evento.getIdPalestrante()) {
                    cmbPalestrante.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            txtData.setText("");
            txtHora.setText("");
        }
    }
    
    private void gerarSlug() {

        String titulo = txtTitulo.getText();
        if (!titulo.isEmpty()) {
            SlugUtil.gerarSlug(titulo);
        }
    }
    
    private void salvar() {

        if (txtTitulo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O título é obrigatório",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (txtData.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "A data é obrigatória",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (txtHora.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "A hora é obrigatória",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (cmbCurso.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um curso",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (cmbPalestrante.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um palestrante",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            LocalDate data = LocalDate.parse(txtData.getText(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalTime hora = LocalTime.parse(txtHora.getText(),
                    DateTimeFormatter.ofPattern("HH:mm"));

            if (evento == null) {
                evento = new Evento();
            }
            
            evento.setTitulo(txtTitulo.getText().trim());
            evento.setSlug(SlugUtil.gerarSlug(txtTitulo.getText().trim()));
            evento.setDescricao(txtDescricao.getText().trim());
            evento.setData(data);
            evento.setHora(hora);
            evento.setIdCurso(((Curso) cmbCurso.getSelectedItem()).getId());
            evento.setIdPalestrante(((Palestrante) cmbPalestrante.getSelectedItem()).getId());

            EventoDAO eventoDAO = new EventoDAO();
            if (evento.getId() == 0) {
                eventoDAO.inserir(evento);
            } else {
                eventoDAO.atualizar(evento);
            }
            
            confirmado = true;
            dispose();
            
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data ou hora inválida",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar evento: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isConfirmado() {
        return confirmado;
    }
} 
