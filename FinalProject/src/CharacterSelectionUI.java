import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CharacterSelectionUI extends JFrame {
    private String petName;
    private String pronouns;
    
    public CharacterSelectionUI(String petName, String pronouns) {
        this.petName = petName;
        this.pronouns = pronouns;
        
        // Set up the frame
        UIComponents.setupFrame(this, "Little Life - Choose Character", 600, 450);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        // Create title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(UIComponents.BACKGROUND_COLOR);
        titlePanel.setMaximumSize(new Dimension(600, 80));
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = UIComponents.createLabel("Choose a character for " + petName, UIComponents.HEADER_FONT);
        titlePanel.add(titleLabel);
        
        // Create character selection panel
        JPanel charactersPanel = new JPanel();
        charactersPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        charactersPanel.setMaximumSize(new Dimension(600, 250));
        charactersPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        charactersPanel.setLayout(new GridLayout(1, 3, 30, 0));
        
        // Character cards with info
        JPanel kuchipatchiCard = createCharacterCard("Kuchipatchi", "Playful", "Eats a lot, loves to have fun", "playful");
        JPanel togetchiCard = createCharacterCard("Togetchi", "Lazy", "Enjoys sleeping & relaxing", "lazy");
        JPanel debatchiCard = createCharacterCard("Debatchi", "Grumpy", "Serious, hard to please", "grumpy");
        
        charactersPanel.add(kuchipatchiCard);
        charactersPanel.add(togetchiCard);
        charactersPanel.add(debatchiCard);
        
        // Add a pet name and pronoun reminder
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        infoPanel.setMaximumSize(new Dimension(600, 40));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel infoLabel = UIComponents.createLabel(
            petName + " (" + pronouns + ")",
            UIComponents.SMALL_FONT
        );
        infoPanel.add(infoLabel);
        
        // Add all panels to the frame
        add(Box.createVerticalStrut(20)); // Add spacing
        add(titlePanel);
        add(Box.createVerticalStrut(30)); // Add spacing
        add(charactersPanel);
        add(Box.createVerticalStrut(20)); // Add spacing
        add(infoPanel);
        
        setVisible(true);
    }
    
    private JPanel createCharacterCard(String name, String personality, String description, String personalityType) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UIComponents.BUTTON_COLOR);
        card.setBorder(BorderFactory.createLineBorder(UIComponents.BORDER_COLOR, 2, true));
        
        // Character name
        JLabel nameLabel = UIComponents.createLabel(name, UIComponents.REGULAR_FONT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Character image (represented by our custom icon)
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(UIComponents.createPetIcon(100, 100, personalityType, false));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Character personality
        JLabel personalityLabel = UIComponents.createLabel(personality, UIComponents.SMALL_FONT);
        personalityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Character description
        JLabel descLabel = new JLabel("<html><center>" + description + "</center></html>");
        descLabel.setFont(UIComponents.SMALL_FONT);
        descLabel.setForeground(UIComponents.TEXT_COLOR);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Select button
        JButton selectButton = UIComponents.createButton("Select");
        selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectButton.setMaximumSize(new Dimension(100, 30));
        
        // Add hover effect to the card
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(UIComponents.BUTTON_HOVER_COLOR);
                card.setBorder(BorderFactory.createLineBorder(UIComponents.BORDER_COLOR, 3, true));
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(UIComponents.BUTTON_COLOR);
                card.setBorder(BorderFactory.createLineBorder(UIComponents.BORDER_COLOR, 2, true));
            }
        });
        
        // Add action to select button
        selectButton.addActionListener(e -> {
            dispose();
            new MainGameUI(petName, pronouns, name);
        });
        
        // Add components to card
        card.add(Box.createVerticalStrut(10));
        card.add(nameLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(imageLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(personalityLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(descLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(selectButton);
        card.add(Box.createVerticalStrut(10));
        
        return card;
    }
}