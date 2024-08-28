package Controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class GameSounds {
    public GameSounds() {

    }

    public void playClickSound(final int theSoundNumber){
        String filePath = "";
        String myButtonClickedSound = "clicksound.wav";
        String myWarriorAttackSound = "warriorAttack.wav";
        String myLoseSound = "loser.wav";
        String myWinSound = "winner.wav";
        String myPickupSound = "itemPickup.wav";
        switch (theSoundNumber) {
            case 1 -> filePath = myButtonClickedSound;
            case 2 -> filePath = myWarriorAttackSound;
            case 3 -> filePath = myWinSound;
            case 4 -> filePath = myLoseSound;
            case 5 -> filePath = myPickupSound;

        }
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/Sounds/" + filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void playBackgroundMusic() {
        try {
            String myBackgroundMusic = "gameSound.wav";
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/Sounds/" + myBackgroundMusic));
            Clip backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioInputStream);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the background music continuously
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
