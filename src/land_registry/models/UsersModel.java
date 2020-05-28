package land_registry.models;

import javafx.beans.property.SimpleStringProperty;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

public class UsersModel extends CollectionModel {
    private final SimpleStringProperty username;
    private final SimpleStringProperty password;

    public UsersModel(@NotNull Document document) {
        this(
                document.getObjectId("_id"), document.getString("username"),
                document.getString("password")
        );
    }

    public UsersModel(ObjectId id, String username, String password) {
        super(id);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
    }

    public String getPassword() {
        return password.get();
    }

    public String getUsername() {
        return username.get();
    }
}
