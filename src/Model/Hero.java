/**
 * Team members: Pham Nguyen, Ali Mohamed and Faisal Nur
 * Class: TCSS360
 * Date: Jun 7, 2024
 * @version 1.0
 */
package Model;

import java.awt.*;

/**
 * The Hero class represents a hero character in the dungeon.
 * It extends the DungeonCharacter class and provides additional attributes and methods for hero characters.
 */
public abstract class Hero extends DungeonCharacter {

    /**
     * The hit box of the hero.
     */
    private Rectangle myHitBox;

    /**
     * The chance for the hero to block an attack.
     */
    private double myChanceToBlock;

    /**
     * The minimum damage of the hero's special attack.
     */
    private int mySpecialMin;

    /**
     * The maximum damage of the hero's special attack.
     */
    private int mySpecialMax;

    /**
     * The chance for the hero to perform a special attack.
     */
    private double myChanceForSpecial;

    /**
     * Indicates whether the hero's special attack was successful.
     */

    private boolean mySpecialSuccess;

    /**
     * The amount of damage dealt by the hero's special attack.
     */
    private int mySpecialAmount;

    /**
     * Indicates whether the hero blocked an attack.
     */

    private boolean myBlockedAttack;


    /**
     * Constructs a new Hero object with the specified attributes.
     *
     * @param theName             the name of the hero
     * @param theHitPoints        the hit points of the hero
     * @param theAttackSpeed      the attack speed of the hero
     * @param theChanceToHit      the chance for the hero to hit
     * @param theMaxDamage        the maximum damage of the hero
     * @param theMinDamage        the minimum damage of the hero
     * @param theX                the x-coordinate of the hero
     * @param theY                the y-coordinate of the hero
     * @param theChanceToBlock    the chance for the hero to block an attack
     * @param theSpecialMin       the minimum damage of the hero's special attack
     * @param theSpecialMax       the maximum damage of the hero's special attack
     * @param theChanceForSpecial the chance for the hero to perform a special attack
     */

    protected Hero(final String theName, final int theHitPoints, final int theAttackSpeed,
                   final double theChanceToHit, final int theMaxDamage, final int theMinDamage, final int theX,
                   final int theY, final double theChanceToBlock, final int theSpecialMin,
                   final int theSpecialMax, final double theChanceForSpecial) {

        super(theName, theHitPoints, theAttackSpeed, theChanceToHit, theMaxDamage, theMinDamage, theX, theY);
        setMyChanceToBlock(theChanceToBlock);
        setMySpecialMax(theSpecialMax);
        setMySpecialMin(theSpecialMin);
        setMyChanceForSpecial(theChanceForSpecial);
        setMySpecialSuccess(false);
        setMySpecialAmount(0);



    }



    /**
     * Sets the chance for the hero to perform a special attack.
     *
     * @param theChanceForSpecial the chance for the hero to perform a special attack
     * @throws IllegalArgumentException if the chance is not between 0.0 and 1.0
     */
    void setMyChanceForSpecial(final double theChanceForSpecial) {
        // Check if the input value is a valid double
        if ( theChanceForSpecial < 0.0 || theChanceForSpecial > 1.0 ) {
            // Handle invalid input
            throw new IllegalArgumentException("Invalid value for chance of special attack chance. It must be a double between 0.0 and 1.0");
        } else {
            myChanceForSpecial = theChanceForSpecial;
        }
    }

    /**
     * Sets the hit box of the hero.
     *
     * @param theHitBox the hit box of the hero
     */
    public void setMyHitBox(Rectangle theHitBox) {
        this.myHitBox = theHitBox;
    }



    /**
     * Sets the maximum damage of the hero's special attack.
     *
     * @param theSpecialMax the maximum damage of the hero's special attack
     * @throws IllegalArgumentException if the maximum damage is negative
     */
    void setMySpecialMax(final int theSpecialMax) {
        if (theSpecialMax < 0) {
            throw new IllegalArgumentException("Invalid input for max special attack damage. It must be a non-negative integer.");
        } else {
            mySpecialMax = theSpecialMax;
        }
    }

    /**
     * Sets whether the hero blocked an attack.
     *
     * @param theBlockedAttack true if the hero blocked an attack, false otherwise
     */
    public void setMyBlockedAttack(boolean theBlockedAttack) {
        myBlockedAttack = theBlockedAttack;
    }

    /**
     * Sets the minimum damage of the hero's special attack.
     *
     * @param theSpecialMin the minimum damage of the hero's special attack
     * @throws IllegalArgumentException if the minimum damage is negative
     */

    void setMySpecialMin(final int theSpecialMin) {
        if (theSpecialMin < 0) {
            throw new IllegalArgumentException("Invalid input for min special attack damage. It must be a non-negative integer.");
        } else {
            mySpecialMin = theSpecialMin;
        }
    }

    /**
     * method that sets chance to block
     * @param theChanceToBlock double
     */
    void setMyChanceToBlock(final double theChanceToBlock){
        if ( theChanceToBlock < 0.0 || theChanceToBlock > 1.0) {
            throw new IllegalArgumentException("Invalid value for chance of chance to block. It must be a double between 0.0 and 1.0");
        } else {
            myChanceToBlock = theChanceToBlock;
        }
    }

    /**
     * Sets whether the hero's special attack was successful.
     *
     * @param theSpecialSuccess true if the hero's special attack was successful, false otherwise
     */
    public void setMySpecialSuccess(final boolean theSpecialSuccess) {
        mySpecialSuccess = theSpecialSuccess;
    }

    /**
     * Sets the amount of damage dealt by the hero's special attack.
     *
     * @param theSpecialAmount the amount of damage dealt by the hero's special attack
     * @throws IllegalArgumentException if the special amount is negative
     */
    void setMySpecialAmount(final int theSpecialAmount) {
        if (theSpecialAmount < 0) {
            throw new IllegalArgumentException("The special amount must be a positive integer.");
        } else{
            mySpecialAmount = theSpecialAmount;
        }
    }

    /**
     * Attempts to block an attack based on the hero's chance to block.
     */
    public void block() {
        double random = Math.random();
        if(random <= myChanceToBlock){
            myBlockedAttack = true;
        }
    }


    /**
     * Returns whether the hero's special attack was successful.
     *
     * @return true if the hero's special attack was successful, false otherwise
     */
    public boolean getMySpecialSuccess() {
        return mySpecialSuccess;
    }


    /**
     * Returns the minimum damage of the hero's special attack.
     *
     * @return the minimum damage of the hero's special attack
     */
    public int getMySpecialMin() {
        return mySpecialMin;
    }


    /**
     * Returns the maximum damage of the hero's special attack.
     *
     * @return the maximum damage of the hero's special attack
     */
    public int getMySpecialMax() {
        return mySpecialMax;
    }

    /**
     * Returns the chance for the hero to perform a special attack.
     *
     * @return the chance for the hero to perform a special attack
     */

    public double getMyChanceForSpecial() {
        return myChanceForSpecial;
    }

    /**
     * Returns the hit box of the hero.
     *
     * @return the hit box of the hero
     */

    public Rectangle getMyHitBox() {
        return myHitBox;
    }

    /**
     * Returns the amount of damage dealt by the hero's special attack.
     *
     * @return the amount of damage dealt by the hero's special attack
     */
    public int getMySpecialAmount() {
        return mySpecialAmount;
    }

    /**
     * Returns whether the hero blocked an attack.
     *
     * @return true if the hero blocked an attack, false otherwise
     */
    public boolean getMyBlockedAttack() {
        return myBlockedAttack;
    }

    /**
     * getter for my chance to block
     * @return double
     */
    public double getMyChanceToBlock() {
        return myChanceToBlock;
    }

    /**
     * Attacks the specified character.
     * If the character is a monster and survives the attack, the monster heals itself.
     * The hero then performs a special attack on the character.
     *
     * @param theOther the character to attack
     */
    @Override
    public void attack(DungeonCharacter theOther) {
        int originalHealth = theOther.getMyHitPoints();
        super.attack(theOther);
        int afterAttackHealth = theOther.getMyHitPoints();
        if(theOther.getMyHitPoints() > 0) {
            if(originalHealth != afterAttackHealth){
                ((Monster) theOther).heal();
            }
            ((Hero) this).special(theOther);
        }
    }

    /**
     * Performs a special attack on the specified character.
     * This method must be implemented by the concrete subclasses of Hero.
     *
     * @param theOther the character to perform the special attack on
     */
    protected abstract void special(final DungeonCharacter theOther);

}