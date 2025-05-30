import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Player implements Serializable {
    private String name;
    private int feedCount;
    private Set<String> foodsFed;
    private List<String> achievements;
    
    public Player(String name) {
        this.name = name;
        this.feedCount = 0;
        this.foodsFed = new HashSet<>();
        this.achievements = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public void feed(Tamagotchi pet, String food) {
        if (pet.isSick()) {
            System.out.println(pet.getName() + " is sick and refuses to eat!");
            return;
        }

        // Track feeding statistics
        feedCount++;
        foodsFed.add(food.toLowerCase());

        switch (food.toLowerCase()) {
            case "apple":
            case "vegetables":
            case "water":
                pet.changeHunger(15);
                pet.changeHealth(15);
                break;
            case "soda":
            case "candy":
                pet.changeHunger(10);
                pet.changeHealth(-10);
                break;
            case "beer":
            case "junk food":
                pet.changeHappiness(5);
                pet.changeHunger(5);
                pet.changeHealth(-5);
                break;
            default:
                System.out.println("Invalid food choice.");
                return;
        }

        if (food.equalsIgnoreCase(pet.getFavoriteFood())) {
            pet.changeHappiness(10);
            System.out.println("It's " + pet.getName() + "'s favorite food! Happiness increased!");
        } else {
            System.out.println("You fed " + pet.getName() + " some " + food + ".");
        }
    }
    
    public void giveMedicine(Tamagotchi pet) {
        pet.giveMedicine();
    }

    public void play(Tamagotchi pet, String activity) {
        if (pet.isSick()) {
            System.out.println(pet.getName() + " is sick and refuses to play! You need to get some medicine!");
            return;
        }

        if (pet.getEnergy() <= 10) {
            System.out.println(pet.getName() + " is too tired to play!");
            return;
        }

        // Lazy pets dislike lots of activity
        if (pet.getPersonality().equals("lazy") && activity.equals("walk")) {
            System.out.println(pet.getName() + " is lazy and doesn't like long walks.");
            pet.changeHappiness(-5);
        }

        int baseHappiness = 0;
        int baseEnergy = 0;

        switch (activity.toLowerCase()) {
            case "fetch":
            case "walk":
                baseHappiness = 15;
                baseEnergy = -20;
                pet.changeHealth(15);
                pet.changeHunger(-5);
                break;
            case "watch movie":
            case "play video game":
                baseHappiness = 10;
                baseEnergy = -10;
                pet.changeHealth(-5);
                break;
            default:
                System.out.println("Invalid activity choice.");
                return;
        }
        
        // Surprise gift
        if (Math.random() < 0.2) {
            String gift = getRandomGift(pet.getPersonality());
            System.out.println("Surprise! " + pet.getName() + " found a gift: " + gift + "!");
            pet.changeHappiness(10);
        }

        // Grumpy pets gain extra if you actually please them
        if (pet.getPersonality().equals("grumpy") && baseHappiness > 10) {
            baseHappiness += 5;
            System.out.println(pet.getName() + " (grumpy) surprisingly had a great time!");
        }

        pet.changeHappiness(baseHappiness);
        pet.changeEnergy(baseEnergy);
        System.out.println("You played " + activity + " with " + pet.getName() + "!");

        pet.resetIgnoredDays(); 
    }

    private String getRandomGift(String personality) {
        switch (personality) {
            case "playful": return "squeaky toy";
            case "lazy": return "comfy pillow";
            case "grumpy": return "rare snack";
            default: return "treat";
        }
    }

    public void date(Tamagotchi pet, String dates, String gift) {
        switch (dates.toLowerCase()) {
            case "movie":
            case "coffee":
                pet.changeHappiness(5);
                pet.changeHealth(5);
                System.out.println(pet.getName() + " enjoyed the date!");
                
                if (gift.equals("flower") || gift.equals("chocolates")) {
                    pet.changeHappiness(5);
                    System.out.println(pet.getName() + "'s date loved the gift.");
                }
                break;
                
            case "dinner":
            case "drinks":
                pet.changeHappiness(10);
                pet.changeHealth(5);
                System.out.println(pet.getName() + " had a wonderful time!");
                
                if (gift.equals("flower") || gift.equals("chocolates")) {
                    pet.changeHappiness(5);
                    System.out.println(pet.getName() + "'s date loved the gift.");
                }
                break;
                
            default:
                System.out.println("Invalid date choice");
        }
    }
    
    // New methods for tracking stats and achievements
    
    /**
     * Get the number of times the pet has been fed
     */
    public int getFeedCount() {
        return feedCount;
    }
    
    /**
     * Get the number of different types of food given
     */
    public int getFoodVarietyCount() {
        return foodsFed.size();
    }
    
    /**
     * Add a new achievement if not already earned
     * @return true if this is a new achievement
     */
    public boolean addAchievement(String achievement) {
        if (!achievements.contains(achievement)) {
            achievements.add(achievement);
            return true;
        }
        return false;
    }
    
    /**
     * Get the number of achievements earned
     */
    public int getAchievementCount() {
        return achievements.size();
    }
    
    /**
     * Get the list of achievements
     */
    public List<String> getAchievements() {
        return new ArrayList<>(achievements);
    }
}