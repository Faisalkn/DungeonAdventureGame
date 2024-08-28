/**
 * Team members: Pham Nguyen, Ali Mohamed and Faisal Nur
 * Class: TCSS360
 * Date: Jun 7, 2024
 * @version 1.0
 */
package Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * The Dungeon class represents a dungeon with a maze of rooms.
 */
public class Dungeon {
    private Room[][] maze;
    public int mySize = 3;
    private int myNumberOfEntrances;
    private int myNumberOfExits;
    private int myNumberOfPillars;
    private static Dungeon myUniqueInstance;

    /**
     * Constructs a new Dungeon object.
     */
    public Dungeon() {
    }



    /**
     * Generates a maze of the specified size.
     *
     * @param theSize the size of the maze
     */
    public void generateMaze(int theSize) {
        mySize = theSize;
        maze = new Room[theSize][theSize];
        boolean validMaze = false;
        while (!validMaze) {
            // Initialize maze with empty rooms
            for (int i = 0; i < mySize; i++) {
                for (int j = 0; j < mySize; j++) {
                    maze[i][j] = new Room(mySize); // This will automatically set up the room items
                    maze[i][j].setMyX(j);
                    maze[i][j].setMyY(i);
                }
            }

            // Place the pillars
            List<int[]> pillarPositions = new ArrayList<>();
            while (pillarPositions.size() < 4) {
                int x = (int) (Math.random() * mySize);
                int y = (int) (Math.random() * mySize);
                if (!maze[x][y].getMyHasPillar() && !maze[x][y].isMyEntrance() && !maze[x][y].isMyExit()) {
                    pillarPositions.add(new int[]{x, y});
                }
            }

            // Assign pillars to the selected positions
            char[] pillars = {'A', 'P', 'E', 'I'};
            Collections.shuffle(Arrays.asList(pillars));

            for (int i = 0; i < 4; i++) {
                int[] pos = pillarPositions.get(i);
                maze[pos[0]][pos[1]].setMyItem(pillars[i]);
                maze[pos[0]][pos[1]].setMyHasPillar(true);
            }

            setEntranceAndExit();

            // Check if the maze is traversable
            if (isTraversable()) {
                validMaze = true;
            } else {
                myNumberOfEntrances = 0;
                myNumberOfExits = 0;
                myNumberOfPillars = 0;
            }
        }
    }

    /**
     * Sets the entrance and exit rooms of the maze.
     */
    private void setEntranceAndExit() {
        int entranceX;
        int entranceY = (int) (Math.random() * mySize);
        int exitX;
        int exitY;
        if(entranceY==0 || entranceY== mySize -1) {
            entranceX = (int) (Math.random() * mySize);
            exitY = mySize - 1 - entranceY;
            exitX = (int) (Math.random() * mySize);
        } else{
            int[]indexes = new int []{0, mySize -1};
            int randomIndex = (int) (Math.random() * indexes.length);
            entranceX = indexes[randomIndex];
            exitX = mySize - 1;
            exitX -= entranceX;
            exitY =  (int) (Math.random() * mySize);
        }

        if(!maze[entranceY][entranceX].getMyHasMonster() && !maze[exitY][exitX].getMyHasMonster()) {
            // Place entrance and exit
            maze[entranceY][entranceX].setMyEntrance(true);
            maze[exitY][exitX].setMyExit(true);
        }else{
            setEntranceAndExit();
        }
    }



    /**
     * Performs a depth-first search to check if the maze is traversable.
     *
     * @param x       the x-coordinate of the current room
     * @param y       the y-coordinate of the current room
     * @param visited a boolean array to keep track of visited rooms
     * @return true if the maze is traversable, false otherwise
     */
    public boolean dfs(int x, int y, boolean[][] visited) {
        if (x < 0 || x >= mySize || y < 0 || y >= mySize || visited[x][y])
            return false;
        visited[x][y] = true;
        if (maze[x][y].isMyExit()) {
            myNumberOfExits++;
        } else if (maze[x][y].isMyEntrance()) {
            myNumberOfEntrances++;
        } else if(maze[x][y].getMyHasPillar()){
            myNumberOfPillars++;
        }
        if (myNumberOfEntrances > 1 || myNumberOfExits > 1 || myNumberOfPillars > 4 ||
                maze[x][y].isMyExit() && maze[x][y].isMyEntrance()) {
            return false; // More than one entrance or exit found, maze is invalid
        }
        if (maze[x][y].isMyExit())
            return true;
        boolean up = dfs(x - 1, y, visited);
        boolean down = dfs(x + 1, y, visited);
        boolean left = dfs(x, y - 1, visited);
        boolean right = dfs(x, y + 1, visited);
        return up || down || left || right;
    }

    /**
     * Checks if the maze is traversable.
     *
     * @return true if the maze is traversable, false otherwise
     */

    public boolean isTraversable() {
        boolean[][] visited = new boolean[mySize][mySize];
        return dfs(0, 0, visited) && myNumberOfPillars==4;
    }


    /**
     * Writes the maze to a file at the specified file path.
     *
     * @param theFilePath the file path to write the maze to
     */
    public void writeMazeToFile(String theFilePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Maps/" + theFilePath))) {
            // Write the top parts of the rooms in the first row
            StringBuilder topRow = new StringBuilder();
            StringBuilder bottomRow = new StringBuilder();
            StringBuilder middleRow = new StringBuilder();
            StringBuilder fillerRow = new StringBuilder();
            StringBuilder bottomFillerRow = new StringBuilder();
            String bottomFiller = "* - - - * ";
            for (int i = 0; i < mySize; i++) {
                bottomFillerRow.append(bottomFiller);
            }


            for (int row = 0; row < maze.length; row++) {
                for (int col = 0; col < maze[row].length; col++) {
                    String filler = getFillerString(row, col);
                    fillerRow.append(filler);
                    topRow.append(maze[row][col].getPartOfTheRoom(maze[row][col], 0));
                    middleRow.append(maze[row][col].getPartOfTheRoom(maze[row][col], 1));
                    bottomRow.append(maze[row][col].getPartOfTheRoom(maze[row][col], 2));
                }
                writer.write(topRow.toString() + "\n");
                writer.write(fillerRow.toString() + "\n");
                writer.write(middleRow.toString() + "\n");
                writer.write(bottomFillerRow.toString() + "\n");
                writer.write(bottomRow.toString() + "\n");
                topRow.setLength(0);
                bottomRow.setLength(0);
                middleRow.setLength(0);
                fillerRow.setLength(0);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Returns the filler string for a room based on its monster type.
     *
     * @param row the row index of the room
     * @param col the column index of the room
     * @return the filler string for the room
     */

    private String getFillerString(int row, int col) {
        String filler = "* - - - * ";
        if(maze[row][col].getMyHasMonster() && maze[row][col].getMyMonsterName().equals("Ogre")){
            filler = "* O - - * ";
        } else if(maze[row][col].getMyHasMonster() && maze[row][col].getMyMonsterName().equals("Gremlin")){
            filler = "* G - - * ";
        }else if((maze[row][col].getMyHasMonster() && maze[row][col].getMyMonsterName().equals("Skeleton"))){
            filler = "* S - - * ";
        }
        return filler;
    }


    /**
     * Returns a string representation of the dungeon.
     *
     * @return a string representation of the dungeon
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mySize; i++) {
            sb.append("+---".repeat(mySize));
            sb.append("+\n");
            for (int j = 0; j < mySize; j++) {
                sb.append("| ");
                sb.append(maze[i][j].getMyItem()).append(" ");
            }
            sb.append("|\n");
        }
        sb.append("+---".repeat(mySize));
        sb.append("+\n");
        return sb.toString();
    }
    /**
     * Returns the unique instance of the Dungeon class.
     *
     * @return the unique instance of the Dungeon class
     */
    public static Dungeon getInstance() {
        if (myUniqueInstance == null) {
            myUniqueInstance = new Dungeon();
        }
        return myUniqueInstance;
    }

    /**
     * Returns the maze of rooms.
     *
     * @return the maze of rooms
     */
    public Room[][] getMaze() {
        return maze;
    }

    /**
     * Returns a new instance of the Dungeon class for testing purposes.
     *
     * @return a new instance of the Dungeon class for testing purposes
     */

    public static Dungeon get_TEST_instance(){
        return new Dungeon();
    }

    /**
     * Returns the size of the maze.
     *
     * @return the size of the maze
     */
    public int getMySize() {
        return mySize;
    }

    /**
     * Sets the maze of rooms.
     *
     * @param maze the maze of rooms to set
     */
    public void setMaze(Room[][] maze) {
        this.maze = maze;
    }
}