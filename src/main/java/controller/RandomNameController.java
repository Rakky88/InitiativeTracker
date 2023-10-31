package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import view.Main;
import view.SceneManager;

import java.util.ArrayList;

public class RandomNameController {
    private final SceneManager SCENEMANAGER = Main.getSceneManager();
    @FXML
    private Label generatedName;
    private ArrayList<String> firstNameMale;
    private ArrayList<String> firstNameFemale;
    private ArrayList<String> lastNameMale;
    private ArrayList<String> lastNameFemale;

    public void initialize(){
        firstNameMale = new ArrayList<>();
        firstNameMale.add("Ben");
        firstNameMale.add("Joe");
        firstNameMale.add("Rick");
        firstNameMale.add("Dimitri");

        lastNameMale = new ArrayList<>();
        lastNameMale.add("Stiller");
        lastNameMale.add("Groot");
        lastNameMale.add("Mills");

        firstNameFemale = new ArrayList<>();
        firstNameFemale.add("Miriam");
        firstNameFemale.add("Tess");

        lastNameFemale = new ArrayList<>();
        lastNameFemale.add("MeijerHof");
        lastNameFemale.add("Heijs");
    }

    public void doBack() {
        SCENEMANAGER.showRandomWelcomeScene();
    }

    public void doMaleName(){
        int indexFirstName = (int) (Math.random() * firstNameMale.size());
        int indexLastName = (int) (Math.random() * lastNameMale.size());

        String maleFullName = firstNameMale.get(indexFirstName) + " " + lastNameMale.get(indexLastName);
        generatedName.setText(maleFullName);
    }

    public void doFemaleName(){
        int indexFirstName = (int) (Math.random() * firstNameFemale.size());
        int indexLastName = (int) (Math.random() * lastNameFemale.size());

        String maleFullName = firstNameFemale.get(indexFirstName) + " " + lastNameFemale.get(indexLastName);
        generatedName.setText(maleFullName);
    }
}
