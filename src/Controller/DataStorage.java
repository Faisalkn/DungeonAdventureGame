package Controller;
import Model.DungeonCharacter;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class DataStorage implements Serializable {

    private int myMaxHitPoint;
    private int myX;

    private int myPlayerHitPoint;
    private int myY;
    private int myPillarAAmount;
    private int myPillarEAmount;
    private int myPillarPAmount;
    private int myPillarIAmount;
    private int myVisionPotionAmount;
    private int myHealthPotionAmount;
    private boolean myEasyModeSelected;
    private static final long serialVersionUID = 1L;
    private String myName;

    private TileManager myTileManager;

    private int[] myExitCoordinates = new int[2];
    private int[] pillarPCoordinates = new int[2];
    private int[] pillarACoordinates = new int[2];
    private int[] pillarECoordinates = new int[2];
    private int[] pillarICoordinates = new int[2];


    private int mySize;

    public int getMySize() {
        return mySize;
    }


    private String[][] myTileNums;


    private boolean myWarriorSelected;
    private boolean myPriestessSelected;
    private boolean myThiefSelected;

    public void setMyTileNums(String[][] myTileNums) {
        this.myTileNums = myTileNums;
    }

    public String[][] getMyTileNums() {
        return myTileNums;
    }

    public void setMySize(int mySize) {
        this.mySize = mySize;
    }


    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public boolean isMyWarriorSelected() {
        return myWarriorSelected;
    }

    public void setMyWarriorSelected(boolean myWarriorSelected) {
        this.myWarriorSelected = myWarriorSelected;
    }

    public boolean isMyPriestessSelected() {
        return myPriestessSelected;
    }

    public void setMyPriestessSelected(boolean myPriestessSelected) {
        this.myPriestessSelected = myPriestessSelected;
    }

    public boolean isMyThiefSelected() {
        return myThiefSelected;
    }

    public void setMyThiefSelected(boolean myThiefSelected) {
        this.myThiefSelected = myThiefSelected;
    }



    public int getMyX() {
        return myX;
    }

    public void setMyX(int myX) {
        this.myX = myX;
    }

    public int getMyY() {
        return myY;
    }

    public void setMyY(int myY) {
        this.myY = myY;
    }

    public ArrayList<String> getItemNames() {
        return itemNames;
    }

    public List<String> getMonsterTypes() {
        return monsterTypes;
    }

    private ArrayList<Integer> itemCoordinates = new ArrayList<>();
    private ArrayList<Integer> monsterCoordinates = new ArrayList<>();
    private ArrayList<String> monsterTypes = new ArrayList<>();
    private ArrayList<String> itemNames = new ArrayList<>();

    public ArrayList<Integer> getItemCoordinates() {
        return itemCoordinates;
    }

    public ArrayList<Integer> getMonsterCoordinates() {
        return monsterCoordinates;
    }

    public int getMyVisionPotionAmount() {
        return myVisionPotionAmount;
    }

    public void setMyVisionPotionAmount(int myVisionPotionAmount) {
        this.myVisionPotionAmount = myVisionPotionAmount;
    }

    public int getMyHealthPotionAmount() {
        return myHealthPotionAmount;
    }

    public void setMyHealthPotionAmount(int myHealthPotionAmount) {
        this.myHealthPotionAmount = myHealthPotionAmount;
    }

    public int getMyPillarAAmount() {
        return myPillarAAmount;
    }

    public void setMyPillarAAmount(int myPillarAAmount) {
        this.myPillarAAmount = myPillarAAmount;
    }

    public int getMyPillarEAmount() {
        return myPillarEAmount;
    }

    public void setMyPillarEAmount(int myPillarEAmount) {
        this.myPillarEAmount = myPillarEAmount;
    }

    public int getMyPillarPAmount() {
        return myPillarPAmount;
    }

    public void setMyPillarPAmount(int myPillarPAmount) {
        this.myPillarPAmount = myPillarPAmount;
    }

    public int getMyPillarIAmount() {
        return myPillarIAmount;
    }

    public void setMyPillarIAmount(int myPillarIAmount) {
        this.myPillarIAmount = myPillarIAmount;
    }

    public int getMyPlayerHitPoint() {
        return myPlayerHitPoint;
    }

    public void setMyPlayerHitPoint(int myPlayerHitPoint) {
        this.myPlayerHitPoint = myPlayerHitPoint;
    }

    public int getMyMaxHitPoint() {
        return myMaxHitPoint;
    }

    public void setMyMaxHitPoint(int myMaxHitPoint) {
        this.myMaxHitPoint = myMaxHitPoint;
    }

    public boolean isMyEasyModeSelected() {
        return myEasyModeSelected;
    }

    public void setMyEasyModeSelected(boolean myEasyModeSelected) {
        this.myEasyModeSelected = myEasyModeSelected;
    }
}