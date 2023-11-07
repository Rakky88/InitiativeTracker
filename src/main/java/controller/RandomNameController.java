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

        String femaleFullName = firstNameFemale.get(indexFirstName) + " " + lastName.get(indexLastName);
        generatedName.setText(femaleFullName);
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
        firstNameMale.add("Ryan");
        firstNameMale.add("Emil");
        firstNameMale.add("Leo");
        firstNameMale.add("Wyatt");
        firstNameMale.add("Fabian");
        firstNameMale.add("Samson");
        firstNameMale.add("Kobi");
        firstNameMale.add("Kayne");
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
        firstNameFemale.add("Marina");
        firstNameFemale.add("Ashley");
        firstNameFemale.add("Hollie");
        firstNameFemale.add("Lilly");
        firstNameFemale.add("Molly");
        firstNameFemale.add("Emmie");
        firstNameFemale.add("Kate");
        firstNameFemale.add("Lydia");
        firstNameFemale.add("Felicia");
        firstNameFemale.add("Janice");
        firstNameFemale.add("Anna");
        firstNameFemale.add("Nicole");
        firstNameFemale.add("Diane");
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
        lastName.add("Bowman");
        lastName.add("O'Ryan");
        lastName.add("Wilkinson");
        lastName.add("Gilmore");
        lastName.add("Hammond");
        lastName.add("Duke");
        lastName.add("Richmond");
        lastName.add("Conner");
        lastName.add("Browning");
        lastName.add("Anderson");
        lastName.add("Green");
        lastName.add("Davis");
        lastName.add("Underwood");
        lastName.add("Holmes");
        lastName.add("Moore");
    }
}