package Model;

/**
 * The Warrior class represents a type of Hero character in the game.
 * Warriors are powerful melee combatants, specializing in dealing heavy damage.
 */
public class Warrior extends Hero {
    private static Warrior MyUniqueInstance;

    /**
     * Constructs a Warrior object with predefined attributes.
     * The constructor is private to ensure only one instance of Warrior can exist.
     */
    private Warrior() {
        super("Warrior", 125, 4, 0.8, 60, 35, 10, 10, 0.2, 75, 175, 0.4);
    }

    /**
     * Performs a special attack unique to the Warrior character.
     * The Warrior has an 80% chance to execute a special attack, dealing additional damage.
     *
     * @param theOther the DungeonCharacter targeted by the special attack
     */
    @Override
    public void special(DungeonCharacter theOther) {
        double random = Math.random();
        if (random <= getMyChanceForSpecial()) {
            int damage = (int) (Math.random() * (getMySpecialMax() - getMySpecialMin() + 1)) + getMySpecialMin();
            theOther.setMyHitPoints(theOther.getMyHitPoints() - damage);
            setMySpecialAmount(damage);
            setMySpecialSuccess(true);
            // Turn this print statement into a GUI component
        }
    }

    /**
     * Retrieves the unique instance of the Warrior class.
     * If the instance does not exist, it creates a new one.
     *
     * @return the unique instance of the Warrior class
     */
    public static Warrior getInstance() {
        if (MyUniqueInstance == null) {
            MyUniqueInstance = new Warrior();
        }
        return MyUniqueInstance;
    }

    /**
     * Creates and returns a new instance of the Warrior class.
     * This method is used for testing purposes only.
     *
     * @return a new instance of the Warrior class
     */
    public static Warrior TEST_getInstance() {
        return new Warrior();
    }
}
