package Controller;

import Model.Monster;
import Model.MonsterDatabase;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.sql.SQLException;

public class SuperItems implements Serializable {
    private  BufferedImage myImage;
    private String myName;
    private boolean myCollision = false;
    private int myX;
    private int myY;

    private int mySolidAreaDefaultX = 0;

    private int mySolidAreaDefaultY = 0;
    private final GameUI myGameUI;

    public Rectangle solidArea = new Rectangle(0,0,40,40);


    public SuperItems(final GameUI theGameUi) {
        myGameUI = theGameUi;
    }

    public void draw(final Graphics2D theGraphics2D) {
        int bound = myGameUI.getMyDungeonPanel().getMyTileSize();
        int screenX = myX - myGameUI.getMyCharacter().getMyX() + myGameUI.getMyCharacter().getMyScreenX();
        int screenY = myY - myGameUI.getMyCharacter().getMyY() + myGameUI.getMyCharacter().getMyScreenY();

        if(myX + bound > myGameUI.getMyCharacter().getMyX() - myGameUI.getMyCharacter().getMyScreenX() &&
                myX - bound < myGameUI.getMyCharacter().getMyX() + myGameUI.getMyCharacter().getMyScreenX() &&
                myY + bound> myGameUI.getMyCharacter().getMyY() - myGameUI.getMyCharacter().getMyScreenY() &&
                myY - bound< myGameUI.getMyCharacter().getMyY() + myGameUI.getMyCharacter().getMyScreenY()){
            theGraphics2D.drawImage(myImage, screenX + 10, screenY + 10, myGameUI.getMyDungeonPanel().getMyTileSize() - 30, myGameUI.getMyDungeonPanel().getMyTileSize() - 30, null);
        }
    }



    public BufferedImage getMyImage() {
        return myImage;
    }

    public String getMyName() {
        return myName;
    }

    public boolean isMyCollisoin() {
        return myCollision;
    }

    public int getWorldX() {
        return myX;
    }

    public int getMyY() {
        return myY;
    }

    public void setMyImage(final BufferedImage theImage) {
        myImage = theImage;
    }

    public void setMyName(final String theName) {
        myName = theName;
    }

    public void setMyCollisoin(final boolean theCollision) {
        myCollision = theCollision;
    }

    public void setWorldX(final int theX) {
        myX = theX;
    }

    public void setMyY(final int theY) {
        myY = theY;
    }


    public int getMySolidAreaDefaultX() {
        return mySolidAreaDefaultX;
    }

    public int getMySolidAreaDefaultY() {
        return mySolidAreaDefaultY;
    }
}