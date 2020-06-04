package land_registry;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import land_registry.components.LandRegistryDatabase;
import land_registry.components.SceneManager;

public class Main extends Application {
    private final SceneManager sceneManager;
    private final LandRegistryDatabase database;
    private Stage primaryStage;

    public Main() {
        database = new LandRegistryDatabase();
        sceneManager = new SceneManager(this);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        sceneManager.switchScene(SceneManager.SceneNames.AUTH);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public LandRegistryDatabase getDatabase() {
        return database;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public void onClose(WindowEvent windowEvent) {
        database.closeConnection();
        System.out.println("goodbye!");
    }
}
