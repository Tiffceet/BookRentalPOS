package bookrentalpos;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private JFXTextField usernameTextField;
    @FXML
    private JFXPasswordField passwordTextField;
    @FXML
    private Button loginButton;

    public void validateAccount(Event event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        // Validate database eeee...
        int staffID = Main.sm.login(username, password);
        // if login() returns -1, meaning the username or password is wrong
        if (staffID != -1) {
            Parent mainMenuParent;
            FXMLLoader fl = new FXMLLoader(getClass().getResource("/FXML/mainMenu.fxml"));
            mainMenuParent = (Parent) fl.load();

            Scene mainMenuScene = new Scene(mainMenuParent, 1366, 768);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Main Menu - HuaheeCheh");
            window.setScene(mainMenuScene);
            window.centerOnScreen();
        } else {
            Dialog.alertBox("Wrong username or password");
            usernameTextField.selectAll();
        }
    }

    public void forgetPassword(MouseEvent event) {
        Dialog.alertBox("Please contact the admin to reset your password");
    }
}
