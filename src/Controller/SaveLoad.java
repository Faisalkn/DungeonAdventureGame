package Controller;

import Model.Dungeon;
import View.DungeonPanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.Map;
import java.util.Objects;


public class SaveLoad {
    private final DungeonPanel myDungeonPanel;
    private static String SAVE_FILE_PATH = "DungeonAdventure/src/SaveGame/save.dat";

    public SaveLoad(DungeonPanel theDungeonPanel) {
        myDungeonPanel = theDungeonPanel;
    }

    public void save() {


        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_PATH))) {
            DataStorage ds = new DataStorage();

            ds.setMyName(myDungeonPanel.getMyGameUi().getMyGameControls().getMyUserName());
            ds.setMyX(myDungeonPanel.getMyCharacter().getMyX());
            ds.setMyY(myDungeonPanel.getMyCharacter().getMyY());
            ds.setMyX(myDungeonPanel.getMyCharacter().getMyX());
            ds.setMyPlayerHitPoint(myDungeonPanel.getMyCharacter().getCharacterType().getMyHitPoints());
            ds.setMyMaxHitPoint(myDungeonPanel.getMyCharacter().getMyMaxHeroHitPoint());
            ds.setMySize(myDungeonPanel.getMyGameUi().getMyMazeSize());
            ds.setMyTileNums(myDungeonPanel.getMyTileManager().getMapTileNums());
            ds.setMyPriestessSelected(myDungeonPanel.getMyGameUi().getMyGameControls().isMyPriestessSelected());
            ds.setMyWarriorSelected(myDungeonPanel.getMyGameUi().getMyGameControls().isMyWarriorSelected());
            ds.setMyThiefSelected(myDungeonPanel.getMyGameUi().getMyGameControls().isMyThiefSelected());
            ds.setMyEasyModeSelected(myDungeonPanel.getMyGameUi().getMyGameControls().isMyEasySelected());


            if (myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().containsKey("V")) {
                ds.setMyVisionPotionAmount(myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().get("V"));

            }
            if (myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().containsKey("H")) {
                ds.setMyHealthPotionAmount(myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().get("H"));

            }
            if (myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().containsKey("A")) {
                ds.setMyPillarAAmount(myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().get("A"));

            }
            if (myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().containsKey("I")) {
                ds.setMyPillarIAmount(myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().get("I"));

            }
            if (myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().containsKey("P")) {
                ds.setMyPillarPAmount(myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().get("P"));

            }
            if (myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().containsKey("E")) {
                ds.setMyPillarEAmount(myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().get("E"));

            }



            for (SuperItems item : myDungeonPanel.getMyItems()) {
                ds.getItemCoordinates().add(item.getWorldX() / 64);
                ds.getItemCoordinates().add(item.getMyY() / 64);
                ds.getItemNames().add(item.getMyName());
            }

            for (Monsters monster : myDungeonPanel.getMyMonsters()) {
                ds.getMonsterCoordinates().add(monster.getMyX() / 64);
                ds.getMonsterCoordinates().add(monster.getMyY() / 64);
                ds.getMonsterTypes().add(monster.getMonsterType());
            }

            oos.writeObject(ds);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE_PATH))) {
            DataStorage ds = (DataStorage) ois.readObject();

            myDungeonPanel.getMyItems().clear();
            myDungeonPanel.getMyMonsters().clear();
            myDungeonPanel.getMyDefaultItems().clear();
            myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().clear();
            myDungeonPanel.getMyDefaultMonsters().clear();
            myDungeonPanel.getMyTileManager().setMyCol(ds.getMySize() * 5);
            myDungeonPanel.getMyTileManager().setMyRow(ds.getMySize() * 5);
            myDungeonPanel.getMyTileManager().setMapTileNums(ds.getMyTileNums());

            if (ds.isMyPriestessSelected()) {
                myDungeonPanel.getMyCharacter().getMyPriestess().setMyX(ds.getMyX());
                myDungeonPanel.getMyCharacter().getMyPriestess().setMyY(ds.getMyY());
            } else if (ds.isMyThiefSelected()) {
                myDungeonPanel.getMyCharacter().getMyThief().setMyX(ds.getMyX());
                myDungeonPanel.getMyCharacter().getMyThief().setMyY(ds.getMyY());
            } else if (ds.isMyWarriorSelected()) {
                myDungeonPanel.getMyCharacter().getMyWarrior().setMyX(ds.getMyX());
                myDungeonPanel.getMyCharacter().getMyWarrior().setMyY(ds.getMyY());
            }
            myDungeonPanel.getMyGameUi().getMyGameControls().setMyEasySelected(ds.isMyEasyModeSelected());




            myDungeonPanel.getMyGameUi().getMyGameControls().setMyThiefSelected(ds.isMyThiefSelected());
            myDungeonPanel.getMyGameUi().getMyGameControls().setMyWarriorSelected(ds.isMyWarriorSelected());
            myDungeonPanel.getMyGameUi().getMyGameControls().setMyPriestessSelected(ds.isMyPriestessSelected());
            myDungeonPanel.getMyGameUi().getMyGameControls().setMyUsernameBoxSelected(true);

            myDungeonPanel.getMyCharacter().getCharacterType().setMyHitPoints(ds.getMyPlayerHitPoint());
            myDungeonPanel.getMyCharacter().setMyMaxHeroHitPoint(ds.getMyMaxHitPoint());
            myDungeonPanel.getMyGameUi().setMyUserName(ds.getMyName());

            SuperItems pillarP = new SuperItems(myDungeonPanel.getMyGameUi());
            SuperItems pillarA = new SuperItems(myDungeonPanel.getMyGameUi());
            SuperItems pillarE = new SuperItems(myDungeonPanel.getMyGameUi());
            SuperItems pillarI = new SuperItems(myDungeonPanel.getMyGameUi());
            SuperItems exitChest = new SuperItems(myDungeonPanel.getMyGameUi());

            pillarP.setMyName("P");
            pillarA.setMyName("A");
            pillarE.setMyName("E");
            pillarI.setMyName("I");
            exitChest.setMyName("o");
            setItemImage(pillarE);
            setItemImage(pillarP);
            setItemImage(pillarA);
            setItemImage(pillarI);

            myDungeonPanel.getMyDefaultItems().put(pillarE.getMyName(), pillarE);
            myDungeonPanel.getMyDefaultItems().put(pillarI.getMyName(), pillarI);
            myDungeonPanel.getMyDefaultItems().put(pillarP.getMyName(), pillarP);
            myDungeonPanel.getMyDefaultItems().put(pillarA.getMyName(), pillarA);


            // Restore items from saved coordinates
            for (int i = 0; i < ds.getItemCoordinates().size(); i += 2) {
                int x = ds.getItemCoordinates().get(i);
                int y = ds.getItemCoordinates().get(i + 1);
                String name = ds.getItemNames().get(i / 2);

                SuperItems item = new SuperItems(myDungeonPanel.getMyGameUi());
                item.setMyName(name);
                item.setWorldX(x * 64);
                item.setMyY(y * 64);
                item.solidArea.x = item.getWorldX() + item.getMySolidAreaDefaultX();
                item.solidArea.y = item.getMyY() + item.getMySolidAreaDefaultY();
                item.solidArea.width = 64 - 20;
                item.solidArea.height = 64 - 20;

                // Set the item's image
                setItemImage(item);

                myDungeonPanel.getMyItems().add(item);
                if (!name.equals("M") && !name.equals("o") && !name.equals("X")) {
                    myDungeonPanel.getMyDefaultItems().put(name, item);
                }
            }
            int v = ds.getMyVisionPotionAmount();
            myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().put("V", v);
            myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().put("H", ds.getMyHealthPotionAmount());
            myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().put("I", ds.getMyPillarIAmount());
            myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().put("A", ds.getMyPillarAAmount());
            myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().put("P", ds.getMyPillarPAmount());
            myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().put("E", ds.getMyPillarEAmount());

            for (Map.Entry<String, Integer> entry : myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().entrySet()) {
                if(myDungeonPanel.getMyTileManager().getMyItemCollisionFrequency().get(entry.getKey()) > 0){
                    if(myDungeonPanel.getMyDefaultItems().containsKey(entry.getKey())){
                        myDungeonPanel.getMyDefaultItems().get(entry.getKey()).setMyCollisoin(true);
                    }
                }
            }

            for (int i = 0; i < ds.getMonsterCoordinates().size(); i += 2) {
                int x = ds.getMonsterCoordinates().get(i);
                int y = ds.getMonsterCoordinates().get(i + 1);
                String type = ds.getMonsterTypes().get(i / 2);

                Monsters monster = new Monsters(myDungeonPanel.getMyGameUi(), type);
                monster.setMyX(x * 64);
                monster.setMyY(y * 64);
                monster.solidArea.x = monster.getMyX();
                monster.solidArea.y = monster.getMyY();
                monster.solidArea.width = 64 * 3;
                monster.solidArea.height = 64 * 3;


                setMonsterImage(monster);

                myDungeonPanel.getMyMonsters().add(monster);
                myDungeonPanel.getMyDefaultMonsters().put(type, monster);
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setItemImage(SuperItems item) {
        BufferedImage itemImage = null;
        try {
            switch (item.getMyName()) {
                case "P" -> itemImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Objects/PillarP.png")));
                case "A" -> itemImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Objects/PillarA.png")));
                case "E" -> itemImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Objects/PillarE.png")));
                case "I" -> itemImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Objects/PillarI.png")));
                case "V" -> itemImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Objects/VisionPotion.png")));
                case "H" -> itemImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Objects/HealthPotion.png")));
                case "o" -> itemImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Objects/ExitChest.png")));
                case "M" -> itemImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Objects/MultipleItems.png")));
                case "X" -> itemImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Objects/pitImg.png")));
                // Add more cases for other item types if needed
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (itemImage != null) {
            item.setMyImage(itemImage);
        }
    }

    private void setMonsterImage(Monsters monster) {
        BufferedImage monsterImage = null;
        try {
            switch (monster.getMonsterType()) {
                case "Ogre" -> monsterImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/MonsterImages/Ogre.png")));
                case "Gremlin" -> monsterImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/MonsterImages/Gremlin.png")));
                case "Skeleton" -> monsterImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/MonsterImages/Skeleton.png")));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (monsterImage != null) {
            monster.setMyMonsterImage(monsterImage);
        }
    }
}