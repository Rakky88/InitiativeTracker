package controller;

import view.Main;
import view.SceneManager;

public class RandomWelcomeController {

    private final SceneManager SCENEMANAGER = Main.getSceneManager();
    public void doName(){
        SCENEMANAGER.showRandomNameScene();
    }

    public void doLocation(){
        SCENEMANAGER.showRandomLocationScene();
    }

    public void doMenu() {
        SCENEMANAGER.showMenuScene();
    }
}
