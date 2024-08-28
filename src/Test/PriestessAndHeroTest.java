package Test;

import Model.Priestess;
import Model.DungeonCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriestessAndHeroTest {
    Priestess myPriestess;

    @BeforeEach
    void setUp() {
        myPriestess = Priestess.Test_getMyUniqueInstance();
    }

    @Test
    void getMyChanceForSpecialTest() {
        assertEquals(0.6, myPriestess.getMyChanceForSpecial());
    }

    @Test
    void getMyChanceToBlockTest() {
        assertEquals(0.3, myPriestess.getMyChanceToBlock());
    }


    @Test
    void getMySpecialMaxTest() {
        assertEquals(100, myPriestess.getMySpecialMax());
    }

    @Test
    void getMySpecialMinTest() {
        assertEquals(50, myPriestess.getMySpecialMin());
    }

    @Test
    void specialTest() {
        DungeonCharacter dummyTarget = new DungeonCharacter("Dummy", 100, 1, 1.0, 10, 5, 0, 0) {
        };
        int originalHitPoints = myPriestess.getMyHitPoints();
        myPriestess.special(dummyTarget);
        if (myPriestess.getMySpecialSuccess()) {
            assertTrue(myPriestess.getMyHitPoints() > originalHitPoints);
        } else {
            assertEquals(originalHitPoints,myPriestess.getMyHitPoints());
        }
    }

}