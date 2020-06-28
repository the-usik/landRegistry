package land_registry.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import land_registry.database.Database;
import land_registry.database.collections.*;
import land_registry.database.models.CollectionModel;
import land_registry.ui.PopupFormUI;

public class MainController extends Controller {
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

    private UsersCollection usersCollection;
    private LandsCollection landsCollection;
    private RegionsCollection regionsCollection;
    private LandOwnersCollection landOwnersCollection;

    private Database.Collection selectedCollection;

    @Override
    public void onShowing() {
        database = mainContext.getDatabase();
        initTables();
        initChoiceBox();
        initEventHandlers();
    }

    private void initTables() {
        usersCollection = new UsersCollection(Database.Collection.USERS.getDbCollection());
        landsCollection = new LandsCollection(Database.Collection.LANDS.getDbCollection());
        regionsCollection = new RegionsCollection(Database.Collection.REGIONS.getDbCollection());
        landOwnersCollection = new LandOwnersCollection(Database.Collection.LAND_OWNERS.getDbCollection());

        tableWrapperPane.getChildren().setAll(
                usersCollection.getTableView(), landsCollection.getTableView(),
                regionsCollection.getTableView(), landOwnersCollection.getTableView()
        );
    }

    private void initChoiceBox() {
        choiceBox.getItems().addAll(Database.Collection.values());
    }

    private void initEventHandlers() {
        choiceBox.setOnAction(this::onChoiceBoxAction);
        searchField.setOnKeyTyped(this::onInputSearchField);
        addButton.setOnMouseClicked(this::onAddButtonClick);
        editButton.setOnMouseClicked(this::onEditButtonClick);
        deleteButton.setOnMouseClicked(this::onDeleteButtonClick);

    }

    private void hideCollections() {
        usersCollection.getTableView().setVisible(false);
        landsCollection.getTableView().setVisible(false);
        regionsCollection.getTableView().setVisible(false);
        landOwnersCollection.getTableView().setVisible(false);
    }

    private void showCollection(Database.Collection collection) {
        getCollection(collection).getTableView().setVisible(true);
    }

    private void selectCollection(Database.Collection collection) {
        hideCollections();
        setSelectedCollection(collection);
        showCollection(selectedCollection);
    }

    @SuppressWarnings("unchecked")
    private <K extends CollectionModel, T extends Collection<K>> T getCollection(Database.Collection collection) {
        return switch (collection) {
            case LANDS -> (T) landsCollection;
            case USERS -> (T) usersCollection;
            case REGIONS -> (T) regionsCollection;
            case LAND_OWNERS -> (T) landOwnersCollection;
        };
    }

    public void setSelectedCollection(Database.Collection selectedCollection) {
        this.selectedCollection = selectedCollection;
    }

    public void onChoiceBoxAction(ActionEvent event) {
        selectCollection(choiceBox.getValue());
    }

    public void onInputSearchField(KeyEvent event) {
    }

    public void onAddButtonClick(MouseEvent event) {
        PopupFormUI popupFormUI = new PopupFormUI();
        // TODO: showing the add popup for collections (users, lands, landOwners, regions)
        popupFormUI.show(getStage());
    }

    public void onEditButtonClick(MouseEvent event) {
    }

    public void onDeleteButtonClick(MouseEvent event) {
    }

}