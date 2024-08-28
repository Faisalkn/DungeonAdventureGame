package Controller;

import java.awt.image.BufferedImage;

public class Tile {

    private boolean mySolid = false;
    private BufferedImage myTileImage;

    public boolean isSolid() {
        return mySolid;
    }

    public BufferedImage getMyTileImage() {
        return myTileImage;
    }

    public void setMySolid(final boolean theSolid) {
        mySolid = theSolid;
    }



    public void setMyTileImage(final BufferedImage theTileImage) {
        myTileImage = theTileImage;
    }
}
