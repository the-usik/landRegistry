package land_registry.database.models;

import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import land_registry.ui.utils.FormNode;
import land_registry.ui.utils.FormNodeGroup;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Objects;

public class CollectionModel {
    protected final ObjectId _id;

    public CollectionModel(ObjectId id) {
        this._id = id;
    }

    public CollectionModel(Document document) {
        this(document.getObjectId("_id"));
    }

    public ObjectId get_id() {
        return _id;
    }

    public FormNodeGroup getFormNodeGroup() {
        FormNodeGroup formNodeGroup = new FormNodeGroup();
        System.out.println(get_id());
        TextField idTextField = new TextField();
        idTextField.setId("_id");
        idTextField.setText(!Objects.isNull(get_id()) ? get_id().toString() : "id");
        idTextField.setPromptText("Collection identify...");
        idTextField.setDisable(true);

        formNodeGroup.append("Identify:", idTextField);

        return formNodeGroup;
    }

//    public static FormNodeGroup createFormNodeGroup() {
//        CollectionModel collectionModel = new CollectionModel((Document) null);
//
//        return collectionModel.getFormNodeGroup();
//    }

    public static Document createDocumentFromFrom(FormNodeGroup formNodeGroup) {
        Document document = new Document();

        for (FormNode formNode : formNodeGroup.getFormNodes()) {
            Node currentNode = formNode.getNode();
            String name = currentNode.getId();
            String value = "";

            if (currentNode instanceof TextField)
                value = ((TextField) currentNode).getText();
            if (currentNode instanceof ChoiceBox) {
                Object objectValue = ((ChoiceBox) currentNode).getValue();
                if (!Objects.isNull(objectValue)) {
                    value = objectValue.toString();
                } else continue;
            }

            document.append(name, value);
        }

        return document;
    }
}
