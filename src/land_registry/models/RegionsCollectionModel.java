package land_registry.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import org.bson.types.ObjectId;

public class RegionsCollectionModel extends CollectionModel {
    private final ObjectId[] landIds;
    private final SimpleStringProperty address;
    private final SimpleDoubleProperty totalAreaSize;

    public RegionsCollectionModel(
            ObjectId id, ObjectId[] landIds,
            String address, double totalAreaSize
    ) {
        super(id);
        this.landIds = landIds;
        this.address = new SimpleStringProperty(address);
        this.totalAreaSize = new SimpleDoubleProperty(totalAreaSize);
    }

    public ObjectId[] getLandIds() {
        return landIds;
    }

    public double getTotalAreaSize() {
        return totalAreaSize.get();
    }

    public String getAddress() {
        return address.get();
    }
}
