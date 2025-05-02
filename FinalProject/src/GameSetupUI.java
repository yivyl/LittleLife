import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameSetupUI extends JFrame {
    private String selectedPronouns = "they/them"; // Default pronouns
    
    public GameSetupUI() {
        // Set up the frame using our utility method
        UIComponents.setupFrame(this, "Little Life - Setup", 500, 400);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        // Create welcome panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(UIComponents.BACKGROUND_COLOR);
        welcomePanel.setMaximumSize(new Dimension(500, 100));
        welcomePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel welcomeLabel = UIComponents.createLabel("Welcome to Little Life!", new Font("Comic Sans MS", Font.BOLD, 24));
        welcomePanel.add(welcomeLabel);
        
        // Create pronouns label panel
        JPanel pronounsLabelPanel = new JPanel();
        pronounsLabelPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        pronounsLabelPanel.setMaximumSize(new Dimension(500, 50));
        pronounsLabelPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel choosePronouns = UIComponents.createLabel("Choose your pet's pronouns:", UIComponents.REGULAR_FONT);
        pronounsLabelPanel.add(choosePronouns);
        
        // Create radio buttons for pronoun selection
        JPanel pronounsPanel = new JPanel();
        pronounsPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        pronounsPanel.setMaximumSize(new Dimension(500, 100));
        pronounsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create a button group for the radio buttons
        ButtonGroup pronounGroup = new ButtonGroup();
        
        // Create the radio buttons
        JRadioButton heHimRadio = new JRadioButton("he/him");
        JRadioButton theyThemRadio = new JRadioButton("they/them");
        JRadioButton sheHerRadio = new JRadioButton("she/her");
        
        // Style the radio buttons
        styleRadioButton(heHimRadio);
        styleRadioButton(theyThemRadio);
        styleRadioButton(sheHerRadio);
        
        // Set they/them as default selected
        theyThemRadio.setSelected(true);
        
        // Add them to the button group
        pronounGroup.add(heHimRadio);
        pronounGroup.add(theyThemRadio);
        pronounGroup.add(sheHerRadio);
        
        // Add action listeners
        heHimRadio.addActionListener(e -> selectedPronouns = "he/him");
        theyThemRadio.addActionListener(e -> selectedPronouns = "they/them");
        sheHerRadio.addActionListener(e -> selectedPronouns = "she/her");
        
        // Add to panel
        pronounsPanel.add(heHimRadio);
        pronounsPanel.add(theyThemRadio);
        pronounsPanel.add(sheHerRadio);
        
        // Create continue button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        buttonPanel.setMaximumSize(new Dimension(500, 100));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton continueButton = UIComponents.createButton("Continue");
        continueButton.setPreferredSize(new Dimension(150, 50));
        continueButton.addActionListener(e -> {
            dispose();
            new NameUI(selectedPronouns);
        });
        buttonPanel.add(continueButton);
        
        // Add a small pet icon for visual appeal
        JPanel iconPanel = new JPanel();
        iconPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        iconPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel petIcon = new JLabel();
        petIcon.setIcon(UIComponents.createPetIcon(100, 100, "playful", false));
        iconPanel.add(petIcon);
        
        // Add all panels to the frame
        add(Box.createVerticalStrut(20)); // Add spacing
        add(welcomePanel);
        add(Box.createVerticalStrut(20)); // Add spacing
        add(pronounsLabelPanel);
        add(pronounsPanel);
        add(Box.createVerticalStrut(20)); // Add spacing
        add(iconPanel);
        add(Box.createVerticalStrut(20)); // Add spacing
        add(buttonPanel);
        
        setVisible(true);
    }
    
    private void styleRadioButton(JRadioButton button) {
        button.setFont(UIComponents.REGULAR_FONT);
        button.setForeground(UIComponents.TEXT_COLOR);
        button.setBackground(UIComponents.BACKGROUND_COLOR);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}