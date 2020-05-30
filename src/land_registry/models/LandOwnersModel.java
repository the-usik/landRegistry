package land_registry.models;

import javafx.beans.property.SimpleStringProperty;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
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
}
