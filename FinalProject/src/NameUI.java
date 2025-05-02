import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NameUI extends JFrame implements ActionListener {
    private JTextField nameField;
    private String pronouns;
    
    public NameUI() {
        this("they/them"); // Default pronouns
    }
    
    public NameUI(String pronouns) {
        this.pronouns = pronouns;
        
        // Set up the frame
        UIComponents.setupFrame(this, "Little Life - Name Your Pet", 500, 400);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        // Create name prompt panel
        JPanel promptPanel = new JPanel();
        promptPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        promptPanel.setMaximumSize(new Dimension(500, 80));
        promptPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel namePrompt = UIComponents.createLabel("What will you name your pet?", UIComponents.HEADER_FONT);
        promptPanel.add(namePrompt);
        
        // Create name input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        inputPanel.setMaximumSize(new Dimension(500, 80));
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        nameField = UIComponents.createTextField();
        nameField.setPreferredSize(new Dimension(250, 40));
        nameField.setFont(UIComponents.REGULAR_FONT);
        nameField.addActionListener(this); // Handle Enter key
        inputPanel.add(nameField);
        
        // Create submit button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        buttonPanel.setMaximumSize(new Dimension(500, 80));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton submitButton = UIComponents.createButton("Submit");
        submitButton.setPreferredSize(new Dimension(150, 50));
        submitButton.addActionListener(this);
        buttonPanel.add(submitButton);
        
        // Add a pronoun reminder
        JPanel pronounPanel = new JPanel();
        pronounPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        pronounPanel.setMaximumSize(new Dimension(500, 40));
        pronounPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel pronounLabel = UIComponents.createLabel("Pronouns selected: " + pronouns, UIComponents.SMALL_FONT);
        pronounPanel.add(pronounLabel);
        
        // Add a small pet icon for visual appeal
        JPanel iconPanel = new JPanel();
        iconPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        iconPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel petIcon = new JLabel();
        petIcon.setIcon(UIComponents.createPetIcon(100, 100, "playful", false));
        iconPanel.add(petIcon);
        
        // Add all panels to the frame
        add(Box.createVerticalStrut(30));
        add(promptPanel);
        add(Box.createVerticalStrut(20));
        add(inputPanel);
        add(Box.createVerticalStrut(10));
        add(pronounPanel);
        add(Box.createVerticalStrut(20));
        add(iconPanel);
        add(Box.createVerticalStrut(30));
        add(buttonPanel);
        
        // Focus the name field when the frame opens
        SwingUtilities.invokeLater(() -> nameField.requestFocusInWindow());
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String petName = nameField.getText().trim();
        if (petName.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter a name for your pet!",
                "Name Required",
                JOptionPane.WARNING_MESSAGE
            );
        } else {
            dispose();
            new CharacterSelectionUI(petName, pronouns);
        }
    }
}