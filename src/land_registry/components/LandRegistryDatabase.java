package land_registry.components;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LandRegistryDatabase {
    private final String DATABASE_HOST = "localhost:27017";
    private final String DATABASE_NAME = "landCadastre";

    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;
    private final HashMap<Collection, MongoCollection<Document>> collections = new HashMap<>();

    public enum Collection {
        LAND_OWNERS("landOwners"), REGIONS("regions"),
        LANDS("lands"), USERS("users");

        private final String collectionName;

        Collection(String collectionName) {
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
        for (Collection collection : Collection.values()) {
            collections.put(collection, mongoDatabase.getCollection(collection.getCollectionName()));
        }
    }

    public void addDataToCollection(Collection collectionName, Document document) {
        MongoCollection<Document> mongoCollection = getCollection(collectionName);
        mongoCollection.insertOne(document);
    }

    public MongoCollection<Document> getCollection(Collection databaseCollection) {
        return collections.get(databaseCollection);
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public void closeConnection() {
        mongoClient.close();
    }
}
