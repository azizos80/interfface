package gestion_phar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class login extends JFrame {
    // Composants
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, quitButton;

    // Configuration
    private static final Color PRIMARY_COLOR = new Color(0, 102, 0);
    private static final Color ERROR_COLOR = new Color(204, 0, 0);
    private static final Color PANEL_BG = new Color(255, 255, 255, 230);
    private static final Dimension PANEL_SIZE = new Dimension(450, 400);
    private static final Dimension FIELD_SIZE = new Dimension(300, 40);
    private static final Dimension BUTTON_SIZE = new Dimension(220, 45);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 28);
    private static final Font MAIN_FONT = new Font("Arial", Font.PLAIN, 16);

    // Chemins absolus (à adapter selon ta machine)
    private static final String USER_ICON_PATH = "C:\\Users\\jammo\\OneDrive\\Desktop\\jframe\\Login\\icon.png";
    private static final String KEY_ICON_PATH = "C:\\Users\\jammo\\OneDrive\\Desktop\\jframe\\loc.jpg";
    private static final String BACKGROUND_PATH = "C:\\Users\\jammo\\OneDrive\\Desktop\\jframe\\backrrr.png";

    public login() {
        initUI();
        setupEventHandlers();
    }

    private void initUI() {
        setTitle("Connexion - Système de Gestion de Pharmacie");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
      //proposition le window selon ecron et null pour placer a la centre
        setUndecorated(true);

        JLayeredPane layeredPane = new JLayeredPane();
        setContentPane(layeredPane);

        // Arrière-plan
        JLabel background = createBackgroundLabel();
        background.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
        //sur forme de coche

        // Panneau de connexion
        JPanel loginPanel = createLoginPanel();
        int x = (getWidth() - PANEL_SIZE.width) / 2;
        int y = (getHeight() - PANEL_SIZE.height) / 2;
        loginPanel.setBounds(x, y, PANEL_SIZE.width, PANEL_SIZE.height);
        layeredPane.add(loginPanel, JLayeredPane.PALETTE_LAYER);
    }

    private JLabel createBackgroundLabel() {
        Image image = Toolkit.getDefaultToolkit().getImage(BACKGROUND_PATH);
        if (image != null) {
            Image scaled = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaled));
        } else {
            System.err.println("Erreur de chargement du fond.");
            JLabel label = new JLabel();
            label.setOpaque(true);
            label.setBackground(Color.LIGHT_GRAY);
            return label;
        }
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(PANEL_BG);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            }
        };

        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(PANEL_SIZE);
        panel.setOpaque(false);

        JLabel title = createTitleLabel();
        JLabel userIcon = createIconLabel(USER_ICON_PATH, 25, 25);
        usernameField = createInputField("Nom d'utilisateur");
        JLabel keyIcon = createIconLabel(KEY_ICON_PATH, 25, 25);
        passwordField = createPasswordField("Mot de passe");
        loginButton = createButton("Connexion", PRIMARY_COLOR);
        quitButton = createButton("Quitter", ERROR_COLOR);

        addComponent(panel, title, new GridBagConstraints(0, 0, 2, 1, 1.0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 20, 30, 20), 0, 0));
        addComponent(panel, userIcon, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 20, 10, 5), 0, 0));
        addComponent(panel, usernameField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 10, 20), 0, 0));
        addComponent(panel, keyIcon, new GridBagConstraints(0, 2, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 20, 10, 5), 0, 0));
        addComponent(panel, passwordField, new GridBagConstraints(1, 2, 1, 1, 1.0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 10, 20), 0, 0));
        addComponent(panel, loginButton, new GridBagConstraints(0, 3, 2, 1, 1.0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(20, 20, 10, 20), 0, 0));
        addComponent(panel, quitButton, new GridBagConstraints(0, 4, 2, 1, 1.0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 20, 20, 20), 0, 0));

        return panel;
    }

    private JLabel createIconLabel(String path, int width, int height) {
        Image image = Toolkit.getDefaultToolkit().getImage(path);
        if (image != null) {
            Image scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaled));
        } else {
            System.err.println("Erreur de chargement de l'icône: " + path);
            return createDefaultIconLabel(width, height);
        }
    }

    private JLabel createDefaultIconLabel(int width, int height) {
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(width, height));
        label.setOpaque(true);
        label.setBackground(Color.GRAY);
        return label;
    }

    private JLabel createTitleLabel() {
        JLabel label = new JLabel("CONNEXION", SwingConstants.CENTER);
        label.setFont(TITLE_FONT);
        label.setForeground(PRIMARY_COLOR);
        return label;
    }

    private JTextField createInputField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(MAIN_FONT);
        field.setForeground(Color.GRAY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        field.setPreferredSize(FIELD_SIZE);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });

        return field;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(placeholder);
        field.setFont(MAIN_FONT);
        field.setForeground(Color.GRAY);
        field.setEchoChar((char) 0);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        field.setPreferredSize(FIELD_SIZE);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setEchoChar('•');
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(field.getPassword()).isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                    field.setEchoChar((char) 0);
                }
            }
        });

        return field;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(MAIN_FONT.deriveFont(Font.BOLD));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setPreferredSize(BUTTON_SIZE);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//methode atrad button yach3al
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void addComponent(Container container, Component component, GridBagConstraints gbc) {
        container.add(component, gbc);
    }

    private void setupEventHandlers() {
        loginButton.addActionListener(e -> handleLogin());
        quitButton.addActionListener(e -> System.exit(0));
        getRootPane().setDefaultButton(loginButton);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if (username.isEmpty() || username.equals("Nom d'utilisateur") ||
            password.isEmpty() || password.equals("Mot de passe")) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir vos identifiants",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String userType = authenticate(username, password);

        if (userType != null) {
            JOptionPane.showMessageDialog(this, "Connexion réussie",
                    "Succès", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // ferme la fenêtre de login

            if (userType.equalsIgnoreCase("administrateur")) {
                new admin();
            } else if (userType.equalsIgnoreCase("pharmacien")) {
                new Pharmacien(); // cette classe doit exister !
            } else {
                JOptionPane.showMessageDialog(this, "Type d'utilisateur inconnu",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Identifiants incorrects",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String authenticate(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/projet";
        String dbUser = "root";
        String dbPass = "14072004336";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT typee FROM utilisateur WHERE nom_uti = ? AND mot_de_passe = ?")) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("typee"); // retourne le type : admin ou pharmacien
                } else {
                    return null; // identifiants incorrects
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur de connexion à la base de données:\n" + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            login form = new login();
            form.setVisible(true);
        });
    }
}
