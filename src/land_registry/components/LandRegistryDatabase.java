package land_registry.components;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import land_registry.models.*;
import org.bson.Document;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LandRegistryDatabase {
    private final String DATABASE_HOST = "localhost:27017";
    private final String DATABASE_NAME = "landCadastre";

    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;
    private final HashMap<CollectionNames, MongoCollection<Document>> collections = new HashMap<>();

    public enum CollectionNames {
        LAND_OWNERS("landOwners"), LANDS("lands"),
        REGIONS("regions"), USERS("users");

        private final String collectionName;

        CollectionNames(String collectionName) {
            this.collectionName = collectionName;
        }


        public String getCollectionName() {
            return collectionName;
        }
    }

    public LandRegistryDatabase() {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        mongoClient = new MongoClient(DATABASE_HOST);
        mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);

        collections.put(CollectionNames.LANDS, mongoDatabase.getCollection(CollectionNames.LANDS.getCollectionName()));
        collections.put(CollectionNames.USERS, mongoDatabase.getCollection(CollectionNames.USERS.getCollectionName()));
        collections.put(CollectionNames.REGIONS, mongoDatabase.getCollection(CollectionNames.REGIONS.getCollectionName()));
        collections.put(CollectionNames.LAND_OWNERS, mongoDatabase.getCollection(CollectionNames.LAND_OWNERS.getCollectionName()));
    }

    public void addDataToCollection(CollectionNames collectionName, Document document) {
        MongoCollection<Document> mongoCollection = getCollection(collectionName);

        mongoCollection.insertOne(document);
    }

    public MongoCollection<Document> getCollection(CollectionNames databaseCollection) {
        return collections.get(databaseCollection);
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public void closeConnection() {
        mongoClient.close();
    }
}
