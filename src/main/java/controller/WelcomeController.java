package controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.Main;
import view.SceneManager;

import java.io.FileNotFoundException;

public class WelcomeController {
    private final SceneManager SCENEMANAGER = Main.getSceneManager();
    @FXML
    private ImageView backgroundImageView;
    @FXML
    private Image backgroundImage;

    public void initialize() throws FileNotFoundException {
        backgroundImage = new Image("file:src/main/resources/images/backgroundHomeScreen.png");
        backgroundImageView.setImage(backgroundImage);
    }

    public void doStart(){
        SCENEMANAGER.showMenuScene();
    }
}
