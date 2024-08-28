package Controller;

import Model.Dungeon;
import View.DungeonPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class GameUI implements Serializable {

    private boolean myPickedVisionPotion = false;
    private boolean myPickedHealthPotion = false;
    private int myHealAmount;
    private boolean myUsedHealthPotion = false;
    private int myMazeSize;
    private Map<String, Rectangle> myItemRectangles;

    private Rectangle myCloseWindowRectangle;
    private RoundRectangle2D myInventoryRectangle;

    private RoundRectangle2D myStartRectangle;
    private RoundRectangle2D myQuitRectangle;
    private RoundRectangle2D myLoadRectangle;

    private Rectangle myWarriorCheckBox;
    private Rectangle myThiefCheckBox;
    private Rectangle myPriestessCheckBox;

    private Rectangle myEasyCheckBox;
    private Rectangle myMediumCheckBox;

    private Rectangle mySelectButton;
    private Rectangle myUserNameBox;

    private Rectangle myHardCheckBox;


    private BufferedImage myBackGroundImage;


    private GameControls myGameControls;

    private String myUserName = "";


    private BufferedImage mySelectionImg;

    private final DungeonPanel myDungeonPanel;
    //    private final TileManager myTileManager = new TileManager(this);
    private Dungeon myDungeon;


    public GameUI(final DungeonPanel theDungeonPanel) {
        myDungeonPanel = theDungeonPanel;
        initGameUI();
    }


    private void initGameUI() {
        myItemRectangles = new HashMap<>();
        myGameControls = new GameControls(this);
        myDungeonPanel.addKeyListener(myGameControls);
        myDungeonPanel.addMouseListener(myGameControls);
        myDungeonPanel.setFocusable(true);
        loadImages();
    }


    public void loadImages() {
        try {
            myBackGroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Backgrounds/StartGameBackground.png")));
            mySelectionImg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Backgrounds/CharacterSelection.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void drawPlayer(final Graphics2D theGraphics) {
        myDungeonPanel.getMyCharacter().drawPlayer(theGraphics);
        theGraphics.setColor(Color.WHITE);
        theGraphics.setFont(theGraphics.getFont().deriveFont(Font.BOLD, 15F));
        theGraphics.drawString(myUserName, getMyCharacter().getMyScreenX(), getMyCharacter().getMyScreenY() - 20);

        theGraphics.setFont(theGraphics.getFont().deriveFont(Font.BOLD, 15F));
        theGraphics.setFont(theGraphics.getFont().deriveFont(Font.BOLD, 15F));
        theGraphics.drawString("Current Health " + myDungeonPanel.getMyCharacter().getCharacterType().getMyHitPoints(), getMyCharacter().getMyScreenX() - 25, getMyCharacter().getMyScreenY() - 50);
        if (myUsedHealthPotion) {
            theGraphics.drawString("Used Heal potion + " + myHealAmount, myDungeonPanel.getMyCharacter().getMyScreenX(), myDungeonPanel.getMyCharacter().getMyScreenY());
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            myUsedHealthPotion = false;
        }
        if (myDungeonPanel.getMyTileManager().isMyPitFall()) {
            theGraphics.setColor(Color.RED);
            theGraphics.drawString("Fall into pit -" + myDungeonPanel.getMyTileManager().getMyPitDmg(), getMyCharacter().getMyScreenX(), getMyCharacter().getMyScreenY());

            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myDungeonPanel.getMyTileManager().setMyPitFall(false);
            }).start();
        }
        if (myDungeonPanel.getMyTileManager().isMyPickedVisionPotion()) {
            theGraphics.setColor(Color.GREEN);
            theGraphics.drawString("Picked up a Vision Potion", getMyCharacter().getMyScreenX(), getMyCharacter().getMyScreenY() + 20);
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myDungeonPanel.getMyTileManager().setMyPickedVisionPotion(false);
            }).start();
        }

        if (myDungeonPanel.getMyTileManager().isMyPickedHealthPotion()) {
            theGraphics.setColor(Color.YELLOW);
            theGraphics.drawString("Picked up a Health Potion", getMyCharacter().getMyScreenX(), getMyCharacter().getMyScreenY() + 40);
            new Thread(() -> {
                try {
                    Thread.sleep(1000); // Sleep for 2 seconds (2000 milliseconds)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myDungeonPanel.getMyTileManager().setMyPickedHealthPotion(false);
            }).start();
        }


    }

    public void drawTitleScreen(final Graphics2D theGraphics) {

        theGraphics.setFont(theGraphics.getFont().deriveFont(Font.PLAIN, 60F));
        String title = "DUNGEON ADVENTURE";
        int x = (int) (myDungeonPanel.getMyTileSize() * 2.5);
        int y = myDungeonPanel.getMyTileSize() * 2;

        theGraphics.drawImage(myBackGroundImage, 0, 0, myDungeonPanel.getMyWidth(), myDungeonPanel.getMyHeight(), null);

        theGraphics.setColor(Color.BLACK);
        theGraphics.drawString(title, x + 5, y + 5);
        theGraphics.setColor(Color.WHITE);
        theGraphics.drawString(title, x, y);
        createButton(theGraphics);
    }


    public void drawCharacterSelection(final Graphics2D theGraphics2D) {
        theGraphics2D.setFont(theGraphics2D.getFont().deriveFont(Font.PLAIN, 20F));
        String chooseHeroTitle = "Choose Your Hero";
        String ChooseDifficultyTitle = "Choose difficulty";
        String selectDoneTitle = "DONE";
        String userNameTitle = "Enter name: ";
        theGraphics2D.setColor(Color.WHITE);

        Rectangle chooseHeroRectangle = new Rectangle(myDungeonPanel.getMyTileSize() * 2, myDungeonPanel.getMyTileSize() * 6);
        theGraphics2D.drawImage(mySelectionImg, 0, 0, myDungeonPanel.getMyWidth(), myDungeonPanel.getMyHeight(), null);
        theGraphics2D.setColor(new Color(0, 0, 0, 200));
        theGraphics2D.fillRoundRect(myDungeonPanel.getMyTileSize() * 2, myDungeonPanel.getMyTileSize() * 2, myDungeonPanel.getMyTileSize() * 6, myDungeonPanel.getMyTileSize() * 6, 35, 35);
        theGraphics2D.fillRoundRect(myDungeonPanel.getMyTileSize() * 9, myDungeonPanel.getMyTileSize() * 2, myDungeonPanel.getMyTileSize() * 6, myDungeonPanel.getMyTileSize() * 6, 35, 35);
        theGraphics2D.fillRoundRect((int) (myDungeonPanel.getMyTileSize() * 7.5), (int) (myDungeonPanel.getMyTileSize() * 9.2), myDungeonPanel.getMyTileSize() * 2, myDungeonPanel.getMyTileSize(), 25, 25);


        theGraphics2D.setStroke(new BasicStroke(5));
        theGraphics2D.setColor(new Color(255, 255, 255));
        theGraphics2D.drawRoundRect(myDungeonPanel.getMyTileSize() * 2, myDungeonPanel.getMyTileSize() * 2, myDungeonPanel.getMyTileSize() * 6, myDungeonPanel.getMyTileSize() * 6, 20, 20);
        theGraphics2D.drawRoundRect(myDungeonPanel.getMyTileSize() * 9, myDungeonPanel.getMyTileSize() * 2, myDungeonPanel.getMyTileSize() * 6, myDungeonPanel.getMyTileSize() * 6, 20, 20);
        theGraphics2D.drawString(chooseHeroTitle, (int) (chooseHeroRectangle.getWidth() * 2) - myDungeonPanel.getMyTileSize() / 2, (int) (myDungeonPanel.getMyTileSize() * 2.5));
        theGraphics2D.drawString(ChooseDifficultyTitle, (int) (myDungeonPanel.getMyTileSize() * 10.5), (int) (myDungeonPanel.getMyTileSize() * 2.5));
        theGraphics2D.drawString(selectDoneTitle, (int) (myDungeonPanel.getMyTileSize() * 8.1), (int) (myDungeonPanel.getMyTileSize() * 9.8));
        theGraphics2D.drawString(userNameTitle, (int) (myDungeonPanel.getMyTileSize() * 9.2), (int) (myDungeonPanel.getMyTileSize() * 6.2));

        theGraphics2D.setFont(theGraphics2D.getFont().deriveFont(Font.PLAIN, 16F));
        theGraphics2D.drawString("Warrior", (myDungeonPanel.getMyTileSize() * 3), (int) (myDungeonPanel.getMyTileSize() * 3.4));
        theGraphics2D.drawString("Thief", (myDungeonPanel.getMyTileSize() * 3), (int) (myDungeonPanel.getMyTileSize() * 4.4));
        theGraphics2D.drawString("Priestess", (myDungeonPanel.getMyTileSize() * 3), (int) (myDungeonPanel.getMyTileSize() * 5.4));


        theGraphics2D.drawString("Easy", (myDungeonPanel.getMyTileSize() * 10), (int) (myDungeonPanel.getMyTileSize() * 3.4));
        theGraphics2D.drawString("Medium", (myDungeonPanel.getMyTileSize() * 10), (int) (myDungeonPanel.getMyTileSize() * 4.4));
        theGraphics2D.drawString("Hard", (myDungeonPanel.getMyTileSize() * 10), (int) (myDungeonPanel.getMyTileSize() * 5.4));


        myWarriorCheckBox = new Rectangle((int) (myDungeonPanel.getMyTileSize() * 2.3), (int) (myDungeonPanel.getMyTileSize() * 3.2), 20, 20);
        myThiefCheckBox = new Rectangle((int) (myDungeonPanel.getMyTileSize() * 2.3), (int) (myDungeonPanel.getMyTileSize() * 4.2), 20, 20);
        myPriestessCheckBox = new Rectangle((int) (myDungeonPanel.getMyTileSize() * 2.3), (int) (myDungeonPanel.getMyTileSize() * 5.2), 20, 20);


        myEasyCheckBox = new Rectangle((int) (myDungeonPanel.getMyTileSize() * 9.3), (int) (myDungeonPanel.getMyTileSize() * 3.2), 20, 20);
        myMediumCheckBox = new Rectangle((int) (myDungeonPanel.getMyTileSize() * 9.3), (int) (myDungeonPanel.getMyTileSize() * 4.2), 20, 20);
        myHardCheckBox = new Rectangle((int) (myDungeonPanel.getMyTileSize() * 9.3), (int) (myDungeonPanel.getMyTileSize() * 5.2), 20, 20);

        mySelectButton = new Rectangle((int) (myDungeonPanel.getMyTileSize() * 7.5), (int) (myDungeonPanel.getMyTileSize() * 9.2), myDungeonPanel.getMyTileSize() * 2, myDungeonPanel.getMyTileSize());

        myUserNameBox = new Rectangle((int) (myDungeonPanel.getMyTileSize() * 11.5), (int) (myDungeonPanel.getMyTileSize() * 5.8), myDungeonPanel.getMyTileSize() * 2, myDungeonPanel.getMyTileSize() / 2);


        theGraphics2D.draw(myWarriorCheckBox);
        theGraphics2D.draw(myThiefCheckBox);
        theGraphics2D.draw(myPriestessCheckBox);

        theGraphics2D.draw(myUserNameBox);

        theGraphics2D.draw(myEasyCheckBox);
        theGraphics2D.draw(myMediumCheckBox);
        theGraphics2D.draw(myHardCheckBox);

        theGraphics2D.draw(mySelectButton);


        theGraphics2D.setStroke(new BasicStroke(3));
        theGraphics2D.setColor(Color.WHITE);

        updateSelectionScreen(theGraphics2D);

    }

    public void updateCheckboxSelection(final Graphics2D theGraphics, final Rectangle theCheckbox) {
        theGraphics.setColor(Color.WHITE);
        theGraphics.drawLine(theCheckbox.x + 1, theCheckbox.y + 1, theCheckbox.x + 17, theCheckbox.y + 17);
        theGraphics.drawLine(theCheckbox.x + 17, theCheckbox.y + 1, theCheckbox.x + 1, theCheckbox.y + 17);
    }


    private void updateSelectionScreen(final Graphics2D theGraphics2D) {
        int width = myDungeonPanel.getMyTileSize() * 3;
        int height = myDungeonPanel.getMyTileSize() * 3;

        if (myGameControls.isMySelection()) {
            if (myGameControls.isMyEasySelected()) {
                myDungeon = Model.Dungeon.getInstance();
                myMazeSize = 3;
                myDungeonPanel.getMyTileManager().setMyCol(3);
                myDungeonPanel.getMyTileManager().setMyRow(3);
                myDungeon.generateMaze(3);
                myDungeonPanel.getMyTileManager().generateDungeon();
            }
            if (myGameControls.isMyMediumSelected()) {
                myDungeon = Model.Dungeon.getInstance();
                myMazeSize = 6;
                myDungeonPanel.getMyTileManager().setMyCol(myMazeSize);
                myDungeonPanel.getMyTileManager().setMyRow(myMazeSize);
                myDungeon.generateMaze(6);
                myDungeonPanel.getMyTileManager().generateDungeon();
            }
            if (myGameControls.isMyHardSelected()) {
                myDungeon = Model.Dungeon.getInstance();
                myMazeSize = 8;
                myDungeonPanel.getMyTileManager().setMyCol(myMazeSize);
                myDungeonPanel.getMyTileManager().setMyRow(myMazeSize);
                myDungeon.generateMaze(8);
                myDungeonPanel.getMyTileManager().generateDungeon();
            }
            myDungeonPanel.setGameState(myDungeonPanel.getPlayState());
        }

        if (myGameControls.isMyWarriorSelected()) {
            myDungeonPanel.getMyCharacter().setMyMaxHeroHitPoint(125);
            updateCheckboxSelection(theGraphics2D, myWarriorCheckBox);
            theGraphics2D.drawImage((myDungeonPanel.getMyCharacter().getMyWarriorCurrentImage()), myDungeonPanel.getMyTileSize() * 4, (myDungeonPanel.getMyTileSize() * 3), width, height, null);
        }

        if (myGameControls.isMyPriestessSelected()) {
            myDungeonPanel.getMyCharacter().setMyMaxHeroHitPoint(75);
            updateCheckboxSelection(theGraphics2D, myPriestessCheckBox);
            theGraphics2D.drawImage((myDungeonPanel.getMyCharacter().getMyPriestessCurrentImage()), myDungeonPanel.getMyTileSize() * 4, (myDungeonPanel.getMyTileSize() * 3), width, height, null);

        }

        if (myGameControls.isMyThiefSelected()) {
            myDungeonPanel.getMyCharacter().setMyMaxHeroHitPoint(75);
            updateCheckboxSelection(theGraphics2D, myThiefCheckBox);
            theGraphics2D.drawImage((myDungeonPanel.getMyCharacter().getMyThiefCurrentImage()), myDungeonPanel.getMyTileSize() * 4, (myDungeonPanel.getMyTileSize() * 3), width, height, null);
        }

        if (myGameControls.isMyEasySelected()) {
            updateCheckboxSelection(theGraphics2D, myEasyCheckBox);
        }
        if (myGameControls.isMyMediumSelected()) {
            updateCheckboxSelection(theGraphics2D, myMediumCheckBox);
        }
        if (myGameControls.isMyHardSelected()) {
            updateCheckboxSelection(theGraphics2D, myHardCheckBox);
        }

        if (myGameControls.isMyUsernameBoxSelected()) {
            myUserName = myGameControls.getUsername();
            theGraphics2D.drawString(myUserName, (myDungeonPanel.getMyTileSize() * 12), (int) (myDungeonPanel.getMyTileSize() * 6.2));
        }
    }


    public void drawInventory(final Graphics2D theGraphics) {

        int x = 64 * 9;
        int y = 64;
        int width = 64 * 6;
        int height = 64 * 5;


        drawFrame(x, y, width, height, theGraphics);
        myCloseWindowRectangle = new Rectangle(x + 20, y + 20, 20, 20);
        theGraphics.setColor(new Color(255, 255, 255)); // Set the color for the closeWindowRectangle outline
        theGraphics.setStroke(new BasicStroke(5));

        updateCheckboxSelection(theGraphics, myCloseWindowRectangle);
        theGraphics.setColor(new Color(0, 0, 0, 0)); // Transparent black color
        theGraphics.fill(myCloseWindowRectangle);


        final int slotXStart = x + 40;
        final int slotYStart = y + 90;
        final int slotSize = 64;


        int slotX = slotXStart;
        int slotY = slotYStart;

        int slotIndex = 0;
        int numSlotsPerRow = 3; // Assuming 3 slots per row


        theGraphics.setFont(theGraphics.getFont().deriveFont(Font.BOLD, 30F));
        theGraphics.drawString("Inventory", slotXStart + 70, slotYStart - 50);

        for (SuperItems item : myDungeonPanel.getMyDefaultItems().values()) {
            int cursorX = slotX;
            int cursorY = slotY;

            theGraphics.setStroke(new BasicStroke(3));
            theGraphics.setColor(Color.WHITE);
            theGraphics.drawRoundRect(cursorX, cursorY, slotSize + 30, slotSize, 10, 10);

            theGraphics.drawImage(item.getMyImage(), cursorX, cursorY, slotSize, slotSize, null);

            if (item.isMyCollisoin()) {
                int val = myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().get(item.getMyName());
                theGraphics.setFont(theGraphics.getFont().deriveFont(Font.BOLD, 30F));
                theGraphics.drawString(String.valueOf(val), cursorX + 50, cursorY + 50);
                theGraphics.setFont(theGraphics.getFont().deriveFont(Font.BOLD, 20F));

            } else {
                theGraphics.setFont(theGraphics.getFont().deriveFont(Font.BOLD, 30F));
                theGraphics.drawString("0", cursorX + 50, cursorY + 50);
                theGraphics.setFont(theGraphics.getFont().deriveFont(Font.BOLD, 20F));
            }

            if (item.getMyName().equals("V") || item.getMyName().equals("H")) {
                myItemRectangles.put(item.getMyName(), new Rectangle(cursorX, cursorY, slotSize + 30, slotSize));
            }

            slotIndex++;
            if (slotIndex % numSlotsPerRow == 0) {

                slotX = slotXStart;
                slotY += slotSize + 50;
            } else {

                slotX += slotSize + 50;
            }
        }
    }


    public void updateInventory() {
        if (myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency() != null) {
            if (myGameControls.isMyVisionPotionSelected() && myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().containsKey("V")) {
                int val = myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().get("V");
                if (val > 0) {
                    val--;
                    myDungeonPanel.getMyLighting().setMyVisionPotionUsed(true);
                    myDungeonPanel.getMyLighting().setMyVisionTimer(System.currentTimeMillis());
                    myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().put("V", val);
                    myGameControls.setMyVisionPotionSelected(false);
                }
                // ... (remove the code that sets isMyVisionPotionUsed to false when val == 0)
            }
            if (myGameControls.isMyHealthPotionSelected() && myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().containsKey("H")) {
                if (myDungeonPanel.getMyCharacter().getCharacterType().getMyHitPoints() < myDungeonPanel.getMyCharacter().getMyMaxHeroHitPoint()) {
                    int health = (int) (Math.random() * 11) + 10;
                    myHealAmount = health;
                    int val = myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().get("H");
                    if (val > 0) {
                        myUsedHealthPotion = true;
                        val--;
                        if(myDungeonPanel.getMyCharacter().getCharacterType().getMyHitPoints() + health > myDungeonPanel.getMyCharacter().getMyMaxHeroHitPoint()){
                            health = myDungeonPanel.getMyCharacter().getMyMaxHeroHitPoint() - myDungeonPanel.getMyCharacter().getCharacterType().getMyHitPoints();
                        }
                        myDungeonPanel.getMyCharacter().getCharacterType().setMyHitPoints(myDungeonPanel.getMyCharacter().getCharacterType().getMyHitPoints() + health);
                        myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().put("H", val);
                        myGameControls.setMyHealthPotionSelected(false);
                    }
                }

                myUsedHealthPotion = false;

            }
        }
    }

    public void drawInventoryRectangle(final Graphics2D theGraphics) {
        int x = 64 * 11;

        myInventoryRectangle = new RoundRectangle2D.Double(x, 30, myDungeonPanel.getMyTileSize() * 2, 30, 10, 10);
        //drawRoundedButton(theGraphics, myInventoryRectangle, "Inventory");
        theGraphics.setColor(Color.WHITE);
        theGraphics.fill(myInventoryRectangle);
        theGraphics.setColor(Color.BLACK);
        theGraphics.setFont(theGraphics.getFont().deriveFont(Font.PLAIN, 15F));
        theGraphics.drawString("Inventory", (int) (myInventoryRectangle.getX() + myInventoryRectangle.getWidth() / 5), ((int) myInventoryRectangle.getCenterY() + myDungeonPanel.getMyTileSize() / 7));
    }

    public void drawFrame(final int theX, final int theY, final int theWidth, final int theHeight, final Graphics2D theGraphics) {

        theGraphics.setColor(new Color(0, 0, 0, 255));
        theGraphics.fillRoundRect(theX, theY, theWidth, theHeight, 35, 35);
        theGraphics.drawRoundRect(theX + 5, theY + 5, theWidth - 10, theHeight - 10, 25, 25);


    }


    private void drawRoundedButton(final Graphics2D theGraphics, final RoundRectangle2D theButton, final String theButtonText) {
        theGraphics.setColor(Color.WHITE);
        theGraphics.fill(theButton);
        theGraphics.setFont(theGraphics.getFont().deriveFont(Font.PLAIN, 20F));
        theGraphics.setColor(Color.BLACK);
        theGraphics.drawString(theButtonText, (int) (theButton.getX() + theButton.getWidth() / 4), (int) theButton.getCenterY() + myDungeonPanel.getMyTileSize() / 5);
    }

    private void createButton(final Graphics2D theGraphics) {
        myStartRectangle = new RoundRectangle2D.Double(myDungeonPanel.getMyTileSize() * 6, myDungeonPanel.getMyTileSize() * 5, myDungeonPanel.getMyTileSize() * 4, myDungeonPanel.getMyTileSize(), 20, 20);
        String myStartGameTitle = "NEW GAME";
        drawRoundedButton(theGraphics, myStartRectangle, myStartGameTitle);

        myQuitRectangle = new RoundRectangle2D.Double(myDungeonPanel.getMyTileSize() * 6, myDungeonPanel.getMyTileSize() * 9, myDungeonPanel.getMyTileSize() * 4, myDungeonPanel.getMyTileSize(), 20, 20);
        String myQuitGameTitle = "QUIT GAME";
        drawRoundedButton(theGraphics, myQuitRectangle, myQuitGameTitle);

        myLoadRectangle = new RoundRectangle2D.Double(myDungeonPanel.getMyTileSize() * 6, myDungeonPanel.getMyTileSize() * 7, myDungeonPanel.getMyTileSize() * 4, myDungeonPanel.getMyTileSize(), 20, 20);
        String myLoadGameTitle = "LOAD GAME";
        drawRoundedButton(theGraphics, myLoadRectangle, myLoadGameTitle);

        if (myGameControls.isStartGameClicked()) {
            myDungeonPanel.setGameState(myDungeonPanel.getSelectionState());
        }
        if(myGameControls.isMyLoadGameClicked()){
            //myDungeonPanel.getMyTileManager().generateDungeon();


            myDungeonPanel.getMyGameUi().getMyCharacter().initHeroes();
            myDungeonPanel.getMySaveLoad().load();
            //myDungeonPanel.getMyTileManager().loadMap();
            //myDungeonPanel.getMyGameUi().getMyDungeonPanel().setObjects();
            myDungeonPanel.setGameState(myDungeonPanel.getPlayState());
        }

        if (myGameControls.isQuitGameClicked()) {
            System.exit(0);
        }
    }


    public void updatePlayerLocation() {
        myDungeonPanel.getMyCharacter().updatePlayerLocation();
    }


    public DungeonPanel getMyDungeonPanel() {
        return myDungeonPanel;
    }


    public RoundRectangle2D getMyStartRectangle() {
        return myStartRectangle;
    }

    public RoundRectangle2D getMyQuitRectangle() {
        return myQuitRectangle;
    }

    public RoundRectangle2D getMyLoadRectangle() {
        return myLoadRectangle;
    }

    public Rectangle getMyWarriorCheckBox() {
        return myWarriorCheckBox;
    }

    public Rectangle getMyThiefCheckBox() {
        return myThiefCheckBox;
    }

    public Rectangle getMyPriestessCheckBox() {
        return myPriestessCheckBox;
    }

    public Rectangle getMyEasyCheckBox() {
        return myEasyCheckBox;
    }

    public Rectangle getMyMediumCheckBox() {
        return myMediumCheckBox;
    }

    public Rectangle getMyHardCheckBox() {
        return myHardCheckBox;
    }

    public Rectangle getMySelectButton() {
        return mySelectButton;
    }

    public GameControls getMyGameControls() {
        return myGameControls;
    }

    public Characters getMyCharacter() {
        return myDungeonPanel.getMyCharacter();
    }


    public TileManager getMyTileManager() {
        return myDungeonPanel.getMyTileManager();
    }

    public Rectangle getMyUserNameBox() {
        return myUserNameBox;
    }

    public Dungeon getMyDungeon() {
        return myDungeon;
    }

    public RoundRectangle2D getMyInventoryRectangle() {
        return myInventoryRectangle;
    }

    public Map<String, Rectangle> getMyItemRectangles() {
        return myItemRectangles;
    }

    public Rectangle getMyCloseWindowRectangle() {
        return myCloseWindowRectangle;
    }

    public int getMyMazeSize() {
        return myMazeSize;
    }

    public void setMyUserName(String myUserName) {
        this.myUserName = myUserName;
    }

    public int getMyHealAmount() {
        return myHealAmount;
    }

    public boolean isMyUsedHealthPotion() {
        return myUsedHealthPotion;
    }

    public void setMyDungeon(Dungeon myDungeon) {
        this.myDungeon = myDungeon;
    }
}