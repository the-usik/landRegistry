package land_registry.database.collections;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import land_registry.database.models.LandOwnersModel;
import org.bson.Document;

public class LandOwnersCollection extends Collection<LandOwnersModel> {
    public LandOwnersCollection(MongoCollection<Document> dbCollection) {
        super(dbCollection);
    }

    @Override
    protected void fillObservableList() {
        FindIterable<Document> findIterable = dbCollection.find();

        for (Document document : findIterable)
            observableList.add(new LandOwnersModel(document));
    }
}
