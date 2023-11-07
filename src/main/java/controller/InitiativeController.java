package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Creature;
import view.Main;
import view.SceneManager;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**Deze class is gelinkt met initiativeScene.fxml en geeft een scherm weer waar je creatures kan invoeren voor
 * initiative, voordat je naar het echte initiative bijhoudt scherm gaat.
 *
 * @author R.Groot
 */
public class InitiativeController {
    private final SceneManager SCENEMANAGER = Main.getSceneManager();
    private ArrayList<Creature> initiative = new ArrayList<>();

    //Checkboxxes
    @FXML
    private CheckBox lairActionCheckBox;
    @FXML
    private ListView<Creature> initiativeList;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField initiativeTextField;
    @FXML
    private TextField hpTextField;
    @FXML
    private TextField maxHPTextField;
    @FXML
    private TextField legResTextField;
    @FXML
    private TextField legActTextField;
    @FXML
    private VBox legendaryControls;
    @FXML
    private CheckBox legendaryCheckBox;

    /**Deze methode wordt gestart wanneer naar dit scherm wordt gegaan. Momenteel doet deze methode niets, maar
     * in de toekomst kunnen hier dingen aan toegevoegd worden als nodig.
     */
    public void setup() {
        legendaryCheckBox.setSelected(false);
        legendaryControls.setVisible(false);
    }

    /**Deze methode zet de initiativeList op volgorde van initiative (hoog naar laag).
     *
     */
    public void orderList() {
        initiative.sort((c1, c2) -> Double.compare(c2.getInitiative(), c1.getInitiative()));
        initiativeList.getItems().setAll(initiative);
    }

    public void handleLegendaryCheckBox(){
        legendaryControls.setVisible(legendaryCheckBox.isSelected());
        if(legendaryCheckBox.isSelected()) {
            legendaryControls.setVisible(true);
            legResTextField.setText(String.valueOf(0));
        } else{
            legendaryControls.setVisible(false);
        }
    }

    /**Met deze methode wordt een creature toegevoegd aan de initiativeList.
     *
     */
    public void doAdd() {
        if(validateCreature()) {
            try {
                double getInitiative = Double.parseDouble(initiativeTextField.getText());
                int getHP = Integer.parseInt(hpTextField.getText());
                int getMaxHP = Integer.parseInt(maxHPTextField.getText());
                int legRes = 0;
                int legAct = 0;

                if (!legResTextField.getText().isEmpty()) {
                    legRes = Integer.parseInt(legResTextField.getText());
                }

                if (!legActTextField.getText().isEmpty()) {
                    legAct = Integer.parseInt(legActTextField.getText());
                }

                initiative.add(new Creature(nameTextField.getText(), getInitiative, getHP, getMaxHP, legRes, legAct));

                nameTextField.setText("");
                initiativeTextField.setText("");
                hpTextField.setText("");
                maxHPTextField.setText("");
                legResTextField.setText("0");
                legActTextField.setText("0");
                legendaryCheckBox.setSelected(false);
                legendaryControls.setVisible(false);

                orderList();
            } catch (NumberFormatException exception) {
                showAlert("Initiative and HP must be valid numbers!");
            }
        }
    }

    /**Met deze methode wordt de gebruiker naar de initiativeTracker scherm gebracht.
     *
     */
    public void doTracker() {
        if(initiative == null) {
            showAlert("Your initiative list is empty!");
        }

        SCENEMANAGER.showInitiativeTrackerScene(initiative, lairActionCheckBox.isSelected());
    }

    /**Met deze methode wordt een creature uit de initiativeList gehaald.
     *
     */
    public void doDelete() {
        Creature selectedCreature = initiativeList.getSelectionModel().getSelectedItem();
        if (selectedCreature != null) {
            initiative.remove(selectedCreature);
            initiativeList.getItems().remove(selectedCreature);
        }
    }

    /**Met deze methode wordt een creature die de gebruiker probeert toe te voegen gevallideerd op een aantal
     * punten. De creature kan alleen toegevoegd worden als het overal aan voldoet.
     *
     * @return: true als het overal aan voldoet.
     */
    public boolean validateCreature() {
        String name = nameTextField.getText();
        String initiativeText = initiativeTextField.getText();
        String hpText = hpTextField.getText();
        String maxHPText = maxHPTextField.getText();

        if (name.isEmpty() || initiativeText.isEmpty() || hpText.isEmpty() || maxHPText.isEmpty()) {
            showAlert("All fields are required!");
            return false;
        }

        if(initiative != null) {
            ArrayList<String> names = initiative.stream().map(Creature::getName).collect(Collectors.toCollection(ArrayList::new));
            for (String creatureName : names) {
                if(name.equalsIgnoreCase(creatureName)) {
                    showAlert("This name is already in the initiative list!");
                    return false;
                }
            }
        }

        if (Integer.parseInt(hpText) < 0 || Integer.parseInt(maxHPText) < 0) {
            showAlert("You can't add a creature with less then 0 (max) HP.");
            return false;
        }

        if(nameTextField.getText().length() > 20) {
            showAlert("The creature's name can't be more then 20 characters long.");
            return false;
        }

        if (Integer.parseInt(hpText) > Integer.parseInt(maxHPText)) {
            showAlert("HP can't be higher than max HP!");
            return false;
        }

        return true;
    }

    public void lowerLegRes(){
        int newLegRes = Integer.parseInt(legResTextField.getText()) - 1;
        if(newLegRes < 0) {
            showAlert("A creature can't have less then 0 legendary resistances!");
            return;
        }

        legResTextField.setText(String.valueOf(newLegRes));
    }

    public void addLegRes(){
        if(legResTextField.getText().isEmpty()) {
            legResTextField.setText("0");
        }

        int newLegRes = Integer.parseInt(legResTextField.getText()) + 1;
        if(newLegRes > 5) {
            showAlert("A creature can't have more then 5 legendary resistances in this program!");
            return;
        }

        legResTextField.setText(String.valueOf(newLegRes));
    }

    public void lowerLegAct(){
        int newLegAct = Integer.parseInt(legActTextField.getText()) - 1;
        if(newLegAct < 0) {
            showAlert("A creature can't have less then 0 legendary actions!");
            return;
        }

        legActTextField.setText(String.valueOf(newLegAct));
    }

    public void addLegAct(){
        if(legActTextField.getText().isEmpty()) {
            legActTextField.setText("0");
        }


        int newLegAct = Integer.parseInt(legActTextField.getText()) + 1;
        if(newLegAct > 5) {
            showAlert("A creature can't have more then 5 legendary actions in this program!");
            return;
        }

        legActTextField.setText(String.valueOf(newLegAct));
    }

    public void doMenu(){
        SCENEMANAGER.showMenuScene();
    }

    /**Geeft een error message als deze methode wordt aangeroepen.
     *
     * @param message: het bericht dat weergegeven wordt in de error message.
     */
    public void showAlert(String message) {
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setContentText(message);
        errorMessage.show();
    }
}