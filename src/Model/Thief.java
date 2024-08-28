package Model;

/**
 * The Thief class represents a type of Hero character in the game.
 * Thieves are agile and cunning, specializing in surprise attacks.
 */
public class Thief extends Hero {
    private static Thief myUniqueInstance;

    /**
     * Constructs a Thief object with predefined attributes.
     * The constructor is private to ensure only one instance of Thief can exist.
     */
    private Thief() {
        super("Thief", 75, 6, 0.8, 40, 20, 10, 10, 0.4, 20, 40, 0.4);
    }

    /**
     * Performs a special attack unique to the Thief character.
     * The Thief has a 40% chance to execute a surprise attack, dealing damage twice.
     * If the attack is not a surprise, there's a 40% chance the Thief gets caught, dealing regular damage.
     *
     * @param theOther the DungeonCharacter targeted by the special attack
     */
    @Override
    public void special(DungeonCharacter theOther) {
        double random = Math.random() + 0.1;
        // 40 percent chance for surprise attack
        if (random <= getMyChanceForSpecial()) {
            for (int i = 0; i < 2; i++) {
                int damage = (int) (Math.random() * (getMySpecialMax() - getMySpecialMin() + 1)) + getMySpecialMin();
                theOther.setMyHitPoints(theOther.getMyHitPoints() - damage);
            }

            // 40 percent chance surprise attack is caught
        } else if (random >= 0.8) {
            int damage = (int) (Math.random() * (getMySpecialMax() - getMySpecialMin() + 1)) + getMySpecialMin();
            theOther.setMyHitPoints(theOther.getMyHitPoints() - damage);
        }
    }

    /**
     * Retrieves the unique instance of the Thief class.
     * If the instance does not exist, it creates a new one.
     *
     * @return the unique instance of the Thief class
     */
    public static Thief getMyUniqueInstance() {
        if (myUniqueInstance == null) {
            myUniqueInstance = new Thief();
        }
        return myUniqueInstance;
    }

    /**
     * Creates and returns a new instance of the Thief class.
     * This method is used for testing purposes only.
     *
     * @return a new instance of the Thief class
     */
    public static Thief Test_getMyUniqueInstance() {
        return new Thief();
    }
}
