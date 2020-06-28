package land_registry.database;

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

    private final MongoClient mongoClient = new MongoClient(DATABASE_HOST);
    private final HashMap<Collection, MongoCollection<Document>> collections = new HashMap<>();

    public enum Collection {
        LANDS("lands"), USERS("users"),
        REGIONS("regions"), LAND_OWNERS("landOwners");

        private final String name;

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
