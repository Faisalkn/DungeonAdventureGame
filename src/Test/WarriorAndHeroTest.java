package Test;

import Model.Warrior;
import Model.DungeonCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WarriorAndHeroTest {
    Warrior myWarrior;

    @BeforeEach
    void setUp() {
        myWarrior = Model.Warrior.TEST_getInstance();
    }

    @Test
    void getMyChanceForSpecialTest() {
        assertEquals(0.4, myWarrior.getMyChanceForSpecial());
    }



    @Test
    void getMyChanceToBlockTest() {
        assertEquals(0.2, myWarrior.getMyChanceToBlock());
    }


    @Test
    void getMySpecialMaxTest() {
        assertEquals(175, myWarrior.getMySpecialMax());
    }



    @Test
    void getMySpecialMinTest() {
        assertEquals(75, myWarrior.getMySpecialMin());
    }


    @Test
    void specialTest() {
        DungeonCharacter dummyTarget = new DungeonCharacter("Dummy", 175, 1, 1.0, 10, 5, 0, 0) {

        };
        int originalHitPoints = dummyTarget.getMyHitPoints();
        myWarrior.special(dummyTarget);
        if(myWarrior.getMySpecialSuccess()) {
            assertTrue(originalHitPoints > dummyTarget.getMyHitPoints());
        } else{
            assertEquals(originalHitPoints, dummyTarget.getMyHitPoints());
        }
    }
}