package land_registry.models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.bson.types.ObjectId;

public class LandsCollectionModel extends CollectionModel {
    private final ObjectId regionId;
    private final ObjectId ownerId;
    private final SimpleDoubleProperty price;
    private final SimpleDoubleProperty plottage;
    private final SimpleBooleanProperty forConstruction;

    public LandsCollectionModel(
            ObjectId id, ObjectId regionId, ObjectId ownerId,
            double price, double plottage, boolean forConstruction
    ) {
        super(id);
        this.regionId = regionId;
        this.ownerId = ownerId;
        this.price = new SimpleDoubleProperty(price);
        this.plottage = new SimpleDoubleProperty(plottage);
        this.forConstruction = new SimpleBooleanProperty(forConstruction);
    }

    public ObjectId getOwnerId() {
        return ownerId;
    }

    public ObjectId getRegionId() {
        return regionId;
    }

    public double getPlottage() {
        return plottage.get();
    }

    public double getPrice() {
        return price.get();
    }

    public boolean getForConstruction() {
        return forConstruction.get();
    }
}
