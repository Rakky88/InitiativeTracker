package controller;

import view.Main;
import view.SceneManager;

public class MenuController {
    private final SceneManager SCENEMANAGER = Main.getSceneManager();

    public void doInitiative(){
        SCENEMANAGER.showInitiativeScene();
    }

    public void doRandom(){
        SCENEMANAGER.showRandomWelcomeScene();
    }
}
