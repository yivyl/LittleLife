import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class UI {
    private JFrame frame;
    private JTextArea statusArea;
    private JTextField petNameField, pronounField;
    private JComboBox<String> actionBox, choiceBox;
    private JButton confirmButton;
    private Tamagotchi pet;
    private Player player;
    private Logic gameLogic;
    private JLabel moodLabel;

    public void showStartScreen() {
        frame = new JFrame("LittleLife - Tamagotchi Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(230, 245, 255));

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(230, 245, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Welcome to LittleLife!");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

        // Pronouns
        JPanel pronounPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pronounPanel.setBackground(new Color(230, 245, 255));
        JLabel pronounLabel = new JLabel("Pronouns:");
        pronounField = new JTextField("they/them", 15);
        pronounPanel.add(pronounLabel);
        pronounPanel.add(pronounField);

        // Pet Name
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        namePanel.setBackground(new Color(230, 245, 255));
        JLabel nameLabel = new JLabel("Pet Name:");
        petNameField = new JTextField(15);
        namePanel.add(nameLabel);
        namePanel.add(petNameField);

        JButton startButton = new JButton("Start Game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setBackground(new Color(100, 149, 237));
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        startButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        startButton.addActionListener(e -> startGame());

        mainPanel.add(title);
        mainPanel.add(namePanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(pronounPanel);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(startButton);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void startGame() {
        String pronouns = pronounField.getText();
        String petName = petNameField.getText();

        if (petName.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a pet name!", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (petName.length() > 15) {
            petName = petName.substring(0, 15);
        }

        pet = new Tamagotchi(petName, pronouns);
        player = new Player("Player1");
        gameLogic = new Logic(pet, player);

        showGameScreen();
    }

    private void showGameScreen() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 248, 255));

        statusArea = new JTextArea(4, 20);
        statusArea.setText(formatStatus(pet.getStatus()));
        statusArea.setFont(new Font("Arial", Font.PLAIN, 14));
        statusArea.setBackground(new Color(255, 250, 240));
        statusArea.setEditable(false);
        statusArea.setLineWrap(true);
        statusArea.setWrapStyleWord(true);
        statusArea.setBorder(BorderFactory.createTitledBorder("Pet Status"));

        JScrollPane scrollPane = new JScrollPane(statusArea);

        JPanel petVisualPanel = new JPanel(new BorderLayout());
        petVisualPanel.setBackground(new Color(240, 248, 255));
        petVisualPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel moodPanel = new JPanel(new BorderLayout());
        moodPanel.setBackground(new Color(240, 248, 255));
        moodPanel.setBorder(BorderFactory.createTitledBorder("Mood"));

        moodLabel = new JLabel(":)", JLabel.CENTER);
        moodLabel.setFont(new Font("Monospaced", Font.BOLD, 24));

        moodPanel.add(moodLabel, BorderLayout.CENTER);
        petVisualPanel.add(moodPanel, BorderLayout.SOUTH);

        updatePetVisuals();

        topPanel.add(scrollPane, BorderLayout.CENTER);
        topPanel.add(petVisualPanel, BorderLayout.EAST);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(new Color(245, 245, 245));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

        actionBox = new JComboBox<>(new String[]{"Feed", "Play"});
        actionBox.setPreferredSize(new Dimension(100, 25));

        choiceBox = new JComboBox<>();
        choiceBox.setPreferredSize(new Dimension(120, 25));
        updateChoices("feed");

        actionBox.addActionListener(e -> {
            String selected = ((String) actionBox.getSelectedItem()).toLowerCase();
            updateChoices(selected);
        });

        confirmButton = new JButton("Do Action");
        confirmButton.setBackground(new Color(60, 179, 113));
        confirmButton.setForeground(Color.BLACK);
        confirmButton.setFocusPainted(false);

        confirmButton.addActionListener(e -> {
            String category = ((String) actionBox.getSelectedItem()).toLowerCase();
            String choice = (String) choiceBox.getSelectedItem();
            gameLogic.makeChoice(category, choice);
            statusArea.setText(formatStatus(pet.getStatus()));
            updatePetVisuals();
        });

        actionPanel.add(new JLabel("Action:"));
        actionPanel.add(actionBox);
        actionPanel.add(new JLabel("Option:"));
        actionPanel.add(choiceBox);
        actionPanel.add(confirmButton);

        frame.add(actionPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private String formatStatus(String status) {
        return status.replace(", ", "\n");
    }

    private void updateChoices(String category) {
        choiceBox.removeAllItems();
        if (category.equals("feed")) {
            for (String item : new String[]{"Apple", "Soda", "Beer", "Vegetables", "Candy", "Water"}) {
                choiceBox.addItem(item);
            }
        } else if (category.equals("play")) {
            for (String item : new String[]{"Fetch", "Walk", "Watch Movie", "Play Video Game"}) {
                choiceBox.addItem(item);
            }
        }
    }

    private void updatePetVisuals() {
        String mood = pet.getMood();
        switch (mood) {
            case "happy":
            case "excited":
            case "love":
                moodLabel.setText(":)");
                break;
            case "normal":
                moodLabel.setText(":|");
                break;
            case "worried":
                moodLabel.setText(":/");
                break;
            case "sad":
                moodLabel.setText(":'(");
                break;
            case "hungry":
                moodLabel.setText("*hungry*");
                break;
            case "sick":
                moodLabel.setText(">_<");
                break;
            case "dead":
                moodLabel.setText("X_X");
                break;
            default:
                moodLabel.setText(":|");
        }
    }
}
