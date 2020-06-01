package land_registry.components.ui;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class PopupUI {
    private static final int DEFAULT_POPUP_WIDTH = 400;
    private static final int DEFAULT_POPUP_HEIGHT = 450;
    private static final String DEFAULT_POPUP_TITLE = "#PopupTitle";

    private final Popup popup;
    private final BorderPane borderPane;

    private Stage parentStage;
    private int popupWidth;
    private int popupHeight;
    private String title;

    public PopupUI() {
        this(DEFAULT_POPUP_WIDTH, DEFAULT_POPUP_HEIGHT);
    }

    public PopupUI(int width, int height) {
        this(width, height, DEFAULT_POPUP_TITLE);
    }

    public PopupUI(int width, int height, String title) {
        popup = new Popup();
        borderPane = new BorderPane();
        popup.setWidth(width);
        popup.setHeight(height);

        setPopupWidth(width);
        setPopupHeight(height);
        setTitle(title);
        initMainContent();
    }

    public void initMainContent() {
        initStyles();
        borderPane.setMaxSize(popupWidth, popupHeight);
        borderPane.setTop(createHeaderPane(title));
        borderPane.setCenter(createFormPane());
        borderPane.setBottom(createFooterPane());
        popup.getContent().setAll(borderPane);
    }

    public void initStyles() {
        borderPane.setStyle("-fx-background-color: #fff; -fx-background-radius: 10;");
    }

    public void show() {
        popup.show(parentStage);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void setPopupWidth(int popupWidth) {
        this.popupWidth = popupWidth;
    }

    public void setPopupHeight(int popupHeight) {
        this.popupHeight = popupHeight;
    }

    public Popup getPopup() {
        return popup;
    }

    public String getTitle() {
        return title;
    }

    public int getPopupWidth() {
        return popupWidth;
    }

    public int getPopupHeight() {
        return popupHeight;
    }

    public Stage getParentStage() {
        return parentStage;
    }

    public ObservableList<Node> getHeaderChildren() {
        return ((Pane) borderPane.getTop()).getChildren();
    }

    public ObservableList<Node> getContentChildren() {
        return ((Pane) borderPane.getCenter()).getChildren();
    }

    public ObservableList<Node> getFooterChildren() {
        return ((Pane) borderPane.getBottom()).getChildren();
    }

    public Pane createFormNode(String key, Node element) {
        Pane pane = new Pane();
        pane.setMinWidth(getPopupWidth());

        double halfWidth = pane.getMinWidth() / 2;

        Pane keyPane = new Pane();
        Pane valuePane = new Pane();

        keyPane.setMinWidth(halfWidth);
        valuePane.setMinWidth(halfWidth);

        Label label = new Label();
        label.setText(key);
        label.setFont(Font.font("SF Mono", 14));

        keyPane.getChildren().setAll(label);
        valuePane.getChildren().setAll(element);
        pane.getChildren().setAll(keyPane, valuePane);

        return pane;
    }

    public Pane createHeaderPane(String title) {
        Pane pane = new Pane();
        pane.setMinWidth(getPopupWidth());

        Label label = new Label();
        label.setText(title);
        label.setAlignment(Pos.CENTER);
        label.setMinWidth(pane.getMinWidth());
        label.setFont(new Font("SF Pro Display", 16));
        pane.getChildren().setAll(label);

        return pane;
    }

    public Pane createFormPane() {
        Pane pane = new Pane();
        VBox verticalBox = new VBox();
        pane.getChildren().add(verticalBox);

        return pane;
    }

    public Pane createFooterPane() {
        Pane pane = new Pane();
        ButtonBar buttonBar = new ButtonBar();
        pane.getChildren().setAll(buttonBar);

        return pane;
    }
}
