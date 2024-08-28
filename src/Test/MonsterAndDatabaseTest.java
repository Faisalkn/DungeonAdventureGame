package Test;

import Model.Monster;
import Model.MonsterDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MonsterAndDatabaseTest {
    Monster myOgre;
    Monster myGremlin;
    Monster mySkeleton;

    @BeforeEach
    void setUp() throws SQLException {
        MonsterDatabase monsterDatabase = MonsterDatabase.getMy_TEST_Instance();
        myOgre = monsterDatabase.getMonster("Ogre");
        myGremlin = monsterDatabase.getMonster("Gremlin");
        mySkeleton = monsterDatabase.getMonster("Skeleton");
    }

    @Test
    void getMyMaxHealPointsOgre() {
        assertEquals(60, myOgre.getMyMaxHealPoints());
    }

    @Test
    void getMyMaxHealPointsGremlin() {
        assertEquals(40, myGremlin.getMyMaxHealPoints());
    }

    @Test
    void getMyMaxHealPointsSkeleton() {
        assertEquals(50, mySkeleton.getMyMaxHealPoints());
    }

    @Test
    void getMyMinHealPointsOgre() {
        assertEquals(30, myOgre.getMyMinHealPoints());
    }

    @Test
    void getMyMinHealPointsGremlin() {
        assertEquals(20, myGremlin.getMyMinHealPoints());
    }

    @Test
    void getMyMinHealPointsSkeleton() {
        assertEquals(30, mySkeleton.getMyMinHealPoints());
    }

    @Test
    void getMyChanceToHealOgre() {
        assertEquals(0.1, myOgre.getMyChanceToHeal());
    }

    @Test
    void getMyChanceToHealGremlin() {
        assertEquals(0.4, myGremlin.getMyChanceToHeal());
    }

    @Test
    void getMyChanceToHealSkeleton() {
        assertEquals(0.3, mySkeleton.getMyChanceToHeal());
    }


    @Test
    void healOgreTest() {
        int originalHitPoints = myOgre.getMyHitPoints();
        myOgre.heal();
        if(myOgre.getMyHealed()){
            assertTrue(myOgre.getMyHitPoints() > originalHitPoints);
        } else{
            assertEquals(myOgre.getMyHitPoints(),originalHitPoints);
        }

    }
    @Test
    void healSkeletonTest() {
        int originalHitPoints = mySkeleton.getMyHitPoints();
        mySkeleton.heal();
        if(mySkeleton.getMyHealed()){
            assertTrue(mySkeleton.getMyHitPoints() > originalHitPoints);
        } else{
            assertEquals(mySkeleton.getMyHitPoints(),originalHitPoints);
        }

    }
    @Test
    void healGremlinTest() {
        int originalHitPoints = myGremlin.getMyHitPoints();
        myGremlin.heal();
        if(myGremlin.getMyHealed()){
            assertTrue(myGremlin.getMyHitPoints() > originalHitPoints);
        } else{
            assertEquals(myGremlin.getMyHitPoints(),originalHitPoints);
        }

    }

}