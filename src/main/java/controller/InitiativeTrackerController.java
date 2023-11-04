package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import model.Creature;
import view.Main;
import view.SceneManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**Deze class is gelinkt met initiativeTrackerScene.fxml en geeft een scherm weer waar je initiative bij kan houden tijdens
 * Dnd. Verder kan je nog verschillende dingen bijhouden voor de creatures in de initiative lijst.
 *
 * @author R.Groot
 */
public class InitiativeTrackerController {
    private final SceneManager SCENEMANAGER = Main.getSceneManager();
    private int amountHighestInitiativeTurnTaken = 1;
    private int roundCountForLairAction = 1;
    private boolean lairAction = false;

    //Checkboxxes
    @FXML
    private CheckBox concentrationCheckBox;
    @FXML
    private CheckBox blindedCheckBox;
    @FXML
    private CheckBox charmedCheckBox;
    @FXML
    private CheckBox deafenedCheckBox;
    @FXML
    private CheckBox frightenedCheckBox;
    @FXML
    private CheckBox grappledCheckBox;
    @FXML
    private CheckBox incapacitatedCheckBox;
    @FXML
    private CheckBox invisibleCheckBox;
    @FXML
    private CheckBox paralyzedCheckBox;
    @FXML
    private CheckBox petrifiedCheckBox;
    @FXML
    private CheckBox poisonedCheckBox;
    @FXML
    private CheckBox proneCheckBox;
    @FXML
    private CheckBox restrainedCheckBox;
    @FXML
    private CheckBox stunnedCheckBox;
    @FXML
    private CheckBox unconsciousCheckBox;
    @FXML
    private CheckBox exhaustionCheckBox;
    @FXML
    private CheckBox legendaryCheckBox;
    @FXML
    private CheckBox legResCheckBox1;
    @FXML
    private CheckBox legResCheckBox2;
    @FXML
    private CheckBox legResCheckBox3;
    @FXML
    private CheckBox legResCheckBox4;
    @FXML
    private CheckBox legResCheckBox5;
    @FXML
    private CheckBox legActCheckBox1;
    @FXML
    private CheckBox legActCheckBox2;
    @FXML
    private CheckBox legActCheckBox3;
    @FXML
    private CheckBox legActCheckBox4;
    @FXML
    private CheckBox legActCheckBox5;


    //Textfields and TextAreas
    @FXML
    private TextField creatureTurnTextfield;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField initiativeTextField;
    @FXML
    private TextField hpTextField;
    @FXML
    private TextField maxHPTextField;
    @FXML
    private TextField initiativeNameTextfield;
    @FXML
    private TextField initiativeHPTextfield;
    @FXML
    private TextField hpLowerAddTextField;
    @FXML
    private TextField tempHPTextField;
    @FXML
    private TextField roundTextField;
    @FXML
    private TextField exhaustionTextField;
    @FXML
    private TextField ACTextField;
    @FXML
    private TextArea extraInfoTextArea;
    @FXML
    private TextField legResTextField;
    @FXML
    private TextField legActTextField;

    //Labels
    @FXML
    private Label legResNameText;
    @FXML
    private Label legActNameText;

    //Misc
    @FXML
    private ListView<Creature> initiativeList;
    @FXML
    private HBox exhaustionControls;
    @FXML
    private VBox legendaryControls;
    @FXML
    private Button deleteButton;

    /**De setup wordt gestarts wanneer de initiativeTrackerScene.fxml wordt geopend. Hierbij wordt de initiativelijst gevuld met
     * eerder ingevoerde creatures en andere textfields gevuld. Verder wordt een drag/drop-systeem geinitialiseerd
     * voor de initiativeList en worden listeners geinitialiseerd voor extraInfo, tempHP en AC.
     *
     * @param creatures: lijst met creatures die in het vorige scherm zijn ingevoerd voor de initiativeList.
     */
    public void setup(ArrayList<Creature> creatures, boolean lairActionConfirmed){
        initiativeList.getItems().setAll(creatures);
        roundTextField.setText("0");
        legendaryControls.setVisible(false);
        exhaustionControls.setVisible(false);
        ACTextField.setPromptText("--");
        extraInfoTextArea.setPromptText("Select a creature to add extra info.");
        tempHPTextField.setPromptText("--");
        legResNameText.setVisible(false);
        legResCheckBox1.setVisible(false);
        legResCheckBox2.setVisible(false);
        legResCheckBox3.setVisible(false);
        legResCheckBox4.setVisible(false);
        legResCheckBox5.setVisible(false);
        legActNameText.setVisible(false);
        legActCheckBox1.setVisible(false);
        legActCheckBox2.setVisible(false);
        legActCheckBox3.setVisible(false);
        legActCheckBox4.setVisible(false);
        legActCheckBox5.setVisible(false);

        setCreatureTurnTextField();
        updateCreatureStats();

        if(lairActionConfirmed) {
            lairAction = true;
            if(initiativeList.getItems().get(0).getInitiative() < 20) {
                showInfo("Lair action!");
            } else {
                roundCountForLairAction = 0;
            }
        }

        initiativeList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Creature> call(ListView<Creature> param) {
                final int MAX_HP_DIVIDE_BY_2 = 2;
                final int MINIMUM_HP = 0;
                ListCell<Creature> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(Creature creature, boolean empty) {
                        super.updateItem(creature, empty);
                        if (empty || creature == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(creature.toString());

                            if (creature.getHP() == MINIMUM_HP) {
                                setTextFill(Color.RED);
                            } else if (creature.getHP() == 1) {
                                setTextFill(Color.ORANGE);
                            } else if (creature.getHP() < ((double) creature.getMaxHP() / MAX_HP_DIVIDE_BY_2)) {
                                setTextFill(Color.YELLOW);
                            } else if (creature.getHP() >= creature.getMaxHP()) {
                                setTextFill(Color.GREEN);
                            } else {
                                setTextFill(Color.BLACK);
                            }
                        }
                    }
                };

                cell.setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent content = new ClipboardContent();
                        content.putString(String.valueOf(cell.getIndex()));
                        db.setContent(content);
                        event.consume();
                    }
                });

                cell.setOnDragOver(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        if (event.getDragboard().hasString()) {
                            if (cell.getIndex() != Integer.parseInt(event.getDragboard().getString())) {
                                event.acceptTransferModes(TransferMode.MOVE);
                            }
                        }
                        event.consume();
                    }
                });

                cell.setOnDragDropped(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        if (db.hasString()) {
                            int draggedIndex = Integer.parseInt(db.getString());
                            Creature draggedCreature = initiativeList.getItems().get(draggedIndex);

                            int dropIndex = cell.getIndex();
                            ObservableList<Creature> items = FXCollections.observableArrayList(initiativeList.getItems());
                            items.remove(draggedIndex);
                            items.add(dropIndex, draggedCreature);
                            initiativeList.setItems(items);

                            success = true;
                        }
                        event.setDropCompleted(success);
                        event.consume();
                    }
                });

                return cell;
            }
        });

        extraInfoTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (initiativeList.getSelectionModel().getSelectedItem() != null) {
                initiativeList.getSelectionModel().getSelectedItem().setExtraInfo(newValue);
            }
        });

        ACTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (initiativeList.getSelectionModel().getSelectedItem() != null) {
                initiativeList.getSelectionModel().getSelectedItem().setAC(Integer.parseInt(newValue));
            }
        });

        tempHPTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (initiativeList.getSelectionModel().getSelectedItem() != null) {
                int tempHP = 0;
                try {
                    tempHP = Integer.parseInt(newValue);
                    initiativeList.getSelectionModel().getSelectedItem().setTempHP(tempHP);
                } catch (NumberFormatException exception) {
                    showAlert("You must enter a whole number!");
                    tempHPTextField.setText(String.valueOf(tempHP));
                }
            }
        });
    }

    /**Bij het opstarten van dit scherm worden toolTips teksten aangemaakt.
     *
     */
    public void initialize(){
        setTooltip(concentrationCheckBox, "A Concentration check is used when you take damage in combat and you are currently \n" +
                "concentrating on a previously cast spell (Like Bless or Bane). \n" +
                "Typically, you make a Constitution saving throw to maintain concentration on the spell. \n" +
                "The DC equals 10 or half the damage taken, whichever number is higher.\n" +
                "ou lose concentration on a spell if you cast another spell that requires concentration. \n" +
                "You can't concentrate on two spells at once.");
        setTooltip(blindedCheckBox, "- A blinded creature can’t see and automatically fails any ability check that requires sight.\n" +
                        "- Attack rolls against the creature have advantage, and the creature's attack rolls have disadvantage.");
        setTooltip(charmedCheckBox, "- A charmed creature can’t attack the charmer or target the charmer with harmful abilities or magical effects.\n" +
                "- The charmer has advantage on any ability check to interact socially with the creature.");
        setTooltip(deafenedCheckBox, "- A deafened creature can’t hear and automatically fails any ability check that requires hearing.");
        setTooltip(frightenedCheckBox, "- A frightened creature has disadvantage on ability checks and attack rolls while the source of " +
                "\n  its fear is within line of sight.\n" +
                "- The creature can’t willingly move closer to the source of its fear.");
        setTooltip(grappledCheckBox, "- A grappled creature’s speed becomes 0, and it can’t benefit from any bonus to its speed.\n" +
                "- The condition ends if the grappler is incapacitated (see the condition).\n" +
                "- The condition also ends if an effect removes the grappled creature from the reach of the grappler or grappling effect, " +
                "\n  such as when a creature is hurled away by the thunderwave spell.");
        setTooltip(incapacitatedCheckBox, "- An incapacitated creature can’t take actions or reactions.");
        setTooltip(invisibleCheckBox, "- An invisible creature is impossible to see without the aid of magic or a special sense. " +
                "\n  For the purpose of hiding, the creature is heavily obscured. " +
                "\n  The creature’s location can be detected by any noise it makes or any tracks it leaves.\n" +
                "- Attack rolls against the creature have disadvantage, and the creature’s attack rolls have advantage.");
        setTooltip(paralyzedCheckBox, "- A paralyzed creature is incapacitated (see the condition) and can’t move or speak.\n" +
                "- The creature automatically fails Strength and Dexterity saving throws.\n" +
                "- Attack rolls against the creature have advantage.\n" +
                "- Any attack that hits the creature is a critical hit if the attacker is within 5 feet of the creature.");
        setTooltip(petrifiedCheckBox, "- A petrified creature is transformed, along with any nonmagical object it is wearing or carrying, " +
                "\n  into a solid inanimate substance (usually stone). " +
                "\n  Its weight increases by a factor of ten, and it ceases aging.\n" +
                "- The creature is incapacitated (see the condition), can’t move or speak, and is unaware of its surroundings.\n" +
                "- Attack rolls against the creature have advantage.\n" +
                "- The creature automatically fails Strength and Dexterity saving throws.\n" +
                "- The creature has resistance to all damage.\n" +
                "- The creature is immune to poison and disease, although a poison or disease already in its system is suspended, not neutralized.");
        setTooltip(poisonedCheckBox, "- A poisoned creature has disadvantage on attack rolls and ability checks.");
        setTooltip(proneCheckBox, "- A prone creature’s only movement option is to crawl, unless it stands up and thereby ends the condition.\n" +
                "- The creature has disadvantage on attack rolls.\n" +
                "- An attack roll against the creature has advantage if the attacker is within 5 feet of the creature. Otherwise, the attack roll has disadvantage.");
        setTooltip(restrainedCheckBox, "- A restrained creature’s speed becomes 0, and it can’t benefit from any bonus to its speed.\n" +
                "- Attack rolls against the creature have advantage, and the creature’s attack rolls have disadvantage.\n" +
                "- The creature has disadvantage on Dexterity saving throws.");
        setTooltip(stunnedCheckBox, "- A stunned creature is incapacitated (see the condition), can’t move, and can speak only falteringly.\n" +
                "- The creature automatically fails Strength and Dexterity saving throws.\n" +
                "- Attack rolls against the creature have advantage.");
        setTooltip(unconsciousCheckBox, "- An unconscious creature is incapacitated (see the condition), can’t move or speak, and is unaware of its surroundings\n" +
                "- The creature drops whatever it’s holding and falls prone.\n" +
                "- The creature automatically fails Strength and Dexterity saving throws.\n" +
                "- Attack rolls against the creature have advantage.\n" +
                "- Any attack that hits the creature is a critical hit if the attacker is within 5 feet of the creature.");
        setTooltip(exhaustionCheckBox, "Some special abilities and environmental hazards, such as starvation and the long-term effects of freezing or scorching temperatures, \n" +
                "can lead to a special condition called exhaustion. Exhaustion is measured in six levels. \n" +
                "An effect can give a creature one or more levels of exhaustion, as specified in the effect’s description.\n\n" +
                "Exhaustion Effects levels:\n" +
                "\t1. Disadvantage on ability checks\n" +
                "\t2. Speed halved\n" +
                "\t3. Disadvantage on attack rolls and saving throws\n" +
                "\t4. Hit point maximum halved\n" +
                "\t5. Speed reduced to 0\n" +
                "\t6. Death\n" +
                "\nIf an already exhausted creature suffers another effect that causes exhaustion, \n" +
                "its current level of exhaustion increases by the amount specified in the effect’s description.\n" +
                "\nA creature suffers the effect of its current level of exhaustion as well as all lower levels. \n" +
                "For example, a creature suffering level 2 exhaustion has its speed halved and has disadvantage on ability checks.\n" +
                "\nAn effect that removes exhaustion reduces its level as specified in the effect’s description, \n" +
                "with all exhaustion effects ending if a creature’s exhaustion level is reduced below 1.\n" +
                "\nFinishing a long rest reduces a creature’s exhaustion level by 1, \n" +
                "provided that the creature has also ingested some food and drink.");
    }

    /**Met deze methode worden alle checkboxxes geinitialiseerd.
     *
     * @param checkBox: de exacte checkbox waar de tooltip bij hoort
     * @param tooltipText: de text bij elke specifieke tooltip
     */
    private void setTooltip(CheckBox checkBox, String tooltipText) {
        Tooltip tooltip = new Tooltip(tooltipText);
        checkBox.setTooltip(tooltip);
    }

    /**Met deze methode wordt weergegeven wie er aan de beurt is (en dus bovenaan de initiativeList staat).
     *
     */
    public void setCreatureTurnTextField(){
        if (initiativeList.getItems().isEmpty()) {
            creatureTurnTextfield.setText("No creature in initiative!");
            return;
        }
            creatureTurnTextfield.setText(initiativeList.getItems().get(0).getName());
    }

    /**Met deze methode kunnen nieuwe creatures toegevoegd worden aan de initiativeList.
     *
     */
    public void doAdd() {
        if(doValidate()) {
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

                if(getHP <= getMaxHP) {
                    initiativeList.getItems().add(new Creature(nameTextField.getText(), getInitiative, getHP, getMaxHP, legRes, legAct));

                    nameTextField.setText("");
                    initiativeTextField.setText("");
                    hpTextField.setText("");
                    maxHPTextField.setText("");
                    legResTextField.setText("0");
                    legActTextField.setText("0");
                    legendaryCheckBox.setSelected(false);
                    legendaryControls.setVisible(false);
                    return;
                }
                showAlert("HP can't be higher then max HP!");
                setCreatureTurnTextField();
            } catch (NumberFormatException exception) {
                showAlert("Initiative and HP must be valid numbers!");
            }
        }
    }

    /**Met deze methode kunnen creatures verwijderd worden uit de initiativeList.
     *
     */
    public void doDelete() {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("By pressing 'OK', you delete this creature from initiative!");
        confirmationDialog.setContentText("Are you sure?");

        Stage stage = (Stage) deleteButton.getScene().getWindow();
        confirmationDialog.initOwner(stage);

        Optional<ButtonType> result = confirmationDialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Creature selectedCreature = initiativeList.getSelectionModel().getSelectedItem();
            if (selectedCreature == null) {
                showAlert("No creature selected!");
                return;
            }
            initiativeList.getItems().remove(selectedCreature);
            setCreatureTurnTextField();
        }
    }

    /**Met deze methode wordt gevalideerd of de nieuw toegevoegde creature voldoet aan verschillende eisen.
     *
     * @return: true als het overal aan voldoet.
     */
    public boolean doValidate() {
        final int MINIMUM_HP = 0;
        String name = nameTextField.getText();
        String initiativeText = initiativeTextField.getText();
        String hpText = hpTextField.getText();
        String maxHPText = maxHPTextField.getText();

        if (name.isEmpty() || initiativeText.isEmpty() || hpText.isEmpty() || maxHPText.isEmpty()) {
            showAlert("All fields are required!");
            return false;
        }

        if(initiativeList.getItems() != null) {
            ArrayList<String> names = new ArrayList<>();
            for(Creature creature : initiativeList.getItems()) {
                names.add(creature.getName());
            }
            for (String creatureName : names) {
                if(name.equalsIgnoreCase(creatureName)) {
                    showAlert("This name is already in the initiative list!");
                    return false;
                }
            }
        }

        if (Integer.parseInt(hpText) < MINIMUM_HP || Integer.parseInt(maxHPText) < MINIMUM_HP) {
            showAlert("You can't add a creature with less then 0 (max) HP.");
            return false;
        }

        return true;
    }

    /**Met deze methode worden de creatures in de initiativeList doorgeschoven en de bovenste in de list
     * komt onderaan te staan.
     *
     */
    public void doNext() {
        final int MINIMUM_AMOUNT_CREATURES = 1;
        final int LAST_CREATURE_INDEX = 0;
        if (initiativeList.getItems().size() <= MINIMUM_AMOUNT_CREATURES) {
            showAlert("Not enough creatures in your initiative list to do this!");
            return;
        }

        Creature lastCreature = initiativeList.getItems().get(LAST_CREATURE_INDEX);
        initiativeList.getItems().remove(LAST_CREATURE_INDEX);
        initiativeList.getItems().add(lastCreature);
        setCreatureTurnTextField();

        updateRoundIfNecessary();

        if(lairAction) {
            if(initiativeList.getItems().get(0).getInitiative() < 20) {
                if(Integer.parseInt(roundTextField.getText()) == roundCountForLairAction) {
                    roundCountForLairAction++;
                    showInfo("Lair action!");
                }
            }
        }
    }

    /**Met deze methode worden de creatures in de initiativeList doorgeschoven en de onderste in de list
     * komt bovenaan te staan.
     *
     */
    public void doBack() {
        final int MINIMUM_AMOUNT_CREATURES = 1;
        final int LAST_INDEX = 1;
        final int FIRST_CREATURE_INDEX = 0;

        if (initiativeList.getItems().size() <= MINIMUM_AMOUNT_CREATURES) {
            showAlert("Not enough creatures in your initiative list to do this!");
            return;
        }

        Creature firstCreature = initiativeList.getItems().get(initiativeList.getItems().size() - LAST_INDEX);
        initiativeList.getItems().remove(initiativeList.getItems().size() - LAST_INDEX);
        initiativeList.getItems().add(FIRST_CREATURE_INDEX, firstCreature);
        setCreatureTurnTextField();
    }

    /**Met deze methode kan ingevoerde HP in het hpLowerAddTextField toegevoegd aan de HP van de creature.
     * Als niets is ingevoerd, dan gaat de HP met 1 omhoog. Ook wordt de achtergrond van het textfield even
     * groen.
     *
     */
    public void doAddHP(){
        if (initiativeList.getSelectionModel().getSelectedItem() == null) {
            showAlert("No creature selected!");
            return;
        }

        final int NO_NUMBER_IN_TEXTBOX = 1;
        final double COLOR_FLASH = 0.1;
        int maxCreatureHP = initiativeList.getSelectionModel().getSelectedItem().getMaxHP();
        int hpCreature = initiativeList.getSelectionModel().getSelectedItem().getHP();
        int addedHP = hpLowerAddTextField.getText().isEmpty() ? NO_NUMBER_IN_TEXTBOX : Integer.parseInt(hpLowerAddTextField.getText());

        initiativeList.getSelectionModel().getSelectedItem().setHP(Math.min(hpCreature + addedHP, maxCreatureHP));
        initiativeHPTextfield.setText(initiativeList.getSelectionModel().getSelectedItem().getHP() + " / " + initiativeList.getSelectionModel().getSelectedItem().getMaxHP());

        initiativeHPTextfield.setStyle("-fx-background-color: green;");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(COLOR_FLASH), e -> initiativeHPTextfield.setStyle("")));
        timeline.play();

        hpLowerAddTextField.setText("");
    }

    /**Met deze methode kan ingevoerde HP in het hpLowerAddTextField weggehaald worden van de HP van een creature.
     * Als niets is ingevoerd, dan gaat de HP met 1 omlaag. Ook wordt de achtergrond van het textfield even
     * rood. Mocht er tempHP zijn, dan wordt eerst de HP daar vanaf gehaald voor het aan de gewone HP gaat werken.
     *
     */
    public void doLowerHP(){
        if (initiativeList.getSelectionModel().getSelectedItem() == null) {
            showAlert("No creature selected!");
            return;
        }

        final int MINIMUM_HP = 0;
        final int MINIMUM_TEMP_HP = 0;
        final int NO_NUMBER_IN_TEXTBOX = 1;
        int hpCreature = initiativeList.getSelectionModel().getSelectedItem().getHP();
        int removedHP = hpLowerAddTextField.getText().isEmpty() ? NO_NUMBER_IN_TEXTBOX : Integer.parseInt(hpLowerAddTextField.getText());
        int tempHP = initiativeList.getSelectionModel().getSelectedItem().getTempHP();

        if(tempHP != MINIMUM_TEMP_HP) {
            if (removedHP <= tempHP) {
                initiativeList.getSelectionModel().getSelectedItem().setTempHP(tempHP - removedHP);
                tempHP = initiativeList.getSelectionModel().getSelectedItem().getTempHP();
                tempHPTextField.setText(String.valueOf(tempHP));
            } else {
                int overflow = removedHP - tempHP;
                initiativeList.getSelectionModel().getSelectedItem().setTempHP(MINIMUM_TEMP_HP);
                tempHP = initiativeList.getSelectionModel().getSelectedItem().getTempHP();
                tempHPTextField.setText(String.valueOf(tempHP));
                if(hpCreature - overflow > MINIMUM_HP) {
                    initiativeList.getSelectionModel().getSelectedItem().setHP(hpCreature - overflow);
                } else {
                    initiativeList.getSelectionModel().getSelectedItem().setHP(MINIMUM_HP);
                }
                initiativeHPTextfield.setText(initiativeList.getSelectionModel().getSelectedItem().getHP() + " / " +
                        initiativeList.getSelectionModel().getSelectedItem().getMaxHP());

                getRed();
            }
        } else {
            if(hpCreature - removedHP > MINIMUM_HP) {
                initiativeList.getSelectionModel().getSelectedItem().setHP(hpCreature - removedHP);
            } else {
                initiativeList.getSelectionModel().getSelectedItem().setHP(MINIMUM_HP);
            }
            initiativeHPTextfield.setText(initiativeList.getSelectionModel().getSelectedItem().getHP() + " / " +
                    initiativeList.getSelectionModel().getSelectedItem().getMaxHP());

            getRed();
        }

        hpLowerAddTextField.setText("");
    }

    /**Met deze methode wordt het initiativeHPTextField voor 0,1 seconde rood gemaakt. Als de HP van de
     * geselecteerde creature 0 is, dan blijft het rood, anders wordt het daarna weer normaal.
     *
     */
    public void getRed(){
        final int MINIMUM_HP = 0;
        final double COLOR_FLASH = 0.1;

        initiativeHPTextfield.setStyle("-fx-background-color: red;");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(COLOR_FLASH), e -> {
            if(initiativeList.getSelectionModel().getSelectedItem().getHP() != MINIMUM_HP) {
                initiativeHPTextfield.setStyle("");
            }
        }));
        timeline.play();
    }

    /**Met deze methode worden de stats die weergegeven worden geupdate naar de stats van de nieuw geselecteerde
     * creature.
     *
     */
    public void updateCreatureStats(){
        final int MINIMUM_HP = 0;

        initiativeList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                initiativeNameTextfield.setText(newSelection.getName());
                initiativeHPTextfield.setText(newSelection.getHP() + " / " + newSelection.getMaxHP());

                concentrationCheckBox.setSelected(newSelection.isConcentration());
                blindedCheckBox.setSelected(newSelection.isBlinded());
                charmedCheckBox.setSelected(newSelection.isCharmed());
                deafenedCheckBox.setSelected(newSelection.isDeafened());
                frightenedCheckBox.setSelected(newSelection.isFrightened());
                grappledCheckBox.setSelected(newSelection.isGrappled());
                incapacitatedCheckBox.setSelected(newSelection.isIncapacitated());
                invisibleCheckBox.setSelected(newSelection.isInvisible());
                paralyzedCheckBox.setSelected(newSelection.isParalyzed());
                petrifiedCheckBox.setSelected(newSelection.isPetrified());
                poisonedCheckBox.setSelected(newSelection.isPoisoned());
                proneCheckBox.setSelected(newSelection.isProne());
                restrainedCheckBox.setSelected(newSelection.isRestrained());
                stunnedCheckBox.setSelected(newSelection.isStunned());
                unconsciousCheckBox.setSelected(newSelection.isUnconscious());
                exhaustionCheckBox.setSelected(newSelection.isExhaustion());
                extraInfoTextArea.setText(newSelection.getExtraInfo());
                tempHPTextField.setText(String.valueOf(newSelection.getTempHP()));
                ACTextField.setText(String.valueOf(newSelection.getAC()));

                initiativeHPTextfield.setStyle(
                        initiativeList.getSelectionModel().getSelectedItem().getHP() != MINIMUM_HP ? "" : "-fx-background-color: red;"
                );

                if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistances() == 0) {
                    legResNameText.setVisible(false);
                    legResCheckBox1.setVisible(false);
                    legResCheckBox2.setVisible(false);
                    legResCheckBox3.setVisible(false);
                    legResCheckBox4.setVisible(false);
                    legResCheckBox5.setVisible(false);
                }

                if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryActions() == 0) {
                    legActNameText.setVisible(false);
                    legActCheckBox1.setVisible(false);
                    legActCheckBox2.setVisible(false);
                    legActCheckBox3.setVisible(false);
                    legActCheckBox4.setVisible(false);
                    legActCheckBox5.setVisible(false);
                }

                if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistances() == 1) {
                    legResNameText.setVisible(true);
                    legResCheckBox1.setVisible(true);
                    legResCheckBox2.setVisible(false);
                    legResCheckBox3.setVisible(false);
                    legResCheckBox4.setVisible(false);
                    legResCheckBox5.setVisible(false);
                    if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 0) {
                        legResCheckBox1.setSelected(true);
                    } else {
                        legResCheckBox1.setSelected(false);
                    }
                }

                if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryActions() == 1) {
                    legActNameText.setVisible(true);
                    legActCheckBox1.setVisible(true);
                    legActCheckBox2.setVisible(false);
                    legActCheckBox3.setVisible(false);
                    legActCheckBox4.setVisible(false);
                    legActCheckBox5.setVisible(false);
                    if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryActionsLeft() == 0) {
                        legActCheckBox1.setSelected(true);
                    } else {
                        legActCheckBox1.setSelected(false);
                    }
                }

                if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistances() == 2) {
                    legResNameText.setVisible(true);
                    legResCheckBox1.setVisible(true);
                    legResCheckBox2.setVisible(true);
                    legResCheckBox3.setVisible(false);
                    legResCheckBox4.setVisible(false);
                    legResCheckBox5.setVisible(false);
                    if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 0) {
                        legResCheckBox1.setSelected(true);
                        legResCheckBox2.setSelected(true);
                    } else if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 1) {
                        legResCheckBox1.setSelected(true);
                        legResCheckBox2.setSelected(false);
                    } else {
                        legResCheckBox1.setSelected(false);
                        legResCheckBox2.setSelected(false);
                    }
                }

                if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryActions() == 2) {
                    legActNameText.setVisible(true);
                    legActCheckBox1.setVisible(true);
                    legActCheckBox2.setVisible(true);
                    legActCheckBox3.setVisible(false);
                    legActCheckBox4.setVisible(false);
                    legActCheckBox5.setVisible(false);
                    if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryActions() == 0) {
                        legResCheckBox1.setSelected(true);
                        legResCheckBox2.setSelected(true);
                    } else if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryActions() == 1) {
                        legActCheckBox1.setSelected(true);
                        legActCheckBox2.setSelected(false);
                    } else {
                        legActCheckBox1.setSelected(false);
                        legActCheckBox2.setSelected(false);
                    }
                }

                if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistances() == 3) {
                    legResNameText.setVisible(true);
                    legResCheckBox1.setVisible(true);
                    legResCheckBox2.setVisible(true);
                    legResCheckBox3.setVisible(true);
                    legResCheckBox4.setVisible(false);
                    legResCheckBox5.setVisible(false);
                    if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 0) {
                        legResCheckBox1.setSelected(true);
                        legResCheckBox2.setSelected(true);
                        legResCheckBox3.setSelected(true);
                    } else if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 1) {
                        legResCheckBox1.setSelected(true);
                        legResCheckBox2.setSelected(true);
                        legResCheckBox3.setSelected(false);
                    } else if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 2) {
                        legResCheckBox1.setSelected(true);
                        legResCheckBox2.setSelected(false);
                        legResCheckBox3.setSelected(false);
                    } else {
                        legResCheckBox1.setSelected(false);
                        legResCheckBox2.setSelected(false);
                        legResCheckBox3.setSelected(false);
                    }
                }

                if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryActions() == 3) {
                    legActNameText.setVisible(true);
                    legActCheckBox1.setVisible(true);
                    legActCheckBox2.setVisible(true);
                    legActCheckBox3.setVisible(true);
                    legActCheckBox4.setVisible(false);
                    legActCheckBox5.setVisible(false);
                    if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryActionsLeft() == 0) {
                        legActCheckBox1.setSelected(true);
                        legActCheckBox2.setSelected(true);
                        legActCheckBox3.setSelected(true);
                    } else if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryActionsLeft() == 1) {
                        legActCheckBox1.setSelected(true);
                        legActCheckBox2.setSelected(true);
                        legActCheckBox3.setSelected(false);
                    } else if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryActionsLeft() == 2) {
                        legActCheckBox1.setSelected(true);
                        legActCheckBox2.setSelected(false);
                        legActCheckBox3.setSelected(false);
                    } else {
                        legActCheckBox1.setSelected(false);
                        legActCheckBox2.setSelected(false);
                        legActCheckBox3.setSelected(false);
                    }
                }

                if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistances() == 4) {
                    legResNameText.setVisible(true);
                    legResCheckBox1.setVisible(true);
                    legResCheckBox2.setVisible(true);
                    legResCheckBox3.setVisible(true);
                    legResCheckBox4.setVisible(true);
                    legResCheckBox5.setVisible(false);
                    if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 0) {
                        legResCheckBox1.setSelected(true);
                        legResCheckBox2.setSelected(true);
                        legResCheckBox3.setSelected(true);
                        legResCheckBox4.setSelected(true);
                    } else if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 1) {
                        legResCheckBox1.setSelected(true);
                        legResCheckBox2.setSelected(true);
                        legResCheckBox3.setSelected(true);
                        legResCheckBox4.setSelected(false);
                    } else if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 2) {
                        legResCheckBox1.setSelected(true);
                        legResCheckBox2.setSelected(true);
                        legResCheckBox3.setSelected(false);
                        legResCheckBox4.setSelected(false);
                    } else if (initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 3) {
                        legResCheckBox1.setSelected(true);
                        legResCheckBox2.setSelected(false);
                        legResCheckBox3.setSelected(false);
                        legResCheckBox4.setSelected(false);
                    } else {
                        legResCheckBox1.setSelected(false);
                        legResCheckBox2.setSelected(false);
                        legResCheckBox3.setSelected(false);
                        legResCheckBox4.setSelected(false);
                    }
                }

                if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryActions() == 4) {
                    legActNameText.setVisible(true);
                    legActCheckBox1.setVisible(true);
                    legActCheckBox2.setVisible(true);
                    legActCheckBox3.setVisible(true);
                    legActCheckBox4.setVisible(true);
                    legActCheckBox5.setVisible(false);
                    if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 0) {
                        legActCheckBox1.setSelected(true);
                        legActCheckBox2.setSelected(true);
                        legActCheckBox3.setSelected(true);
                        legActCheckBox4.setSelected(true);
                    } else if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 1) {
                        legActCheckBox1.setSelected(true);
                        legActCheckBox2.setSelected(true);
                        legActCheckBox3.setSelected(true);
                        legActCheckBox4.setSelected(false);
                    } else if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 2) {
                        legActCheckBox1.setSelected(true);
                        legActCheckBox2.setSelected(true);
                        legActCheckBox3.setSelected(false);
                        legActCheckBox4.setSelected(false);
                    } else if (initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 3) {
                        legActCheckBox1.setSelected(true);
                        legActCheckBox2.setSelected(false);
                        legActCheckBox3.setSelected(false);
                        legActCheckBox4.setSelected(false);
                    } else {
                        legActCheckBox1.setSelected(false);
                        legActCheckBox2.setSelected(false);
                        legActCheckBox3.setSelected(false);
                        legActCheckBox4.setSelected(false);
                    }
                }

                if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistances() == 5) {
                    legResNameText.setVisible(true);
                    legResCheckBox1.setVisible(true);
                    legResCheckBox2.setVisible(true);
                    legResCheckBox3.setVisible(true);
                    legResCheckBox4.setVisible(true);
                    legResCheckBox5.setVisible(true);
                    if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 0) {
                        legResCheckBox1.setSelected(true);
                        legResCheckBox2.setSelected(true);
                        legResCheckBox3.setSelected(true);
                        legResCheckBox4.setSelected(true);
                        legResCheckBox5.setSelected(true);
                    } else if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 1) {
                        legResCheckBox1.setSelected(true);
                        legResCheckBox2.setSelected(true);
                        legResCheckBox3.setSelected(true);
                        legResCheckBox4.setSelected(true);
                        legResCheckBox5.setSelected(false);
                    } else if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 2) {
                        legResCheckBox1.setSelected(true);
                        legResCheckBox2.setSelected(true);
                        legResCheckBox3.setSelected(true);
                        legResCheckBox4.setSelected(false);
                        legResCheckBox5.setSelected(false);
                    } else if (initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 3) {
                        legResCheckBox1.setSelected(true);
                        legResCheckBox2.setSelected(true);
                        legResCheckBox3.setSelected(false);
                        legResCheckBox4.setSelected(false);
                        legResCheckBox5.setSelected(false);
                    } else if (initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistancesLeft() == 4) {
                        legResCheckBox1.setSelected(true);
                        legResCheckBox2.setSelected(false);
                        legResCheckBox3.setSelected(false);
                        legResCheckBox4.setSelected(false);
                        legResCheckBox5.setSelected(false);
                    } else {
                        legResCheckBox1.setSelected(false);
                        legResCheckBox2.setSelected(false);
                        legResCheckBox3.setSelected(false);
                        legResCheckBox4.setSelected(false);
                        legResCheckBox5.setSelected(false);
                    }
                }

                if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryActions() == 5) {
                    legActNameText.setVisible(true);
                    legActCheckBox1.setVisible(true);
                    legActCheckBox2.setVisible(true);
                    legActCheckBox3.setVisible(true);
                    legActCheckBox4.setVisible(true);
                    legActCheckBox5.setVisible(true);
                    if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryActionsLeft() == 0) {
                        legActCheckBox1.setSelected(true);
                        legActCheckBox2.setSelected(true);
                        legActCheckBox3.setSelected(true);
                        legActCheckBox4.setSelected(true);
                        legActCheckBox5.setSelected(true);
                    } else if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryActionsLeft() == 1) {
                        legActCheckBox1.setSelected(true);
                        legActCheckBox2.setSelected(true);
                        legActCheckBox3.setSelected(true);
                        legActCheckBox4.setSelected(true);
                        legActCheckBox5.setSelected(false);
                    } else if(initiativeList.getSelectionModel().getSelectedItem().getLegendaryActionsLeft() == 2) {
                        legActCheckBox1.setSelected(true);
                        legActCheckBox2.setSelected(true);
                        legActCheckBox3.setSelected(true);
                        legActCheckBox4.setSelected(false);
                        legActCheckBox5.setSelected(false);
                    } else if (initiativeList.getSelectionModel().getSelectedItem().getLegendaryActionsLeft() == 3) {
                        legActCheckBox1.setSelected(true);
                        legActCheckBox2.setSelected(true);
                        legActCheckBox3.setSelected(false);
                        legActCheckBox4.setSelected(false);
                        legActCheckBox5.setSelected(false);
                    } else if (initiativeList.getSelectionModel().getSelectedItem().getLegendaryActionsLeft() == 4) {
                        legActCheckBox1.setSelected(true);
                        legActCheckBox2.setSelected(false);
                        legActCheckBox3.setSelected(false);
                        legActCheckBox4.setSelected(false);
                        legActCheckBox5.setSelected(false);
                    } else {
                        legActCheckBox1.setSelected(false);
                        legActCheckBox2.setSelected(false);
                        legActCheckBox3.setSelected(false);
                        legActCheckBox4.setSelected(false);
                        legActCheckBox5.setSelected(false);
                    }
                }

                if(!exhaustionCheckBox.isSelected()) {
                    exhaustionControls.setVisible(false);
                    return;
                }

                exhaustionControls.setVisible(true);
                exhaustionTextField.setText(String.valueOf(initiativeList.getSelectionModel().getSelectedItem().getExhaustionLevel()));
            }
        });
    }

    /**Met deze methode worden checkboxxes die aangevinkt worden voor de geselecteerde creature opgeslagen. Verder
     * wordt gecheckt of de exhaustionCheckBox is geselecteerd, als true: worden extra knoppen weergegeven waarmee
     * je het exhautionLevel kan bijhouden.
     *
     */
    public void handleCheckBox(){
        if (initiativeList.getSelectionModel().getSelectedItem() == null) {
            showAlert("No creature selected!");
            return;
        }

        final int MINIMUM_EXHAUSTION_LEVEL = 1;
        initiativeList.getSelectionModel().getSelectedItem().setConcentration(concentrationCheckBox.isSelected());
        initiativeList.getSelectionModel().getSelectedItem().setBlinded(blindedCheckBox.isSelected());
        initiativeList.getSelectionModel().getSelectedItem().setCharmed(charmedCheckBox.isSelected());
        initiativeList.getSelectionModel().getSelectedItem().setDeafened(deafenedCheckBox.isSelected());
        initiativeList.getSelectionModel().getSelectedItem().setFrightened(frightenedCheckBox.isSelected());
        initiativeList.getSelectionModel().getSelectedItem().setGrappled(grappledCheckBox.isSelected());
        initiativeList.getSelectionModel().getSelectedItem().setIncapacitated(incapacitatedCheckBox.isSelected());
        initiativeList.getSelectionModel().getSelectedItem().setInvisible(invisibleCheckBox.isSelected());
        initiativeList.getSelectionModel().getSelectedItem().setParalyzed(paralyzedCheckBox.isSelected());
        initiativeList.getSelectionModel().getSelectedItem().setPetrified(petrifiedCheckBox.isSelected());
        initiativeList.getSelectionModel().getSelectedItem().setPoisoned(poisonedCheckBox.isSelected());
        initiativeList.getSelectionModel().getSelectedItem().setProne(proneCheckBox.isSelected());
        initiativeList.getSelectionModel().getSelectedItem().setRestrained(restrainedCheckBox.isSelected());
        initiativeList.getSelectionModel().getSelectedItem().setStunned(stunnedCheckBox.isSelected());
        initiativeList.getSelectionModel().getSelectedItem().setUnconscious(unconsciousCheckBox.isSelected());
        initiativeList.getSelectionModel().getSelectedItem().setExhaustion(exhaustionCheckBox.isSelected());

        boolean isCheckedExhaustion = exhaustionCheckBox.isSelected();
        exhaustionControls.setVisible(isCheckedExhaustion);
        if (!isCheckedExhaustion) {
            initiativeList.getSelectionModel().getSelectedItem().setMaxHP(initiativeList.getSelectionModel().getSelectedItem().getOriginalMaxHP());
            initiativeHPTextfield.setText(initiativeList.getSelectionModel().getSelectedItem().getHP() + " / " +
                    initiativeList.getSelectionModel().getSelectedItem().getMaxHP());
        } else {
            exhaustionTextField.setText(String.valueOf(MINIMUM_EXHAUSTION_LEVEL));
            initiativeList.getSelectionModel().getSelectedItem().setExhaustionLevel(1);
        }
    }

    public void handleLegendaryCheckBox(){
        boolean isCheckedLegendary = legendaryCheckBox.isSelected();
        legendaryControls.setVisible(isCheckedLegendary);
    }

    /**Met deze methode wordt terug gegaan naar het vorige scherm. Er wordt eerst gevraagd of de gebruiker
     * het zeker weet, aangezien dan de initiativeList gewist wordt.
     *
     */
    public void doMenu() {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("By pressing 'OK', you delete your entire initiative!");
        confirmationDialog.setContentText("Are you sure?");

        Stage stage = (Stage) deleteButton.getScene().getWindow();
        confirmationDialog.initOwner(stage);

        Optional<ButtonType> result = confirmationDialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            SCENEMANAGER.showMenuScene();
        }
    }

    /**Met deze methode wordt met een listener bijgehouden of er tekst wordt gezet in de extraInfoTextArea en
     * die tekst wordt dan direct opgeslagen bij de geselecteerde creature.
     *
     */
    public void handleSaveExtraInfo(){
        if(initiativeList.getSelectionModel().getSelectedItem() == null) {
            showAlert("No creature selected!");
            extraInfoTextArea.clear();
            extraInfoTextArea.setPromptText("Select a creature to add extra info.");
            return;
        }

        extraInfoTextArea.textProperty().addListener((observable, oldValue, newValue) ->
                initiativeList.getSelectionModel().getSelectedItem().setExtraInfo(newValue));
    }

    /**Met deze methode wordt met een listener bijgehouden of er tekst wordt gezet in de tempHPTextField en
     * die tekst wordt dan direct opgeslagen bij de geselecteerde creature.
     *
     */
    public void handleSaveTempHP() {
        if (initiativeList.getSelectionModel().getSelectedItem() == null) {
            showAlert("No creature selected!");
            tempHPTextField.clear();
            tempHPTextField.setPromptText("--");
            return;
        }

        tempHPTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            int tempHP = 0;
            try {
                tempHP = Integer.parseInt(newValue);
                initiativeList.getSelectionModel().getSelectedItem().setTempHP(tempHP);
            } catch (NumberFormatException exception) {
                showAlert("You must enter a whole number!");
                tempHPTextField.setText(String.valueOf(tempHP));
            }
        });
    }

    /**Met deze methode gaat de round counter met 1 omhoog.
     *
     */
    public void doAddRound(){
        final int SINGLE_ROUND_ADDED = 1;
        int round = Integer.parseInt(roundTextField.getText());
        int newRound = round + SINGLE_ROUND_ADDED;
        roundTextField.setText(String.valueOf(newRound));
    }

    /**Met deze methode gaat de round counter met 1 omlaag.
     *
     */
    public void doLowerRound(){
        final int SINGLE_ROUND_REMOVED = 1;
        int round = Integer.parseInt(roundTextField.getText());
        int newRound = round - SINGLE_ROUND_REMOVED;
        roundTextField.setText(String.valueOf(newRound));
    }

    /**Met deze methode wordt bijgehouden of de gehele iniativeList is geweest. Als true: dan gaat de round counter
     * met 1 omhoog. Ook wordt er rekening gehouden met of er meer dan 1 creature is met de hoogste initiative.
     *
     */
    public void updateRoundIfNecessary() {
        final int TOP_CREATURE_INDEX = 0;
        final int MINIMUM_AMOUNT_HIGHEST_INITIATIVE_TURN_TAKEN = 1;
        final int STARTING_NUMBER_AMOUNT_HIGHEST_INITIATIVE = 0;
        if (!initiativeList.getItems().isEmpty()) {
            Creature topCreature = initiativeList.getItems().get(TOP_CREATURE_INDEX);
            Creature highestInitiative = topCreature;

            for (Creature creature : initiativeList.getItems()) {
                if (creature.getInitiative() > highestInitiative.getInitiative()) {
                    highestInitiative = creature;
                }
            }

            int amountHighestInitiative = STARTING_NUMBER_AMOUNT_HIGHEST_INITIATIVE;
            for(Creature creature : initiativeList.getItems()) {
                if(creature.getInitiative() == highestInitiative.getInitiative()) {
                    amountHighestInitiative ++;
                }
            }

            if (topCreature == highestInitiative) {
                if(amountHighestInitiative != amountHighestInitiativeTurnTaken) {
                    amountHighestInitiativeTurnTaken++;
                }else {
                    doAddRound();
                    amountHighestInitiativeTurnTaken = MINIMUM_AMOUNT_HIGHEST_INITIATIVE_TURN_TAKEN;
                }
            }
        }
    }

    /**Met deze methode wordt het exhaustionLevel van de geselecteerde met 1 omhoog gebracht tot een max van 6.
     * Als exhaustionLevel 4 gehaald wordt, dan wordt de maxHP gedeeld door 2.
     * Als exhaustionLevel 6 gehaald wordt, dan wordt het HP naar 0 gebracht en is de creature dood.
     *
     */
    public void addExhaustion() {
        Creature selectedCreature = initiativeList.getSelectionModel().getSelectedItem();

        if (selectedCreature == null) {
            showAlert("Please select a creature to modify exhaustion.");
            return;
        }

        final int MAXIMUM_EXHAUSTION = 6;
        final int ADD_1_EXHAUSTION = 1;
        final int MINIMUM_HP = 0;
        final int HALVE_HP_DIVIDE_BY_2 = 2;
        int currentLevel = selectedCreature.getExhaustionLevel();

        if (currentLevel == MAXIMUM_EXHAUSTION) {
            showAlert("Exhaustion can't go higher than " + MAXIMUM_EXHAUSTION + "!");
            return;
        }

        int newLevel = currentLevel + ADD_1_EXHAUSTION;
        selectedCreature.setExhaustionLevel(newLevel);
        exhaustionTextField.setText(String.valueOf(newLevel));

        if (newLevel == 4) {
            selectedCreature.setMaxHP(selectedCreature.getMaxHP() / HALVE_HP_DIVIDE_BY_2);

            if (selectedCreature.getHP() > selectedCreature.getMaxHP()) {
                selectedCreature.setHP(selectedCreature.getMaxHP());
            }
            initiativeHPTextfield.setText(selectedCreature.getHP() + " / " + selectedCreature.getMaxHP());

            showAlert("This creature's max HP is now halved!");

            getRed();
            return;
        }

        if (newLevel == MAXIMUM_EXHAUSTION) {
            selectedCreature.setHP(MINIMUM_HP);
            initiativeHPTextfield.setText(selectedCreature.getHP() + " / " + selectedCreature.getMaxHP());

            initiativeHPTextfield.setStyle("-fx-background-color: red;");

            showAlert("This creature is now dead!");
        }
    }

    /**Met deze methode wordt het exhaustionLevel met 1 omlaag gebracht. Als het level op 0 komt, dan gaat
     * de checkbox van exhaustion automatisch op false.
     *
     */
    public void lowerExhaustion() {
        Creature selectedCreature = initiativeList.getSelectionModel().getSelectedItem();

        if (selectedCreature == null) {
            showAlert("Please select a creature to modify exhaustion.");
            return;
        }

        final double COLOR_FLASH = 0.1;
        final int MINIMUM_EXHAUSTION = 0;
        final int MINIMUM_HP = 0;
        final int EXHAUSTION_LEVEL_3 = 3;
        int currentLevel = selectedCreature.getExhaustionLevel();
        int newLevel = currentLevel - 1;

        selectedCreature.setExhaustionLevel(newLevel);
        exhaustionTextField.setText(String.valueOf(newLevel));

        if (newLevel == EXHAUSTION_LEVEL_3) {
            selectedCreature.setMaxHP(selectedCreature.getOriginalMaxHP());
            initiativeHPTextfield.setText(selectedCreature.getHP() + " / " + selectedCreature.getMaxHP());

            initiativeHPTextfield.setStyle("-fx-background-color: green;");
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(COLOR_FLASH), e -> {
                        initiativeHPTextfield.setStyle(selectedCreature.getHP() == MINIMUM_HP ? "-fx-background-color: red;": "");
                   })
            );
            timeline.play();
        }

        if (newLevel == MINIMUM_EXHAUSTION) {
            exhaustionCheckBox.setSelected(false);
            exhaustionControls.setVisible(false);
        }
    }


    /**Met deze methode wordt met een listener bijgehouden of er tekst wordt gezet in de ACTextField en
     * die tekst wordt dan direct opgeslagen bij de geselecteerde creature.
     *
     */
    public void handleAC() {
        if(initiativeList.getSelectionModel().getSelectedItem() == null) {
            showAlert("No creature selected!");
            ACTextField.clear();
            ACTextField.setPromptText("--.");
            return;
        }

        ACTextField.textProperty().addListener((observable, oldValue, newValue) ->
                initiativeList.getSelectionModel().getSelectedItem().setAC(Integer.parseInt(newValue)));
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

    public void handleLegResCheckBox() {
        int checked = 0;
        int legResLeft = initiativeList.getSelectionModel().getSelectedItem().getLegendaryResistances();

        CheckBox[] legResCheckBoxes = {legResCheckBox1, legResCheckBox2, legResCheckBox3, legResCheckBox4, legResCheckBox5};

        for (CheckBox checkBox : legResCheckBoxes) {
            if (checkBox.isSelected()) {
                checked++;
            }
        }

        initiativeList.getSelectionModel().getSelectedItem().setLegendaryResistancesLeft(legResLeft - checked);
    }

    public void handleLegActCheckBox(){
        int checked = 0;
        int legActLeft = initiativeList.getSelectionModel().getSelectedItem().getLegendaryActions();

        CheckBox[] legActCheckBoxes = {legActCheckBox1, legActCheckBox2, legActCheckBox3, legActCheckBox4, legActCheckBox5};

        for (CheckBox checkBox : legActCheckBoxes) {
            if (checkBox.isSelected()) {
                checked++;
            }
        }

        initiativeList.getSelectionModel().getSelectedItem().setLegendaryActionsLeft(legActLeft - checked);
    }

    /**Met deze methode kan een error melding gegeven worden met een ingebrachte message.
     *
     * @param message: De message die wordt weergegeven bij de errormelding.
     */
    public void showAlert(String message) {
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setContentText(message);
        errorMessage.show();
    }

    public void showInfo(String message) {
        Alert infoMessage = new Alert(Alert.AlertType.INFORMATION);
        infoMessage.setHeaderText(message);
        infoMessage.show();
    }
}