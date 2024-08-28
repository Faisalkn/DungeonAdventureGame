/**
 * Team members: Pham Nguyen, Ali Mohamed and Faisal Nur
 * Class: TCSS360
 * Date: Jun 7, 2024
 * @version 1.0
 */
package Model;


public class Monster extends DungeonCharacter{

    private double myChanceToHeal;
    private int myMinHealPoints;
    private int myMaxHealPoints;
    private double myRandomToHeal;
    private boolean myHealed;
    private int myHealedAmount;



    protected Monster(final String theName, final int theHitPoints, final int theAttackSpeed, final double theChanceToHit,
                      final int theMaxDamage, final int theMinDamage, final double theChanceToHeal, final int theMinHealPoints,
                      final int theMaxHealPoints, final int theX, final int theY) {
        super(theName,theHitPoints,theAttackSpeed,theChanceToHit,theMaxDamage,theMinDamage,theX, theY);
        setMyChanceToHeal(theChanceToHeal);
        setMyMinHealPoints(theMinHealPoints);
        setMyMaxHealPoints(theMaxHealPoints);
        setMyRandomToHeal(Math.random());
        setMyHealed(false);
        setMyHealedAmount(0);




    }


    void setMyMaxHealPoints(final int theMaxHealPoints){
        if(theMaxHealPoints < 0){
            throw new IllegalArgumentException("The max heal points cannot be less than zero.");
        } else{
            myMaxHealPoints = theMaxHealPoints;
        }
    }

    public void setMyHealed(boolean theHealed) {
        myHealed = theHealed;
    }
    private void setMyHealedAmount(int theHealedAmount) {
        myHealedAmount = theHealedAmount;
    }


    void setMyMinHealPoints(final int theMinHealPoints){
        if(theMinHealPoints < 0){
            throw new IllegalArgumentException("The minimum heal points cannot be less than zero.");
        } else{
            myMinHealPoints = theMinHealPoints;
        }
    }

    void setMyChanceToHeal(final double theChanceToHeal){
        if(theChanceToHeal < 0.0 || theChanceToHeal > 1.0){
            throw new IllegalArgumentException("The chance to heal must be between 0.0 and 1.0");
        }else{
            myChanceToHeal = theChanceToHeal;
        }
    }
    void setMyRandomToHeal(final double theRandomToHeal) {
        if(theRandomToHeal < 0.0 || theRandomToHeal > 1.0){
            throw new IllegalArgumentException("The random to heal must be between 0.0 and 1.0");
        } else{
            myRandomToHeal = theRandomToHeal;
        }
    }

    public double getMyChanceToHeal() {
        return myChanceToHeal;
    }

    public int getMyMaxHealPoints() {
        return myMaxHealPoints;
    }

    public boolean getMyHealed() {
        return myHealed;
    }

    public int getMyHealedAmount() {
        return myHealedAmount;
    }

    public int getMyMinHealPoints() {
        return myMinHealPoints;
    }


    public void heal(){
        if(myRandomToHeal <= myChanceToHeal){
            int healAmount =  (int) (Math.random() * (myMaxHealPoints - myMinHealPoints + 1)) + myMinHealPoints;
            setMyHitPoints(getMyHitPoints()+healAmount);
            setMyHealedAmount(healAmount);
            setMyHealed(true);

        }
    }

    @Override
    public void attack(DungeonCharacter theOther) {
        Hero myHero = ((Hero) theOther);
        myHero.block();
        if(!myHero.getMyBlockedAttack()){
            super.attack(theOther);
        }
    }

}
