package sample;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import my.edu.tarc.dco.bookrentalpos.Staff;

public class EditProfileController {
    public Button cancelButton;
    public Button confirmButton;
    public TextField nameField;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;
    public Label staffIDLabel;

    public void cancelEdit(MouseEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void confirmEdit(MouseEvent event) {
        String newUsername = nameField.getText();
        String newPW = passwordField.getText();

        Staff stfToEdit = Main.sm.getLogOnStaff();
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            System.out.println("Password do not match");
            return;
        }

        // if username already exist
        // only check usernames of other staff and not self
        if (Main.sm.getStaffByName(newUsername) != null && Main.sm.getStaffByName(newUsername).getId() != stfToEdit.getId()) {
            System.out.println("Username already exist");
            return;
        }
        stfToEdit.setPassword(passwordField.getText());
        stfToEdit.setName(newUsername);

        // update staff to database
        if (!Main.sm.updateStaff(stfToEdit)) {
            // if failed
        }

        System.out.println("Edit Profile Done");
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();

    }
}
