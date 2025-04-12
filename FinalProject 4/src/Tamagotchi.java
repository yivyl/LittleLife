class Tamagotchi {
    private String name;
    private String pronouns;
    private int health;
    private int happiness;
    private int hunger;
    private boolean isSick;

    public Tamagotchi(String name, String pronouns) {
        this.name = name;
        this.pronouns = pronouns;
        this.health = 75;
        this.happiness = 75;
        this.hunger = 75;
        this.isSick = false;
    }

    public void changeHealth(int amount) {
        health += amount;
        checkSickness();
        checkGameOver();
    }

    public void changeHappiness(int amount) {
        happiness += amount;
        checkSickness();
        checkGameOver();
    }

    public void changeHunger(int amount) {
        hunger += amount;
        checkSickness();
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

    private void checkGameOver() {
        if (health <= 0 && happiness <= 0 && hunger <= 0) {
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
        if (isSick) status += " (Sick 🤒)";
        if (canGoOnDate()) status += " 🎉 Ready for a date! Will update for Final-term";
        return status;
    }

    public String getName() {
        return name;
    }
}
