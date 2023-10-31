package controller;

import view.Main;
import view.SceneManager;

public class RandomWelcomeController {

    private final SceneManager SCENEMANAGER = Main.getSceneManager();
    public void doName(){}

    public void doLocation(){}

    public void doMenu() {
        SCENEMANAGER.showMenuScene();
    }
}
