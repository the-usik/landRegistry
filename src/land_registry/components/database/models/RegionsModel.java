package land_registry.components.database.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RegionsModel extends CollectionModel {
    private final ArrayList<ObjectId> landIds;
    private final SimpleStringProperty address;
    private final SimpleDoubleProperty totalAreaSize;

    public RegionsModel(@NotNull Document document) {
        this(
                document.getObjectId("_id"), (ArrayList<ObjectId>) document.get("landIds"),
                document.getString("address"), document.getDouble("totalAreaSize")
        );
    }

    public RegionsModel(
            ObjectId id, ArrayList<ObjectId> landIds,
            String address, double totalAreaSize
    ) {
        super(id);
        this.landIds = landIds;
        this.address = new SimpleStringProperty(address);
        this.totalAreaSize = new SimpleDoubleProperty(totalAreaSize);
    }

    public ArrayList<ObjectId> getLandIds() {
        return landIds;
    }

    public double getTotalAreaSize() {
        return totalAreaSize.get();
    }

    public String getAddress() {
        return address.get();
    }
}
