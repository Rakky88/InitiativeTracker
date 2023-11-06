package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import view.Main;
import view.SceneManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RandomLocationController {
    private final SceneManager SCENEMANAGER = Main.getSceneManager();
    @FXML
    private Label generatedLocation;
    private Set<String> randomLocation = new HashSet<>();
    private ArrayList<String> randomLocationList = new ArrayList<>(randomLocation);

    public void initialize() {
        setRandomLocations();
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
    }

    public void doBack() {
        SCENEMANAGER.showRandomWelcomeScene();
    }

    public void doLocation(){
        int index = (int) (Math.random() * randomLocationList.size());

        String theRandomLocation = randomLocationList.get(index);
        generatedLocation.setText(theRandomLocation);
    }
}
