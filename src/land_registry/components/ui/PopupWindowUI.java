package land_registry.components.ui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import javafx.scene.text.Font;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class PopupWindowUI {
    // default values
    // header, main, footer
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 500;
    private static final String DEFAULT_TITLE = "unknown title";

    private boolean initialized = false;
    private int windowWidth;
    private int windowHeight;
    private String windowTitle;
    private Popup popup;
    private VBox parentNode;
    private Stage parentStage;
    private StackPane headerPanel;
    protected StackPane contentPanel;
    private StackPane footerPanel;

    public PopupWindowUI() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public PopupWindowUI(int width, int height) {
        setInitialized(false);
        setWindowWidth(width);
        setWindowHeight(height);
        setWindowTitle(DEFAULT_TITLE);
        initContent();
    }

    private void initContent() {
        popup = new Popup();
        popup.setWidth(getWindowWidth());
        popup.setHeight(getWindowHeight());

        parentNode = new VBox();
        parentNode.setFillWidth(true);
        parentNode.setId("parentNode");
        parentNode.setPrefSize(this.getWindowWidth(), this.getWindowHeight());

        headerPanel = new StackPane();
        contentPanel = new StackPane();
        footerPanel = new StackPane();

        Label headerLabel = new Label(windowTitle);
        headerLabel.setId("headerTitle");
        headerPanel.setId("headerPanel");
        headerPanel.setAlignment(Pos.CENTER);
        headerPanel.getChildren().add(headerLabel);

        contentPanel.setPrefHeight(windowHeight * .8);
        contentPanel.setId("contentPanel");

        HBox buttonBar = new HBox();
        buttonBar.setSpacing(10);
        buttonBar.setPadding(new Insets(0, 5, 0, 5));
        buttonBar.setFillHeight(true);
        buttonBar.setAlignment(Pos.CENTER);

        footerPanel.setId("contentPanel");
        footerPanel.setAlignment(Pos.CENTER);
        footerPanel.getChildren().add(buttonBar);

        VBox.setVgrow(headerPanel, Priority.ALWAYS);
        VBox.setVgrow(contentPanel, Priority.ALWAYS);
        VBox.setVgrow(footerPanel, Priority.ALWAYS);
        parentNode.getChildren().setAll(headerPanel, contentPanel, footerPanel);
        popup.getContent().setAll(parentNode);
        initStyles();
        setInitialized(true);
    }

    private void initStyles() {
        parentNode.getStylesheets().add(String.valueOf(getClass().getResource("../../styles/popup.css")));
    }

    public void hide() {
        popup.hide();
    }

    public void show() {
        popup.show(parentStage);
    }

    public void show(Stage stage) {
        popup.show(stage);
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public Stage getParentStage() {
        return parentStage;
    }

    public ObservableList<Node> getContent() {
        return contentPanel.getChildren();
    }

    public ObservableList<Node> getControlButtons() {
        return ((HBox) footerPanel.getChildren().get(0)).getChildren();
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;

        if (isInitialized()) {
            Label label = (Label) headerPanel.getChildren().get(0);
            label.setText(windowTitle);
        }
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    private void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}