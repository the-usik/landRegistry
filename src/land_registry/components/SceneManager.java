package land_registry.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import land_registry.Main;
import land_registry.controllers.Controller;

import java.io.IOException;
import java.util.HashMap;

public class SceneManager {
    private final String DEFAULT_WINDOW_TITLE = "Land Registry | by Usein Abilev";

    private final Main mainContext;
    private final HashMap<SceneNames, SceneWrapper> scenes = new HashMap<>();
    private Stage currentStage;

    public enum SceneNames {
        AUTH, MAIN
    }

    private static class SceneWrapper {
        private final Scene scene;
        private final Controller controller;

        public SceneWrapper(Scene scene, Controller controller) {
            this.scene = scene;
            this.controller = controller;
        }

        public Scene getScene() {
            return scene;
        }

        public Controller getController() {
            return controller;
        }
    }


    public SceneManager(Main mainContext) {
        this.mainContext = mainContext;
        this.initScenes();
    }

    private void initScenes() {
        try {
            this.addSceneWrapper(SceneNames.AUTH, loadScene("./pages/auth.fxml", 350, 350));
            this.addSceneWrapper(SceneNames.MAIN, loadScene("./pages/main.fxml", 600, 400));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public SceneWrapper loadScene(String path, int width, int height) throws IOException {
        FXMLLoader loader = new FXMLLoader(mainContext.getClass().getResource(path));
        Parent parent = loader.load();
        Controller controller = loader.getController();
        if (controller == null) {
            loader.setController(new Controller() {
                @Override
                public void onShowing() {
                }

                @Override
                public void onMainContextInit() {
                }
            });
            controller = loader.getController();
        }
        controller.setMainContext(mainContext);
        controller.onMainContextInit();
        Scene scene = new Scene(parent, width, height);
        return new SceneWrapper(scene, controller);
    }

    public void switchScene(SceneNames sceneName) {
        if (currentStage != null)
            currentStage.hide();

        SceneWrapper sceneWrapper = getSceneWrapper(sceneName);
        Scene scene = sceneWrapper.getScene();
        Controller controller = sceneWrapper.getController();

        currentStage = new Stage();
        controller.setStage(currentStage);
        currentStage.setResizable(false);
        currentStage.setTitle(DEFAULT_WINDOW_TITLE);
        currentStage.setScene(scene);
        currentStage.setOnCloseRequest(mainContext::onClose);
        currentStage.setOnShowing(event -> controller.onShowing());
        currentStage.show();
    }

    public SceneWrapper getSceneWrapper(SceneNames sceneName) {
        return scenes.get(sceneName);
    }

    public void addSceneWrapper(SceneNames sceneName, SceneWrapper scene) {
        scenes.put(sceneName, scene);
    }

    public void removeSceneWrapper(SceneNames sceneName) {
        scenes.remove(sceneName);
    }
}