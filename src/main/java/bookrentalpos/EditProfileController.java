package bookrentalpos;

import javafx.scene.Node;
import javafx.scene.control.*;
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
            Dialog.alertBox("Password do not match");
            return;
        }

        if (newPW.isEmpty()) {
            Dialog.alertBox("Password can not be empty.");
            return;
        }

        // if username already exist
        // only check usernames of other staff and not self
        if (Main.sm.getStaffByName(newUsername) != null && Main.sm.getStaffByName(newUsername).getId() != stfToEdit.getId()) {
            Dialog.alertBox("Username already exist");
            return;
        }
        stfToEdit.setPassword(passwordField.getText());
        stfToEdit.setName(newUsername);

        // update staff to database
        if (!Main.sm.updateStaff(stfToEdit)) {
            Dialog.alertBox("Something went wrong. Please contact the admin.");
        }

        Dialog.alertBox("Your profile have been successfully updated.");
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();

    }
}
