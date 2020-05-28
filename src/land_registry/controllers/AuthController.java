package land_registry.controllers;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import land_registry.components.LandRegistryDatabase;
import land_registry.components.SceneManager;
import org.bson.Document;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class AuthController extends Controller implements Initializable {
    @FXML
    private TextField loginInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Button authButton;

    @FXML
    private Label authMessageLabel;

    @Override
    public void onShowing() {
    }

    @Override
    public void onMainContextInit() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void onAuthButtonClick(MouseEvent event) {
        boolean isValid = isValidAccountData(loginInput.getText(), passwordInput.getText());

        authMessageLabel.getStyleClass().clear();

        if (!isValid) {
            authMessageLabel.getStyleClass().add("error-message");
            authMessageLabel.setText("Login failed. Incorrect login or password.");
            authMessageLabel.setVisible(true);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (authMessageLabel.isVisible())
                        authMessageLabel.setVisible(false);
                }
            }, 2000);
        } else {
            mainContext.getSceneManager().switchScene(SceneManager.SceneNames.MAIN);
        }
    }

    private boolean isValidAccountData(String userLogin, String userPassword) {
        MongoCollection<Document> usersCollection = mainContext.getDatabase().getCollection(LandRegistryDatabase.Collection.USERS);
        Document authDocument = new Document();
        authDocument.append("username", userLogin);
        authDocument.append("password", userPassword);

        FindIterable<Document> findResult = usersCollection.find(authDocument);

        return (findResult.first() != null);
    }
}
