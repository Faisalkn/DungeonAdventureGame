package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameControls implements KeyListener, MouseListener {




    private boolean myUpArrow;
    private boolean myDownArrow;
    private boolean myLeftArrow;
    private boolean myRightArrow;

    private boolean myStartGameClicked;
    private boolean myQuitGameClicked;
    private boolean myLoadGameClicked;

    private boolean myWarriorSelected;
    private boolean myThiefSelected;
    private boolean myPriestessSelected;

    private boolean myEasySelected;
    private boolean myMediumSelected;
    private boolean myHardSelected;

    private boolean myUsernameBoxSelected;

    private boolean mySpaceKeyPressed;

    private boolean mySelection;

    private boolean myInventorySelected;

    private boolean myVisionPotionSelected;
    private boolean myHealthPotionSelected;

    private boolean isMyStartOverGameClicked;
    private boolean myCloseInventory;
    private boolean myCloseBattleWindow;

    private String myUserName;


    public boolean isMySelection() {
        return mySelection;
    }

    private final GameUI myGameUi;


    private final StringBuilder userName = new StringBuilder();

    GameControls(GameUI thePlayerUI) {
        myGameUi = thePlayerUI;
    }




    @Override
    public void keyTyped(KeyEvent theKeyEvent) {
        if (myUsernameBoxSelected) {
            char keyChar = theKeyEvent.getKeyChar();
            if (Character.isLetterOrDigit(keyChar)) {
                userName.append(keyChar); // Append the typed character to the username
            } else if (keyChar == KeyEvent.VK_SPACE) {
                userName.append(" ");
            } else if (keyChar == KeyEvent.VK_BACK_SPACE && userName.length() > 0) {
                userName.deleteCharAt(userName.length() - 1); // Remove the last character if backspace is pressed
            }
        }
    }

    // Character movement
    @Override
    public void keyPressed(KeyEvent theKeyEvent) {
        int keyCode = theKeyEvent.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            myUpArrow = true;
            myGameUi.getMyCharacter().setMyPlayerIsMoving(true);
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            myDownArrow = true;
            myGameUi.getMyCharacter().setMyPlayerIsMoving(true);
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            myLeftArrow = true;
            myGameUi.getMyCharacter().setMyPlayerIsMoving(true);

        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            myRightArrow = true;
            myGameUi.getMyCharacter().setMyPlayerIsMoving(true);
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            mySpaceKeyPressed = true;
        }
        if(keyCode == KeyEvent.VK_ENTER){
            myInventorySelected = true;
        }
        if (myInventorySelected) {
            myCloseInventory = false;
            if (myGameUi.getMyItemRectangles().size() > 1) {
                if (keyCode == KeyEvent.VK_V) {
                    myVisionPotionSelected = true;
                }
                if (keyCode == KeyEvent.VK_H) {
                    myHealthPotionSelected = true;

                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent theKeyEvent) {
        int keyCode = theKeyEvent.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            myUpArrow = false;
            myGameUi.getMyCharacter().setMyPlayerIsMoving(false);
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            myDownArrow = false;
            myGameUi.getMyCharacter().setMyPlayerIsMoving(false);
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            myLeftArrow = false;
            myGameUi.getMyCharacter().setMyPlayerIsMoving(false);
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            myRightArrow = false;
            myGameUi.getMyCharacter().setMyPlayerIsMoving(false);
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            mySpaceKeyPressed = false;
        }
        if(keyCode == KeyEvent.VK_ENTER){
            myInventorySelected = false;
        }
        if(keyCode == KeyEvent.VK_V){
            myVisionPotionSelected = false;

        }
        if(keyCode == KeyEvent.VK_H){
            myHealthPotionSelected = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (myGameUi.getMyDungeonPanel().getGameState() == myGameUi.getMyDungeonPanel().getMyBeginningState()) {
            if (myGameUi.getMyStartRectangle().contains(e.getPoint())) {
                myStartGameClicked = true;
                myLoadGameClicked = false;
                myQuitGameClicked = false;
                myGameUi.getMyDungeonPanel().getMyGameSounds().playClickSound(1);

            }

            if (myGameUi.getMyLoadRectangle().contains(e.getPoint())) {
                myStartGameClicked = false;
                myLoadGameClicked = true;
                myQuitGameClicked = false;
                myGameUi.getMyDungeonPanel().getMyGameSounds().playClickSound(1);

            }
            if (myGameUi.getMyQuitRectangle().contains(e.getPoint())) {
                myStartGameClicked = false;
                myLoadGameClicked = false;
                myQuitGameClicked = true;
                myGameUi.getMyDungeonPanel().getMyGameSounds().playClickSound(1);

            }
        } else if(myGameUi.getMyDungeonPanel().getGameState() == myGameUi.getMyDungeonPanel().getSelectionState()){
            if (myGameUi.getMyWarriorCheckBox() != null && myGameUi.getMyWarriorCheckBox().contains(e.getPoint())) {
                myWarriorSelected = true;
                myThiefSelected = false;
                myPriestessSelected = false;
                myUsernameBoxSelected = false;
                myGameUi.getMyDungeonPanel().getMyGameSounds().playClickSound(1);
                myGameUi.getMyDungeonPanel().getMyGameSounds().playClickSound(2);
            }
            if (myGameUi.getMyUserNameBox() != null && myGameUi.getMyUserNameBox().contains(e.getPoint())){
                myUsernameBoxSelected = true;
                myGameUi.getMyDungeonPanel().getMyGameSounds().playClickSound(1);
            }
            if (myGameUi.getMyThiefCheckBox() != null && myGameUi.getMyThiefCheckBox().contains(e.getPoint())) {
                myWarriorSelected = false;
                myThiefSelected = true;
                myPriestessSelected = false;
                myUsernameBoxSelected = false;
                myGameUi.getMyDungeonPanel().getMyGameSounds().playClickSound(1);
            }
            if (myGameUi.getMyPriestessCheckBox() != null && myGameUi.getMyPriestessCheckBox().contains(e.getPoint())) {
                myWarriorSelected = false;
                myThiefSelected = false;
                myPriestessSelected = true;
                myUsernameBoxSelected = false;
                myGameUi.getMyDungeonPanel().getMyGameSounds().playClickSound(1);

            }

            if (myGameUi.getMyEasyCheckBox().contains(e.getPoint())) {
                myEasySelected = true;
                myMediumSelected = false;
                myHardSelected = false;
                myUsernameBoxSelected = false;
                myGameUi.getMyDungeonPanel().getMyGameSounds().playClickSound(1);

            }
            if (myGameUi.getMyMediumCheckBox().contains(e.getPoint())) {
                myEasySelected = false;
                myMediumSelected = true;
                myHardSelected = false;
                myUsernameBoxSelected = false;
                myGameUi.getMyDungeonPanel().getMyGameSounds().playClickSound(1);

            }
            if (myGameUi.getMyHardCheckBox().contains(e.getPoint())) {
                myEasySelected = false;
                myMediumSelected = false;
                myHardSelected = true;
                myUsernameBoxSelected = false;
                myGameUi.getMyDungeonPanel().getMyGameSounds().playClickSound(1);

            }
            if (myGameUi.getMySelectButton().contains(e.getPoint())) {
                mySelection = true;
                myUsernameBoxSelected = false;
                myGameUi.getMyDungeonPanel().getMyGameSounds().playClickSound(1);
            }

        }else {
            if (myGameUi.getMyInventoryRectangle() != null && myGameUi.getMyInventoryRectangle().contains(e.getPoint())) {
                myInventorySelected = true;
            }
            if(myGameUi.getMyTileManager().getMyCloseBattleWindow() != null && myGameUi.getMyTileManager().getMyCloseBattleWindow().contains(e.getPoint())){
                myCloseBattleWindow = true;
            }
            if(myInventorySelected) {
                myCloseInventory = false;
                if (myGameUi.getMyItemRectangles().containsKey("V")) {
                    if (myGameUi.getMyItemRectangles().get("V").contains(e.getPoint())) {
                        myVisionPotionSelected = true;
                    }
                }
                if (myGameUi.getMyItemRectangles().containsKey("H")) {
                    if (myGameUi.getMyItemRectangles().get("H").contains(e.getPoint())) {
                        myHealthPotionSelected = true;
                    }
                }

            }
            if(myGameUi.getMyCloseWindowRectangle() != null  && myGameUi.getMyCloseWindowRectangle().contains(e.getPoint())){
                myCloseInventory = true;
                myInventorySelected = false;
            }
        }
        myGameUi.getMyDungeonPanel().repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (myGameUi.getMyDungeonPanel().getGameState() == myGameUi.getMyDungeonPanel().getPlayState()) {
            if (myGameUi.getMyItemRectangles().containsKey("V") && myGameUi.getMyItemRectangles().get("V").contains(e.getPoint())) {
                myVisionPotionSelected = false;
            }
            if (myGameUi.getMyItemRectangles().containsKey("H") && myGameUi.getMyItemRectangles().get("H").contains(e.getPoint())) {
                myHealthPotionSelected = false;
            }
            if(myGameUi.getMyTileManager().getMyCloseBattleWindow() != null && myGameUi.getMyTileManager().getMyCloseBattleWindow().contains(e.getPoint())){
                myCloseBattleWindow = false;
            }
        }
        myGameUi.getMyDungeonPanel().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public boolean isStartGameClicked() {
        return myStartGameClicked;
    }

    public boolean isQuitGameClicked() {
        return myQuitGameClicked;
    }

    public boolean isLoadGameClicked() {
        return myLoadGameClicked;
    }

    public boolean isMyWarriorSelected() {
        return myWarriorSelected;
    }

    public boolean isMyThiefSelected() {
        return myThiefSelected;
    }

    public boolean isMyPriestessSelected() {
        return myPriestessSelected;
    }

    public boolean isMyEasySelected() {
        return myEasySelected;
    }

    public boolean isMyMediumSelected() {
        return myMediumSelected;
    }

    public boolean isMyHardSelected() {
        return myHardSelected;
    }
    public boolean isMySpaceKeyPressed() {
        return mySpaceKeyPressed;
    }
    public String getUsername() {
        return userName.toString();
    }

    public boolean isMyUsernameBoxSelected() {
        return myUsernameBoxSelected;
    }

    public boolean isMyInventorySelected() {
        return myInventorySelected;
    }

    public boolean isMyVisionPotionSelected() {
        return myVisionPotionSelected;
    }

    public boolean isMyHealthPotionSelected() {
        return myHealthPotionSelected;
    }

    public void setMyVisionPotionSelected(boolean theVisionPotionSelected) {
        myVisionPotionSelected = theVisionPotionSelected;
    }

    public void setMyHealthPotionSelected(boolean theHealthPotionSelected) {
        myHealthPotionSelected = theHealthPotionSelected;
    }

    public boolean isMyStartOverGameClicked() {
        return isMyStartOverGameClicked;
    }

    public boolean closeWindow() {
        return myCloseInventory;
    }

    public boolean isMyCloseBattleWindow() {
        return myCloseBattleWindow;
    }

    public boolean isMyLoadGameClicked() {
        return myLoadGameClicked;
    }

    public boolean isMyUpArrow() {
        return myUpArrow;
    }

    public boolean isMyDownArrow() {
        return myDownArrow;
    }

    public boolean isMyLeftArrow() {
        return myLeftArrow;
    }

    public boolean isMyRightArrow() {
        return myRightArrow;
    }

    public void setMyWarriorSelected(boolean myWarriorSelected) {
        this.myWarriorSelected = myWarriorSelected;
    }

    public void setMyThiefSelected(boolean myThiefSelected) {
        this.myThiefSelected = myThiefSelected;
    }

    public void setMyPriestessSelected(boolean myPriestessSelected) {
        this.myPriestessSelected = myPriestessSelected;
    }


    public void setMyUsernameBoxSelected(boolean myUsernameBoxSelected) {
        this.myUsernameBoxSelected = myUsernameBoxSelected;
    }

    public String getMyUserName() {
        return userName.toString();
    }

    public void setMyUserName(String myUserName) {
        this.myUserName = myUserName;
    }

    public void setMyCloseBattleWindow(boolean theCloseBattleWindow) {
        myCloseBattleWindow = theCloseBattleWindow;
    }

    public void setMyEasySelected(boolean myEasySelected) {
        this.myEasySelected = myEasySelected;
    }
}
