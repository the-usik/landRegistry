package land_registry.controllers;

import javafx.stage.Stage;
import land_registry.Main;

public class Controller {
    protected Main mainContext;
    protected Stage stage;

    public void setMainContext(Main mainContext) {
        this.mainContext = mainContext;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void onMainContextInit() {
    }

    public void onShowing() {

    }
}
