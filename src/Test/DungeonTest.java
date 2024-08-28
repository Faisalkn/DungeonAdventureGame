package Test;
import Model.Dungeon;
import Model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DungeonTest {

    private Dungeon dungeon;

    @BeforeEach
    void setUp() {
        dungeon = Dungeon.get_TEST_instance(); // Ensure a new instance for each test
    }

    @Test
    void testSingletonInstance() {
        Dungeon instance1 = Dungeon.getInstance();
        Dungeon instance2 = Dungeon.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    void testGenerateMaze() {
        dungeon.generateMaze(3);
        Room[][] maze = dungeon.getMaze();
        assertNotNull(maze);
        assertEquals(3, maze.length);
        assertEquals(3, maze[0].length);
    }



    @Test
    void testSetEntranceAndExit() {
        dungeon.generateMaze(3);
        int entranceCount = 0;
        int exitCount = 0;
        Room[][] maze = dungeon.getMaze();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j].isMyEntrance()) entranceCount++;
                if (maze[i][j].isMyExit()) exitCount++;
            }
        }
        assertEquals(1, entranceCount);
        assertEquals(1, exitCount);
    }

    @Test
    void testWriteMazeToFile() {
        dungeon.generateMaze(3);
        assertDoesNotThrow(() -> dungeon.writeMazeToFile("test_maze.txt"));
    }

    @Test
    void testToString() {
        dungeon.generateMaze(3);
        String mazeString = dungeon.toString();
        assertNotNull(mazeString);
        assertFalse(mazeString.isEmpty());
    }


    @Test
    void testDFS_NotTraversable() {
        // Manipulate the dungeon to make it not traversable
        Room[][] maze = new Room[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                maze[i][j] = new Room(3);
            }
        }
        maze[1][1].setMyHasPillar(true); // Block the path
        dungeon.generateMaze(3);
        boolean[][] visited = new boolean[dungeon.getMySize()][dungeon.getMySize()];
        assertFalse(dungeon.dfs(0, 0, visited));
    }

    @Test
    void testGetMaze() {
        dungeon.generateMaze(3);
        assertNotNull(dungeon.getMaze());
    }

    @Test
    void testGetMySize() {
        dungeon.generateMaze(3);
        assertEquals(3, dungeon.getMySize());
    }

    @Test
    void testGet_TEST_instance() {
        assertNotNull(Dungeon.get_TEST_instance());
    }
}