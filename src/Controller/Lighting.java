package Controller;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {
    private long myVisionTimer;
    private final long myVisionDuration = 10000;
    private BufferedImage myDarknessFiter;
    private final GameUI myGameUi;
    private float myFilterAlpha = 1f; // Default to 1 (fully opaque) if no potion is used
    private double myScale = 1;
    private boolean myVisionPotionUsed;
    private boolean myHealthPotionUsed;

    public Lighting(GameUI theGameUI) {
        myGameUi = theGameUI;
    }

    public void setGameLight(final int theCircleSize) {
        // Buffer Image
        myDarknessFiter = new BufferedImage(myGameUi.getMyDungeonPanel().getMyWidth(), myGameUi.getMyDungeonPanel().getMyHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = myDarknessFiter.createGraphics();

        // Center X and Y of the light circle
        int centerX = myGameUi.getMyCharacter().getMyScreenX() + myGameUi.getMyDungeonPanel().getMyTileSize() / 2;
        int centerY = myGameUi.getMyCharacter().getMyScreenY() + myGameUi.getMyDungeonPanel().getMyTileSize() / 2;

        // Define colors and fractions for the radial gradient
        Color[] colors = {
                new Color(0, 0, 0, 0f), // Transparent at the center
                new Color(0, 0, 0, 0.25f), // Semi-transparent black
                new Color(0, 0, 0, 0.5f), // More opaque black
                new Color(0, 0, 0, 0.75f), // Nearly opaque black
                new Color(0, 0, 0, 1f)}; // Fully opaque black
        float[] fractions = {0f, 0.25f, 0.5f, 0.75f, 1f};

        // Create and apply the radial gradient paint
        int radius = (int) (theCircleSize * myScale); // Adjust the radius here
        RadialGradientPaint gradientPaint = new RadialGradientPaint(centerX, centerY, radius, fractions, colors);
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, myGameUi.getMyDungeonPanel().getMyWidth(), myGameUi.getMyDungeonPanel().getMyHeight());
        g2d.dispose();
    }

    public void update() {
        //myFilterAlpha = 1f;
        if (myVisionPotionUsed) {
            if(myGameUi.getMyGameControls().isMyEasySelected()) {
                myScale = 3.5;
            }else{
                myScale = 2.5;
            }
            if (System.currentTimeMillis() - myVisionTimer >= myVisionDuration) {
                myVisionPotionUsed = false;
                myFilterAlpha = 1f;
                myScale = 1; // Reset the scale to 1 when the vision potion expires
            }
        }
        else {
            if(myGameUi.getMyGameControls().isMyEasySelected()) {
                myScale = 2.5;
            }else{
                myScale = 1.5;
            }

        }
    }



    public void draw(final Graphics2D theG2d) {


        setGameLight((int) (64 * myScale));
        // Apply darkness filter
        theG2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, myFilterAlpha));
        theG2d.drawImage(myDarknessFiter, 0, 0, null);
        theG2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Draw time of day indicator
        String timeOfDay = "Dawn";
        theG2d.setColor(Color.WHITE);
        theG2d.setFont(theG2d.getFont().deriveFont(30f));
        theG2d.drawString(timeOfDay, 850, 700);

        if (myGameUi.getMyGameControls().isMyInventorySelected()) {

            if(!myGameUi.getMyGameControls().closeWindow()) {
                myGameUi.drawInventory(theG2d);
            }
        }
        myGameUi.drawInventoryRectangle(theG2d);


        myGameUi.getMyTileManager().drawMonsterEncounter(theG2d);


    }
    public boolean isMyVisionPotionUsed() {
        return myVisionPotionUsed;
    }

    public void setMyVisionPotionUsed(boolean theVisionPotionUsed) {
        myVisionPotionUsed = theVisionPotionUsed;
    }

    public long getMyVisionTimer() {
        return myVisionTimer;
    }

    public void setMyVisionTimer(long theVisionTimer) {
        myVisionTimer = theVisionTimer;
    }

    public double getMyScale() {
        return  myScale;
    }

    public void setMyScale(int theScale) {
        myScale = theScale;
    }

    public void setMyHealthPotionUsed(boolean theHealthPotionUsed) {
        myHealthPotionUsed = theHealthPotionUsed;
    }
}