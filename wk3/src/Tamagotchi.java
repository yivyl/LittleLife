class Tamagotchi {
    private String name;
    private String pronouns;
    private int health;
    private int happiness;
    private int hunger;
    private boolean isSick;
    private String mood;  // Added mood property

    public Tamagotchi(String name, String pronouns) {
        this.name = name;
        this.pronouns = pronouns;
        this.health = 75;
        this.happiness = 75;
        this.hunger = 75;
        this.isSick = false;
        this.mood = "normal";  // Default mood
    }

    public void changeHealth(int amount) {
        health += amount;
        checkSickness();
        updateMood();
        checkGameOver();
    }

    public void changeHappiness(int amount) {
        happiness += amount;
        checkSickness();
        updateMood();
        checkGameOver();
    }

    public void changeHunger(int amount) {
        hunger += amount;
        checkSickness();
        updateMood();
        checkGameOver();
    }

    private void checkSickness() {
        if (health < 20 || happiness < 20 || hunger < 20) {
            isSick = true;
            System.out.println(name + " is sick! Take better care of your pet.");
        } else {
            isSick = false;
        }
    }

    // New method to update mood based on pet stats
    private void updateMood() {
        if (isSick) {
            mood = "sick";
        } else if (health < 30 || happiness < 30 || hunger < 30) {
            mood = "sad";
        } else if (health < 50 || happiness < 50 || hunger < 50) {
            mood = "worried";
        } else if (canGoOnDate()) {
            mood = "love";
        } else if (health > 100 && happiness > 90) {
            mood = "excited";
        } else if (happiness > 90) {
            mood = "happy";
        } else if (hunger < 60) {
            mood = "hungry";
        } else {
            mood = "normal";
        }
    }

    private void checkGameOver() {
        if (health <= 0 && happiness <= 0 && hunger <= 0) {
            mood = "dead";
            System.out.println(name + " has died. Game Over.");
            System.exit(0);
        }
    }

    public boolean canGoOnDate() {
        return health > 100 && happiness > 100 && hunger > 100;
    }

    public boolean isAlive() {
        return health > 0 || happiness > 0 || hunger > 0;
    }

    public String getStatus() {
        String status = name + " (" + pronouns + ") - Health: " + health + ", Happiness: " + happiness + ", Hunger: " + hunger;
        status += ", Mood: " + mood;
        if (isSick) status += " (Sick 🤒)";
        if (canGoOnDate()) status += " 🎉 Ready for a date! Will update for Final-term";
        return status;
    }

    public String getName() {
        return name;
    }
    
    public int getHealth() {
        return health;
    }
    
    public int getHappiness() {
        return happiness;
    }
    
    public int getHunger() {
        return hunger;
    }
    
    public String getMood() {
        return mood;
    }
    
    public boolean isSick() {
        return isSick;
    }
}