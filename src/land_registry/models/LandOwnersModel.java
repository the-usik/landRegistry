package land_registry.models;

import javafx.beans.property.SimpleStringProperty;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

public class LandOwnersModel extends CollectionModel {
    private final ObjectId[] landIds;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty middleName;
    private final SimpleStringProperty lastName;

    public LandOwnersModel(@NotNull Document document) {
        this(
                document.getObjectId("_id"), null,
                document.getString("firstName"), document.getString("middleName"),
                document.getString("lastName")
        );
    }

    public LandOwnersModel(ObjectId id, ObjectId[] landIds, String firstName, String middleName, String lastName) {
        super(id);
        this.landIds = landIds;
        this.firstName = new SimpleStringProperty(firstName);
        this.middleName = new SimpleStringProperty(middleName);
        this.lastName = new SimpleStringProperty(lastName);
    }

    public ObjectId[] getLandIds() {
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
}
