import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import janelas.TelaPrincipal;

public static void main(String[] args) {
        // Tenta aplicar o design system do sistema operacional (deixa as bordas mais modernas)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }