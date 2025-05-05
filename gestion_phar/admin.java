package gestion_phar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class admin extends JFrame {

    public admin() {
        setTitle("Interface Administrateur");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal avec image de fond personnalisée
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
        gbc.gridx = 0;

        // Titre de l'interface (couleur plus claire)
        JLabel title = new JLabel("Bienvenue, Administrateur");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(245, 245, 245)); // couleur plus claire
        gbc.gridy = 0;
        panel.add(title, gbc);

        // Libellés et chemins des icônes
        String[] labels = {
            "Gérer les clients",
            "Gérer les médicaments"
        };
        String[] iconPaths = {
            "C:\\Users\\jammo\\Downloads\\gcli.png",
            "C:\\Users\\jammo\\Downloads\\qsq.png"
        };

        for (int i = 0; i < labels.length; i++) {
            JButton button = new JButton(labels[i]);
            button.setPreferredSize(new Dimension(300, 60));
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setBackground(new Color(224, 255, 238));
            button.setForeground(new Color(0, 51, 0));
            button.setFocusPainted(false);

            // Ajout de l’icône (40x40)
            ImageIcon icon = new ImageIcon(iconPaths[i]);
            Image scaled = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaled));
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setIconTextGap(15);

            int index = i;
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    switch (labels[index]) {
                        case "Gérer les médicaments" -> new GereMED().setVisible(true);
                        case "Gérer les clients" -> new GereClient().setVisible(true);
                    }
                }
            });

            gbc.gridy = i + 1;
            panel.add(button, gbc);
        }

        // Bouton Se Déconnecter avec icône
        JButton logoutButton = new JButton("Se déconnecter");
        logoutButton.setPreferredSize(new Dimension(300, 60));
        logoutButton.setFont(new Font("Arial", Font.BOLD, 18));
        logoutButton.setBackground(new Color(255, 204, 204));
        logoutButton.setForeground(new Color(102, 0, 0));
        logoutButton.setFocusPainted(false);

        // Icône pour le bouton déconnexion
        ImageIcon logoutIcon = new ImageIcon("C:\\Users\\jammo\\Downloads\\sqs.png");
        Image logoutScaled = logoutIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        logoutButton.setIcon(new ImageIcon(logoutScaled));
        logoutButton.setHorizontalAlignment(SwingConstants.LEFT);
        logoutButton.setIconTextGap(15);

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new login().setVisible(true);
            }
        });

        gbc.gridy = labels.length + 1;
        panel.add(logoutButton, gbc);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new admin());
    }
}
