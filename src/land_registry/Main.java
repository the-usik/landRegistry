package land_registry;

import javafx.application.Application;
import javafx.stage.Stage;
import land_registry.components.LandRegistryDatabase;
import land_registry.components.SceneManager;
import land_registry.models.UsersModel;
import org.bson.types.ObjectId;

import javax.lang.model.type.NullType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
        sceneManager.switchScene(SceneManager.SceneNames.MAIN);
    }

    public void close() {
        database.closeConnection();
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
}
