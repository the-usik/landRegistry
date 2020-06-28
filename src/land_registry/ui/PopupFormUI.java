package land_registry.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import land_registry.ui.utils.FormNode;
import land_registry.ui.utils.FormNodeGroup;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PopupFormUI extends PopupWindowUI {
    private static final int FORM_NODE_PADDING = 30;
    private VBox formContainer;
    private FormNodeGroup formNodeGroup;

    public PopupFormUI() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public PopupFormUI(int width, int height) {
        super(width, height);
        initForm();
    }

    private void initForm() {
        formContainer = new VBox();
        formNodeGroup = new FormNodeGroup();
        contentPanel.getChildren().add(formContainer);
    }

    private void renderFormNode(FormNode formNode) {
        if (formNode == null) return;

        HBox formNodeContainer = new HBox();
        StackPane keyWrapperPanel = new StackPane();
        StackPane nodeWrapperPanel = new StackPane();

        HBox.setHgrow(keyWrapperPanel, Priority.ALWAYS);
        HBox.setHgrow(nodeWrapperPanel, Priority.ALWAYS);
        VBox.setVgrow(formNodeContainer, Priority.ALWAYS);

        keyWrapperPanel.setPrefWidth(0);
        keyWrapperPanel.setAlignment(Pos.CENTER_LEFT);
        keyWrapperPanel.getChildren().add(new Label(formNode.getName()));
        keyWrapperPanel.setPrefWidth((double) getWindowWidth() / 2);
        keyWrapperPanel.setPadding(new Insets(0, FORM_NODE_PADDING, 0, FORM_NODE_PADDING));

        nodeWrapperPanel.setAlignment(Pos.CENTER_LEFT);
        nodeWrapperPanel.getChildren().add(formNode.getNode());
        nodeWrapperPanel.setPrefWidth((double) getWindowWidth() / 2);
        nodeWrapperPanel.setPadding(new Insets(0, FORM_NODE_PADDING, 0, FORM_NODE_PADDING));

        formNodeContainer.getChildren().addAll(keyWrapperPanel, nodeWrapperPanel);
        formContainer.getChildren().add(formNodeContainer);
    }

    private void renderFormNodes() {
        for (FormNode formNode : formNodeGroup.getFormNodes()) {
            renderFormNode(formNode);
        }
    }

    public void setFormNodeGroup(FormNodeGroup formNodeGroup) {
        this.formNodeGroup = formNodeGroup;
    }

    public FormNodeGroup getFormNodeGroup() {
        return formNodeGroup;
    }

    @Override
    public void show() {
        this.renderFormNodes();
        super.show();
    }

    @Override
    public void show(Stage stage) {
        this.renderFormNodes();
        super.show(stage);
    }

    @Contract("_, _ -> new")
    public static @NotNull PopupFormUI createPopupForm(int width, int height) {
        return new PopupFormUI(width, height);
    }
}
