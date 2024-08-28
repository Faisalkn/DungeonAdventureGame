package Test;

import Model.Thief;
import Model.DungeonCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThiefAndHeroTest {
    Thief myThief;

    @BeforeEach
    void setUp() {
        myThief = Model.Thief.Test_getMyUniqueInstance();
    }

    @Test
    void getMyChanceForSpecialTest() {
        assertEquals(0.4, myThief.getMyChanceForSpecial());
    }


    @Test
    void getMyChanceToBlockTest() {
        assertEquals(0.4, myThief.getMyChanceToBlock());
    }


    @Test
    void getMySpecialMaxTest() {
        assertEquals(40, myThief.getMySpecialMax());
    }


    @Test
    void getMySpecialMinValid() {
        assertEquals(20, myThief.getMySpecialMin());
    }


    @Test
    void specialTest() {
        DungeonCharacter dummyTarget = new DungeonCharacter("Dummy", 100, 1, 1.0, 10, 5, 0, 0) {
        };
        int originalHitPoints = dummyTarget.getMyHitPoints();
        myThief.special(dummyTarget);
        if (myThief.getMySpecialSuccess()) {
            assertTrue(originalHitPoints > dummyTarget.getMyHitPoints());
        } else {
            assertEquals(originalHitPoints, dummyTarget.getMyHitPoints());
        }
    }
}