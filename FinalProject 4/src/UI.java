import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class UI {
    private JFrame frame;
    private JPanel mainPanel;
    private JTextArea statusArea;
    private Tamagotchi pet;
    private Player player;
    private Logic gameLogic;

    public void showStartScreen() {
        frame = new JFrame("LittleLife - Tamagotchi Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        // center window
        frame.setLocationRelativeTo(null); 

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        // padding
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        JLabel pronounLabel = new JLabel("Choose pronouns: (he/him, she/her, they/them)");
        JTextField pronounField = new JTextField(15); 

        JLabel nameLabel = new JLabel("Enter pet name (Max 15 characters):");
        JTextField nameField = new JTextField(15);

        JButton startButton = new JButton("Start Game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.addActionListener(e -> {
            String pronouns = pronounField.getText();
            String petName = nameField.getText();
            if (petName.length() > 15) petName = petName.substring(0, 15);

            pet = new Tamagotchi(petName, pronouns);
            player = new Player("Player1");
            gameLogic = new Logic(pet, player);

            showGameScreen();
        });

        mainPanel.add(pronounLabel);
        mainPanel.add(pronounField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(startButton);

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }


    private void showGameScreen() {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setSize(400, 400);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        statusArea = new JTextArea(pet.getStatus());
        statusArea.setEditable(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton feedButton = new JButton("Feed");
        JButton playButton = new JButton("Play");

        feedButton.addActionListener(e -> showFeedChoices());
        playButton.addActionListener(e -> showPlayChoices());

        buttonPanel.add(feedButton);
        buttonPanel.add(playButton);

        mainPanel.add(statusArea, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);
        frame.revalidate();
    }

    private void showFeedChoices() {
        JOptionPane.showMessageDialog(frame, "Available foods: apple, soda, beer, vegetables, candy, water");
    }

    private void showPlayChoices() {
        JOptionPane.showMessageDialog(frame, "Available activities: fetch, walk, watch movie");
    }
}
