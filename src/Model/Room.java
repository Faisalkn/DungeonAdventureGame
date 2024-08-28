/**
 * Team members: Pham Nguyen, Ali Mohamed and Faisal Nur
 * Class: TCSS360
 * Date: Jun 7, 2024
 * @version 1.0
 */
package Model;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Room class represents a room in our dungeon. It contains information about the room's
 * position, items, and chances for various events such as encountering monsters or finding
 * healing potions, vision potions, or pits.
 */


public class Room {
    private int myHealingPotionAmount;
    private int myPitAmount;
    private boolean myEntrance;
    private boolean myExit;
    private int myX;
    private int myY;
    private int myBorderSize;
    private char myItem;
    private boolean myHasVisionPotion;
    private boolean myHasPillar;
    private double myChanceForMultipleItems;
    private double myChanceForHealingPotion;
    private double myChanceForVisionPotion;
    private double myChanceForPit;
    private double myRandom;
    private double myChanceForMonster;
    private boolean myHasMonster;
    private String myMonsterName;
    private final static ArrayList<String> MONSTER_NAMES = new ArrayList<>(Arrays.asList("Ogre","Gremlin","Skeleton"));

    /**
     * setter for the monster name
     * @param theMonsterName String
     */
    public void setMyMonsterName(String theMonsterName) {
        myMonsterName = theMonsterName;
    }

    /**
     * setter for if the room has monster
     * @param theHasMonster boolean
     */
    public void setMyHasMonster(boolean theHasMonster) {
        myHasMonster = theHasMonster;
    }

    /**
     * getter for the monster name
     * @return String
     */
    public String getMyMonsterName() {
        return myMonsterName;
    }

    /**
     * getter for if room has monster
     * @return boolean
     */
    public boolean getMyHasMonster() {
        return myHasMonster;
    }

    /**
     * getter for the chance for a monster
     * @return double
     */
    public double getMyChanceForMonster() {
        return myChanceForMonster;
    }

    /**
     * constructor that initializes the room size,
     * chance for a monster, and random double
     * @param theBorderSize room size
     */
    public Room(int theBorderSize) {
        setMyChanceForMonster(Math.random());
        setMyRandom(Math.random());
        setMyBorderSize(theBorderSize);
        setUpChances(theBorderSize);
        setUpRoom();

    }

    /**
     * set the chances for room to have different items
     * based on the game difficulty selected
     * @param theBorderSize int
     */

    private void setUpChances(int theBorderSize) {
        myChanceForMultipleItems = 0.10;

        switch (theBorderSize) {
            case 3:
                myChanceForMonster = 0.8;
                myChanceForHealingPotion = 0.65;
                myChanceForVisionPotion = 0.3;
                myChanceForPit = 0.45;
                break;
            case 6:
                myChanceForMonster = 0.85;
                myChanceForHealingPotion = 0.6;
                myChanceForVisionPotion = 0.25;
                myChanceForPit = 0.45;
                break;
            case 8:
                myChanceForMonster = 0.90;
                myChanceForHealingPotion = 0.55;
                myChanceForVisionPotion = 0.3;
                myChanceForPit = 0.4;
                break;
            default:
                // Handle invalid border size if needed
                break;
        }
    }

    /**
     * setter for chance for monster
     * @param theChanceForMonster double
     */

    private void setMyChanceForMonster(double theChanceForMonster) {
        myChanceForMonster = theChanceForMonster;
    }
    /**
     * setter for chance for random
     * @param theRandom double
     */

    private void setMyRandom(double theRandom) {
        myRandom = theRandom;
    }
    /**
     * method that sets up the room and its
     * components
     */

    private void setUpRoom() {
        if (myRandom < myChanceForMultipleItems) {
            // 10% chance for multiple items (healing potion, pit, and vision potion)
            setUpMultipleItems();
        }  else if (myRandom < myChanceForVisionPotion) {
            // 10% chance for vision potion
            setMyVisionPotion(true);
            myItem = 'V';
        } else if (myRandom < myChanceForPit) {
            // 20% chance for pit
            myPitAmount = (int) (Math.random() * 20) + 1;
            myItem = 'X';
        } else if(myRandom < myChanceForHealingPotion){
            // 25% chance for healing potion
            myHealingPotionAmount = (int) (Math.random() * 11) + 10;
            myItem = 'H';
        } else if(myRandom < myChanceForMonster){
            //20 percent chance for empty room
            setMyHasMonster(true);
            setMyMonsterName(MONSTER_NAMES.get((int) (Math.random() * MONSTER_NAMES.size())));
            myItem = 'N';
        }else{
            myItem = 'N';
        }

    }

    /**
     * Override of the toString()
     * method that will print out
     * the room
     * @return String
     */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();


        if(myY == 0){
            sb.append("* * * * * ").append("\n");
        } else{
            sb.append("* * D * * ").append("\n");

        }
        if(myX == 0){
            sb.append("* - ").append(myItem).append(" - D ").append("\n");
        }else if(myX == myBorderSize - 1){
            sb.append("D - ").append(myItem).append(" - * ").append("\n");
        }else{
            sb.append("D - ").append(myItem).append(" - D ").append("\n");
        }
        if(myY== myBorderSize - 1){
            sb.append("* * * * * ").append("\n");
        } else{
            sb.append("* * D * * ").append("\n");
        }
        return sb.toString();

    }

    private void setUpMultipleItems() {
        myHealingPotionAmount = (int) (Math.random() * 11) + 10;
        myPitAmount = (int) (Math.random() * 20) + 10;
        myHasVisionPotion = true;
        setMyItem('M');
    }

    public void setMyHasPillar(final boolean theHasPillar) {
        myHasPillar = theHasPillar;
    }

    public void setMyEntrance(final boolean theEntrance) {
        myEntrance = theEntrance;
        myItem = 'i';
    }

    public void setMyItem(final char theItem) {
        myItem = theItem;
    }

    public void setMyY(final int theY) {
        if(theY < 0){
            throw new IllegalArgumentException("The Y value cannot be negative.");
        }else{
            myY = theY;
        }
    }

    public void setMyX(final int theX) {
        if(theX < 0){
            throw new IllegalArgumentException("The X value cannot be negative.");
        }else{
            myX = theX;
        }
    }

    public void setMyExit(final boolean theExit) {
        myExit = theExit;
        myItem = 'o';
    }

    public void setMyVisionPotion(final boolean theHasVisionPotion) {
        myHasVisionPotion = theHasVisionPotion;
    }
    public void setMyBorderSize(int theBorderSize) {
        if(theBorderSize < 0){
            throw new IllegalArgumentException("The border size cannot be negative.");
        }else {
            myBorderSize = theBorderSize;
        }
    }

    public boolean isMyEntrance() {
        return myEntrance;
    }


    public boolean isMyExit() {
        return myExit;
    }

    public char getMyItem() {
        return myItem;
    }
    public boolean getMyHasPillar(){
        return myHasPillar;
    }

    public int getMyX() {
        return myX;
    }

    public int getMyY() {
        return myY;
    }
    public boolean getMyHasVisionPotion() {
        return myHasVisionPotion;
    }

    public int getMyPitAmount() {
        return myPitAmount;
    }

    public int getMyHealingPotionAmount() {
        return myHealingPotionAmount;
    }
    public int getMyBorderSize() {
        return myBorderSize;
    }

    public String getPartOfTheRoom(Room theRoom, int thePart) {
        String roomString = theRoom.toString();
        String[] lines = roomString.split("\n");

        if (thePart >= 0 && thePart < lines.length) {
            return lines[thePart];
        } else {
            return "Invalid part number";
        }
    }

}

