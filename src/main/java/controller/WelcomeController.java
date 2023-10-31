package controller;

import view.Main;
import view.SceneManager;

public class WelcomeController {
    private final SceneManager SCENEMANAGER = Main.getSceneManager();

    public void doStart(){
        SCENEMANAGER.showMenuScene();
    }
}
