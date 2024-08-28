package Controller;

import Model.*;

public class DungeonAdventure {

    private boolean myBattleSuccess;
    private int myBattleDamage;

    public DungeonAdventure() {
        setMyBattleSuccess(false);
        setMyBattleDamage(0);
    }

    public void setMyBattleDamage(int theBattleDamage) {
        myBattleDamage = theBattleDamage;
    }

    public void setMyBattleSuccess(boolean theBattleSuccess) {
        myBattleSuccess = theBattleSuccess;
    }

    public boolean getBattleSuccess() {
        return myBattleSuccess;
    }

    public int getMyBattleDamage() {
        return myBattleDamage;
    }

    public void battle(DungeonCharacter theCharacter, DungeonCharacter theOther) {
        int startingHealth = theOther.getMyHitPoints();
        int numberOfAttacks = Math.max(theCharacter.getMyAttackSpeed() / theOther.getMyAttackSpeed(), 1);

        setMyBattleSuccess(false);
        setMyBattleDamage(0);

        while (numberOfAttacks > 0) {
            if (theOther.getMyHitPoints() > 0) {
                theCharacter.attack(theOther);
                numberOfAttacks--;
            } else {
                break;
            }
        }

        if (startingHealth > theOther.getMyHitPoints()) {
            setMyBattleSuccess(true);
            setMyBattleDamage(startingHealth - theOther.getMyHitPoints());
        }
    }
}