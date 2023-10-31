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
    private ArrayList<String> randomLocation;

    public void initialize() {
        randomLocation = new ArrayList<>();
        randomLocation.add("Sewers");
        randomLocation.add("Artic");
        randomLocation.add("Swamp");
        randomLocation.add("Mountains");
        randomLocation.add("Grassy field");
        randomLocation.add("Undermountain");
        randomLocation.add("Hell");
    }

    public void doBack() {
        SCENEMANAGER.showRandomWelcomeScene();
    }

    public void doLocation(){
        int index = (int) (Math.random() * randomLocation.size());

        String theRandomLocation = randomLocation.get(index);
        generatedLocation.setText(theRandomLocation);
    }
}
