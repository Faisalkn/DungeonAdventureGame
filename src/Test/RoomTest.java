package Test;

import Model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room(3); // Adjust the size parameter as needed
    }

    @Test
    void testRoomInitialization() {
        assertNotNull(room);
        assertTrue(room.getMyX() >= 0);
        assertTrue(room.getMyY() >= 0);
    }

    @Test
    void testSetMyX_Valid() {
        room.setMyX(1);
        assertEquals(1, room.getMyX());
    }

    @Test
    void testSetMyX_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> room.setMyX(-1));
    }

    @Test
    void testSetMyY_Valid() {
        room.setMyY(1);
        assertEquals(1, room.getMyY());
    }

    @Test
    void testSetMyY_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> room.setMyY(-1));
    }

    @Test
    void testSetMyEntrance() {
        room.setMyEntrance(true);
        assertTrue(room.isMyEntrance());
        assertEquals('i', room.getMyItem());
    }

    @Test
    void testSetMyExit() {
        room.setMyExit(true);
        assertTrue(room.isMyExit());
        assertEquals('o', room.getMyItem());
    }

    @Test
    void testSetMyItem() {
        room.setMyItem('X');
        assertEquals('X', room.getMyItem());
    }

    @Test
    void testSetMyHasPillar() {
        room.setMyHasPillar(true);
        assertTrue(room.getMyHasPillar());
    }

    @Test
    void testSetMyVisionPotion() {
        room.setMyVisionPotion(true);
        assertTrue(room.getMyHasVisionPotion());
    }

    @Test
    void testSetMyBorderSize_Valid() {
        room.setMyBorderSize(5);
        assertEquals(5, room.getMyBorderSize());
    }

    @Test
    void testSetMyBorderSize_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> room.setMyBorderSize(-1));
    }

    @Test
    void testSetMyMonsterName() {
        room.setMyMonsterName("Dragon");
        assertEquals("Dragon", room.getMyMonsterName());
    }

    @Test
    void testSetMyHasMonster() {
        room.setMyHasMonster(true);
        assertTrue(room.getMyHasMonster());
    }

    @Test
    void testGetPartOfTheRoom() {
        String part = room.getPartOfTheRoom(room, 0);
        assertNotNull(part);
        assertFalse(part.isEmpty());
    }

    @Test
    void testGetPartOfTheRoom_InvalidPart() {
        String part = room.getPartOfTheRoom(room, 10);
        assertEquals("Invalid part number", part);
    }

    @Test
    void testToString() {
        String roomString = room.toString();
        assertNotNull(roomString);
        assertFalse(roomString.isEmpty());
    }
}
