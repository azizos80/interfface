package gestion_phar;

import DAO.MedIDAO;
import DAO.IDAO;
import modele.Medicament;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class GereMED extends JFrame {
    private JTextField textFieldNom;
    private JTextField textFieldStock;
    private DefaultListModel<String> model;
    private JList<String> list;
    private int idSelectionne = -1;

    private final IDAO<Medicament> dao = new MedIDAO();

    public GereMED() {
        setTitle("Gestion des Médicaments");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        setContentPane(new BackgroundPanel());
        setLayout(null);

        JLabel labelNom = new JLabel("Nom du médicament :");
        labelNom.setBounds(40, 20, 150, 25);
        add(labelNom);

        textFieldNom = new JTextField();
        textFieldNom.setBounds(200, 20, 200, 25);
        add(textFieldNom);

        JLabel labelStock = new JLabel("Stock :");
        labelStock.setBounds(420, 20, 50, 25);
        add(labelStock);

        textFieldStock = new JTextField();
        textFieldStock.setBounds(470, 20, 60, 25);
        add(textFieldStock);

        model = new DefaultListModel<>();
        list = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(40, 60, 490, 180);
        add(scrollPane);
        list.addListSelectionListener(e -> chargerSelection());

        // Boutons
        String[] noms = {"Ajouter", "Modifier", "Supprimer", "Enregistrer", "Fermer", "Retour"};
        int x = 10;
        for (String nom : noms) {
            JButton btn = new JButton(nom);
            btn.setBounds(x, 270, 90, 30);
            add(btn);
            x += 95;

            switch (nom) {
                case "Ajouter" -> btn.addActionListener(e -> ajouterMedicament());
                case "Modifier" -> btn.addActionListener(e -> activerEdition());
                case "Supprimer" -> btn.addActionListener(e -> supprimerMedicament());
                case "Enregistrer" -> btn.addActionListener(e -> enregistrerModif());
                case "Fermer" -> btn.addActionListener(e -> dao.fermer(this));
                case "Retour" -> btn.addActionListener(e -> {
                    dispose(); // Ferme cette fenêtre
                    new admin().setVisible(true); // Retour à l'admin
                });
            }
        }

        chargerMedicaments();
    }

    private void chargerMedicaments() {
        model.clear();
        List<Medicament> medicaments = dao.lister();
        for (Medicament m : medicaments) {
            model.addElement(m.id_med() + " - " + m.nom_med() + " - Stock: " + m.stock());
        }
    }

    private void chargerSelection() {
        String selected = list.getSelectedValue();
        if (selected == null) return;
        try {
            String[] parts = selected.split(" - ");
            idSelectionne = Integer.parseInt(parts[0]);
            textFieldNom.setText(parts[1]);
            textFieldStock.setText(parts[2].replace("Stock: ", ""));
        } catch (Exception e) {
            idSelectionne = -1;
        }
    }

    private void activerEdition() {
        if (idSelectionne == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un médicament.");
        } else {
            textFieldNom.setEditable(true);
            textFieldStock.setEditable(true);
        }
    }

    private void ajouterMedicament() {
        String nom = textFieldNom.getText().trim();
        String stockStr = textFieldStock.getText().trim();

        if (nom.isEmpty() || stockStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Champs vides.");
            return;
        }

        try {
            int stock = Integer.parseInt(stockStr);
            dao.ajouter(new Medicament(0, nom, stock));
            JOptionPane.showMessageDialog(this, "Médicament ajouté.");
            chargerMedicaments();
            textFieldNom.setText("");
            textFieldStock.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stock doit être un entier.");
        }
    }

    private void enregistrerModif() {
        if (idSelectionne == -1) {
            JOptionPane.showMessageDialog(this, "Aucun médicament sélectionné.");
            return;
        }

        String nom = textFieldNom.getText().trim();
        String stockStr = textFieldStock.getText().trim();

        if (nom.isEmpty() || stockStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Champs vides.");
            return;
        }

        try {
            int stock = Integer.parseInt(stockStr);
            dao.modifier(new Medicament(idSelectionne, nom, stock));
            JOptionPane.showMessageDialog(this, "Modification enregistrée.");
            chargerMedicaments();
            textFieldNom.setText("");
            textFieldStock.setText("");
            idSelectionne = -1;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stock doit être un entier.");
        }
    }

    private void supprimerMedicament() {
        if (idSelectionne == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un médicament.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Confirmer la suppression ?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
				dao.supprimer(idSelectionne);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            JOptionPane.showMessageDialog(this, "Médicament supprimé.");
            chargerMedicaments();
            textFieldNom.setText("");
            textFieldStock.setText("");
            idSelectionne = -1;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GereMED().setVisible(true));
    }

    class BackgroundPanel extends JPanel {
        private final Image backgroundImage;

        public BackgroundPanel() {
            backgroundImage = new ImageIcon("C:\\Users\\jammo\\Downloads\\ChatGPT Image May 1, 2025, 11_06_13 AM.png").getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
