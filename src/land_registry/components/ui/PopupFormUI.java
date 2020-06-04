package land_registry.components.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class PopupFormUI extends PopupWindowUI {
    private final int FORM_NODE_PADDING = 30;
    private VBox formContainer;

    public PopupFormUI() {
        super();
        initForm();
    }

    public PopupFormUI(int width, int height) {
        super(width, height);
        initForm();
    }

    private void initForm() {
        formContainer = new VBox();
        contentPanel.getChildren().add(formContainer);
    }

    public void addFormNode(String name, Node node) {
        HBox formNode = new HBox();
        StackPane keyWrapperPanel = new StackPane();
        StackPane nodeWrapperPanel = new StackPane();

        HBox.setHgrow(keyWrapperPanel, Priority.ALWAYS);
        HBox.setHgrow(nodeWrapperPanel, Priority.ALWAYS);
        VBox.setVgrow(formNode, Priority.ALWAYS);

        keyWrapperPanel.setPrefWidth(0);
        keyWrapperPanel.setAlignment(Pos.CENTER_LEFT);
        keyWrapperPanel.getChildren().add(new Label(name));
        keyWrapperPanel.setPadding(new Insets(0, FORM_NODE_PADDING, 0, FORM_NODE_PADDING));

        nodeWrapperPanel.setAlignment(Pos.CENTER_LEFT);
        nodeWrapperPanel.getChildren().add(node);
        nodeWrapperPanel.setPadding(new Insets(0, FORM_NODE_PADDING, 0, FORM_NODE_PADDING));

        formNode.getChildren().addAll(keyWrapperPanel, nodeWrapperPanel);
        formContainer.getChildren().add(formNode);
    }
}
