package br.com.ies;

import br.com.ies.gui.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Classe principal do sistema
 * Responsável por iniciar a aplicação e configurar a interface gráfica
 */
public class Main {
    /**
     * Método principal que inicia a aplicação
     * @param args Argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        try {
            // Configura o look and feel do sistema operacional
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Inicia a interface gráfica na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
} 