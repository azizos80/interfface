package gestion_phar;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pharmacien extends JFrame {

    public Pharmacien() {
        setTitle("Interface Pharmacien");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal avec image de fond
        JPanel panel = new JPanel() {
            Image bg = new ImageIcon("C:\\Users\\jammo\\OneDrive\\Desktop\\jframe\\aaeafc00-f1dd-41b3-bb75-7947d41c2e67.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 20, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel title = new JLabel("Bienvenue, Pharmacien");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(0, 77, 26)); // Vert foncé
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        // Nouveaux boutons
        String[] labels = {
            "Consulter les clients et leurs crédits respectifs",
            "Consulter les médicaments et leurs stocks respectifs",
            "Gérer les ordonnances",
            "Rechercher un médicament"
        };

        for (int i = 0; i < labels.length; i++) {
            JButton button = new JButton(labels[i]);
            button.setPreferredSize(new Dimension(400, 50));
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBackground(new Color(224, 255, 238));
            button.setForeground(new Color(0, 51, 0));
            button.setFocusPainted(false);
            int finalI = i;
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Action : " + labels[finalI]);
                }
            });
            gbc.gridy = i + 1;
            panel.add(button, gbc);
        }

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Pharmacien());
    }
}

