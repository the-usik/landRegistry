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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
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

    private final String HASH_GENERATION_ALGORITHM = "SHA-256";

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
        System.out.println(generateHash("test"));
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
        authDocument.append("password", generateHash(userPassword));
        FindIterable<Document> findResult = usersCollection.find(authDocument);

        return (findResult.first() != null);
    }

    private String generateHash(String inputString) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_GENERATION_ALGORITHM);
            byte[] encodedBytes = messageDigest.digest(inputString.getBytes(StandardCharsets.UTF_8));

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < encodedBytes.length; i++) {
                String hex = Integer.toHexString(0xff & encodedBytes[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
