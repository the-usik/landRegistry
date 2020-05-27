package land_registry.models;

import org.bson.types.ObjectId;

public class CollectionModel {
    protected final ObjectId id;

    public CollectionModel(ObjectId id) {
        this.id = id;
    }

    public ObjectId getId() {
        return id;
    }
}
