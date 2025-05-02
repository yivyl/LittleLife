import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class MainGameUI extends JFrame {
    private Tamagotchi pet;
    private Player player;
    private Logic logic;
    
    // UI Components
    private JProgressBar healthBar;
    private JProgressBar happinessBar;
    private JProgressBar hungerBar;
    private JProgressBar energyBar;
    private JLabel statusLabel;
    private JLabel characterIcon;
    private JButton dateButton;
    private JLabel timeLabel;
    private JLabel dayCountLabel;
    private int dayCount = 1;
    
    // Constructor with default values (called from NameUI)
    public MainGameUI() {
        this("Default", "they/them", "Kuchipatchi");
    }
    
    // Main constructor
    public MainGameUI(String petName, String pronouns, String character) {
        // Create game objects
        this.pet = new Tamagotchi(petName, pronouns);
        this.player = new Player("Player");
        this.logic = new Logic(pet, player);
        
        // Set up the frame
        UIComponents.setupFrame(this, "Little Life - " + petName, 700, 500);
        setLayout(new BorderLayout(10, 10));
        
        // Start the pet's cycle
        pet.startDayCycle();
        
        // Initialize the UI
        initializeUI(character);
        
        // Update initial stats
        updateStats();
        
        setVisible(true);
    }
    
    private void initializeUI(String character) {
        // Top panel with status and day information
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);
        
        // Center panel with pet visualization and stats
        JPanel centerPanel = createCenterPanel(character);
        add(centerPanel, BorderLayout.CENTER);
        
        // Bottom panel with action buttons
        JPanel bottomPanel = createActionPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UIComponents.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Status information on the left
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        
        statusLabel = UIComponents.createLabel(pet.getName() + " is happy!", UIComponents.REGULAR_FONT);
        statusPanel.add(statusLabel);
        
        // Day and time information on the right
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        timePanel.setBackground(UIComponents.BACKGROUND_COLOR);
        
        timeLabel = UIComponents.createLabel(pet.isNight() ? "🌙 Night" : "☀️ Day", UIComponents.REGULAR_FONT);
        dayCountLabel = UIComponents.createLabel("Day " + dayCount, UIComponents.REGULAR_FONT);
        
        timePanel.add(dayCountLabel);
        timePanel.add(Box.createHorizontalStrut(20));
        timePanel.add(timeLabel);
        
        panel.add(statusPanel, BorderLayout.WEST);
        panel.add(timePanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createCenterPanel(String character) {
        JPanel panel = new JPanel(new BorderLayout(20, 0));
        panel.setBackground(UIComponents.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Pet visualization on the left
        JPanel petPanel = new JPanel();
        petPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        petPanel.setBorder(BorderFactory.createLineBorder(UIComponents.BORDER_COLOR, 2, true));
        petPanel.setPreferredSize(new Dimension(300, 300));
        
        // Create character icon with personality
        characterIcon = new JLabel(pet.getName() + " (" + character + ")");
        characterIcon.setIcon(UIComponents.createPetIcon(200, 200, pet.getPersonality(), pet.isNight()));
        characterIcon.setHorizontalTextPosition(JLabel.CENTER);
        characterIcon.setVerticalTextPosition(JLabel.BOTTOM);
        characterIcon.setFont(UIComponents.REGULAR_FONT);
        characterIcon.setForeground(UIComponents.TEXT_COLOR);
        
        petPanel.add(characterIcon);
        
        // Stats panel on the right
        JPanel statsPanel = createStatsPanel();
        
        // Pet information panel below stats
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        
        JLabel personalityLabel = UIComponents.createLabel("Personality: " + pet.getPersonality(), UIComponents.SMALL_FONT);
        JLabel favFoodLabel = UIComponents.createLabel("Favorite Food: " + pet.getFavoriteFood(), UIComponents.SMALL_FONT);
        
        infoPanel.add(personalityLabel);
        infoPanel.add(favFoodLabel);
        
        // Combine stats and info in right panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        rightPanel.add(statsPanel, BorderLayout.CENTER);
        rightPanel.add(infoPanel, BorderLayout.SOUTH);
        
        panel.add(petPanel, BorderLayout.CENTER);
        panel.add(rightPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 0, 10));
        panel.setBackground(UIComponents.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIComponents.BORDER_COLOR, 2, true),
            "Pet Stats",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            UIComponents.REGULAR_FONT,
            UIComponents.TEXT_COLOR
        ));
        panel.setPreferredSize(new Dimension(250, 200));
        
        // Health bar
        JPanel healthPanel = new JPanel(new BorderLayout(5, 0));
        healthPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        JLabel healthLabel = UIComponents.createLabel("Health: ", UIComponents.SMALL_FONT);
        healthBar = UIComponents.createProgressBar(new Color(0x66CC66)); // Green
        healthPanel.add(healthLabel, BorderLayout.WEST);
        healthPanel.add(healthBar, BorderLayout.CENTER);
        
        // Happiness bar
        JPanel happinessPanel = new JPanel(new BorderLayout(5, 0));
        happinessPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        JLabel happinessLabel = UIComponents.createLabel("Happiness: ", UIComponents.SMALL_FONT);
        happinessBar = UIComponents.createProgressBar(new Color(0x3399FF)); // Blue
        happinessPanel.add(happinessLabel, BorderLayout.WEST);
        happinessPanel.add(happinessBar, BorderLayout.CENTER);
        
        // Hunger bar
        JPanel hungerPanel = new JPanel(new BorderLayout(5, 0));
        hungerPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        JLabel hungerLabel = UIComponents.createLabel("Hunger: ", UIComponents.SMALL_FONT);
        hungerBar = UIComponents.createProgressBar(new Color(0xFF9933)); // Orange
        hungerPanel.add(hungerLabel, BorderLayout.WEST);
        hungerPanel.add(hungerBar, BorderLayout.CENTER);
        
        // Energy bar
        JPanel energyPanel = new JPanel(new BorderLayout(5, 0));
        energyPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        JLabel energyLabel = UIComponents.createLabel("Energy: ", UIComponents.SMALL_FONT);
        energyBar = UIComponents.createProgressBar(new Color(0xCCCCFF)); // Light blue
        energyPanel.add(energyLabel, BorderLayout.WEST);
        energyPanel.add(energyBar, BorderLayout.CENTER);
        
        panel.add(healthPanel);
        panel.add(happinessPanel);
        panel.add(hungerPanel);
        panel.add(energyPanel);
        
        return panel;
    }
    
    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(UIComponents.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create action buttons
        JButton feedButton = UIComponents.createButton("🍎 Feed");
        JButton playButton = UIComponents.createButton("🎮 Play");
        JButton medicineButton = UIComponents.createButton("💊 Medicine");
        dateButton = UIComponents.createButton("❤️ Date");
        JButton nextDayButton = UIComponents.createButton("⏭️ Next Day");
        
        // Set button size
        Dimension buttonSize = new Dimension(120, 40);
        feedButton.setPreferredSize(buttonSize);
        playButton.setPreferredSize(buttonSize);
        medicineButton.setPreferredSize(buttonSize);
        dateButton.setPreferredSize(buttonSize);
        nextDayButton.setPreferredSize(buttonSize);
        
        // Disable date button initially
        dateButton.setEnabled(false);
        
        // Add action listeners
        feedButton.addActionListener(e -> showFeedOptions());
        playButton.addActionListener(e -> showPlayOptions());
        medicineButton.addActionListener(e -> {
            logic.makeChoice("medicine", "");
            updateStats();
            checkPetStatus();
        });
        dateButton.addActionListener(e -> showDateOptions());
        nextDayButton.addActionListener(e -> advanceToNextDay());
        
        // Add buttons to panel
        panel.add(feedButton);
        panel.add(playButton);
        panel.add(medicineButton);
        panel.add(dateButton);
        panel.add(nextDayButton);
        
        return panel;
    }
    
    private void showFeedOptions() {
        String[] options = {"Apple", "Vegetables", "Water", "Soda", "Candy", "Junk Food"};
        
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(UIComponents.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel feedLabel = UIComponents.createLabel("What would you like to feed " + pet.getName() + "?", UIComponents.SMALL_FONT);
        JLabel favoriteLabel = UIComponents.createLabel("Favorite food: " + pet.getFavoriteFood(), UIComponents.SMALL_FONT);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        topPanel.add(feedLabel, BorderLayout.NORTH);
        topPanel.add(favoriteLabel, BorderLayout.SOUTH);
        
        panel.add(topPanel, BorderLayout.NORTH);
        
        String selection = (String) JOptionPane.showInputDialog(
            this,
            panel,
            "Feeding Time",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (selection != null) {
            // Check if pet is ignoring player due to too many ignored days
            if (pet.getPersonality().equals("playful") && pet.getDaysIgnored() >= 3) {
                JOptionPane.showMessageDialog(
                    this,
                    pet.getName() + " is angry and ignores you!",
                    "Ignored!",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            // Process feeding selection
            String foodChoice = selection.toLowerCase();
            logic.makeChoice("feed", foodChoice);
            
            // Show feedback message
            showFeedbackMessage("You fed " + pet.getName() + " some " + selection);
            
            // Update UI
            updateStats();
            checkDateEligibility();
            checkPetStatus();
        }
    }
    
    private void showPlayOptions() {
        String[] options = {"Fetch", "Walk", "Watch Movie", "Play Video Game"};
        
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(UIComponents.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel playLabel = UIComponents.createLabel("What would you like to do with " + pet.getName() + "?", UIComponents.SMALL_FONT);
        JLabel energyLabel = UIComponents.createLabel("Current energy: " + pet.getEnergy() + "/100", UIComponents.SMALL_FONT);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        topPanel.add(playLabel, BorderLayout.NORTH);
        topPanel.add(energyLabel, BorderLayout.SOUTH);
        
        panel.add(topPanel, BorderLayout.NORTH);
        
        String selection = (String) JOptionPane.showInputDialog(
            this,
            panel,
            "Play Time",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (selection != null) {
            // Check if pet has enough energy
            if (pet.getEnergy() <= 10) {
                JOptionPane.showMessageDialog(
                    this,
                    pet.getName() + " is too tired to play!",
                    "Low Energy",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            // Check if pet is ignoring player
            if (pet.getPersonality().equals("playful") && pet.getDaysIgnored() >= 3) {
                JOptionPane.showMessageDialog(
                    this,
                    pet.getName() + " is angry and ignores you!",
                    "Ignored!",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            // Process play selection
            String activityChoice = selection.toLowerCase();
            logic.makeChoice("play", activityChoice);
            
            // Random gift chance
            if (Math.random() < 0.2) {
                String gift = getGiftByPersonality(pet.getPersonality());
                showFeedbackMessage("🎁 Surprise! " + pet.getName() + " found a " + gift + "!");
                pet.changeHappiness(10);
            }
            
            // Show feedback message
            showFeedbackMessage("You played " + selection + " with " + pet.getName());
            
            // Update UI
            updateStats();
            checkDateEligibility();
            checkPetStatus();
        }
    }
    
    private void showDateOptions() {
        String[] dateOptions = {"Movie Date", "Coffee Date", "Dinner Date", "Grab Drinks"};
        String[] giftOptions = {"Flower", "Chocolates", "No Gift"};
        
        JPanel datePanel = new JPanel(new BorderLayout(0, 10));
        datePanel.setBackground(UIComponents.BACKGROUND_COLOR);
        datePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel dateLabel = UIComponents.createLabel("What kind of date for " + pet.getName() + "?", UIComponents.SMALL_FONT);
        datePanel.add(dateLabel, BorderLayout.NORTH);
        
        String dateSelection = (String) JOptionPane.showInputDialog(
            this,
            datePanel,
            "Date Time",
            JOptionPane.QUESTION_MESSAGE,
            null,
            dateOptions,
            dateOptions[0]
        );
        
        if (dateSelection != null) {
            JPanel giftPanel = new JPanel(new BorderLayout(0, 10));
            giftPanel.setBackground(UIComponents.BACKGROUND_COLOR);
            giftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JLabel giftLabel = UIComponents.createLabel("Would you like to bring a gift?", UIComponents.SMALL_FONT);
            giftPanel.add(giftLabel, BorderLayout.NORTH);
            
            String giftSelection = (String) JOptionPane.showInputDialog(
                this,
                giftPanel,
                "Date Gift",
                JOptionPane.QUESTION_MESSAGE,
                null,
                giftOptions,
                giftOptions[0]
            );
            
            if (giftSelection != null) {
                // Process date selection
                String dateChoice = dateSelection.split(" ")[0].toLowerCase();
                String giftChoice = giftSelection.equals("No Gift") ? "" : giftSelection.toLowerCase();
                
                logic.makeChoice("date", dateChoice);
                
                // Show feedback message
                if (giftSelection.equals("No Gift")) {
                    showFeedbackMessage("You took " + pet.getName() + " on a " + dateSelection);
                } else {
                    showFeedbackMessage("You took " + pet.getName() + " on a " + dateSelection + 
                                      " and brought " + giftSelection);
                }
                
                // Update UI
                updateStats();
                dateButton.setEnabled(false);
                checkPetStatus();
            }
        }
    }
    
    private void advanceToNextDay() {
        pet.endOfDaySummary();
        pet.toggleDayNight();
        pet.startDayCycle();
        dayCount++;
        dayCountLabel.setText("Day " + dayCount);
        updateStats();
        checkDateEligibility();
        checkPetStatus();
        
        // Visual update
        characterIcon.setIcon(UIComponents.createPetIcon(200, 200, pet.getPersonality(), pet.isNight()));
        timeLabel.setText(pet.isNight() ? "🌙 Night" : "☀️ Day");
        
        // Apply day/night colors
        applyDayNightColors();
    }
    
    private void applyDayNightColors() {
        Color bgColor = pet.isNight() ? UIComponents.NIGHT_COLOR : UIComponents.BACKGROUND_COLOR;
        
        // Change background color of all panels
        getContentPane().setBackground(bgColor);
        
        // Update all child components
        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JPanel) {
                updatePanelColors((JPanel) comp, bgColor);
            }
        }
        
        // Update text colors for better visibility at night
        if (pet.isNight()) {
            statusLabel.setForeground(Color.WHITE);
            timeLabel.setForeground(Color.WHITE);
            dayCountLabel.setForeground(Color.WHITE);
            characterIcon.setForeground(Color.WHITE);
        } else {
            statusLabel.setForeground(UIComponents.TEXT_COLOR);
            timeLabel.setForeground(UIComponents.TEXT_COLOR);
            dayCountLabel.setForeground(UIComponents.TEXT_COLOR);
            characterIcon.setForeground(UIComponents.TEXT_COLOR);
        }
    }
    
    private void updatePanelColors(JPanel panel, Color bgColor) {
        panel.setBackground(bgColor);
        
        // Update all child components
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                updatePanelColors((JPanel) comp, bgColor);
            } else if (comp instanceof JLabel && !(comp == characterIcon)) {
                if (pet.isNight()) {
                    ((JLabel) comp).setForeground(Color.WHITE);
                } else {
                    ((JLabel) comp).setForeground(UIComponents.TEXT_COLOR);
                }
            }
        }
    }
    
    private void showFeedbackMessage(String message) {
        Icon icon = UIComponents.createPetIcon(50, 50, pet.getPersonality(), pet.isNight());
        
        JOptionPane.showMessageDialog(
            this,
            message,
            "Action Result",
            JOptionPane.INFORMATION_MESSAGE,
            icon
        );
    }
    
    public void updateStats() {
        if (pet != null) {
            // Update progress bars
            healthBar.setValue(pet.getHealth());
            happinessBar.setValue(pet.getHappiness());
            hungerBar.setValue(pet.getHunger());
            energyBar.setValue(pet.getEnergy());
            
            // Update day/night colors
            applyDayNightColors();
            
            // Update status message
            updateStatusLabel();
        }
    }
    
    private void updateStatusLabel() {
        if (!pet.isAlive()) {
            statusLabel.setText(pet.getName() + " has died!");
            statusLabel.setForeground(Color.RED);
        } else if (pet.isSick()) {
            statusLabel.setText(pet.getName() + " is sick! Give medicine!");
            statusLabel.setForeground(Color.RED);
        } else if (pet.getEnergy() < 15) {
            statusLabel.setText(pet.getName() + " is exhausted!");
            statusLabel.setForeground(Color.GRAY);
        } else if (pet.canGoOnDate()) {
            statusLabel.setText(pet.getName() + " is ready for a date!");
            statusLabel.setForeground(new Color(0xFF69B4)); // Hot Pink
        } else {
            statusLabel.setText(pet.getName() + " is " + getMoodText());
            statusLabel.setForeground(pet.isNight() ? Color.WHITE : UIComponents.TEXT_COLOR);
        }
    }
    
    private String getMoodText() {
        if (pet.getHappiness() > 80) return "very happy!";
        if (pet.getHappiness() > 60) return "happy!";
        if (pet.getHappiness() > 40) return "content.";
        if (pet.getHappiness() > 20) return "sad.";
        return "very sad!";
    }
    
    private void checkDateEligibility() {
        if (pet != null && pet.canGoOnDate()) {
            dateButton.setEnabled(true);
            showFeedbackMessage("🎉 " + pet.getName() + " is ready for a date!");
        }
    }
    
    private void checkPetStatus() {
        if (pet != null && !pet.isAlive()) {
            // Custom game over dialog
            JPanel gameOverPanel = new JPanel();
            gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS));
            gameOverPanel.setBackground(UIComponents.BACKGROUND_COLOR);
            
            JLabel sadIcon = new JLabel("💔");
            sadIcon.setFont(new Font("Dialog", Font.PLAIN, 48));
            sadIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel gameOverLabel = new JLabel("Oh no! " + pet.getName() + " has died!");
            gameOverLabel.setFont(UIComponents.HEADER_FONT);
            gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel daysLabel = new JLabel("Days survived: " + dayCount);
            daysLabel.setFont(UIComponents.SMALL_FONT);
            daysLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            gameOverPanel.add(Box.createVerticalStrut(10));
            gameOverPanel.add(sadIcon);
            gameOverPanel.add(Box.createVerticalStrut(10));
            gameOverPanel.add(gameOverLabel);
            gameOverPanel.add(Box.createVerticalStrut(10));
            gameOverPanel.add(daysLabel);
            gameOverPanel.add(Box.createVerticalStrut(10));
            
            // Show game over dialog
            int choice = JOptionPane.showConfirmDialog(
                this,
                gameOverPanel,
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null
            );
            
            if (choice == JOptionPane.YES_OPTION) {
                dispose();
                new StartScreen();
            } else {
                System.exit(0);
            }
        }
    }
    
    private String getGiftByPersonality(String personality) {
        switch (personality.toLowerCase()) {
            case "playful": return "squeaky toy";
            case "lazy": return "cozy blanket";
            case "grumpy": return "rare snack";
            default: return "treat";
        }
    }
}