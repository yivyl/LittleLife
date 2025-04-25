import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class MainGameUI extends JFrame {
    private Tamagotchi pet;
    private Player player;
    private Logic logic;
    
    // UI Components
    private JProgressBar healthBar;
    private JProgressBar happinessBar;
    private JProgressBar hungerBar;
    private JLabel statusLabel;
    private JLabel characterLabel;
    private JButton dateButton;
    
    public MainGameUI() {
        // Default constructor - will need to be updated with pet information later
        // This constructor is called from NameUI without parameters
        
        // Create default pet and player objects to avoid null pointer exceptions
        this.pet = new Tamagotchi("Default", "they/them");
        this.player = new Player("Player");
        this.logic = new Logic(pet, player);
        
        pet.startDayCycle();
        initializeUI();
        updateStats(); // Initial stats update
    }
    
    public MainGameUI(String petName, String pronouns, String character) {
        // Constructor that takes pet information
        this.pet = new Tamagotchi(petName, pronouns);
        this.player = new Player("Player");
        this.logic = new Logic(pet, player);
        
        initializeUI();
        updateCharacterDisplay(character);
        updateStats(); // Initial stats update
        
        // Update window title to include pet name
        setTitle("Little Life - " + petName);
    }
    
    private void initializeUI() {
        setTitle("Little Life - Main Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(252, 252, 206));
        setLayout(new BorderLayout(10, 10));
        
        // Top Panel - Pet Name and Status
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);
        
        // Center Panel - Pet Character and Stats
        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);
        
        // Bottom Panel - Action Buttons
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Show the frame
        setVisible(true);
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(new Color(252, 252, 206));
        
        // Add pet name to status
        statusLabel = new JLabel(pet.getName() + " Status: Happy");
        statusLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        statusLabel.setForeground(new Color(0x8B4513));
        panel.add(statusLabel);
        
        JLabel favFoodLabel = new JLabel("Favorite Food: " + pet.getFavoriteFood());
        favFoodLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        favFoodLabel.setForeground(new Color(0x8B4513));
        panel.add(favFoodLabel);
        
        return panel;
    }
    
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(252, 252, 206));
        
        // Pet Character Display in Center
        characterLabel = new JLabel(pet.getName(), JLabel.CENTER);
        characterLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        characterLabel.setPreferredSize(new Dimension(200, 200));
        
        // Placeholder for character image
        characterLabel.setIcon(createPlaceholderIcon(150, 150));
        
        panel.add(characterLabel, BorderLayout.CENTER);
        
        // Stats Panel (Right Side)
        JPanel statsPanel = createStatsPanel();
        panel.add(statsPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBackground(new Color(252, 252, 206));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
        
        // Health Bar
        JPanel healthPanel = new JPanel(new BorderLayout(5, 0));
        healthPanel.setBackground(new Color(252, 252, 206));
        JLabel healthLabel = new JLabel("Health: ");
        healthLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        healthBar = new JProgressBar(0, 100);
        healthBar.setValue(50); // Default value
        healthBar.setStringPainted(true);
        healthBar.setForeground(new Color(0x66CC66)); // Green
        healthPanel.add(healthLabel, BorderLayout.WEST);
        healthPanel.add(healthBar, BorderLayout.CENTER);
        
        // Happiness Bar
        JPanel happinessPanel = new JPanel(new BorderLayout(5, 0));
        happinessPanel.setBackground(new Color(252, 252, 206));
        JLabel happinessLabel = new JLabel("Happiness: ");
        happinessLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        happinessBar = new JProgressBar(0, 100);
        happinessBar.setValue(50); // Default value
        happinessBar.setStringPainted(true);
        happinessBar.setForeground(new Color(0x3399FF)); // Blue
        happinessPanel.add(happinessLabel, BorderLayout.WEST);
        happinessPanel.add(happinessBar, BorderLayout.CENTER);
        
        // Hunger Bar
        JPanel hungerPanel = new JPanel(new BorderLayout(5, 0));
        hungerPanel.setBackground(new Color(252, 252, 206));
        JLabel hungerLabel = new JLabel("Hunger: ");
        hungerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        hungerBar = new JProgressBar(0, 100);
        hungerBar.setValue(50); // Default value
        hungerBar.setStringPainted(true);
        hungerBar.setForeground(new Color(0xFF9933)); // Orange
        hungerPanel.add(hungerLabel, BorderLayout.WEST);
        hungerPanel.add(hungerBar, BorderLayout.CENTER);
        
        panel.add(healthPanel);
        panel.add(happinessPanel);
        panel.add(hungerPanel);
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(new Color(252, 252, 206));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        
        // Feed Button
        JButton feedButton = createActionButton("Feed", "");
        feedButton.addActionListener(e -> showFeedOptions());
        
        // Play Button
        JButton playButton = createActionButton("Play", "");
        playButton.addActionListener(e -> showPlayOptions());
        
        JButton medicineButton = createActionButton("Medicine", "");
        medicineButton.addActionListener(e -> {
            logic.makeChoice("medicine", "");
            updateStats();
            checkPetStatus();
        });
        panel.add(medicineButton);
        
        // Date Button (disabled initially)
        dateButton = createActionButton("Date", "");
        dateButton.setEnabled(false); // Disabled until pet can go on date
        dateButton.addActionListener(e -> showDateOptions());
        
         // Next Day Button
        JButton nextDayButton = createActionButton("Next Day", "");
        nextDayButton.addActionListener(e -> {
            pet.endOfDaySummary();
            pet.startDayCycle();
            updateStats();
            checkDateEligibility();
            checkPetStatus();
        });
        panel.add(nextDayButton);

        
        panel.add(feedButton);
        panel.add(playButton);
        panel.add(dateButton);
        
        return panel;
    }
    
    private JButton createActionButton(String text, String emoji) {
        JButton button = new JButton(emoji + " " + text);
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        button.setForeground(new Color(0x8B4513));
        button.setBackground(new Color(0xFFDAB9));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 50));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        
        // Hover effects
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0xFFB6C1)); // Light Pink
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0xFFDAB9)); // Soft Peach
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
            }
            public void mousePressed(MouseEvent e) {
                button.setBackground(new Color(0xFFA07A)); // Pale Orange
            }
            public void mouseReleased(MouseEvent e) {
                button.setBackground(new Color(0xFFDAB9));
            }
        });
        
        return button;
    }
    
    private void showFeedOptions() {
        String[] options = {"Apple", "Vegetables", "Water", "Soda", "Candy", "Junk Food"};
        String selection = (String) JOptionPane.showInputDialog(
            this,
            "What would you like to feed your pet?",
            "Feeding Time",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (selection != null) {
            if (pet.getPersonality().equals("playful") && pet.getDaysIgnored() >= 3) {
                JOptionPane.showMessageDialog(this, pet.getName() + " is angry and ignores you!", "Ignored!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Process the feeding selection
            String foodChoice = selection.split(" ")[0].toLowerCase();
            logic.makeChoice("feed", foodChoice);
            showFeedbackMessage("You fed your pet " + selection);
            updateStats();
            checkDateEligibility();
            checkPetStatus();
        }
    }
    
    private void showPlayOptions() {
        String[] options = {"Fetch", "Walk", "Watch Movie", "Play Video Game?"};
        String selection = (String) JOptionPane.showInputDialog(
            this,
            "What would you like to do with your pet?",
            "Play Time",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (selection != null) {
            // Process the play selection
            String activityChoice;
            if (selection.startsWith("Watch") || selection.startsWith("Play Video")) {
                activityChoice = selection.split(" ")[0].toLowerCase() + " " + selection.split(" ")[1].toLowerCase();
            } else {
                activityChoice = selection.split(" ")[0].toLowerCase();
            }
            
            if (pet.getPersonality().equals("playful") && pet.getDaysIgnored() >= 3) {
                JOptionPane.showMessageDialog(this, pet.getName() + " is angry and ignores you!", "Ignored!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            logic.makeChoice("play", activityChoice);
            
            if (Math.random() < 0.2) {
                String gift = getGiftByPersonality(pet.getPersonality());
                showFeedbackMessage("🎁 Surprise! " + pet.getName() + " found a " + gift + "!");
                pet.changeHappiness(10);
            }
            
            // Show feedback to the player
            showFeedbackMessage("You played " + selection + " with your pet");
            
            // Update the UI with new stats
            updateStats();
            
            // Check if pet can now go on a date
            checkDateEligibility();
            
            // Check if pet is still alive
            checkPetStatus();
        }
    }
    
    private String getGiftByPersonality(String p) {
        switch (p) {
            case "playful": return "squeaky toy";
            case "lazy": return "cozy blanket";
            case "grumpy": return "rare snack";
            default: return "treat";
        }
    }

    
    private void showDateOptions() {
        String[] dateOptions = {"Movie Date", "Coffee Date", "Dinner Date️", "Grab Drinks"};
        String[] giftOptions = {"Flower", "Chocolates", "No Gift"};
        
        String dateSelection = (String) JOptionPane.showInputDialog(
            this,
            "What kind of date?",
            "Date Time",
            JOptionPane.QUESTION_MESSAGE,
            null,
            dateOptions,
            dateOptions[0]
        );
        
        if (dateSelection != null) {
            String giftSelection = (String) JOptionPane.showInputDialog(
                this,
                "Would you like to bring a gift?",
                "Date Gift",
                JOptionPane.QUESTION_MESSAGE,
                null,
                giftOptions,
                giftOptions[0]
            );
            
            if (giftSelection != null) {
                // Process the date and gift selections
                String dateChoice = dateSelection.split(" ")[0].toLowerCase();
                String giftChoice = giftSelection.equals("No Gift") ? "" : giftSelection.split(" ")[0].toLowerCase();
                
                logic.makeChoice("date", dateChoice);
                
                // Show feedback message
                if (giftSelection.equals("No Gift")) {
                    showFeedbackMessage("You went on a " + dateSelection + " with " + pet.getName());
                } else {
                    showFeedbackMessage("You went on a " + dateSelection + " with " + pet.getName() + " and brought " + giftSelection);
                }
                
                // Update the UI with new stats
                updateStats();
                
                // Reset date eligibility (pet needs to build stats again after a date)
                dateButton.setEnabled(false);
                
                // Check if pet is still alive
                checkPetStatus();
            }
        }
    }
    
    // Method to show a feedback message to the player
    private void showFeedbackMessage(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Action Result",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    // Method to update UI with current pet stats
    public void updateStats() {
        if (pet != null) {
            // Update progress bars
            healthBar.setValue(pet.getHealth());
            happinessBar.setValue(pet.getHappiness());
            hungerBar.setValue(pet.getHunger());
            
            // Update status message
            if (!pet.isAlive()) {
                statusLabel.setText(pet.getName() + " is dead!");
                statusLabel.setForeground(Color.RED);
            } else if (pet.isSick()) {
                statusLabel.setText(pet.getName() + " is sick! Take better care!");
                statusLabel.setForeground(Color.RED);
            } else if (pet.canGoOnDate()) {
                statusLabel.setText(pet.getName() + " is ready for a date!");
                statusLabel.setForeground(new Color(0xFF69B4)); // Hot Pink
            } else {
                statusLabel.setText(pet.getName() + " is happy! (" + pet.getPersonality() + ")");
                statusLabel.setForeground(new Color(0x8B4513));
            }
        }
    }
    
    // Method to check if pet can go on a date
    private void checkDateEligibility() {
        if (pet != null && pet.canGoOnDate()) {
            dateButton.setEnabled(true);
            showFeedbackMessage("Congratulations! " + pet.getName() + " is ready for a date!");
        }
    }
    
    // Method to check if pet is still alive
    private void checkPetStatus() {
        if (pet != null && !pet.isAlive()) {
            // Pet is dead
            JOptionPane.showMessageDialog(
                this,
                "Oh no! " + pet.getName() + " has died! Game Over!",
                "Game Over",
                JOptionPane.ERROR_MESSAGE
            );
            
            // Ask if player wants to play again
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Would you like to play again?",
                "Play Again?",
                JOptionPane.YES_NO_OPTION
            );
            
            if (choice == JOptionPane.YES_OPTION) {
                dispose();
                new StartScreen();
            } else {
                System.exit(0);
            }
        }
    }
    
    private void updateCharacterDisplay(String character) {
        // In a real implementation, you would load appropriate images
        // based on the selected character and possibly the pet's state
        characterLabel.setText(pet.getName() + " (" + character + ")");
    }
    
    // Helper method to create a placeholder icon 
    private Icon createPlaceholderIcon(int width, int height) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                g.setColor(new Color(0xFFDAB9));
                g.fillOval(x, y, width, height);
                g.setColor(new Color(0x8B4513));
                g.drawOval(x, y, width, height);
                
                // Draw simple face features
                g.fillOval(x + width/4, y + height/3, 10, 10);      // Left eye
                g.fillOval(x + 3*width/4 - 10, y + height/3, 10, 10); // Right eye
                g.drawArc(x + width/4, y + 2*height/3, width/2, height/6, 0, -180); // Smile
            }
            
            @Override
            public int getIconWidth() {
                return width;
            }
            
            @Override
            public int getIconHeight() {
                return height;
            }
        };
    }
}
