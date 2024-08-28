package Controller;

import Model.Monster;
import Model.MonsterDatabase;




import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.sql.SQLException;


public class Monsters implements Serializable {



    private int myX;
    private int myY;

    private String monsterType;
    private final GameUI gameUI;

    private long encounterTime;

    private Monster myMonster;

    private boolean isAlive;

    public Rectangle solidArea = new Rectangle(0,0,40,40);

    private BufferedImage myMonsterImage;

    private boolean collision = false;

    private static Monsters myInstance;




    public Monsters(GameUI gameUI, String theMonsterType) {
        this.gameUI = gameUI;
        monsterType = theMonsterType;
        init();
    }

    public void draw(Graphics2D theGraphics2D) {
        int bound = gameUI.getMyDungeonPanel().getMyTileSize();
        int screenX = myX - gameUI.getMyCharacter().getMyX() + gameUI.getMyCharacter().getMyScreenX();
        int screenY = myY - gameUI.getMyCharacter().getMyY() + gameUI.getMyCharacter().getMyScreenY();


        if (myX + bound > gameUI.getMyCharacter().getMyX() - gameUI.getMyCharacter().getMyScreenX() &&
                myX - bound < gameUI.getMyCharacter().getMyX() + gameUI.getMyCharacter().getMyScreenX() &&
                myY + bound > gameUI.getMyCharacter().getMyY() - gameUI.getMyCharacter().getMyScreenY() &&
                myY - bound < gameUI.getMyCharacter().getMyY() + gameUI.getMyCharacter().getMyScreenY()) {
            theGraphics2D.drawImage(myMonsterImage, screenX, screenY, gameUI.getMyDungeonPanel().getMyTileSize(), gameUI.getMyDungeonPanel().getMyTileSize(), null);

        }
        //theGraphics2D.setColor(Color.RED); // Set the color for the solid area rectangle
        //theGraphics2D.drawRect(solidAreaScreenX, solidAreaScreenY, solidArea.width, solidArea.height);
    }


    private void init()  {
        isAlive = true;
        // Create a Monster instance from the database
        try {
            myMonster = MonsterDatabase.getMonster(monsterType);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getMyX() {
        return myX;
    }

    public void setMyX(final int theX) {
        myX = theX;
    }

    public int getMyY() {
        return myY;
    }

    public void setMyY(final int theY) {
        myY = theY;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public Monster getMyMonster() {
        return myMonster;
    }

    public void setMyMonsterImage(final BufferedImage theMonsterImage) {
        this.myMonsterImage = theMonsterImage;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(final boolean theCollision) {
        collision = theCollision;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public void setMonsterType(final String theMonsterType) {
        monsterType = theMonsterType;
    }
}