package land_registry.controllers;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javafx.scene.text.Text;
import land_registry.components.database.Database;
import land_registry.components.database.models.CollectionModel;
import land_registry.components.ui.PopupFormUI;
import land_registry.components.database.models.*;

import land_registry.components.ui.utils.FormNode;
import land_registry.components.ui.utils.FormNodeGroup;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {
    @FXML
    private Button addDataButton;

    @FXML
    private Button removeDataButton;

    @FXML
    private Button editDataButton;

    @FXML
    private TextField searchField;

    @FXML
    private Pane tableWrapperPane;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private ChoiceBox<Database.Collection> choiceBox;

    @FXML
    private final HashMap<Database.Collection, TableView<? extends CollectionModel>> tableViewMap = new HashMap<>();

    private Database.Collection selectedTableCollection;

    @Override
    public void onMainContextInit() {
        System.out.println("main context inited!");
    }

    @Override
    public void onShowing() {
        database = mainContext.getDatabase();

        initTableViewMap();
        loadTableCollectionsOnPage();
        loadChoiceDatabaseItems();
        setSelectedCollection(Database.Collection.LANDS);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.setOnAction(this::onChoiceBoxAction);
        searchField.setOnKeyTyped(this::onInputDataSearchField);
        addDataButton.setOnMouseClicked(this::onAddDataButtonClick);
        editDataButton.setOnMouseClicked(this::onEditDataButtonClick);
        removeDataButton.setOnMouseClicked(this::onRemoveDataButtonClick);
    }

    private void hideActiveTables() {
        for (Database.Collection collection : Database.Collection.values()) {
            if (tableViewMap.get(collection).isVisible())
                tableViewMap.get(collection).setVisible(false);
        }
    }

    private void initTableViewMap() {
        tableViewMap.put(
                Database.Collection.LANDS,
                createDynamicTableForCollection(Database.Collection.LANDS, LandsModel.class)
        );
        tableViewMap.put(
                Database.Collection.LAND_OWNERS,
                createDynamicTableForCollection(Database.Collection.LAND_OWNERS, LandOwnersModel.class)
        );
        tableViewMap.put(
                Database.Collection.REGIONS,
                createDynamicTableForCollection(Database.Collection.REGIONS, RegionsModel.class)
        );
        tableViewMap.put(
                Database.Collection.USERS,
                createDynamicTableForCollection(Database.Collection.USERS, UsersModel.class)
        );
    }

    private void loadChoiceDatabaseItems() {
        choiceBox
                .getItems()
                .setAll(Database.Collection.values());
    }

    private void loadTableCollectionsOnPage() {
        tableWrapperPane
                .getChildren()
                .setAll(tableViewMap.values());
    }

    private void onChoiceBoxAction(ActionEvent actionEvent) {
        hideActiveTables();
        setSelectedCollection(choiceBox.getValue());
    }

    private void onInputDataSearchField(KeyEvent keyEvent) {
        System.out.println("Searching data...");
    }

    private void onAddDataButtonClick(MouseEvent mouseEvent) {
        FormNodeGroup formNodeGroup = CollectionModel.createFormNodeGroup();

        PopupFormUI popupFormUI = new PopupFormUI(350, 500);
        popupFormUI.setFormNodeGroup(formNodeGroup);
        popupFormUI.setWindowTitle("Adding Data");
        popupFormUI.setParentStage(stage);

        Button button = new Button("add");
        button.setMinWidth(popupFormUI.getWindowWidth());

        popupFormUI.getControlButtons().add(button);
        popupFormUI.show();
    }

    private void onEditDataButtonClick(MouseEvent mouseEvent) {
        CollectionModel collectionModel = getSelectedTableView().getSelectionModel().getSelectedItem();
        if (collectionModel == null) return;

        FormNodeGroup formNodeGroup = collectionModel.getFormNodeGroup();

        Button editButton = new Button();
        editButton.setText("Edit");
        editButton.setMinWidth(350);
        editButton.setOnMouseClicked(editMouseEvent -> {
            Document document = new Document();
            for (FormNode formNode : formNodeGroup.getFormNodes()) {
                String valueNode = null;

                if (formNode.getNode() instanceof TextField) {
                    valueNode = ((TextField) formNode.getNode()).getText();
                }

                if (formNode.getNode() instanceof ChoiceBox) {
                    valueNode = (String) ((ChoiceBox) formNode.getNode()).getValue();
                }

                if (formNode.getNode() instanceof ToggleButton) {
                    valueNode = Boolean.toString(((ToggleButton) formNode.getNode()).isSelected());
                }

                document.append(formNode.getNode().getId(), valueNode);
            }
            System.out.println("get data for editing:" + document.toJson());
        });

        PopupFormUI popupFormUI = new PopupFormUI(350, 500);
        popupFormUI.setWindowTitle("Editing Data");
        popupFormUI.setFormNodeGroup(formNodeGroup);
        popupFormUI.getControlButtons().add(editButton);
        popupFormUI.show(stage);
    }

    private void onRemoveDataButtonClick(MouseEvent mouseEvent) {
        System.out.println(getSelectedTableView().getSelectionModel().getSelectedItem().get_id());
    }

    private <T extends CollectionModel> @NotNull TableView<T> createDynamicTableForCollection(Database.Collection collectionName, Class<T> collectionModel) {
        TableView<T> collectionTableView = new TableView<>();
        ObservableList<T> collectionData = FXCollections.observableArrayList();

        MongoCollection<Document> collection = database.getCollection(collectionName);
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> iterator = findIterable.iterator();

        for (Map.Entry<String, Object> entry : findIterable.first().entrySet()) {
            TableColumn<T, String> column = new TableColumn<>(entry.getKey());
            column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
            collectionTableView.getColumns().add(column);
        }

        while (iterator.hasNext()) {
            Document document = iterator.next();
            try {
                collectionData.add(
                        collectionModel
                                .getDeclaredConstructor(Document.class)
                                .newInstance(document)
                );
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }

        collectionTableView.setItems(collectionData);
        return collectionTableView;
    }

    public Database.Collection getSelectedTableCollection() {
        return selectedTableCollection;
    }

    public TableView<? extends CollectionModel> getSelectedTableView() {
        return tableViewMap.get(getSelectedTableCollection());
    }

    private void setSelectedCollection(Database.Collection collection) {
        selectedTableCollection = collection;
        tableViewMap.get(collection).setVisible(true);
        choiceBox.setValue(selectedTableCollection);
    }
}