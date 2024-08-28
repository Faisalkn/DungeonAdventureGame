package Controller;

import Model.DungeonCharacter;
import Model.Priestess;
import Model.Thief;
import Model.Warrior;
import View.DungeonPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Characters implements Serializable {
    private boolean myPlayerIsMoving = false;
    private int myPlayerSpeed = 4;
    private Rectangle myHealthBarRect;

    private int myX;
    private int myY;


    private Rectangle myOuterHealthBarRect;

    private final int myScreenY;
    private final int myScreenX;
    //private final GameUI myDungeonPanel.getMyGameUi();
    private DungeonPanel myDungeonPanel;

    private  Warrior myWarrior;
    private  Thief myThief;
    private  Priestess myPriestess;

    private final transient BufferedImage myWarriorCurrentImage;
    private final transient Map<Integer, BufferedImage> myThiefImages;
    private final transient BufferedImage myThiefCurrentImage;
    private final transient BufferedImage myPriestessCurrentImage;

    private transient BufferedImage myHeroCurrentImage;

    private final transient Map<Integer, BufferedImage> myWarriorImage;
    private final transient Map<Integer, BufferedImage> myPriestessImages;
    private static final long serialVersionUID = 1L;


    private int myMaxHeroHitPoint;
    private Direction direction = Direction.EAST;

    private static final int RUNNING_RIGHT = 1;
    private static final int RUNNING_LEFT = 2;
    private static final int RUNNING_DOWN = 3;
    private static final int RUNNING_UP = 4;

    private DungeonCharacter myDungeonCharacter;

    public Characters(final DungeonPanel theDungeonPanel) {
        myDungeonPanel = theDungeonPanel;
        myWarriorImage = new HashMap<>();
        myThiefImages = new HashMap<>();
        myPriestessImages = new HashMap<>();

        myScreenX = myDungeonPanel.getMyWidth() / 2 - (myDungeonPanel.getMyTileSize() / 2);
        myScreenY = myDungeonPanel.getMyHeight() / 2 - (myDungeonPanel.getMyTileSize() / 2);
        load();
        myWarriorCurrentImage = myWarriorImage.get(RUNNING_LEFT);
        myThiefCurrentImage = myThiefImages.get(RUNNING_LEFT);
        myPriestessCurrentImage = myPriestessImages.get(RUNNING_LEFT);
    }




    public void initHeroes(){
         myX = myDungeonPanel.getMyTileSize() * myDungeonPanel.getMyTileManager().getMyEntranceCol();
         myY = myDungeonPanel.getMyTileSize() * myDungeonPanel.getMyTileManager().getMyEntranceRow();

        int width = 40;
        int height = 40;

            myWarrior = Model.Warrior.getInstance();
            myWarrior.setMyY(myY);
            myWarrior.setMyX(myX);
            myWarrior.setMyHitBox(new Rectangle( myWarrior.getMyX() + 10,  myWarrior.getMyY()+20, width, height));


            myThief = Model.Thief.getMyUniqueInstance();
            myThief.setMyY(myY);
            myThief.setMyX(myX);
            myThief.setMyHitBox(new Rectangle( myThief.getMyX() + 10,  myThief.getMyY()+20, width, height));

            myPriestess = Model.Priestess.getMyUniqueInstance();
            myPriestess.setMyY(myY);
            myPriestess.setMyX(myX);
            myPriestess.setMyHitBox(new Rectangle( myPriestess.getMyX() + 10,  myPriestess.getMyY()+20, width, height));

            myHealthBarRect = new Rectangle(myScreenX - 2, myScreenY - 12, 45, 10);
            myOuterHealthBarRect = new Rectangle(myScreenX - 2, myScreenY - 12, 46, 11);

            myDungeonCharacter = getCharacterType();
    }


    public void drawPlayer(final Graphics2D theGraphics) {
        if(myDungeonPanel.getMyGameUi().getMyGameControls().isMyWarriorSelected()){
            theGraphics.drawImage(myHeroCurrentImage, myScreenX, myScreenY, 40, 40, null);
        }else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyThiefSelected()){
            theGraphics.drawImage(myHeroCurrentImage, myScreenX - 10, myScreenY - 10, 70, 70, null);
        }else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyPriestessSelected()){
            theGraphics.drawImage(myHeroCurrentImage, myScreenX - 20, myScreenY - 30, 70, 70, null);
        }
    }



    public void updatePlayerLocation() {
        if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyWarriorSelected()) {
            myHeroCurrentImage = myWarriorCurrentImage;
        } else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyThiefSelected()) {
            myHeroCurrentImage = myThiefCurrentImage;
        }else if(myDungeonPanel.getMyGameUi().getMyGameControls().isMyPriestessSelected()){
            myHeroCurrentImage = myPriestessCurrentImage;
        }

        if (myPlayerIsMoving) {
            if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyUpArrow()) {
                direction = Direction.NORTH;
                if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyThiefSelected()) {
                    myHeroCurrentImage = myThiefImages.get(RUNNING_UP);
                } else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyWarriorSelected()) {
                    myHeroCurrentImage = myWarriorImage.get(RUNNING_UP);
                } else if(myDungeonPanel.getMyGameUi().getMyGameControls().isMyPriestessSelected()){
                    myHeroCurrentImage = myPriestessImages.get(RUNNING_UP);
                }
            } else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyDownArrow()) {
                direction = Direction.SOUTH;
                if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyThiefSelected()) {
                    myHeroCurrentImage = myThiefImages.get(RUNNING_DOWN);
                } else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyWarriorSelected()) {
                    myHeroCurrentImage = myWarriorImage.get(RUNNING_DOWN);
                }
                else if(myDungeonPanel.getMyGameUi().getMyGameControls().isMyPriestessSelected()){
                    myHeroCurrentImage = myPriestessImages.get(RUNNING_DOWN);
                }
            } else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyLeftArrow()) {
                direction = Direction.WEST;
                if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyThiefSelected()) {
                    myHeroCurrentImage = myThiefImages.get(RUNNING_LEFT);
                } else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyWarriorSelected()) {
                    myHeroCurrentImage = myWarriorImage.get(RUNNING_LEFT);
                }
                else if(myDungeonPanel.getMyGameUi().getMyGameControls().isMyPriestessSelected()){
                    myHeroCurrentImage = myPriestessImages.get(RUNNING_LEFT);
                }
            } else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyRightArrow()) {
                direction = Direction.EAST;
                if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyThiefSelected()) {
                    myHeroCurrentImage = myThiefImages.get(RUNNING_RIGHT);
                } else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyWarriorSelected()) {
                    myHeroCurrentImage = myWarriorImage.get(RUNNING_RIGHT);
                }
                else if(myDungeonPanel.getMyGameUi().getMyGameControls().isMyPriestessSelected()){
                    myHeroCurrentImage = myPriestessImages.get(RUNNING_RIGHT);
                }
            }

            int newX = 0;
            int newY = 0;

            if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyWarriorSelected()) {
                newX = myWarrior.getMyX();
                newY = myWarrior.getMyY();
            }
            if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyThiefSelected()) {
                newX = myThief.getMyX();
                newY = myThief.getMyY();
            }
            if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyPriestessSelected()) {
                newX = myPriestess.getMyX();
                newY = myPriestess.getMyY();
            }


                switch (direction) {
                    case NORTH -> newY -= myPlayerSpeed;
                    case SOUTH -> newY += myPlayerSpeed;
                    case WEST -> newX -= myPlayerSpeed;
                    case EAST -> newX += myPlayerSpeed;
                }


            if(myPlayerIsMoving) {
                if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyThiefSelected()) {
                    myThief.getMyHitBox().x = newX;
                    myThief.getMyHitBox().y = newY;
                    if (!myDungeonPanel.getMyGameUi().getMyTileManager().isTileCollision(myThief.getMyHitBox())) {
                        myThief.setMyX(newX);
                        myThief.setMyY(newY);
                    }
                }
                if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyWarriorSelected()) {
                    myWarrior.getMyHitBox().x = newX;
                    myWarrior.getMyHitBox().y = newY;
                    if (!myDungeonPanel.getMyGameUi().getMyTileManager().isTileCollision(myWarrior.getMyHitBox())) {
                        myWarrior.setMyX(newX);
                        myWarrior.setMyY(newY);
                    }
                }
                if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyPriestessSelected()) {
                    myPriestess.getMyHitBox().x = newX;
                    myPriestess.getMyHitBox().y = newY;
                    if (!myDungeonPanel.getMyGameUi().getMyTileManager().isTileCollision(myPriestess.getMyHitBox())) {
                        myPriestess.setMyX(newX);
                        myPriestess.setMyY(newY);
                    }
                }
            }

            updateHitBox();
            //checkMonsterEncounter();
        } else {
            // Set the appropriate image when not moving
            if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyThiefSelected()) {
                switch (direction) {
                    case NORTH -> myHeroCurrentImage = myThiefImages.get(RUNNING_UP);
                    case SOUTH -> myHeroCurrentImage = myThiefImages.get(RUNNING_DOWN);
                    case WEST -> myHeroCurrentImage = myThiefImages.get(RUNNING_LEFT);
                    case EAST -> myHeroCurrentImage = myThiefImages.get(RUNNING_RIGHT);
                }
            } else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyWarriorSelected()) {
                switch (direction) {
                    case NORTH -> myHeroCurrentImage = myWarriorImage.get(RUNNING_UP);
                    case SOUTH -> myHeroCurrentImage = myWarriorImage.get(RUNNING_DOWN);
                    case WEST -> myHeroCurrentImage = myWarriorImage.get(RUNNING_LEFT);
                    case EAST -> myHeroCurrentImage = myWarriorImage.get(RUNNING_RIGHT);
                }
            }
            else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyPriestessSelected()) {
                switch (direction) {
                    case NORTH -> myHeroCurrentImage = myPriestessImages.get(RUNNING_UP);
                    case SOUTH -> myHeroCurrentImage = myPriestessImages.get(RUNNING_DOWN);
                    case WEST -> myHeroCurrentImage = myPriestessImages.get(RUNNING_LEFT);
                    case EAST -> myHeroCurrentImage = myPriestessImages.get(RUNNING_RIGHT);
                }
            }
        }
    }


    public void load() {
        try {
           myWarriorImage.put(RUNNING_LEFT, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WarriorImages/WarriorLeft.png"))));
           myWarriorImage.put(RUNNING_RIGHT, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WarriorImages/WarriorRight.png"))));
           myWarriorImage.put(RUNNING_UP, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WarriorImages/WarriorUp.png"))));
           myWarriorImage.put(RUNNING_DOWN, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WarriorImages/WarriorDown.png"))));

            myThiefImages.put(RUNNING_LEFT, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Thief/ThiefLeft.png"))));
            myThiefImages.put(RUNNING_RIGHT, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Thief/ThiefRight.png"))));
            myThiefImages.put(RUNNING_UP, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Thief/ThiefUp.png"))));
            myThiefImages.put(RUNNING_DOWN, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Thief/ThiefDown.png"))));


            myPriestessImages.put(RUNNING_LEFT, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Priestess/PriestessLeft.png"))));
            myPriestessImages.put(RUNNING_RIGHT, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Priestess/PriestessRight.png"))));
            myPriestessImages.put(RUNNING_UP, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Priestess/PriestessUp.png"))));
            myPriestessImages.put(RUNNING_DOWN, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Priestess/PriestessDown.png"))));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setMyPlayerIsMoving(boolean myPlayerIsMoving) {
        this.myPlayerIsMoving = myPlayerIsMoving;
    }


    public int getMyScreenY() {
        return myScreenY;
    }

    public int getMyScreenX() {
        return myScreenX;
    }

    public Warrior getMyWarrior() {
        return myWarrior;
    }


    public void updateHitBox() {
        if(myDungeonPanel.getMyGameUi().getMyGameControls().isMyWarriorSelected()) {
            int playerX =  myWarrior.getMyX();
            int playerY = myWarrior.getMyY();
            myWarrior.getMyHitBox().x = playerX;
            myWarrior.getMyHitBox().y = playerY;
        }
        if(myDungeonPanel.getMyGameUi().getMyGameControls().isMyPriestessSelected()) {
            int playerX =  myPriestess.getMyX();
            int playerY =  myPriestess.getMyY();
            myPriestess.getMyHitBox().x = playerX;
            myPriestess.getMyHitBox().y = playerY;
        }
        if(myDungeonPanel.getMyGameUi().getMyGameControls().isMyThiefSelected()) {
            int playerX =  myThief.getMyX();
            int playerY =  myThief.getMyY();
            myThief.getMyHitBox().x = playerX;
            myThief.getMyHitBox().y = playerY;
        }
    }


    public void drawHitBox(Graphics2D theGraphics) {
        //debugging
        theGraphics.setColor(Color.RED);
        theGraphics.drawRect(myScreenX, myScreenY, myWarrior.getMyHitBox().width, myWarrior.getMyHitBox().height);
    }

    public BufferedImage getMyWarriorCurrentImage() {
        return  myWarriorCurrentImage;

    }

    public BufferedImage getMyThiefCurrentImage() {
        return myThiefCurrentImage;
    }

    public int getMyX() {
        if(myDungeonPanel.getMyGameUi().getMyGameControls().isMyWarriorSelected()){
            return myWarrior.getMyX();
        }else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyThiefSelected()){
            return myThief.getMyX();
        }else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyPriestessSelected()) {
            return myPriestess.getMyX();
        }else{
            return  0;
        }

    }

    public int getMyY() {
        if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyWarriorSelected()) {
            return myWarrior.getMyY();
        } else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyThiefSelected()) {
            return myThief.getMyY();
        } else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyPriestessSelected()) {
            return myPriestess.getMyY();
        }else{
            return  0;
        }
    }

    public BufferedImage getMyPriestessCurrentImage() {
        return myPriestessCurrentImage;
    }

    public Rectangle getHitBox(){
        if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyWarriorSelected()) {
            return myWarrior.getMyHitBox();
        } else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyThiefSelected()) {
            return myThief.getMyHitBox();
        } else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyPriestessSelected()) {
            return myPriestess.getMyHitBox();
        }else{
            return  null;
        }
    }



    public DungeonCharacter getCharacterType(){
        DungeonCharacter hero = null;
        if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyWarriorSelected()) {
            hero = myWarrior;
        } else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyThiefSelected()) {
            hero = myThief;

        } else if (myDungeonPanel.getMyGameUi().getMyGameControls().isMyPriestessSelected()) {
            hero = myPriestess;

        }

        return hero;
    }

    public void setMyPlayerSpeed(int myPlayerSpeed) {
        this.myPlayerSpeed = myPlayerSpeed;
    }


    public int getMyPlayerSpeed() {
        return myPlayerSpeed;
    }

    public boolean isMyPlayerIsMoving() {
        return myPlayerIsMoving;
    }

    public int getMyMaxHeroHitPoint() {
        return myMaxHeroHitPoint;
    }

    public void setMyX(int myX) {
        this.myX = myX;
    }

    public void setMyY(int myY) {
        this.myY = myY;
    }

    public void setMyDungeonCharacter(final DungeonCharacter theDungeonCharacter){
        myDungeonCharacter = theDungeonCharacter;
    }

    public BufferedImage getMyHeroCurrentImage() {
        return myHeroCurrentImage;
    }

    public void setMyHeroCurrentImage(BufferedImage myHeroCurrentImage) {
        this.myHeroCurrentImage = myHeroCurrentImage;
    }

    public void setMyWarrior(Warrior myWarrior) {
        this.myWarrior = myWarrior;
    }

    public Thief getMyThief() {
        return myThief;
    }

    public void setMyThief(Thief myThief) {
        this.myThief = myThief;
    }

    public Priestess getMyPriestess() {
        return myPriestess;
    }

    public void setMyPriestess(Priestess myPriestess) {
        this.myPriestess = myPriestess;
    }

    public void setMyMaxHeroHitPoint(int myMaxHeroHitPoint) {
        this.myMaxHeroHitPoint = myMaxHeroHitPoint;
    }

}
