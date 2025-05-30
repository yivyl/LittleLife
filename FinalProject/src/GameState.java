import java.io.Serializable;

/**
 * Represents the saved game state
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Tamagotchi pet;
    private Player player;
    private int dayCount;
    
    /**
     * Creates a new game state
     */
    public GameState(Tamagotchi pet, Player player, int dayCount) {
        this.pet = pet;
        this.player = player;
        this.dayCount = dayCount;
    }
    
    /**
     * Gets the pet
     */
    public Tamagotchi getPet() {
        return pet;
    }
    
    /**
     * Gets the player
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * Gets the day count
     */
    public int getDayCount() {
        return dayCount;
    }
}