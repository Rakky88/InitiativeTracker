package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Creature;
import view.Main;
import view.SceneManager;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**Deze class is gelinkt met welcomeScene.fxml en geeft een scherm weer waar je creatures kan invoeren voor
 * initiative, voordat je naar het echte initiative bijhoudt scherm gaat.
 *
 * @author R.Groot
 */
public class WelcomeController {
    private final SceneManager SCENEMANAGER = Main.getSceneManager();
    private ArrayList<Creature> initiative = new ArrayList<>();
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

    /**Deze methode wordt gestart wanneer naar dit scherm wordt gegaan. Momenteel doet deze methode niets, maar
     * in de toekomst kunnen hier dingen aan toegevoegd worden als nodig.
     */
    public void setup() {}

    /**Deze methode zet de initiativeList op volgorde van initiative (hoog naar laag).
     *
     */
    public void orderList() {
        initiative.sort((c1, c2) -> Double.compare(c2.getInitiative(), c1.getInitiative()));
        initiativeList.getItems().setAll(initiative);
    }

    /**Met deze methode wordt een creature toegevoegd aan de initiativeList.
     *
     */
    public void doAdd() {
        if(doValidate()) {
            try {
                double getInitiative = Double.parseDouble(initiativeTextField.getText());
                int getHP = Integer.parseInt(hpTextField.getText());
                int getMaxHP = Integer.parseInt(maxHPTextField.getText());
                initiative.add(new Creature(nameTextField.getText(), getInitiative, getHP, getMaxHP));

                nameTextField.setText("");
                initiativeTextField.setText("");
                hpTextField.setText("");
                maxHPTextField.setText("");

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
        if(initiative != null) {
            SCENEMANAGER.showTrackerScene(initiative);
        } else {
            showAlert("Your initiative list is empty!");
        }
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
    public boolean doValidate() {
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

        return true;
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