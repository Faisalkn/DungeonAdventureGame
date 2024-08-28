package Controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class GameSounds {

    private final String myButtonClickedSound = "clicksound.wav";
    private final String myWarriorAttackSound = "warriorAttack.wav";
    private final String myLoseSound = "loser.wav";
    private final String myWinSound = "winner.wav";
    private final String myGameSound = "gameSound.wav";
    private final String myPickupSound = "itemPickup.wav";
    public GameSounds() {

    }

    public void playClickSound(final int theSoundNumber){
        String filePath = "";
        switch (theSoundNumber) {
            case 1 -> filePath = myButtonClickedSound;
            case 2 -> filePath = myWarriorAttackSound;
            case 3 -> filePath = myWinSound;
            case 4 -> filePath = myLoseSound;
            case 5 -> filePath = myPickupSound;

        }
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("DungeonAdventure/src/Sounds/" + filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private final String myBackgroundMusic = "gameSound.wav";

    // Add the background music file name

    private Clip backgroundMusicClip;
    public void playBackgroundMusic() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("DungeonAdventure/src/Sounds/" + myBackgroundMusic));
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioInputStream);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the background music continuously
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop(); // Stop the background music
        }
    }
}
