package land_registry.controllers;

import javafx.stage.Stage;
import land_registry.Main;
import land_registry.components.database.Database;

public abstract class Controller {
    protected Stage stage;
    protected Main mainContext;
    protected Database database;

    public abstract void onShowing();

    public abstract void onMainContextInit();

    public Stage getStage() {
        return stage;
    }

    public Main getMainContext() {
        return mainContext;
    }

    public Database getDatabase() {
        return database;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMainContext(Main mainContext) {
        this.mainContext = mainContext;
    }
}
