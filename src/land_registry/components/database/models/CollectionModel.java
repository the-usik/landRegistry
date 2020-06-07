package land_registry.components.database.models;

import javafx.scene.control.TextField;
import land_registry.components.ui.utils.FormNodeGroup;
import org.bson.types.ObjectId;

public class CollectionModel {
    protected final ObjectId _id;

    public CollectionModel(ObjectId id) {
        this._id = id;
    }

    public ObjectId get_id() {
        return _id;
    }

    public FormNodeGroup getFormNodeGroup() {
        FormNodeGroup formNodeGroup = new FormNodeGroup();

        TextField idTextField = new TextField();
        idTextField.setText(get_id().toString());
        idTextField.setPromptText("Collection identify...");
        idTextField.setDisable(true);

        formNodeGroup.append("_id", idTextField);

        return formNodeGroup;
    }

    public static FormNodeGroup createFormNodeGroup() {
        CollectionModel collectionModel = new CollectionModel(null);

        return collectionModel.getFormNodeGroup();
    }
}
