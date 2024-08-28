/**
 * Team members: Pham Nguyen, Ali Mohamed and Faisal Nur
 * Class: TCSS360
 * Date: Jun 7, 2024
 * @version 1.0
 */
package Model;



/**
 * The DungeonCharacter class represents a character in the dungeon.
 * It serves as an abstract base class for specific character types.
 */

public abstract class DungeonCharacter {
    private String myName;
    private int myHitPoints;
    private int myAttackSpeed;
    private double myChanceToHit;
    private int myMaxDamage;
    private int myMinDamage;
    private int myX;
    private int myY;




    /**
     * Constructs a new DungeonCharacter object with the specified attributes.
     *
     * @param theName        the name of the character
     * @param theHitPoints   the hit points of the character
     * @param theAttackSpeed the attack speed of the character
     * @param theChanceToHit the chance to hit of the character
     * @param theMaxDamage   the maximum damage the character can inflict
     * @param theMinDamage   the minimum damage the character can inflict
     * @param theX           the x-coordinate of the character
     * @param theY           the y-coordinate of the character
     */


    protected DungeonCharacter(final String theName, final int theHitPoints, final int theAttackSpeed,
                               final double theChanceToHit, final int theMaxDamage, final int theMinDamage,
                               final int theX, final int theY){
        setMyName(theName);
        setMyHitPoints(theHitPoints);
        setMyAttackSpeed(theAttackSpeed);
        setMyChanceToHit(theChanceToHit);
        setMyMaxDamage(theMaxDamage);
        setMyMinDamage(theMinDamage);
        setMyY(theY);
        setMyX(theX);


    }

    /**
     * Sets the chance to hit of the character.
     *
     * @param theChanceToHit the chance to hit value to set
     * @throws IllegalArgumentException if the chance to hit is not between 0.0 and 1.0
     */

    protected void setMyChanceToHit(final double theChanceToHit) {
        if (myChanceToHit < 0.0 || myChanceToHit > 1.0) {
            throw new IllegalArgumentException("Chance to hit must be between 0.0 and 1.0");
        }else {
            myChanceToHit = theChanceToHit;
        }
    }

    /**
     * Sets the y-coordinate of the character.
     *
     * @param theY the y-coordinate to set
     */
    public void setMyY(final int theY) {
        if(theY<0 || theY>4096){
            throw new IllegalArgumentException("Charcter is out of bounds");
        }else{
            myY = theY;
        }

    }

    /**
     * Sets the X-coordinate of the character.
     *
     * @param theX the y-coordinate to set
     */
    public void setMyX(final int theX) {
        if(theX<0 || theX>4096){
            throw new IllegalArgumentException("Character is out of bounds");
        }else{
            myX = theX;
            }

    }


    /**
     * Sets the minimum damage to the character.
     *
     * @param theMinDamage the minimum damage value to set
     * @throws IllegalArgumentException if the minimum damage is less than 0
     */
    void setMyMinDamage(final int theMinDamage) {
        if(theMinDamage < 0 ) {
            throw new IllegalArgumentException("Min damage must be greater than 0");
        }
        myMinDamage = theMinDamage;
    }
    /**
     *
     * Sets the maximum damage of the character.
     *
     * @param theMaxDamage the maximum damage value to set
     * @throws IllegalArgumentException if the maximum damage is less than 0
     */
    void setMyMaxDamage(final int theMaxDamage) {
        if(theMaxDamage < 0 ) {
            throw new IllegalArgumentException("Max damage must be greater than 0");
        }
        myMaxDamage = theMaxDamage;
    }

    /**
     * Sets the attack speed of the character.
     *
     * @param theAttackSpeed the attack speed value to set
     * @throws IllegalArgumentException if the attack speed is less than 0
     */
    void setMyAttackSpeed(final int theAttackSpeed) {
        if(theAttackSpeed < 0){
            throw  new IllegalArgumentException("AttackSpeed must be greater than 0");
        }
        myAttackSpeed = theAttackSpeed;
    }

    /**
     * Sets the hit points of the character.
     *
     * @param theHealthPoints the hit points value to set
     */
    public void setMyHitPoints(final int theHealthPoints) {
        myHitPoints = theHealthPoints;
    }

    /**
     * Sets the name of the character.
     *
     * @param theName the name to set
     * @throws IllegalArgumentException if the name is null
     */
    void setMyName(final String theName) {
        if(theName == null) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        myName = theName;
    }
    /**
     * Returns the hit points of the character.
     *
     * @return the hit points of the character
     */
    public int getMyHitPoints() {
        return myHitPoints;
    }

    /**
     * Returns the attack speed of the character.
     *
     * @return the attack speed of the character
     */
    public int getMyAttackSpeed() {
        return myAttackSpeed;
    }

    /**
     * Returns the chance to hit of the character.
     *
     * @return the chance to hit of the character
     */

    public double getMyChanceToHit() {
        return myChanceToHit;
    }

    /**
     * Returns the maximum damage the character can inflict.
     *
     * @return the maximum damage of the character
     */
    public int getMyMaxDamage() {
        return myMaxDamage;
    }

    /**
     * Returns the name of the character.
     *
     * @return the name of the character
     */
    public String getMyName() {
        return myName;
    }

    /**
     * Returns the minimum damage the character can inflict.
     *
     * @return the minimum damage of the character
     */
    public int getMyMinDamage() {
        return myMinDamage;
    }

    /**
     * Returns the x-coordinate of the character.
     *
     * @return the x-coordinate of the character
     */

    public int getMyX() {
        return myX;
    }

    /**
     * Returns the y-coordinate of the character.
     *
     * @return the y-coordinate of the character
     */
    public int getMyY() {
        return myY;
    }


    /**
     * Attacks another character.
     *
     * @param theOther the character to attack
     */
    public void attack(final DungeonCharacter theOther){
        double random = Math.random();
        if(random <= myChanceToHit) {
            int damage = (int) (Math.random() * (getMyMaxDamage() - getMyMinDamage() + 1)) + getMyMinDamage();
            int newHitPoint = theOther.getMyHitPoints()-damage;
            theOther.setMyHitPoints(newHitPoint);
        }
    }
}
