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
    private ArrayList<String> firstNameMale = new ArrayList<>();
    private ArrayList<String> firstNameFemale = new ArrayList<>();
    private ArrayList<String> lastName = new ArrayList<>();

    public void initialize(){
        initMaleFirstName();
        initLastName();
        initFemaleFirstName();
    }

    public void initMaleFirstName(){
        firstNameMale.add("Ben");
        firstNameMale.add("Joe");
        firstNameMale.add("Rick");
        firstNameMale.add("Dimitri");
        firstNameMale.add("Michael");
        firstNameMale.add("Tim");
    }

    public void initFemaleFirstName(){
        firstNameFemale.add("Miriam");
        firstNameFemale.add("Tess");
        firstNameFemale.add("Joan");
        firstNameFemale.add("Kirsten");
        firstNameFemale.add("Esmee");
        firstNameFemale.add("Scarlet");
    }

    public void initLastName(){
        lastName.add("MeijerHof");
        lastName.add("Groot");
        lastName.add("Heijs");
        lastName.add("Loving");
        lastName.add("Milestone");
        lastName.add("Limes");
        lastName.add("Stiller");
        lastName.add("Mills");
    }

    public void doBack() {
        SCENEMANAGER.showRandomWelcomeScene();
    }

    public void doMaleName(){
        int indexFirstName = (int) (Math.random() * firstNameMale.size());
        int indexLastName = (int) (Math.random() * lastName.size());

        String maleFullName = firstNameMale.get(indexFirstName) + " " + lastName.get(indexLastName);
        generatedName.setText(maleFullName);
    }

    public void doFemaleName(){
        int indexFirstName = (int) (Math.random() * firstNameFemale.size());
        int indexLastName = (int) (Math.random() * lastName.size());

        if(indexFirstName == 0 && (indexLastName == 0 || indexLastName == 1)) {
            String maleFullName = firstNameFemale.get(indexFirstName) + " " + lastName.get(indexLastName);
            generatedName.setText(maleFullName);
        } else {
            String maleFullName = firstNameFemale.get(indexFirstName) + " " + lastName.get(indexLastName);
            generatedName.setText(maleFullName);
        }
    }
}
