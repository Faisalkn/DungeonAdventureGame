package Test;

import Model.DungeonCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DungeonCharacterTest {
    private DungeonCharacter myCharacter;
    private DungeonCharacter myDummyTarget;
    @BeforeEach
    void setUp() {
        myCharacter = new DungeonCharacter("TestCharacter",200,10,0.6,
                100,90,10,10) {
            @Override
            public void setMyChanceToHit(double theChanceToHit) {
                super.setMyChanceToHit(theChanceToHit);
            }
        };
        myDummyTarget = new DungeonCharacter("TestDummy",100,2,0.3,100,30,10,10) {
            @Override
            protected void setMyChanceToHit(double theChanceToHit) {
                super.setMyChanceToHit(theChanceToHit);
            }
        };

    }

    @Test
    void getMyChanceToHitTest() {
        assertEquals(0.6, myCharacter.getMyChanceToHit());
    }





    @Test
    void getMyMinDamageTest() {
        assertEquals(90, myCharacter.getMyMinDamage());
    }


    @Test
    void getMyMaxDamageTest() {
        assertEquals(100, myCharacter.getMyMaxDamage());
    }


    @Test
    void getMyAttackSpeedTest() {
        assertEquals(10,myCharacter.getMyAttackSpeed());
    }

    @Test
    void getMyHitPointsTest() {
        assertEquals(200,myCharacter.getMyHitPoints());
    }

    @Test
    void getMyNameTest() {
        assertEquals("TestCharacter",myCharacter.getMyName());
    }

    @Test
    void getMyXTest() {
        assertEquals(10, myCharacter.getMyX());
    }

    @Test
    void getMyYTest() {
        assertEquals(10,myCharacter.getMyY());
    }

    @Test
    void attackTest(){



    }

}