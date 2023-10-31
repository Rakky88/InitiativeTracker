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

    public void setWelcomeTool() throws FileNotFoundException {
        FXMLLoader loader = getScene("/welcomeScene.fxml");
        if (loader != null) {
            WelcomeController controller = loader.getController();
            controller.setup();
        } else {
            System.out.println("set welcomeTool: Loader is not initialized");
            System.out.flush();
        }
    }

    public void showTrackerScene(ArrayList<Creature> creatures) {
        FXMLLoader loader = getScene("/trackerScene.fxml");
        TrackerController controller = loader.getController();
        controller.setup(creatures);
    }
}
