class Player {
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void feed(Tamagotchi pet, String food) {
        switch (food.toLowerCase()) {
            case "apple":
            case "vegetables":
            case "water":
                pet.changeHunger(10);
                pet.changeHealth(10);
                System.out.println("You fed " + pet.getName() + " healthy food! 🍏");
                break;
            case "soda":
            case "candy":
                pet.changeHunger(5);
                pet.changeHealth(-5);
                System.out.println("You gave " + pet.getName() + " some sugary food. 😬");
                break;
            case "beer":
            case "junk food":
                pet.changeHunger(5);
                pet.changeHealth(-10);
                System.out.println("You fed " + pet.getName() + " unhealthy food. 🚨");
                break;
            default:
                System.out.println("Invalid food choice.");
        }
    }

    public void play(Tamagotchi pet, String activity) {
        switch (activity.toLowerCase()) {
            case "fetch":
            case "walk":
                pet.changeHappiness(10);
                pet.changeHealth(5);
                System.out.println("You played with " + pet.getName() + "! 🐶");
                break;
            case "watch movie":
            case "play video game":
                pet.changeHappiness(5);
                pet.changeHealth(-5);
                System.out.println(pet.getName() + " enjoyed a relaxing movie/game night. 🎬");
                break;
            default:
                System.out.println("Invalid activity choice.");
        }
    }
}
