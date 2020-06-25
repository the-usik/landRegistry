package land_registry.controllers;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
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

import land_registry.database.Database;
import land_registry.database.models.CollectionModel;
import land_registry.database.models.*;
import org.bson.Document;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
public class MainController extends Controller implements Initializable {
    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private TextField searchField;

    @FXML
    private Pane tableWrapperPane;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private ChoiceBox<Database.Collection> choiceBox;

    private TableView<UsersModel> usersTable;
    private TableView<LandsModel> landsTable;
    private TableView<RegionsModel> regionsTable;
    private TableView<LandOwnersModel> ownersTable;

    private Database.Collection selectedCollection;

    @Override
    public void onShowing() {
        database = mainContext.getDatabase();
        initTables();
        fillTables();
        initChoiceBox();
        initEventHandlers();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void onMainContextInit() {

    }

    private void initTables() {
        usersTable = new TableView<>();
        landsTable = new TableView<>();
        regionsTable = new TableView<>();
        ownersTable = new TableView<>();

        ObservableList<UsersModel> usersObservableList = FXCollections.observableArrayList();
        ObservableList<LandsModel> landsObservableList = FXCollections.observableArrayList();
        ObservableList<RegionsModel> regionsObservableList = FXCollections.observableArrayList();
        ObservableList<LandOwnersModel> ownersObservableList = FXCollections.observableArrayList();

        database
                .getCollection(Database.Collection.USERS)
                .find()
                .first()
                .forEach((key, value) -> {
                    TableColumn<UsersModel, String> column = new TableColumn<>(key);
                    column.setCellValueFactory(new PropertyValueFactory<>(key));
                    usersTable.getColumns().add(column);
                });


        database
                .getCollection(Database.Collection.LANDS)
                .find()
                .first()
                .forEach((key, value) -> {
                    TableColumn<LandsModel, String> column = new TableColumn<>(key);
                    column.setCellValueFactory(new PropertyValueFactory<>(key));
                    landsTable.getColumns().add(column);
                });


        database
                .getCollection(Database.Collection.REGIONS)
                .find()
                .first()
                .forEach((key, value) -> {
                    TableColumn<RegionsModel, String> column = new TableColumn<>(key);
                    column.setCellValueFactory(new PropertyValueFactory<>(key));
                    regionsTable.getColumns().add(column);
                });


        database
                .getCollection(Database.Collection.LAND_OWNERS)
                .find()
                .first()
                .forEach((key, value) -> {
                    TableColumn<LandOwnersModel, String> column = new TableColumn<>(key);
                    column.setCellValueFactory(new PropertyValueFactory<>(key));
                    ownersTable.getColumns().add(column);
                });

        usersTable.setItems(usersObservableList);
        landsTable.setItems(landsObservableList);
        ownersTable.setItems(ownersObservableList);
        regionsTable.setItems(regionsObservableList);

        tableWrapperPane
                .getChildren()
                .addAll(usersTable, landsTable, regionsTable, ownersTable);
    }

    private void initChoiceBox() {
        choiceBox
                .getItems()
                .addAll(Database.Collection.values());
    }

    private void initEventHandlers() {
        choiceBox.setOnAction(this::onChoiceBoxAction);
        searchField.setOnKeyTyped(this::onInputSearchField);
        addButton.setOnMouseClicked(this::onAddButtonClick);
        editButton.setOnMouseClicked(this::onEditButtonClick);
        deleteButton.setOnMouseClicked(this::onDeleteButtonClick);

    }

    private void fillTables() {
        for (Document document : database.getCollection(Database.Collection.USERS).find()) {
            usersTable
                    .getItems()
                    .add(new UsersModel(document));
        }

        for (Document document : database.getCollection(Database.Collection.REGIONS).find()) {
            regionsTable
                    .getItems()
                    .add(new RegionsModel(document));
        }

        for (Document document : database.getCollection(Database.Collection.LANDS).find()) {
            landsTable
                    .getItems()
                    .add(new LandsModel(document));
        }

        for (Document document : database.getCollection(Database.Collection.LAND_OWNERS).find()) {
            ownersTable
                    .getItems()
                    .add(new LandOwnersModel(document));
        }

    }

    public void selectCollection(Database.Collection collection) {
        hideTables();
        setSelectedCollection(collection);
        setSelectedTableCollection(collection);
    }

    public void hideTables() {
        usersTable.setVisible(false);
        landsTable.setVisible(false);
        ownersTable.setVisible(false);
        regionsTable.setVisible(false);
    }

    public void showTable(Database.Collection collection) {
        getTableCollection(collection).setVisible(true);
    }

    private TableView<?> getTableCollection(Database.Collection collection) {
        return switch (collection) {
            case LAND_OWNERS -> ownersTable;
            case REGIONS -> regionsTable;
            case LANDS -> landsTable;
            case USERS -> usersTable;
        };
    }

    public void setSelectedCollection(Database.Collection collection) {
        selectedCollection = collection;
    }

    public void setSelectedTableCollection(Database.Collection collection) {
        showTable(collection);
    }

    public void onChoiceBoxAction(ActionEvent event) {
        selectCollection(choiceBox.getValue());
        System.out.println("Collection is selected");
    }

    public void onInputSearchField(KeyEvent event) {
    }

    public void onAddButtonClick(MouseEvent event) {

    }

    public void onEditButtonClick(MouseEvent event) {
    }

    public void onDeleteButtonClick(MouseEvent event) {
    }
}