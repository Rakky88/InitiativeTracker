package view;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static SceneManager sceneManager = null;
    private static Stage primaryStage = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
      public void start(Stage primaryStage) {
        try {
            Main.primaryStage = primaryStage;
            primaryStage.setTitle("DnD 5e - DM Helper");
            getSceneManager().setWelcomeTool();
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SceneManager getSceneManager() {
        if (sceneManager == null) {
            sceneManager = new SceneManager(primaryStage);
        }
        return sceneManager;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
