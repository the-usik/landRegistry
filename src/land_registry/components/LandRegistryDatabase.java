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
    private final HashMap<DatabaseCollectionNames, MongoCollection<Document>> collections = new HashMap<>();

    public enum DatabaseCollectionNames {
        LAND_OWNERS("landOwners"), LANDS("lands"), REGIONS("regions"), USERS("users");

        private final String collectionName;

        DatabaseCollectionNames(String collectionName) {
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

        collections.put(DatabaseCollectionNames.LANDS, mongoDatabase.getCollection(DatabaseCollectionNames.LANDS.getCollectionName()));
        collections.put(DatabaseCollectionNames.USERS, mongoDatabase.getCollection(DatabaseCollectionNames.USERS.getCollectionName()));
        collections.put(DatabaseCollectionNames.REGIONS, mongoDatabase.getCollection(DatabaseCollectionNames.REGIONS.getCollectionName()));
        collections.put(DatabaseCollectionNames.LAND_OWNERS, mongoDatabase.getCollection(DatabaseCollectionNames.LAND_OWNERS.getCollectionName()));
    }

    public MongoCollection<Document> getCollection(DatabaseCollectionNames databaseCollection) {
        return collections.get(databaseCollection);
    }

    public void removeCollectionDataById(String id) {

    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public void closeConnection() {
        mongoClient.close();
    }
}
