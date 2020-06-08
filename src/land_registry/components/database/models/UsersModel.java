package land_registry.components.database.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TextField;
import land_registry.components.ui.utils.FormNodeGroup;
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

    @Override
    public FormNodeGroup getFormNodeGroup() {
        FormNodeGroup formNodeGroup = super.getFormNodeGroup();

        TextField usernameTextField = new TextField();
        usernameTextField.setId("username");
        usernameTextField.setText(getUsername());
        usernameTextField.setPromptText("Enter the username...");

        TextField passwordTextField = new TextField();
        passwordTextField.setId("password");
        passwordTextField.setText("");
        passwordTextField.setPromptText("Enter the password...");

        formNodeGroup.append("Username", usernameTextField);
        formNodeGroup.append("Password", passwordTextField);

        return formNodeGroup;
    }
}
