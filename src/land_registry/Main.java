package land_registry;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import land_registry.components.SceneManager;
import land_registry.database.Database;

public class Main extends Application {
    private final SceneManager sceneManager;
    private final Database database;
    private Stage primaryStage;

    public Main() {
        database = new Database();
        sceneManager = new SceneManager(this);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        sceneManager.switchScene(SceneManager.SceneNames.MAIN);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Database getDatabase() {
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
