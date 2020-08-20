package bookrentalpos;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import my.edu.tarc.dco.bookrentalpos.Staff;

import java.io.IOException;

public class StaffManagerController implements TableInterface {
    @FXML
    private Label dateTime;
    @FXML
    private Label recordsCount;
    @FXML
    private Button backButton;
    @FXML
    private TableView staffManagerTable;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField searchByNameTextField;
    @FXML
    private TextField searchByIdTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    public void initialize() {
        // Because some popup uses the controller class, we need to check if its null before allowing the clock to start
        if (dateTime != null) {
            Clock.display(dateTime);
        }
        if (staffManagerTable != null) {
            reloadTableView();
            staffManagerTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
    }

    @Override
    public void reloadTableView() {
        ObservableList ol = staffManagerTable.getItems();
        ol.clear();
        Staff[] stfs = Main.sm.getCache();

        for (int a = 0; a < Main.sm.getStaffCount(); a++)
            ol.add(stfs[a]);
        reloadRecordsCountLabel();
    }

    @Override
    public void tableOnClick(Event event) {
        if (((MouseEvent) event).getClickCount() >= 2) {
            if (staffManagerTable.getSelectionModel().getSelectedItems().size() == 1) {
                editButtonOnPressed(event);
            }
        }
    }

    @Override
    public void tableOnKeyPressed(Event event) {
        if (((KeyEvent) event).getCode() == KeyCode.F2) {
            editButtonOnPressed(event);
            return;
        }
        if (((KeyEvent) event).getCode() == KeyCode.DELETE) {
            removeButtonOnPressed(event);
            return;
        }
    }

    public void reloadRecordsCountLabel() {
        ObservableList ol = staffManagerTable.getItems();
        recordsCount.setText(ol.size() + " record(s) Found.");
    }

    public void backToMain(MouseEvent event) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/FXML/mainMenu.fxml"));
        Scene mainMenuScene = new Scene(mainMenuParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Main Menu - HuaheeCheh");
        window.setScene(mainMenuScene);
    }

    // ========================================================================================================================
    // StaffManager Event Functions
    // ========================================================================================================================

    public void searchQueryOnKeyPressed(Event event) {
        if (((KeyEvent) event).getCode() == KeyCode.ENTER)
            searchOnPressed(event);
    }

    public void searchOnPressed(Event event) {
        String nameQuery = searchByNameTextField.getText();
        String idQuery = searchByIdTextField.getText();
        ObservableList ol = staffManagerTable.getItems();
        ol.clear();
        boolean checkName = !nameQuery.isEmpty();
        boolean checkID = !idQuery.isEmpty();
        if (!checkID && !checkName) {
            Dialog.alertBox("Please insert search query");
            reloadTableView();
            return;
        }
        Staff[] stfList = Main.sm.getCache();
        for (int a = 0; a < Main.sm.getStaffCount(); a++) {
            if (checkName && !stfList[a].getName().contains(nameQuery)) {
                continue;
            }
            if (checkID && !(stfList[a].getId() + "").contains(idQuery)) {
                continue;
            }
            ol.add(stfList[a]);
        }
        reloadRecordsCountLabel();
        Dialog.alertBox(ol.size() + " records found");
    }

    public void clearOnPressed(Event event) {
        searchByIdTextField.setText("");
        searchByNameTextField.setText("");
        reloadTableView();
        Dialog.alertBox("Search query cleared.");
    }


    public void addButtonOnPressed(Event event) {
        Parent addStaffParent;
        try {
            addStaffParent = FXMLLoader.load(getClass().getResource("/FXML/StaffManager/staffRegister.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            Dialog.alertBox("JARFile seems to be corrupted and FXML/StaffRegister.fxml is not found");
            return;
        }
        Stage addStaffWindow = new Stage();

        addStaffWindow.initModality(Modality.APPLICATION_MODAL);
        addStaffWindow.setTitle("Add Staff - HuaheeCheh");
        addStaffWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        addStaffWindow.setScene(new Scene(addStaffParent, 600, 350));
        addStaffWindow.showAndWait();
        reloadTableView();
    }

    public void editButtonOnPressed(Event event) {
        ObservableList ol = staffManagerTable.getSelectionModel().getSelectedItems();
        if (ol.size() <= 0) {
            Dialog.alertBox("Please select at least 1 row of data to edit");
            return;
        } else if (ol.size() > 1) {
            Dialog.alertBox("Batch edit is not supported.");
            return;
        }

        Staff stfToEdit = (Staff) ol.get(0);

        Parent addStaffParent;
        FXMLLoader fl = new FXMLLoader(getClass().getResource("/FXML/EditProfile/editProfile.fxml"));
        try {
            addStaffParent = (Parent) fl.load();
        } catch (IOException e) {
            e.printStackTrace();
            Dialog.alertBox("JARFile seems to be corrupted and FXML/editProfile.fxml is not found");
            return;
        }
        EditProfileController epc = fl.getController();
        // admin will not be able to change username
        if (stfToEdit.getName().equals("root")) {
            epc.disableNameTextField();
        }
        epc.loadDataToEdit(stfToEdit);

        Stage addStaffWindow = new Stage();

        addStaffWindow.initModality(Modality.APPLICATION_MODAL);
        addStaffWindow.setTitle("Edit Staff - HuaheeCheh");
        addStaffWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        addStaffWindow.setScene(new Scene(addStaffParent, 600, 350));
        addStaffWindow.showAndWait();
        reloadTableView();
    }

    public void removeButtonOnPressed(Event event) {
        ObservableList ol = staffManagerTable.getSelectionModel().getSelectedItems();
        if (ol.size() <= 0) {
            Dialog.alertBox("Please select at least 1 row of data to delete");
            return;
        }

        for (int a = 0; a < ol.size(); a++) {
            if (((Staff) ol.get(a)).getName().equals("root")) {
                Dialog.alertBox("You are not suppose to remove root account (admin account).");
                return;
            }
        }

        if (!Dialog.confirmBox("Are you sure you want to remove " + ol.size() + " staff(s) ?")) {
            return;
        }
        for (int a = 0; a < ol.size(); a++) {
            if (Main.sm.remove(((Staff) ol.get(a)).getId())) {

            } else {
                // not sure how to handle this
            }
        }
        Dialog.alertBox(ol.size() + " staff(s) have been removed successfully.");
        reloadTableView();
    }
    // ========================================================================================================================
    // ========================================================================================================================

    // ========================================================================================================================
    // Add Staff pop up events
    // ========================================================================================================================

    public void confirmAddOnPressed(Event event) {
        String newUsername = usernameField.getText();
        String newPassword = passwordField.getText();
        String newPasswordConfirmation = confirmPasswordField.getText();
        if (newUsername.trim().isEmpty()) {
            Dialog.alertBox("Username cannot be empty");
            return;
        }

        if (Main.sm.getByName(newUsername) != null) {
            Dialog.alertBox("Username already exists.");
            return;
        }

        if (newPassword.trim().isEmpty()) {
            Dialog.alertBox("Password cannot be empty");
            return;
        }

        if (!newPassword.equals(newPasswordConfirmation)) {
            Dialog.alertBox("Password do not match");
            return;
        }

        Staff newStaff = new Staff(newUsername, newPassword);
        if (Main.sm.add(newStaff)) {
            Dialog.alertBox("Staff registration successful");
        } else
            Dialog.alertBox("Something went wrong internally and staff was not registered.");
        cancelPopupOnPressed(event);
    }

    public void cancelPopupOnPressed(Event event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void textFieldOnKeyPressed(Event event) {
        if (((KeyEvent) event).getCode() == KeyCode.ENTER) {
            confirmAddOnPressed(event);
            return;
        }
        if (((KeyEvent) event).getCode() == KeyCode.ESCAPE) {
            cancelPopupOnPressed(event);
            return;
        }
    }
    // ========================================================================================================================
    // ========================================================================================================================

}
