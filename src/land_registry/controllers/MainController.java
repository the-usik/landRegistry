package land_registry.controllers;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import land_registry.components.LandRegistryDatabase;
import org.bson.Document;

import java.net.URL;
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
    private ProgressBar progressBar;

    @FXML
    private TableView<String> tableView;

    @FXML
    private ChoiceBox<LandRegistryDatabase.DatabaseCollectionNames> choiceBox;

    private LandRegistryDatabase database;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.setOnAction(this::onChoiceBoxAction);
        searchField.setOnKeyTyped(this::onInputDataSearchField);
        addDataButton.setOnMouseClicked(this::onAddDataButtonClick);
        editDataButton.setOnMouseClicked(this::onEditDataButtonClick);
        removeDataButton.setOnMouseClicked(this::onRemoveDataButtonClick);
    }

    @Override
    public void onShowing() {
        database = mainContext.getDatabase();

        loadChoiceDatabaseItems();
        loadSelectedDatabaseCollectionTable();
    }

    private void loadSelectedDatabaseCollectionTable() {
        MongoCollection<Document> selectedCollection = getSelectedCollection();
        MongoCursor<Document> collectionIterator = selectedCollection.find().iterator();
        ObservableList<String> tableData = FXCollections.observableArrayList();
        tableView.getColumns().clear();

        while (collectionIterator.hasNext()) {
            Document document = collectionIterator.next();

            for (Map.Entry<String, Object> documentEntry : document.entrySet()) {
                TableColumn<String, String> column = new TableColumn<>(documentEntry.getKey());
                column.setCellValueFactory(
                        cellDataFeatures -> new SimpleStringProperty(documentEntry.getValue().toString())
                );

                tableView.getColumns().add(column);
            }

            tableData.add("");
        }

        tableView.setItems(tableData);
    }

    private void loadChoiceDatabaseItems() {
        choiceBox.getItems().addAll(
                LandRegistryDatabase.DatabaseCollectionNames.LANDS,
                LandRegistryDatabase.DatabaseCollectionNames.LAND_OWNERS,
                LandRegistryDatabase.DatabaseCollectionNames.REGIONS
        );

        choiceBox.setValue(LandRegistryDatabase.DatabaseCollectionNames.LANDS);
    }

    private MongoCollection<Document> getSelectedCollection() {
        return database.getCollection(choiceBox.getValue());
    }


    private void onChoiceBoxAction(ActionEvent actionEvent) {
        loadSelectedDatabaseCollectionTable();
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
}
