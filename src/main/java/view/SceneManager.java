package view;

import controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SceneManager {

    private Stage primaryStage;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Laadt een scene
    public FXMLLoader getScene(String fxml) {
        Scene scene;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            scene = new Scene(root);
            primaryStage.setScene(scene);
            return loader;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }

    public void setWelcomeTool() {
        getScene("/welcomeScene.fxml");
    }

    public void showInitiativeTrackerScene(ArrayList<Creature> creatures) {
        FXMLLoader loader = getScene("/initiativeTrackerScene.fxml");
        InitiativeTrackerController controller = loader.getController();
        controller.setup(creatures);
    }

    public void showMenuScene() {
        getScene("/menuScene.fxml");
    }

    public void showInitiativeScene() {
        FXMLLoader loader = getScene("/initiativeScene.fxml");
        InitiativeController controller = loader.getController();
        controller.setup();
    }

    public void showRandomWelcomeScene() {
        getScene("/randomWelcomeScene.fxml");
    }

    public void showRandomNameScene() {
        getScene("/randomNameScene.fxml");
    }

    public void showRandomLocationScene() {
        getScene("/randomLocationScene.fxml");
    }
}
