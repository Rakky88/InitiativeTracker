package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import view.Main;
import view.SceneManager;
import java.util.ArrayList;

public class RandomLocationController {
    private final SceneManager SCENEMANAGER = Main.getSceneManager();
    @FXML
    private Label generatedLocation;
    private ArrayList<String> randomLocation = new ArrayList<>();
    private int randomLocationIndex;

    public void initialize() {
        setRandomLocations();
    }

    public void doBack() {
        SCENEMANAGER.showRandomWelcomeScene();
    }

    public void doLocation(){
        int newRandomLocationIndex = (int) (Math.random() * randomLocation.size());

        if(newRandomLocationIndex != randomLocationIndex) {
            randomLocationIndex = newRandomLocationIndex;
            String theRandomLocation = randomLocation.get(randomLocationIndex);
            generatedLocation.setText(theRandomLocation);
            return;
        }

        doLocation();
    }

    public void setRandomLocations(){
        randomLocation.add("Sewers");
        randomLocation.add("Artic");
        randomLocation.add("Swamp");
        randomLocation.add("Mountains");
        randomLocation.add("Grassy field");
        randomLocation.add("Undermountain");
        randomLocation.add("Hell");
        randomLocation.add("Abyss");
        randomLocation.add("Fey realm");
        randomLocation.add("Shadowfell");
        randomLocation.add("Astral plane");
        randomLocation.add("Wizard tower");
        randomLocation.add("Prison");
        randomLocation.add("Church");
        randomLocation.add("Castle");
        randomLocation.add("School");
        randomLocation.add("Orphanage");
        randomLocation.add("Forest");
        randomLocation.add("Jungle");
        randomLocation.add("Temple");
        randomLocation.add("Marsh");
        randomLocation.add("Elemental plane of Fire");
        randomLocation.add("Elemental plane of Water");
        randomLocation.add("Elemental plane of Air");
        randomLocation.add("Elemental plane of Earth");
    }
}
