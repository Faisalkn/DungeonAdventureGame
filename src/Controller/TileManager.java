package Controller;

import Model.Hero;
import Model.Priestess;
import Model.Thief;
import Model.Warrior;
import View.DungeonPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

public class TileManager implements Serializable{


    private final DungeonAdventure myDungeonAdventurer;
    private boolean myPitFall = false;

    private boolean myPickedVisionPotion;
    private boolean myPickedHealthPotion;

    private Random myRandom = new Random();
    private int myPitDmg;
    private int myEntranceRow;
    private int myEntranceCol;

    private boolean myIsGameOver = false;

    private boolean myIsPlayerWin = false;

    private String battleResultMessage;
    private boolean battleCalculated = false;

    private Rectangle myCloseBattleWindow;
    private int[] myPillarACoordinates;
    private int[] myPillarPCoordinates;
    private int[] myPillarICoordinates;
    private int[] myPillarECoordinates;
    private int[] myExitCoordinates;

    private ArrayList<Integer> myVisionPotionCoordinatesList;
    private ArrayList<Integer> myHealthPotionCoordinatesList;
    private ArrayList<Integer> myMultipleCoordinates = new ArrayList<>();

    private  Map<String, Integer> myItemCollisionFrequency;

    private  ArrayList<Integer> myOgreCoordinatesList = new ArrayList<>();
    private  ArrayList<Integer> myGremlinCoordinatesList = new ArrayList<>();
    private  ArrayList<Integer> mySkeletonCoordinatesList = new ArrayList<>();
    private ArrayList<Integer> myPitCoordinates = new ArrayList<>();






    private final String myHealthPotion = "H";
    private final String myVisionPotion = "V";
    private final String myPillarP = "P";
    private final String myPillarI = "I";
    private final String myPillarE = "E";
    private final String myPillarA = "A";
    private final String myEntrance = "i";
    private final String myExit = "o";
    private final String myMultiple = "M";
    private final String myPit = "X";
    private final String myEmpty = "N";
    private final String myOgre = "O";
    private final String myGremlin = "G";
    private final String mySkeleton = "S";
    private  int myRow;
    private  int myCol;
    //    private final GameUI myGameUi;
    private final Map<String, Tile> myTile;

    private Monsters monsterEncountered;


    private DungeonPanel myDungeonPanel;

    private String[][] mapTileNums;


    public TileManager(final DungeonPanel theDungeonPanel) {
        myDungeonPanel = theDungeonPanel;
        myTile = new HashMap<>();
        myVisionPotionCoordinatesList = new ArrayList<>();
        myHealthPotionCoordinatesList = new ArrayList<>();
        myPitCoordinates = new ArrayList<>();
        myItemCollisionFrequency = new HashMap<>();
        myDungeonAdventurer = new DungeonAdventure();
        getTileImage();
    }


    public void getTileImage(){
        try{
            Tile rock = new Tile();
            rock.setMySolid(true);
            rock.setMyTileImage((ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Tiles/wall.png")))));

            Tile earth = new Tile();
            earth.setMyTileImage((ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Tiles/earth.png")))));

            Tile door = new Tile();
            door.setMyTileImage((ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Tiles/Door.png")))));

            myTile.put("*", rock);
            myTile.put("-", earth);
            myTile.put("D", door);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void drawTiles(final Graphics2D theGraphics){
        int row = 0;
        int col = 0;


        while (row < this.myRow && col < this.myCol) {
            String title = mapTileNums[row][col];

            switch (title) {
                case myHealthPotion, myVisionPotion, myEntrance, myPillarP, myPillarI,
                     myPillarE, myExit, myMultiple, myPit, myEmpty,
                     myPillarA, myOgre, myGremlin, mySkeleton -> title = "-";
            }

            int bound = myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyTileSize();

            int x = col * myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyTileSize();
            int y = row * myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyTileSize();

            int screenX = x - myDungeonPanel.getMyGameUi().getMyCharacter().getMyX() + myDungeonPanel.getMyGameUi().getMyCharacter().getMyScreenX();
            int screenY = y - myDungeonPanel.getMyGameUi().getMyCharacter().getMyY() + myDungeonPanel.getMyGameUi().getMyCharacter().getMyScreenY();

            if(x + bound > myDungeonPanel.getMyGameUi().getMyCharacter().getMyX() - myDungeonPanel.getMyGameUi().getMyCharacter().getMyScreenX() &&
                    x  - bound < myDungeonPanel.getMyGameUi().getMyCharacter().getMyX() + myDungeonPanel.getMyGameUi().getMyCharacter().getMyScreenX() &&
                    y  + bound> myDungeonPanel.getMyGameUi().getMyCharacter().getMyY() - myDungeonPanel.getMyGameUi().getMyCharacter().getMyScreenY() &&
                    y  - bound< myDungeonPanel.getMyGameUi().getMyCharacter().getMyY() + myDungeonPanel.getMyGameUi().getMyCharacter().getMyScreenY()){
                theGraphics.drawImage(myTile.get(title).getMyTileImage(), screenX, screenY, myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyTileSize() - 1, myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyTileSize() - 1, null);
            }
            row++;
            if(row == this.myRow){
                row = 0;
                col++;
            }
        }
    }


    public void generateDungeon(){
        String path = "Maze.txt";

        //myRow = myDungeonPanel.getMyGameUi().getMyMazeSize() * 5;//myCol = myDungeonPanel.getMyGameUi().getMyMazeSize() * 5;
        myRow *= 5;
        myCol *= 5;
        mapTileNums = new String[myRow][myCol];
        myDungeonPanel.getMyGameUi().getMyDungeon().writeMazeToFile(path);
        loadMap();
    }
    public void loadMap(){
        try (BufferedReader reader = new BufferedReader(new FileReader(("DungeonAdventure/src/Maps/Maze.txt")))){
            for(int row = 0; row < this.myRow; row++) { // 20
                String line = reader.readLine();
                for(int col = 0; col < this.myCol; col++) { // 20
                    String[] numbers = line.split(" "); // * * A *
                    String s = numbers[col];
                    if(s.equals("i")){
                        myEntranceRow = row;
                        myEntranceCol = col;
                    }
                    if(s.equals("M")){
                        myMultipleCoordinates.add(row);
                        myMultipleCoordinates.add(col);
                    }
                    if(s.equals("A")){
                        myPillarACoordinates = new int[2];
                        myPillarACoordinates[0] = row;
                        myPillarACoordinates[1] = col;
                    }
                    if(s.equals("I")){
                        myPillarICoordinates = new int[2];
                        myPillarICoordinates[0] = row;
                        myPillarICoordinates[1] = col;
                    }

                    if(s.equals("E")){
                        myPillarECoordinates = new int[2];
                        myPillarECoordinates[0] = row;
                        myPillarECoordinates[1] = col;
                    }

                    if(s.equals("H")){
                        myHealthPotionCoordinatesList.add(row);
                        myHealthPotionCoordinatesList.add(col);
                    }
                    if(s.equals("V")){
                        myVisionPotionCoordinatesList.add(row);
                        myVisionPotionCoordinatesList.add(col);
                    }
                    if(s.equals("O")){
                        myOgreCoordinatesList.add(row);
                        myOgreCoordinatesList.add(col);
                    }
                    if(s.equals("G")){
                        myGremlinCoordinatesList.add(row);
                        myGremlinCoordinatesList.add(col);
                    }
                    if(s.equals("S")){
                        mySkeletonCoordinatesList.add(row);
                        mySkeletonCoordinatesList.add(col);
                    }
                    if(s.equals("P")){
                        myPillarPCoordinates = new int[2];
                        myPillarPCoordinates[0] = row;
                        myPillarPCoordinates[1] = col;
                    }
                    if(s.equals("X")){
                        myPitCoordinates.add(row);
                        myPitCoordinates.add(col);
                    }
                    if(s.equals("o")){
                        myExitCoordinates = new int[2];
                        myExitCoordinates[0] = row;
                        myExitCoordinates[1] = col;
                    }
                    mapTileNums[row][col] = s;
                }
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        myDungeonPanel.getMyGameUi().getMyDungeonPanel().setObjects();
        myDungeonPanel.getMyGameUi().getMyCharacter().initHeroes();
    }

    public boolean isTileCollision(final Rectangle theHitBox) {
        int tileSize = myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyTileSize();
        int x1 = theHitBox.x / tileSize;
        int y1 = theHitBox.y / tileSize;
        int x2 = (theHitBox.x + theHitBox.width) / tileSize;
        int y2 = (theHitBox.y + theHitBox.height) / tileSize;
        boolean isTileSolid = false;
        for (int row = y1; row <= y2; row++) {
            for (int col = x1; col <= x2; col++) {
                String tile = mapTileNums[row][col];
                if (myTile.get(tile) != null && myTile.get(tile).isSolid()) {
                    isTileSolid = true;
                }
            }
        }

        List<SuperItems> itemsCopy = new ArrayList<>(myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyItems());
        for (SuperItems item : itemsCopy) {
            if (item.solidArea.intersects(theHitBox)) {

                if (item.getMyName().equals("o")) {
                    handleSpecialItemCollision(item);
                } else {
                    handleDefaultItemCollision(item);
                }
                if (item.getMyName().equals("X") || item.getMyName().equals("M")) {
                    myPitDmg = myRandom.nextInt(1, 21); // Move this line before damage calculation
                    int oldHitPoint = myDungeonPanel.getMyCharacter().getCharacterType().getMyHitPoints();
                    int newHitPoint = oldHitPoint - myPitDmg;
                    if (oldHitPoint <= 0 || newHitPoint <= 0) {
                        myDungeonPanel.getMyCharacter().setMyPlayerSpeed(0);
                        myDungeonPanel.getMyGameSounds().playClickSound(4);
                        myIsGameOver = true;
                    }
                    myPitFall = true;
                    myDungeonPanel.getMyCharacter().getCharacterType().setMyHitPoints(newHitPoint);
                    myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyItems().remove(item);
                    myDungeonPanel.getMyGameSounds().playClickSound(5);

                }



            }
        }

        List<Monsters> monsterCopy = new ArrayList<>(myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyMonsters());
        for (Monsters monster : monsterCopy) {
            if (monster.solidArea.intersects(theHitBox)) {
                if (myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyDefaultMonsters().containsKey(monster.getMonsterType())) {
                    myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyDefaultMonsters().get(monster.getMonsterType()).setCollision(true);
                    monsterEncountered = monster;

                    if (myDungeonPanel.getMyGameUi().getMyCharacter().getCharacterType().getMyHitPoints() > 0 && monsterEncountered.getMyMonster().getMyHitPoints() <= 0) {
                        myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyMonsters().remove(monster);
                    } else if (myDungeonPanel.getMyCharacter().getCharacterType().getMyHitPoints() <= 0) {
                        myIsGameOver = true;
                        myDungeonPanel.getMyGameSounds().playClickSound(4);
                    }
                }
            }
        }

        return isTileSolid;
    }



    private void handleSpecialItemCollision(SuperItems item) {
        if (myItemCollisionFrequency.containsKey(myPillarA) && myItemCollisionFrequency.containsKey(myPillarE) &&
                myItemCollisionFrequency.containsKey(myPillarI) && myItemCollisionFrequency.containsKey(myPillarP)) {
            if (myItemCollisionFrequency.get(myPillarA) == 1 && myItemCollisionFrequency.get(myPillarE) == 1 &&
                    myItemCollisionFrequency.get(myPillarI) == 1 && myItemCollisionFrequency.get(myPillarP) == 1) {
                myIsPlayerWin = true;
                myDungeonPanel.getMyCharacter().setMyPlayerSpeed(0);
                myDungeonPanel.getMyGameSounds().playClickSound(3);
                myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyItems().remove(item);
                myDungeonPanel.getMyGameSounds().playClickSound(5);
            }
        }
    }

    private void handleDefaultItemCollision(SuperItems item) {
        if (item.getMyName().equals("M")) {
            myItemCollisionFrequency.put("H", myItemCollisionFrequency.getOrDefault("H", 0) + 1);
            myItemCollisionFrequency.put("V", myItemCollisionFrequency.getOrDefault("V", 0) + 1);
            myPickedHealthPotion = true;
            myPickedVisionPotion = true;
        } else {
            if (myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyDefaultItems().containsKey(item.getMyName())) {
                if(item.getMyName().equals("V")){
                    myPickedVisionPotion = true;
                }
                if(item.getMyName().equals("H")){
                    myPickedHealthPotion = true;
                }
                myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyDefaultItems().get(item.getMyName()).setMyCollisoin(true);
                myItemCollisionFrequency.put(item.getMyName(), myItemCollisionFrequency.getOrDefault(item.getMyName(), 0) + 1);
            }
        }
        myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyItems().remove(item);
        myDungeonPanel.getMyGameSounds().playClickSound(5);

    }




    private void drawBattleWindow(final Graphics2D theGraphics) {
        int x = myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyWidth() / 2;
        int y = myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyHeight() / 2 - 200;

        myDungeonPanel.getMyGameUi().drawFrame(x, y, 450, 600, theGraphics);
        myCloseBattleWindow = new Rectangle(x + 20, y + 20, 20, 20);
        theGraphics.setColor(new Color(255, 255, 255));
        theGraphics.setStroke(new BasicStroke(5));

        myDungeonPanel.getMyGameUi().updateCheckboxSelection(theGraphics, myCloseBattleWindow);
        theGraphics.setColor(new Color(0, 0, 0, 0));
        theGraphics.fill(myCloseBattleWindow);
        int textX = x + 60;
        int textY = y + 40;

        theGraphics.setColor(Color.WHITE);
        theGraphics.drawString("You encountered an " + monsterEncountered.getMyMonster().getMyName(), textX, textY);
    }
    public void drawMonsterEncounter(final Graphics2D theGraphics) {
        if (monsterEncountered != null && !myDungeonPanel.getMyGameUi().getMyGameControls().isMyCloseBattleWindow()) {
            // Use an off-screen image for double buffering
            BufferedImage offScreen = new BufferedImage(myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyWidth(),
                    myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D offScreenG2 = offScreen.createGraphics();

            // Draw the battle window on the off-screen image
            drawBattleWindow(offScreenG2);

            // Calculate the battle result only once
            if (!battleCalculated) {
                calculateBattleResult();
                battleCalculated = true;
            }

            // Display the stored battle result on the off-screen image
            drawBattleResult(offScreenG2);

            // Copy the off-screen image to the actual graphics context
            theGraphics.drawImage(offScreen, 0, 0, null);

            myDungeonPanel.getMyGameUi().getMyCharacter().setMyPlayerIsMoving(false); // Set the character to frozen state
            myDungeonPanel.getMyGameUi().getMyCharacter().setMyPlayerSpeed(0);
            offScreenG2.dispose();
        } else {
            monsterEncountered = null;
            if (!myIsGameOver) {
                myDungeonPanel.getMyGameUi().getMyCharacter().setMyPlayerSpeed(4);
            }
            battleCalculated = false; // Reset the flag for the next encounter
        }
    }

    private void calculateBattleResult() {
        StringBuilder resultMessage = new StringBuilder();
        int beforeBattle, afterBattle;

        while (myDungeonPanel.getMyCharacter().getCharacterType().getMyHitPoints() > 0 && monsterEncountered.getMyMonster().getMyHitPoints() > 0) {
            if (Math.random() < 0.5) {
                // Character attacks
                beforeBattle = monsterEncountered.getMyMonster().getMyHitPoints();
                myDungeonAdventurer.battle(myDungeonPanel.getMyCharacter().getCharacterType(), monsterEncountered.getMyMonster());
                afterBattle = monsterEncountered.getMyMonster().getMyHitPoints();

                boolean characterAttackSuccess = myDungeonAdventurer.getBattleSuccess();
                if (characterAttackSuccess) {
                    resultMessage.append("You successfully battled with ")
                            .append(monsterEncountered.getMonsterType())
                            .append(" and dealt ")
                            .append(myDungeonAdventurer.getMyBattleDamage()-((Hero)myDungeonPanel.getMyCharacter().getCharacterType()).getMySpecialAmount())
                            .append(" damage.")
                            .append("\n");
                    myDungeonAdventurer.setMyBattleSuccess(false);
                    myDungeonAdventurer.setMyBattleDamage(0);

                    if (myDungeonPanel.getMyCharacter().getCharacterType() instanceof Warrior warrior) {
                        if (warrior.getMySpecialSuccess()) {
                            resultMessage.append("You successfully landed a Landing Blow for ")
                                    .append(warrior.getMySpecialAmount())
                                    .append(" damage!")
                                    .append("\n");
                            warrior.setMySpecialSuccess(false);
                        }
                    } else if (myDungeonPanel.getMyCharacter().getCharacterType() instanceof Priestess priestess) {
                        if (priestess.getMySpecialSuccess()) {
                            resultMessage.append("You successfully used special healing for ")
                                    .append(priestess.getMySpecialAmount())
                                    .append(" hit points!")
                                    .append("\n");
                            priestess.setMySpecialSuccess(false);
                        }
                    } else if (myDungeonPanel.getMyCharacter().getCharacterType() instanceof Thief thief) {
                        if (thief.getMySpecialSuccess()) {
                            resultMessage.append("You successfully landed a surprise attack for ")
                                    .append(thief.getMySpecialAmount())
                                    .append(" damage!")
                                    .append("\n");
                            thief.setMySpecialSuccess(false);
                        }
                    }
                } else {
                    resultMessage.append("You unsuccessfully battled with ")
                            .append(monsterEncountered.getMonsterType())
                            .append(".")
                            .append("\n");
                }

                // Check if monster heals

                if (monsterEncountered.getMyMonster().getMyHealed()) {
                    resultMessage.append(monsterEncountered.getMonsterType())
                            .append(" healed for ")
                            .append(monsterEncountered.getMyMonster().getMyHealedAmount())
                            .append(" hit points.")
                            .append("\n");
                }

            } else {
                // Monster attacks
                beforeBattle = myDungeonPanel.getMyCharacter().getCharacterType().getMyHitPoints();
                myDungeonAdventurer.battle(monsterEncountered.getMyMonster(), myDungeonPanel.getMyCharacter().getCharacterType());
                afterBattle = myDungeonPanel.getMyCharacter().getCharacterType().getMyHitPoints();

                boolean monsterAttackSuccess = myDungeonAdventurer.getBattleSuccess();
                if (monsterAttackSuccess) {
                    resultMessage.append(monsterEncountered.getMonsterType())
                            .append(" successfully battled with ")
                            .append(myDungeonPanel.getMyCharacter().getCharacterType().getMyName())
                            .append(" and dealt ")
                            .append(myDungeonAdventurer.getMyBattleDamage())
                            .append(" damage.")
                            .append("\n");
                    myDungeonAdventurer.setMyBattleSuccess(false);
                    myDungeonAdventurer.setMyBattleDamage(0);
                } else if (((Hero) myDungeonPanel.getMyCharacter().getCharacterType()).getMyBlockedAttack()) {
                    resultMessage.append("You blocked an attack from the ")
                            .append(monsterEncountered.getMonsterType())
                            .append("\n");
                    ((Hero) myDungeonPanel.getMyCharacter().getCharacterType()).setMyBlockedAttack(false);
                } else {
                    resultMessage.append(monsterEncountered.getMonsterType())
                            .append(" unsuccessfully battled with ")
                            .append(myDungeonPanel.getMyCharacter().getCharacterType().getMyName())
                            .append(".")
                            .append("\n");
                }
            }
        }

        if (myDungeonPanel.getMyCharacter().getCharacterType().getMyHitPoints() <= 0) {
            resultMessage.append("You have been defeated by ").append(monsterEncountered.getMonsterType()).append(".\n");
        } else if (monsterEncountered.getMyMonster().getMyHitPoints() <= 0) {
            resultMessage.append("You have defeated ").append(monsterEncountered.getMonsterType()).append(".\n");
            resultMessage.append("Your current hit point is ").append(myDungeonPanel.getMyCharacter().getCharacterType().getMyHitPoints());
        }

        battleResultMessage = resultMessage.toString();
    }


    private void drawBattleResult(Graphics theGraphics) {
        int y = myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyHeight() / 2 - 200;
        int textY = y + 70;
        int x = myDungeonPanel.getMyGameUi().getMyDungeonPanel().getMyWidth() / 2;
        int textX =  x + 60;
        String[] messages = battleResultMessage.split("\n");
        for (String message : messages) {
            theGraphics.drawString(message, textX, textY);
            textY += 20; // Increase Y coordinate for the next message
        }
    }




    public int[] getMyPillarACoordinates() {
        return myPillarACoordinates;
    }

    public int[] getMyPillarPCoordinates() {
        return myPillarPCoordinates;
    }

    public int[] getMyPillarICoordinates() {
        return myPillarICoordinates;
    }

    public int[] getMyPillarECoordinates() {
        return myPillarECoordinates;
    }




    public ArrayList<Integer> getMyVisionPotionCoordinatesList() {
        return myVisionPotionCoordinatesList;
    }

    public ArrayList<Integer> getMyHealthPotionCoordinatesList() {
        return myHealthPotionCoordinatesList;
    }

    public Map<String, Integer> getMyItemCollisionFrequency() {
        return myItemCollisionFrequency;
    }

    public ArrayList<Integer> getMyOgreCoordinatesList() {
        return myOgreCoordinatesList;
    }

    public ArrayList<Integer> getMyGremlinCoordinatesList() {
        return myGremlinCoordinatesList;
    }

    public ArrayList<Integer> getMySkeletonCoordinatesList() {
        return mySkeletonCoordinatesList;
    }

    public String[][] getMapTileNums() {
        return mapTileNums;
    }

    public int[] getMyExitCoordinates() {
        return myExitCoordinates;
    }

    public boolean isMyIsPlayerWin() {
        return myIsPlayerWin;
    }

    public Rectangle getMyCloseBattleWindow() {
        return myCloseBattleWindow;
    }

    public int getMyEntranceRow() {
        return myEntranceRow;
    }

    public boolean isMyIsGameOver() {
        return myIsGameOver;
    }

    public int getMyEntranceCol() {
        return myEntranceCol;
    }

    public int getMyRow() {
        return myRow;
    }

    public void setMyRow(int myRow) {
        this.myRow = myRow;
    }

    public int getMyCol() {
        return myCol;
    }

    public void setMyCol(int myCol) {
        this.myCol = myCol;
    }

    public void setMapTileNums(String[][] mapTileNums) {
        this.mapTileNums = mapTileNums;
    }

    public void setMyPillarACoordinates(int[] myPillarACoordinates) {
        this.myPillarACoordinates = myPillarACoordinates;
    }

    public void setMyPillarPCoordinates(int[] myPillarPCoordinates) {
        this.myPillarPCoordinates = myPillarPCoordinates;
    }

    public void setMyPillarICoordinates(int[] myPillarICoordinates) {
        this.myPillarICoordinates = myPillarICoordinates;
    }

    public void setMyPillarECoordinates(int[] myPillarECoordinates) {
        this.myPillarECoordinates = myPillarECoordinates;
    }

    public void setMyExitCoordinates(int[] myExitCoordinates) {
        this.myExitCoordinates = myExitCoordinates;
    }

    public ArrayList<Integer> getMyMultipleCoordinates() {
        return myMultipleCoordinates;
    }

    public void setMyMultipleCoordinates(ArrayList<Integer> myMultipleCoordinates) {
        this.myMultipleCoordinates = myMultipleCoordinates;
    }

    public void setMyOgreCoordinatesList(ArrayList<Integer> myOgreCoordinatesList) {
        this.myOgreCoordinatesList = myOgreCoordinatesList;
    }

    public void setMyGremlinCoordinatesList(ArrayList<Integer> myGremlinCoordinatesList) {
        this.myGremlinCoordinatesList = myGremlinCoordinatesList;
    }

    public void setMySkeletonCoordinatesList(ArrayList<Integer> mySkeletonCoordinatesList) {
        this.mySkeletonCoordinatesList = mySkeletonCoordinatesList;
    }

    public void setMyItemCollisionFrequency(Map<String, Integer> myItemCollisionFrequency) {
        this.myItemCollisionFrequency = myItemCollisionFrequency;
    }

    public ArrayList<Integer> getMyPitCoordinates() {
        return myPitCoordinates;
    }

    public boolean isMyPitFall() {
        return myPitFall;
    }

    public int getMyPitDmg() {
        return myPitDmg;
    }

    public void setMyPitFall(boolean myPitFall) {
        this.myPitFall = myPitFall;
    }

    public boolean isMyPickedVisionPotion() {
        return myPickedVisionPotion;
    }

    public void setMyPickedVisionPotion(boolean myPickedVisionPotion) {
        this.myPickedVisionPotion = myPickedVisionPotion;
    }

    public boolean isMyPickedHealthPotion() {
        return myPickedHealthPotion;
    }

    public void setMyPickedHealthPotion(boolean myPickedHealthPotion) {
        this.myPickedHealthPotion = myPickedHealthPotion;
    }
}