package land_registry.components.database.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import land_registry.components.ui.utils.FormNodeGroup;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LandOwnersModel extends CollectionModel {
    private final ArrayList<ObjectId> landIds;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty middleName;
    private final SimpleStringProperty lastName;

    public LandOwnersModel(@NotNull Document document) {
        this(
                document.getObjectId("_id"), (ArrayList<ObjectId>) document.get("landIds"),
                document.getString("firstName"), document.getString("middleName"),
                document.getString("lastName")
        );
    }

    public LandOwnersModel(ObjectId id, ArrayList<ObjectId> landIds, String firstName, String middleName, String lastName) {
        super(id);
        this.landIds = landIds;
        this.firstName = new SimpleStringProperty(firstName);
        this.middleName = new SimpleStringProperty(middleName);
        this.lastName = new SimpleStringProperty(lastName);
    }

    public ArrayList<ObjectId> getLandIds() {
        return landIds;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getMiddleName() {
        return middleName.get();
    }

    @Override
    public FormNodeGroup getFormNodeGroup() {
        FormNodeGroup formNodeGroup = super.getFormNodeGroup();

        TextField firstNameField = new TextField();
        firstNameField.setId("firstName");
        firstNameField.setText(getFirstName());
        firstNameField.setPromptText("Enter a first name...");

        TextField lastNameField = new TextField();
        lastNameField.setId("lastName");
        lastNameField.setText(getLastName());
        lastNameField.setPromptText("Enter a last name...");

        TextField middleNameField = new TextField();
        middleNameField.setId("middleName");
        middleNameField.setText(getMiddleName());
        middleNameField.setPromptText("Enter a middle name...");

        formNodeGroup.append("First name", firstNameField);
        formNodeGroup.append("Last name", lastNameField);
        formNodeGroup.append("Middle name", middleNameField);

        return formNodeGroup;
    }
}
