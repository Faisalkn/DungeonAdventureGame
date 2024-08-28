/**
 * Team members: Pham Nguyen, Ali Mohamed and Faisal Nur
 * Class: TCSS360
 * Date: Jun 7, 2024
 * @version 1.0
 */
package Model;

/**
 * The Priestess class extends Hero class, represents a hero character with unique healing abilities.
 * It is implemented as a Singleton to ensure only one instance of the class is created.
 */
public class Priestess extends Hero{
    /**
     * The unique instance field of the Priestess.
     */
    private static Priestess myUniqueInstance;

    /**
     * Private constructor to prevent instantiation.
     * Initializes the Priestess with specific attributes.
     */
    private Priestess(){
        super("Priestess",75,4,0.7,45,25,10,10,
                0.3,50,100,0.6);
    }

    /**
     * The special ability of the Priestess to heal herself.
     * Heals a random amount of hit points based on the special skill's range.
     * @param theOther The DungeonCharacter the Priestess is interacting with (not used in this implementation).
     */
    @Override
    public void special(DungeonCharacter theOther) {
        double random = Math.random();
        if( random <= getMyChanceForSpecial()){
            int healedPoints = (int) (Math.random() * (getMySpecialMax() - getMySpecialMin() + 1)) + getMySpecialMin();
            //should not heal if already at max points
            if(getMyHitPoints() == 75){
                setMySpecialSuccess(false);
                // ensures priestess only heals to 75 at most
            } else if(getMyHitPoints() + healedPoints > 75){
                setMySpecialAmount(75-getMyHitPoints());
                setMySpecialSuccess(true);
                this.setMyHitPoints(getMyHitPoints() + getMySpecialAmount());
            } else {
                setMySpecialAmount(healedPoints);
                setMySpecialSuccess(true);
                this.setMyHitPoints(getMyHitPoints() + getMySpecialAmount());
            }

        }
    }

    /**
     * This is a method gets the unique instance of the Priestess.
     * @return The unique instance of the Priestess.
     */
    public static Priestess getMyUniqueInstance() {
        if(myUniqueInstance == null) {
            myUniqueInstance = new Priestess();
        }
        return myUniqueInstance;
    }

    /**
     * This is a method gets a new instance of the Priestess for testing purposes.
     * @return A new instance of the Priestess.
     */
    public static Priestess Test_getMyUniqueInstance(){
        return new Priestess();
    }
}