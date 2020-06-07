package land_registry.components.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static final String DATABASE_HOST = "localhost:27017";
    private static final String DATABASE_NAME = "landCadastre";

    private final MongoClient mongoClient;
    private final HashMap<Collection, MongoCollection<Document>> collections;
    public enum Collection {
        LANDS("lands"), REGIONS("regions"),
        LAND_OWNERS("landOwners"), USERS("users");

        private String name;

        Collection(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public Database() {
        Logger logger = Logger.getLogger("org.mongodb.driver");
        logger.setLevel(Level.SEVERE);

        collections = new HashMap<>();
        mongoClient = new MongoClient(DATABASE_HOST);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);

        for (Collection collection : Collection.values()) {
            collections.put(collection, mongoDatabase.getCollection(collection.getName()));
        }
    }

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }

    public static String getDatabaseHost() {
        return DATABASE_HOST;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoCollection<Document> getCollection(Collection collection) {
        return collections.get(collection);
    }

    public HashMap<Collection, MongoCollection<Document>> getCollections() {
        return collections;
    }

    public void closeConnection() {
        mongoClient.close();
    }
}
