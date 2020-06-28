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
        private MongoCollection<Document> dbCollection;

        Collection(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public MongoCollection<Document> getDbCollection() {
            return dbCollection;
        }

        public void setDbCollection(MongoCollection<Document> dbCollection) {
            this.dbCollection = dbCollection;
        }
    }

    public Database() {
        Logger logger = Logger.getLogger("org.mongodb.driver");
        logger.setLevel(Level.SEVERE);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);

        for (Collection collection : Collection.values()) {
            collection.setDbCollection(mongoDatabase.getCollection(collection.getName()));
            collections.put(collection, collection.getDbCollection());
        }
    }

    public MongoCollection<Document> getCollection(Collection collection) {
        return collections.get(collection);
    }

    public void closeConnection() {
        mongoClient.close();
    }
}
