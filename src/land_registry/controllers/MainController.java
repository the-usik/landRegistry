package land_registry.controllers;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import com.mongodb.client.MongoDatabase;
import javafx.beans.property.*;
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
import land_registry.models.CollectionModel;
import land_registry.models.LandOwnersModel;
import land_registry.models.LandsModel;
import land_registry.models.RegionsModel;
import org.bson.Document;

import javax.print.Doc;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
    private TableView<String> tableView;

    @FXML
    private HashMap<LandRegistryDatabase.CollectionNames, TableView<? extends CollectionModel>> tableViewMap = new HashMap<>();

    @FXML
    private ChoiceBox<LandRegistryDatabase.CollectionNames> choiceBox;

    private LandRegistryDatabase database;
    private LandRegistryDatabase.CollectionNames selectedTableName;

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

        // lands
        TableView<LandsModel> landsTableView = new TableView<>();
        ObservableList<LandsModel> landsData = FXCollections.observableArrayList();
        MongoCollection<Document> landsCollection = database.getCollection(LandRegistryDatabase.CollectionNames.LANDS);
        FindIterable<Document> findIterable = landsCollection.find();
        MongoCursor<Document> iterator = findIterable.iterator();

        // add columns
        for (Map.Entry<String, Object> entry : findIterable.first().entrySet()) {
            TableColumn<LandsModel, String> column = new TableColumn<>(entry.getKey());
            column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
            landsTableView.getColumns().add(column);
        }

        // add data
        while (iterator.hasNext()) {
            Document document = iterator.next();
            landsData.add(LandsModel.createModelOfDocument(document));
        }

        landsTableView.setItems(landsData);
        tableWrapperPane.getChildren().add(landsTableView);
        tableView.setVisible(false);

        loadChoiceDatabaseItems();
    }


    private void loadSelectedCollectionTable() {

    }
//    private void loadSelectedDatabaseCollectionTable() {
//        MongoCollection<Document> selectedCollection = getSelectedCollection();
//        MongoCursor<Document> collectionIterator = selectedCollection.find().iterator();
//        ObservableList<String> tableData = FXCollections.observableArrayList();
//        tableView.getColumns().clear();
//
//        while (collectionIterator.hasNext()) {
//            Document document = collectionIterator.next();
//
//            for (Map.Entry<String, Object> documentEntry : document.entrySet()) {
//                TableColumn<String, String> column = new TableColumn<>(documentEntry.getKey());
//                column.setCellValueFactory(
//                        cellDataFeatures -> new SimpleStringProperty(documentEntry.getValue().toString())
//                );
//
//                tableView.getColumns().add(column);
//            }
//
//            tableData.add("");
//        }
//
//        tableView.setItems(tableData);
//    }

    private void loadChoiceDatabaseItems() {
        choiceBox.getItems().addAll(LandRegistryDatabase.CollectionNames.values());

        choiceBox.setValue(LandRegistryDatabase.CollectionNames.LANDS);
    }

    private void onChoiceBoxAction(ActionEvent actionEvent) {

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
