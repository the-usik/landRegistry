package land_registry.database.collections;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import land_registry.database.models.CollectionModel;
import org.bson.Document;

public class Collection<T extends CollectionModel> {
    protected TableView<T> tableView;
    protected ObservableList<T> observableList;
    protected MongoCollection<Document> dbCollection;
    private boolean initialized = false;

    public Collection(MongoCollection<Document> dbCollection) {
        setTableView(new TableView<>());
        setObservableList(FXCollections.observableArrayList());
        setDbCollection(dbCollection);
        initTable();
        fillObservableList();
    }

    private void initTable() {
        FindIterable<Document> findIterable = dbCollection.find();
        Document firstDocument = findIterable.first();

        if (firstDocument != null) {
            firstDocument.forEach((key, value) -> {
                TableColumn<T, String> column = new TableColumn<>(key);
                column.setCellValueFactory(new PropertyValueFactory<>(key));
                tableView.getColumns().add(column);
            });
        } else setInitialized(false);

        tableView.setItems(observableList);
        setInitialized(true);
    }

    protected void fillObservableList() {

    }

    public boolean isInitialized() {
        return initialized;
    }

    public TableView<T> getTableView() {
        return tableView;
    }

    public ObservableList<T> getObservableList() {
        return observableList;
    }

    public MongoCollection<Document> getDbCollection() {
        return dbCollection;
    }

    private void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public void setTableView(TableView<T> tableView) {
        this.tableView = tableView;
    }

    public void setObservableList(ObservableList<T> observableList) {
        this.observableList = observableList;
    }

    public void setDbCollection(MongoCollection<Document> dbCollection) {
        this.dbCollection = dbCollection;
    }
}
