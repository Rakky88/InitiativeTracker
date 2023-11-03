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
        firstNameMale.add("Rick");
        firstNameMale.add("Ben");
        firstNameMale.add("Joe");
        firstNameMale.add("Dimitri");
        firstNameMale.add("Michael");
        firstNameMale.add("Tim");
        firstNameMale.add("Mick");
        firstNameMale.add("Mickey");
        firstNameMale.add("Paul");
        firstNameMale.add("Roland");
        firstNameMale.add("Peter");
        firstNameMale.add("Derrick");
        firstNameMale.add("Dennis");
        firstNameMale.add("Frank");
        firstNameMale.add("Harry");
        firstNameMale.add("Matthew");
        firstNameMale.add("Arnold");
        firstNameMale.add("Sam");
    }

    public void initFemaleFirstName(){
        firstNameFemale.add("Miriam");
        firstNameFemale.add("Tess");
        firstNameFemale.add("Joan");
        firstNameFemale.add("Kirsten");
        firstNameFemale.add("Esmee");
        firstNameFemale.add("Scarlet");
        firstNameFemale.add("Ayten");
        firstNameFemale.add("Mary");
        firstNameFemale.add("Heather");
        firstNameFemale.add("Fleur");
        firstNameFemale.add("Merle");
        firstNameFemale.add("Sue");
        firstNameFemale.add("Madelon");
    }

    public void initLastName(){
        lastName.add("Meijerhof");
        lastName.add("Groot");
        lastName.add("Heijs");
        lastName.add("Loving");
        lastName.add("Milestone");
        lastName.add("Limes");
        lastName.add("Stiller");
        lastName.add("Mills");
        lastName.add("Fledder");
        lastName.add("Kingsly");
        lastName.add("Zuthphen");
        lastName.add("Wernink");
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
