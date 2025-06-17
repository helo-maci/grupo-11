package br.com.ies.gui;

import br.com.ies.dao.PalestranteDAO;
import br.com.ies.model.Palestrante;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

public class PalestranteForm extends JDialog {
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextArea txtMiniCurriculo;
    private JButton btnSelecionarFoto;
    private JLabel lblFoto;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private boolean confirmado = false;
    private Palestrante palestrante;
    private byte[] fotoSelecionada;
    
    public PalestranteForm(Frame owner, Palestrante palestrante) {

        super(owner, palestrante == null ? "Novo Palestrante" : "Editar Palestrante", true);
        this.palestrante = palestrante;
        
        initComponents();
        carregarDados();
        
        setSize(400, 400);
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
        formPanel.add(new JLabel("Nome:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtNome = new JTextField(20);
        formPanel.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtEmail = new JTextField(20);
        formPanel.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Mini Currículo:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtMiniCurriculo = new JTextArea(3, 20);
        txtMiniCurriculo.setLineWrap(true);
        txtMiniCurriculo.setWrapStyleWord(true);
        txtMiniCurriculo.setFont(txtNome.getFont());
        JScrollPane scrollMiniCurriculo = new JScrollPane(txtMiniCurriculo);
        scrollMiniCurriculo.setPreferredSize(new Dimension(300, 60));
        formPanel.add(scrollMiniCurriculo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Foto:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel fotoPanel = new JPanel(new BorderLayout());
        btnSelecionarFoto = new JButton("Selecionar Foto");
        lblFoto = new JLabel("Nenhuma foto selecionada");
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
        
        btnSelecionarFoto.addActionListener(e -> selecionarFoto());
        
        fotoPanel.add(btnSelecionarFoto, BorderLayout.NORTH);
        fotoPanel.add(lblFoto, BorderLayout.CENTER);
        formPanel.add(fotoPanel, gbc);
        
        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        
        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void selecionarFoto() {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory()) return true;
                String name = f.getName().toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
            }
            
            public String getDescription() {
                return "Imagens (*.jpg, *.jpeg, *.png)";
            }
        });
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                fotoSelecionada = Files.readAllBytes(selectedFile.toPath());
                lblFoto.setText(selectedFile.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao ler a foto: " + ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void salvar() {

        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome é obrigatório",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O email é obrigatório",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {

            if (palestrante == null) {
                palestrante = new Palestrante();
            }
            
            palestrante.setNome(txtNome.getText().trim());
            palestrante.setEmail(txtEmail.getText().trim());
            palestrante.setMiniCurriculo(txtMiniCurriculo.getText().trim());
            if (fotoSelecionada != null) {
                palestrante.setFoto(fotoSelecionada);
            }

            new PalestranteDAO().salvar(palestrante);
            
            confirmado = true;
            dispose();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar palestrante: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void carregarDados() {

        if (palestrante != null) {
            txtNome.setText(palestrante.getNome());
            txtEmail.setText(palestrante.getEmail());
            txtMiniCurriculo.setText(palestrante.getMiniCurriculo());
            if (palestrante.getFoto() != null) {
                fotoSelecionada = palestrante.getFoto();
                lblFoto.setText("Foto carregada");
            }
        }
    }
    
    public boolean isConfirmado() {
        return confirmado;
    }
    
    public Palestrante getPalestrante() {
        return palestrante;
    }
} 