package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import view.Main;
import view.SceneManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RandomNameController {
    private final SceneManager SCENEMANAGER = Main.getSceneManager();
    @FXML
    private Label generatedName;
    private Set<String> firstNameMale = new HashSet<>();
    private Set<String> firstNameFemale = new HashSet<>();
    private Set<String> lastName = new HashSet<>();
    private ArrayList<String> firstNameMaleList = new ArrayList<>(firstNameMale);
    private ArrayList<String> firstNameFemaleList = new ArrayList<>(firstNameFemale);
    private ArrayList<String> lastNameList = new ArrayList<>(lastName);

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
        firstNameMale.add("Wesley");
        firstNameMale.add("Brian");
        firstNameMale.add("Jesse");
        firstNameMale.add("Robin");
        firstNameMale.add("Will");
        firstNameMale.add("Jack");
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
        firstNameFemale.add("Suzan");
        firstNameFemale.add("Suzanne");
        firstNameFemale.add("Anne");
        firstNameFemale.add("Robin");
        firstNameFemale.add("Rose");
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
        lastName.add("Bennink");
        lastName.add("Keystone");
        lastName.add("Heart");
        lastName.add("Harkness");
        lastName.add("Soul");
    }

    public void doBack() {
        SCENEMANAGER.showRandomWelcomeScene();
    }

    public void doMaleName(){
        int indexFirstName = (int) (Math.random() * firstNameMaleList.size());
        int indexLastName = (int) (Math.random() * lastNameList.size());

        String maleFullName = firstNameMaleList.get(indexFirstName) + " " + lastNameList.get(indexLastName);
        generatedName.setText(maleFullName);
    }

    public void doFemaleName(){
        int indexFirstName = (int) (Math.random() * firstNameFemaleList.size());
        int indexLastName = (int) (Math.random() * lastNameList.size());

        String femaleFullName = firstNameFemaleList.get(indexFirstName) + " " + lastNameList.get(indexLastName);
        generatedName.setText(femaleFullName);
    }
}