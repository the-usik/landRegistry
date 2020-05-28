package land_registry.controllers;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import land_registry.components.LandRegistryDatabase;
import land_registry.models.*;
import org.bson.Document;

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
    private ChoiceBox<LandRegistryDatabase.Collection> choiceBox;

    @FXML
    private HashMap<LandRegistryDatabase.Collection, TableView<? extends CollectionModel>> tableViewMap = new HashMap<>();

    private LandRegistryDatabase database;
    private LandRegistryDatabase.Collection activeTableCollection;

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
        setActiveCollection(LandRegistryDatabase.Collection.LANDS);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.setOnAction(this::onChoiceBoxAction);
        searchField.setOnKeyTyped(this::onInputDataSearchField);
        addDataButton.setOnMouseClicked(this::onAddDataButtonClick);
        editDataButton.setOnMouseClicked(this::onEditDataButtonClick);
        removeDataButton.setOnMouseClicked(this::onRemoveDataButtonClick);
    }

    private void initTableViewMap() {
        tableViewMap.put(
                LandRegistryDatabase.Collection.LANDS,
                createDynamicTableForCollection(LandRegistryDatabase.Collection.LANDS, LandsModel.class)
        );
        tableViewMap.put(
                LandRegistryDatabase.Collection.LAND_OWNERS,
                createDynamicTableForCollection(LandRegistryDatabase.Collection.LAND_OWNERS, LandOwnersModel.class)
        );
        tableViewMap.put(
                LandRegistryDatabase.Collection.REGIONS,
                createDynamicTableForCollection(LandRegistryDatabase.Collection.REGIONS, RegionsModel.class)
        );
        tableViewMap.put(
                LandRegistryDatabase.Collection.USERS,
                createDynamicTableForCollection(LandRegistryDatabase.Collection.USERS, UsersModel.class)
        );
    }

    private void loadTableCollectionsOnPage() {
        tableWrapperPane
                .getChildren()
                .setAll(tableViewMap.values());
    }

    private void loadChoiceDatabaseItems() {
        choiceBox
                .getItems()
                .setAll(LandRegistryDatabase.Collection.values());
    }

    private void hideActiveTables() {
        for (LandRegistryDatabase.Collection collection : LandRegistryDatabase.Collection.values()) {
            if (tableViewMap.get(collection).isVisible())
                tableViewMap.get(collection).setVisible(false);
        }
    }

    private void setActiveCollection(LandRegistryDatabase.Collection collection) {
        activeTableCollection = collection;
        tableViewMap.get(collection).setVisible(true);
        choiceBox.setValue(activeTableCollection);
    }

    private void onChoiceBoxAction(ActionEvent actionEvent) {
        hideActiveTables();
        setActiveCollection(choiceBox.getValue());
    }

    private void onInputDataSearchField(KeyEvent keyEvent) {
        System.out.println("Searching data...");
    }

    private void onAddDataButtonClick(MouseEvent mouseEvent) {
    }

    private void onEditDataButtonClick(MouseEvent mouseEvent) {
        System.out.println("editing data...");
    }

    private void onRemoveDataButtonClick(MouseEvent mouseEvent) {
        System.out.println("removing data....");
    }

    private <T extends CollectionModel> TableView<T> createDynamicTableForCollection(LandRegistryDatabase.Collection collectionName, Class<T> collectionModel) {
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
}
