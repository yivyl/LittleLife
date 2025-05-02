import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartScreen extends JFrame {
    public StartScreen() {
        // Use our new component utility methods
        UIComponents.setupFrame(this, "Little Life - Welcome!", 500, 400);
        setLayout(new BorderLayout());
        
        // Create a panel for the title
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(UIComponents.BACKGROUND_COLOR);
        JLabel titleLabel = UIComponents.createLabel("Little Life", new Font("Comic Sans MS", Font.BOLD, 32));
        titlePanel.add(titleLabel);
        
        // Create a panel for the pet image
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(UIComponents.BACKGROUND_COLOR);
        // This would be replaced with an actual image in a full implementation
        JLabel petImage = new JLabel();
        petImage.setIcon(UIComponents.createPetIcon(150, 150, "playful", false));
        imagePanel.add(petImage);
        
        // Create a panel for the start button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        JButton startButton = UIComponents.createButton("Start Game");
        startButton.setPreferredSize(new Dimension(200, 60));
        startButton.addActionListener(e -> {
            dispose();
            new GameSetupUI();
        });
        buttonPanel.add(startButton);
        
        // Add instruction text
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        JLabel infoLabel = UIComponents.createLabel("Take care of your virtual pet!", UIComponents.SMALL_FONT);
        infoPanel.add(infoLabel);
        
        // Add panels to the frame
        add(titlePanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.CENTER); // This will overlay on the image
        add(buttonPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    // Main method for direct testing
    public static void main(String[] args) {
        new StartScreen();
    }
}