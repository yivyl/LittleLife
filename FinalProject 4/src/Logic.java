class Logic {
    private Tamagotchi pet;
    private Player player;

    public Logic(Tamagotchi pet, Player player) {
        this.pet = pet;
        this.player = player;
    }

    public void makeChoice(String category, String choice) {
        switch (category.toLowerCase()) {
            case "feed":
                player.feed(pet, choice);
                break;
            case "play":
                player.play(pet, choice);
                break;
            default:
                System.out.println("Invalid category.");
        }

        if (!pet.isAlive()) {
            System.out.println(pet.getStatus());
            System.out.println(pet.getName() + " has died. Game Over.");
            System.exit(0);
        }

        if (pet.canGoOnDate()) {
            System.out.println("🎉 " + pet.getName() + " is ready to go on a date! 🎉");
        }
    }
}

