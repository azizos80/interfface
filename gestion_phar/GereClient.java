package gestion_phar;

import DAO.ClientIDAO;
import DAO.IDAO;
import modele.Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GereClient extends JFrame {
    private JTextField textNom, textPrenom, textCredit, textAdresse, textNumTel;
    private DefaultListModel<String> model;
    private JList<String> list;
    private int idSelectionne = -1;

    private IDAO<Client> dao = new ClientIDAO();

    public GereClient() {
        setTitle("Gestion des Clients");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setContentPane(new BackgroundPanel());
        setLayout(null);

        // Icône centrée en haut
        ImageIcon icon = new ImageIcon("C:\\Users\\jammo\\Downloads\\clinet.png");
        Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(img));
        iconLabel.setBounds((getWidth() - 80) / 2, 10, 80, 80);
        add(iconLabel);

        // Bouton Retour
        JButton btnRetour = new JButton("⬅");
        btnRetour.setBounds(10, 10, 60, 30);
        add(btnRetour);
        btnRetour.addActionListener(e -> {
            dispose();
            new admin().setVisible(true);
        });

        // Champs de saisie
        JLabel labelNom = new JLabel("Nom :");
        labelNom.setBounds(40, 100, 100, 25);
        add(labelNom);
        textNom = new JTextField();
        textNom.setBounds(90, 100, 150, 25);
        add(textNom);

        JLabel labelPrenom = new JLabel("Prénom :");
        labelPrenom.setBounds(260, 100, 100, 25);
        add(labelPrenom);
        textPrenom = new JTextField();
        textPrenom.setBounds(330, 100, 150, 25);
        add(textPrenom);

        JLabel labelCredit = new JLabel("Crédit :");
        labelCredit.setBounds(40, 140, 100, 25);
        add(labelCredit);
        textCredit = new JTextField();
        textCredit.setBounds(90, 140, 150, 25);
        add(textCredit);

        JLabel labelAdresse = new JLabel("Adresse :");
        labelAdresse.setBounds(40, 180, 100, 25);
        add(labelAdresse);
        textAdresse = new JTextField();
        textAdresse.setBounds(90, 180, 150, 25);
        add(textAdresse);

        JLabel labelNumTel = new JLabel("Num Tel :");
        labelNumTel.setBounds(260, 180, 100, 25);
        add(labelNumTel);
        textNumTel = new JTextField();
        textNumTel.setBounds(330, 180, 150, 25);
        add(textNumTel);

        // Liste des clients
        model = new DefaultListModel<>();
        list = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(40, 220, 490, 140);
        add(scrollPane);
        list.addListSelectionListener(e -> chargerSelection());

        // Boutons CRUD
        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.setBounds(40, 380, 100, 30);
        add(btnAjouter);
        btnAjouter.addActionListener(e -> ajouterClient());

        JButton btnModifier = new JButton("Modifier");
        btnModifier.setBounds(150, 380, 100, 30);
        add(btnModifier);
        btnModifier.addActionListener(e -> activerEdition());

        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.setBounds(260, 380, 100, 30);
        add(btnSupprimer);
        btnSupprimer.addActionListener(e -> supprimerClient());

        JButton btnEnregistrer = new JButton("Enregistrer");
        btnEnregistrer.setBounds(370, 380, 120, 30);
        add(btnEnregistrer);
        btnEnregistrer.addActionListener(e -> enregistrerModif());

        JButton btnFermer = new JButton("Fermer");
        btnFermer.setBounds(500, 380, 80, 30);
        add(btnFermer);
        btnFermer.addActionListener(e -> dispose());

        chargerClients();
    }

    private void chargerClients() {
        model.clear();
        List<Client> clients = dao.lister();
        for (Client c : clients) {
            String ligne = String.format(
                "%d - %s %s - Crédit: %.2f - Adresse: %s - Tel: %s",
                c.id_cl(), c.nom_cli(), c.prenom(), c.credit(), c.adress(), c.num_tel()
            );
            model.addElement(ligne);
        }
    }

    private void chargerSelection() {
        String selected = list.getSelectedValue();
        if (selected == null) return;
        try {
            String[] parts = selected.split(" - ");
            idSelectionne = Integer.parseInt(parts[0]);
            String[] noms = parts[1].split(" ");
            textNom.setText(noms[0]);
            textPrenom.setText(noms.length > 1 ? noms[1] : "");
            textCredit.setText(parts[2].replace("Crédit: ", ""));
            textAdresse.setText(parts[3].replace("Adresse: ", ""));
            textNumTel.setText(parts[4].replace("Tel: ", ""));
        } catch (Exception e) {
            idSelectionne = -1;
        }
    }

    private void activerEdition() {
        if (idSelectionne == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
        } else {
            textNom.setEditable(true);
            textPrenom.setEditable(true);
            textCredit.setEditable(true);
            textAdresse.setEditable(true);
            textNumTel.setEditable(true);
        }
    }

    private void ajouterClient() {
        String nom = textNom.getText().trim();
        String prenom = textPrenom.getText().trim();
        String creditStr = textCredit.getText().trim();
        String adresse = textAdresse.getText().trim();
        String numTel = textNumTel.getText().trim();

        if (nom.isEmpty() || prenom.isEmpty() || creditStr.isEmpty() || adresse.isEmpty() || numTel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Champs vides.");
            return;
        }

        try {
            double credit = Double.parseDouble(creditStr);
            dao.ajouter(new Client(0, nom, prenom, credit, adresse, numTel));
            JOptionPane.showMessageDialog(this, "Client ajouté.");
            chargerClients();
            textNom.setText("");
            textPrenom.setText("");
            textCredit.setText("");
            textAdresse.setText("");
            textNumTel.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Crédit doit être un nombre.");
        }
    }

    private void enregistrerModif() {
        if (idSelectionne == -1) {
            JOptionPane.showMessageDialog(this, "Aucun client sélectionné.");
            return;
        }

        String nom = textNom.getText().trim();
        String prenom = textPrenom.getText().trim();
        String creditStr = textCredit.getText().trim();
        String adresse = textAdresse.getText().trim();
        String numTel = textNumTel.getText().trim();

        if (nom.isEmpty() || prenom.isEmpty() || creditStr.isEmpty() || adresse.isEmpty() || numTel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Champs vides.");
            return;
        }

        try {
            double credit = Double.parseDouble(creditStr);
            dao.modifier(new Client(idSelectionne, nom, prenom, credit, adresse, numTel));
            JOptionPane.showMessageDialog(this, "Client modifié.");
            chargerClients();
            textNom.setText("");
            textPrenom.setText("");
            textCredit.setText("");
            textAdresse.setText("");
            textNumTel.setText("");
            idSelectionne = -1;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Crédit doit être un nombre.");
        }
    }

    private void supprimerClient() {
        if (idSelectionne == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Confirmer la suppression ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                dao.supprimer(idSelectionne);
                JOptionPane.showMessageDialog(this, "Client supprimé.");
                chargerClients();
                textNom.setText("");
                textPrenom.setText("");
                textCredit.setText("");
                textAdresse.setText("");
                textNumTel.setText("");
                idSelectionne = -1;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erreur de suppression.");
            }
        }
    }

    // Classe pour gérer le fond d'écran
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                backgroundImage = new ImageIcon("C:\\\\Users\\\\jammo\\\\Downloads\\\\ChatGPT Image May 1, 2025, 11_06_13 AM.png").getImage();
            } catch (Exception e) {
                System.out.println("Erreur image : " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null)
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GereClient().setVisible(true));
    }
}
