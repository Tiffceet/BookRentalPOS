package bookrentalpos;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import my.edu.tarc.dco.bookrentalpos.Staff;

public class EditProfileController {
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label staffIDLabel;

    public void cancelEdit(Event event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void confirmEdit(Event event) {
        String newUsername = nameField.getText();
        String newPW = passwordField.getText();

        if(newUsername.trim().isEmpty()) {
            Dialog.alertBox("Username cannot be empty");
            return;
        }

        Staff stfToEdit = Main.sm.getById(Integer.parseInt(staffIDLabel.getText()));
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            Dialog.alertBox("Password do not match");
            return;
        }

        // if username already exist
        // only check usernames of other staff and not self
        if (Main.sm.getByName(newUsername) != null && !Main.sm.getByName(newUsername).equals(stfToEdit.getId())) {
            Dialog.alertBox("Username already exist");
            return;
        }

        // leave blank to stay unchanged
        if (!newPW.isEmpty()) {
            stfToEdit.setPassword(passwordField.getText());
        }

        stfToEdit.setName(newUsername);

        // update staff to database
        if (!Main.sm.update(stfToEdit)) {
            Dialog.alertBox("Something went wrong. Please contact the admin.");
        }

        Dialog.alertBox("Your profile have been successfully updated.");
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();

    }

    public void textFieldOnKeyPressed(Event event){
        if(((KeyEvent) event).getCode() == KeyCode.ESCAPE) {
            cancelEdit(event);
        }
        if(((KeyEvent) event).getCode() == KeyCode.ENTER) {
            confirmEdit(event);
        }
    }

    /**
     * Used when i dont want staff to change name
     */
    public void disableNameTextField() {
        nameField.setStyle("-fx-text-inner-color: grey;");
        nameField.setEditable(false);
        nameField.setFocusTraversable(false);
        passwordField.requestFocus();
    }

    /**
     * Referring to Staff ID and staff name
     */
    public void loadDataToEdit(Staff stf) {
        nameField.setText(stf.getName());
        staffIDLabel.setText(stf.getId() + "");
    }
}
