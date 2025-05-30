import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.io.*;
import javax.sound.sampled.*;

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
    private JLabel notificationLabel;
    private int dayCount = 1;
    
    // New UI Components
    private JPanel dayNightPanel;
    private Timer animationTimer;
    private JLabel achievementLabel;
    
    // Flag for animation
    private boolean isAnimating = false;
    private int animationFrame = 0;
    
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
        UIComponents.setupFrame(this, "Little Life - " + petName, 700, 550);
        setLayout(new BorderLayout(10, 10));
        
        // Start the pet's cycle
        pet.startDayCycle();
        
        // Initialize the UI
        initializeUI(character);
        
        // Update initial stats
        updateStats();
        
        // Start animation timer
        startAnimationTimer();
        
        setVisible(true);
        
        // Play welcome sound
        playSound("welcome");
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
        
        // Notification panel
        JPanel notificationPanel = createNotificationPanel();
        add(notificationPanel, BorderLayout.EAST);
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
        dayNightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        dayNightPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        
        timeLabel = UIComponents.createLabel(pet.isNight() ? "🌙 Night" : "☀️ Day", UIComponents.REGULAR_FONT);
        dayCountLabel = UIComponents.createLabel("Day " + dayCount, UIComponents.REGULAR_FONT);
        
        // New save/load buttons
        JButton saveButton = UIComponents.createButton("💾 Save");
        saveButton.setPreferredSize(new Dimension(80, 30));
        saveButton.addActionListener(e -> saveGame());
        
        JButton loadButton = UIComponents.createButton("📂 Load");
        loadButton.setPreferredSize(new Dimension(80, 30));
        loadButton.addActionListener(e -> loadGame());
        
        dayNightPanel.add(saveButton);
        dayNightPanel.add(loadButton);
        dayNightPanel.add(Box.createHorizontalStrut(20));
        dayNightPanel.add(dayCountLabel);
        dayNightPanel.add(Box.createHorizontalStrut(10));
        dayNightPanel.add(timeLabel);
        
        // Update colors based on day/night
        updateDayNightPanel();
        
        panel.add(statusPanel, BorderLayout.WEST);
        panel.add(dayNightPanel, BorderLayout.EAST);
        
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
        
        // Add a click event to pet the character
        characterIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        characterIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                petCharacter();
            }
        });
        
        // Add tooltip to let the user know they can pet the character
        characterIcon.setToolTipText("Click to pet " + pet.getName() + "!");
        
        petPanel.add(characterIcon);
        
        // Stats panel on the right
        JPanel statsPanel = createStatsPanel();
        
        // Pet information panel below stats
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBackground(UIComponents.BACKGROUND_COLOR);
        
        JLabel personalityLabel = UIComponents.createLabel("Personality: " + pet.getPersonality(), UIComponents.SMALL_FONT);
        JLabel favFoodLabel = UIComponents.createLabel("Favorite Food: " + pet.getFavoriteFood(), UIComponents.SMALL_FONT);
        
        // New achievements label
        achievementLabel = UIComponents.createLabel("Achievements: 0", UIComponents.SMALL_FONT);
        
        infoPanel.add(personalityLabel);
        infoPanel.add(favFoodLabel);
        infoPanel.add(achievementLabel);
        
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
        JButton feedButton = UIComponents.createButton("Feed");
        JButton playButton = UIComponents.createButton("Play");
        JButton medicineButton = UIComponents.createButton("Medicine");
        dateButton = UIComponents.createButton("Date");
        JButton minigameButton = UIComponents.createButton("Mini-Game");
        JButton nextDayButton = UIComponents.createButton("Next Day");
        
        // Set button size
        Dimension buttonSize = new Dimension(120, 40);
        feedButton.setPreferredSize(buttonSize);
        playButton.setPreferredSize(buttonSize);
        medicineButton.setPreferredSize(buttonSize);
        dateButton.setPreferredSize(buttonSize);
        minigameButton.setPreferredSize(buttonSize);
        nextDayButton.setPreferredSize(buttonSize);
        
        // Disable date button initially
        dateButton.setEnabled(false);
        
        // Add action listeners
        feedButton.addActionListener(e -> {
            playSound("button");
            showFeedOptions();
        });
        
        playButton.addActionListener(e -> {
            playSound("button");
            showPlayOptions();
        });
        
        medicineButton.addActionListener(e -> {
            playSound("button");
            logic.makeChoice("medicine", "");
            playSound("medicine");
            updateStats();
            checkPetStatus();
        });
        
        dateButton.addActionListener(e -> {
            playSound("button");
            showDateOptions();
        });
        
        minigameButton.addActionListener(e -> {
            playSound("button");
            playMiniGame();
        });
        
        nextDayButton.addActionListener(e -> {
            playSound("button");
            advanceToNextDay();
        });
        
        // Add buttons to panel
        panel.add(feedButton);
        panel.add(playButton);
        panel.add(medicineButton);
        panel.add(dateButton);
        panel.add(minigameButton);
        panel.add(nextDayButton);
        
        return panel;
    }
    
    private JPanel createNotificationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UIComponents.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
        panel.setPreferredSize(new Dimension(120, 400));
        
        // Notification title
        JLabel notificationTitle = UIComponents.createLabel("Notifications", UIComponents.REGULAR_FONT);
        notificationTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Notification content
        notificationLabel = new JLabel("<html><body><p>Welcome to Little Life!</p></body></html>");
        notificationLabel.setFont(UIComponents.SMALL_FONT);
        notificationLabel.setForeground(UIComponents.TEXT_COLOR);
        notificationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(notificationTitle);
        panel.add(Box.createVerticalStrut(10));
        panel.add(notificationLabel);
        
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
                playSound("ignored");
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
            
            // Play appropriate sound
            if (foodChoice.equals(pet.getFavoriteFood().toLowerCase())) {
                playSound("favorite");
            } else {
                playSound("feed");
            }
            
            // Start happy animation if it's favorite food
            if (foodChoice.equals(pet.getFavoriteFood().toLowerCase())) {
                startPetAnimation("happy");
            }
            
            // Show feedback message
            showFeedbackMessage("You fed " + pet.getName() + " some " + selection);
            
            // Update UI
            updateStats();
            checkDateEligibility();
            checkPetStatus();
            
            // Add notification
            addNotification("Fed " + pet.getName() + " some " + selection);
            
            // Check for achievements
            checkFeedingAchievements();
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
                playSound("tired");
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
                playSound("ignored");
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
            
            // Play sound
            playSound("play");
            
            // Random gift chance
            if (Math.random() < 0.2) {
                String gift = getGiftByPersonality(pet.getPersonality());
                playSound("gift");
                showFeedbackMessage("Surprise! " + pet.getName() + " found a " + gift + "!");
                pet.changeHappiness(10);
                addNotification("Found a " + gift + "!");
            }
            
            // Show feedback message
            showFeedbackMessage("You played " + selection + " with " + pet.getName());
            
            // Update UI
            updateStats();
            checkDateEligibility();
            checkPetStatus();
            
            // Add notification
            addNotification("Played " + selection + " with " + pet.getName());
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
                
                // Play date sound
                playSound("date");
                
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
                
                // Add notification
                addNotification("Went on a " + dateSelection + "!");
                
                // Achievement for first date
                unlockAchievement("First Date");
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
        timeLabel.setText(pet.isNight() ? "Night" : "Day");
        
        // Apply day/night colors
        applyDayNightColors();
        
        // Play sound
        if (pet.isNight()) {
            playSound("night");
        } else {
            playSound("day");
        }
        
        // Add notification
        addNotification(pet.isNight() ? "Night has fallen" : "A new day has begun!");
        
        // Achievement for reaching day milestones
        if (dayCount == 5) unlockAchievement("5 Days Survived");
        if (dayCount == 10) unlockAchievement("10 Days Survived");
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
        
        // Update day/night panel
        updateDayNightPanel();
    }
    
    private void updateDayNightPanel() {
        if (pet.isNight()) {
            // Night mode visual indicators
            dayNightPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 5, new Color(100, 100, 255)));
            timeLabel.setIcon(UIComponents.createMoonIcon());
        } else {
            // Day mode visual indicators
            dayNightPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 5, new Color(255, 200, 0)));
            timeLabel.setIcon(UIComponents.createSunIcon());
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
            
            // Check for critical needs and add notifications
            checkCriticalNeeds();
        }
    }
    
    private void updateStatusLabel() {
        if (!pet.isAlive()) {
            statusLabel.setText(pet.getName() + " has died!");
            statusLabel.setForeground(Color.RED);
        } else if (pet.isSick()) {
            statusLabel.setText(pet.getName() + " is sick! Give medicine!");
            statusLabel.setForeground(Color.RED);
            // Start sick animation
            startPetAnimation("sick");
        } else if (pet.getEnergy() < 15) {
            statusLabel.setText(pet.getName() + " is exhausted!");
            statusLabel.setForeground(Color.GRAY);
        } else if (pet.canGoOnDate()) {
            statusLabel.setText(pet.getName() + " is ready for a date!");
            statusLabel.setForeground(new Color(0xFF69B4)); // Hot Pink
            // Start happy animation
            startPetAnimation("happy");
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
            playSound("date_ready");
            showFeedbackMessage("🎉 " + pet.getName() + " is ready for a date!");
            addNotification(pet.getName() + " is ready for a date!");
        }
    }
    
    private void checkPetStatus() {
        if (pet != null && !pet.isAlive()) {
            // Play game over sound
            playSound("game_over");
            
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
    
    // ========== NEW METHODS ==========
    
    /**
     * Handles petting the character
     */
    private void petCharacter() {
        // Small happiness boost
        pet.changeHappiness(2);
        
        // Play petting sound
        playSound("pet");
        
        // Show a brief animation
        startPetAnimation("happy");
        
        // Update UI
        updateStats();
        
        // Add notification
        addNotification("You petted " + pet.getName() + "!");
        
        // Achievement for first pet
        unlockAchievement("First Pet");
    }
    
    /**
     * Starts a simple animation for the pet
     */
    private void startPetAnimation(String type) {
        // Don't start a new animation if one is already running
        if (isAnimating) return;
        
        isAnimating = true;
        animationFrame = 0;
        
        final String animationType = type;
        final Timer timer = new Timer(150, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationFrame++;
                
                // Create a new icon based on animation type and frame
                Icon newIcon = UIComponents.createAnimatedPetIcon(
                    200, 200, 
                    pet.getPersonality(), 
                    pet.isNight(),
                    animationType,
                    animationFrame
                );
                
                characterIcon.setIcon(newIcon);
                
                // Stop after 6 frames
                if (animationFrame >= 6) {
                    timer.stop();
                    isAnimating = false;
                    
                    // Reset to normal icon
                    characterIcon.setIcon(UIComponents.createPetIcon(
                        200, 200, pet.getPersonality(), pet.isNight()
                    ));
                }
            }
        });
        
        timer.start();
    }
    
    /**
     * Start the animation timer
     */
    private void startAnimationTimer() {
        // Create and start animation timer for idle animations
        animationTimer = new Timer(30000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Only do idle animations if not sick or dead
                if (pet.isAlive() && !pet.isSick() && !isAnimating) {
                    // Random idle animation
                    if (Math.random() < 0.3) {
                        startPetAnimation("idle");
                    }
                }
            }
        });
        
        animationTimer.start();
    }
    
    /**
     * Play the mini-game
     */
    private void playMiniGame() {
        // Only allow playing if pet has enough energy
        if (pet.getEnergy() < 20) {
            playSound("tired");
            JOptionPane.showMessageDialog(
                this,
                pet.getName() + " is too tired to play a mini-game!",
                "Too Tired",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        // Simple number guessing game
        int secretNumber = (int)(Math.random() * 10) + 1;
        int attempts = 0;
        int maxAttempts = 3;
        boolean won = false;
        
        // Create custom panel for mini game
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        gamePanel.setBackground(UIComponents.BACKGROUND_COLOR);
        
        JLabel gameTitle = UIComponents.createLabel("Number Guessing Game", UIComponents.REGULAR_FONT);
        gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel instructions = new JLabel("<html><body>I'm thinking of a number between 1 and 10.<br>Try to guess it in " + maxAttempts + " attempts!</body></html>");
        instructions.setFont(UIComponents.SMALL_FONT);
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        gamePanel.add(gameTitle);
        gamePanel.add(Box.createVerticalStrut(10));
        gamePanel.add(instructions);
        
        // Show game panel
        while (attempts < maxAttempts && !won) {
            String input = JOptionPane.showInputDialog(
                gamePanel,
                "Attempt " + (attempts + 1) + "/" + maxAttempts + ": Enter your guess (1-10)",
                "Mini-Game",
                JOptionPane.QUESTION_MESSAGE
            );
            
            // Check if user cancelled
            if (input == null) {
                break;
            }
            
            try {
                int guess = Integer.parseInt(input);
                attempts++;
                
                if (guess == secretNumber) {
                    won = true;
                    playSound("win");
                    JOptionPane.showMessageDialog(
                        this,
                        "Correct! The number was " + secretNumber + "!",
                        "You Win!",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else if (guess < secretNumber) {
                    playSound("wrong");
                    JOptionPane.showMessageDialog(
                        this,
                        "Too low! Try again.",
                        "Mini-Game",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    playSound("wrong");
                    JOptionPane.showMessageDialog(
                        this,
                        "Too high! Try again.",
                        "Mini-Game",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid number.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        }
        
        // Game results
        if (won) {
            // Happiness boost for winning
            pet.changeHappiness(15);
            pet.changeEnergy(-10);
            
            // Achievement for winning mini-game
            unlockAchievement("Game Winner");
            
            // Add notification
            addNotification(pet.getName() + " won the mini-game!");
        } else if (attempts >= maxAttempts) {
            // Small happiness decrease for losing
            pet.changeHappiness(-5);
            pet.changeEnergy(-10);
            
            // Show correct answer
            JOptionPane.showMessageDialog(
                this,
                "Game over! The number was " + secretNumber + ".",
                "Mini-Game",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            // Add notification
            addNotification(pet.getName() + " lost the mini-game.");
        }
        
        // Update UI
        updateStats();
    }
    
    /**
     * Add a notification to the notification panel
     */
    private void addNotification(String message) {
        // Get current notifications
        String currentNotifications = notificationLabel.getText();
        
        // Extract content inside the <p> tags
        String content = currentNotifications.replaceAll("<html><body>", "").replaceAll("</body></html>", "");
        
        // Add new notification at the top with timestamp
        String time = pet.isNight() ? "🌙" : "☀️";
        String newNotification = "<p>" + time + " Day " + dayCount + ": " + message + "</p>";
        
        // Keep only the last 5 notifications
        String[] notifications = content.split("<p>");
        StringBuilder updatedContent = new StringBuilder("<html><body>");
        updatedContent.append(newNotification);
        
        int count = 1;
        for (int i = 1; i < notifications.length && count < 5; i++) {
            if (!notifications[i].trim().isEmpty()) {
                updatedContent.append("<p>").append(notifications[i]);
                count++;
            }
        }
        
        updatedContent.append("</body></html>");
        
        // Update the label
        notificationLabel.setText(updatedContent.toString());
    }
    
    /**
     * Check for critical needs (low health, hunger, etc.)
     */
    private void checkCriticalNeeds() {
        // Check for low health
        if (pet.getHealth() < 20 && !pet.isSick()) {
            addNotification("WARNING: Health is very low!");
        }
        
        // Check for low hunger
        if (pet.getHunger() < 20) {
            addNotification("WARNING: " + pet.getName() + " is very hungry!");
        }
        
        // Check for low happiness
        if (pet.getHappiness() < 20) {
            addNotification("WARNING: " + pet.getName() + " is very unhappy!");
        }
        
        // Check for low energy
        if (pet.getEnergy() < 20) {
            addNotification("WARNING: " + pet.getName() + " is very tired!");
        }
    }
    
    /**
     * Unlock an achievement
     */
    private void unlockAchievement(String achievement) {
        // Get current player from game
        Player player = this.player;
        
        // Add achievement to player
        if (player.addAchievement(achievement)) {
            // Play achievement sound
            playSound("achievement");
            
            // Show achievement notification
            JOptionPane.showMessageDialog(
                this,
                "Achievement Unlocked: " + achievement,
                "Achievement",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            // Update achievement label
            achievementLabel.setText("Achievements: " + player.getAchievementCount());
            
            // Add notification
            addNotification("Achievement: " + achievement);
        }
    }
    
    /**
     * Check feeding related achievements
     */
    private void checkFeedingAchievements() {
        Player player = this.player;
        
        // Check for first feed achievement
        if (player.getFeedCount() == 1) {
            unlockAchievement("First Meal");
        }
        
        // Check for feeding variety
        if (player.getFoodVarietyCount() >= 3) {
            unlockAchievement("Balanced Diet");
        }
    }
    
    /**
     * Play a sound effect
     */
    private void playSound(String soundName) {
        try {
            // For a real game, you'd load actual sound files
            // This is a placeholder that would be replaced with actual sound loading code
            
            // Simple beep for now (would be replaced with real sounds)
            if (soundName.equals("achievement") || soundName.equals("win")) {
                Toolkit.getDefaultToolkit().beep();
            }
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }
    
    /**
     * Save the game state
     */
    private void saveGame() {
        try {
            // Create a GameState object
            GameState gameState = new GameState(pet, player, dayCount);
            
            // Open file chooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Game");
            fileChooser.setSelectedFile(new File("littlelife_save.dat"));
            
            int userSelection = fileChooser.showSaveDialog(this);
            
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                
                // Add .dat extension if not present
                if (!fileToSave.getAbsolutePath().endsWith(".dat")) {
                    fileToSave = new File(fileToSave.getAbsolutePath() + ".dat");
                }
                
                // Save the game state
                FileOutputStream fileOut = new FileOutputStream(fileToSave);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(gameState);
                out.close();
                fileOut.close();
                
                // Play save sound
                playSound("save");
                
                // Show confirmation
                JOptionPane.showMessageDialog(
                    this,
                    "Game saved successfully!",
                    "Save Complete",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
                // Add notification
                addNotification("Game saved successfully!");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                this,
                "Error saving game: " + e.getMessage(),
                "Save Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Load a saved game
     */
    private void loadGame() {
        try {
            // Open file chooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Load Game");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".dat");
                }
                
                @Override
                public String getDescription() {
                    return "Little Life Save Files (*.dat)";
                }
            });
            
            int userSelection = fileChooser.showOpenDialog(this);
            
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();
                
                // Load the game state
                FileInputStream fileIn = new FileInputStream(fileToLoad);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                GameState gameState = (GameState) in.readObject();
                in.close();
                fileIn.close();
                
                // Update game with loaded state
                this.pet = gameState.getPet();
                this.player = gameState.getPlayer();
                this.dayCount = gameState.getDayCount();
                
                // Recreate logic
                this.logic = new Logic(pet, player);
                
                // Update UI
                dayCountLabel.setText("Day " + dayCount);
                characterIcon.setIcon(UIComponents.createPetIcon(200, 200, pet.getPersonality(), pet.isNight()));
                timeLabel.setText(pet.isNight() ? "🌙 Night" : "☀️ Day");
                updateStats();
                checkDateEligibility();
                
                // Apply day/night colors
                applyDayNightColors();
                
                // Play load sound
                playSound("load");
                
                // Show confirmation
                JOptionPane.showMessageDialog(
                    this,
                    "Game loaded successfully!",
                    "Load Complete",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
                // Add notification
                addNotification("Game loaded successfully!");
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(
                this,
                "Error loading game: " + e.getMessage(),
                "Load Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}