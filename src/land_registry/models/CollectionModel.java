package land_registry.models;

import org.bson.Document;
import org.bson.types.ObjectId;

public class CollectionModel {
    protected final ObjectId _id;

    public CollectionModel(ObjectId id) {
        this._id = id;
    }

    // I didn't want...
    public ObjectId get_id() {
        return _id;
    }
}
