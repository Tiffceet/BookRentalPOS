package bookrentalpos;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import my.edu.tarc.dco.bookrentalpos.CustomUtil;
import my.edu.tarc.dco.bookrentalpos.Member;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MemberManagerController implements Initializable, TableInterface {
    @FXML
    private Button backButton;
    @FXML
    private Label recordsCount;
    public static Stage getWindow;
    @FXML
    private JFXTextField memberNameField;
    @FXML
    private JFXTextField memberICField;
    @FXML
    private JFXTextField memberPhoneField;
    @FXML
    private JFXTextField memberEmailField;
    @FXML
    private TextField memberID; // used for edit popup
    @FXML
    private TextField searchByNameField;
    @FXML
    private TextField searchByICField;
    @FXML
    private TextField searchByIDField;
    @FXML
    private Button addMemberButton;
    @FXML
    private Button editMemberButton;
    @FXML
    private Button deleteMemberButton;
    @FXML
    private Button confirmAddButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmEditButton;
    @FXML
    private Button cancelDeleteButton;
    @FXML
    private Button confirmDeleteButton;
    @FXML
    private Label dateTime;

    public TableView memberTableView;

    // Overriding initialize so that when MemberManager FXML is first loaded, it gets data from database
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (memberTableView != null) {
            memberTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
        reloadTableView();

        // Because some popup uses the controller class, we need to check if its null before allowing the clock to start
        if (dateTime != null) {
            Clock.display(dateTime);
        }
    }

    @Override
    public void tableOnClick(Event event) {
        if (((MouseEvent) event).getClickCount() >= 2) {
            // only triger this event when there are row selected
            // prevents the error box saying "Please select a row of data to edit" when user click on blank area of the table view
            if (memberTableView.getSelectionModel().getSelectedItems().size() >= 1)
                popEditMember();
        }
    }

    @Override
    public void tableOnKeyPressed(Event event) {
        KeyEvent ke = (KeyEvent) event;
        // trigger edit event and delete event
        if (ke.getCode() == KeyCode.F2) {
            // only triger this event when there are row selected
            // prevents the error box saying "Please select a row of data to edit" when user click on blank area of the table view
            if (memberTableView.getSelectionModel().getSelectedItems().size() >= 1)
                popEditMember();
        } else if (ke.getCode() == KeyCode.DELETE) {
            // only triger this event when there are row selected
            // prevents the error box saying "Please select a row of data to edit" when user click on blank area of the table view
            if (memberTableView.getSelectionModel().getSelectedItems().size() >= 1)
                popDeleteMember(event);
        }
    }

    @Override
    public void reloadTableView() {
        // clear the current list items first
        if (memberTableView == null)
            return;
        memberTableView.getItems().clear();

        Member[] mem = Main.mm.getCache(); // get member List
        // As stated in getMemberListCache() javadoc, you could obtain the length of the array through getMemberCount
        for (int a = 0; a < Main.mm.getMemberCount(); a++) {
            memberTableView.getItems().add(mem[a]);
        }
        reloadRecordsCountLabel();
    }

    public void reloadRecordsCountLabel() {
        ObservableList ol = memberTableView.getItems();
        recordsCount.setText(ol.size() + " record(s) Found.");
    }

    public void backToMain(MouseEvent event) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/FXML/mainMenu.fxml"));
        Scene mainMenuScene = new Scene(mainMenuParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Main Menu - HuaheeCheh");
        window.setScene(mainMenuScene);
    }

    public void popAddMember() throws IOException {
        Parent addMemberParent = FXMLLoader.load(getClass().getResource("/FXML/MemberManager/memberManagerAdd.fxml"));
        Stage addMemberWindow = new Stage();

        addMemberWindow.initModality(Modality.APPLICATION_MODAL);
        addMemberWindow.setTitle("Add Member - HuaheeCheh");
        addMemberWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        addMemberWindow.setScene(new Scene(addMemberParent, 800, 600));
        addMemberWindow.showAndWait();
        reloadTableView(); // Glad showAndWait() is not asynchronous xd
    }

    public void popEditMember() {
        ObservableList ol = memberTableView.getSelectionModel().getSelectedItems();
        if (ol.size() > 1) {
            Dialog.alertBox("Sorry but batch edit is not supported.\nPlease select only 1 row of data.");
            return;
        }
        Member memToEdit = (Member) ol.get(0);
        if (memToEdit == null) {
            // User didnt select the row to delete
            Dialog.alertBox("Please select a row of data to edit.");
            return;
        }

        FXMLLoader fl = new FXMLLoader(getClass().getResource("/FXML/MemberManager/memberManagerEdit.fxml"));
        Parent editMemberParent;
        try {
            editMemberParent = (Parent) fl.load();
        } catch (IOException e) {
            e.printStackTrace();
            Dialog.alertBox("Something went wrong and edit pop up was unable to show");
            return;
        }
        MemberManagerController mmc = fl.getController();
        mmc.loadDataToEdit(memToEdit);

        Stage editMemberWindow = new Stage();

        editMemberWindow.initModality(Modality.APPLICATION_MODAL);
        editMemberWindow.setTitle("Edit Member - HuaheeCheh");
        editMemberWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        editMemberWindow.setScene(new Scene(editMemberParent, 800, 600));
        editMemberWindow.showAndWait();
        reloadTableView();
    }

    public void popDeleteMember(Event event) {

        ObservableList ol = memberTableView.getSelectionModel().getSelectedItems();
        Member memToDelete = (Member) ol.get(0);
        if (memToDelete == null) {
            // User didnt select the row to delete
            Dialog.alertBox("Please select a row of data to delete.");
            return;
        }

        if (Dialog.confirmBox("Are you sure you want to delete " + ol.size() + " record(s) ?")) {
            for (int a = 0; a < ol.size(); a++) {
                if (Main.mm.remove(((Member) ol.get(a)).getId())) {
                    // do nothing yet
                } else {
                    // Somewhere in the database went wrong
                }
            }
            Dialog.alertBox(ol.size() + " member has successfully deleted!");
        }
        reloadTableView();
    }

    public void cancelButton(Event event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void confirmAddButton(Event event) throws IOException {
        // Add to database.
        // Need to do validation.
        getWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String memberName = memberNameField.getText();
        String memberIC = memberICField.getText();
        String memberPhone = memberPhoneField.getText();
        String memberEmail = memberEmailField.getText();

        // Input Validation Code here
        //     if you are reading this, code the validation for email, phone number and IC number here thanks
        if (memberName.trim().isEmpty() || memberIC.trim().isEmpty()) {
            Dialog.alertBox("Empty name or IC is not allowed");
            return;
        }

        if (!CustomUtil.checkIC(memberIC)) {
            Dialog.alertBox("Invalid IC");
            return;
        }

        if (!memberEmail.isEmpty() && !CustomUtil.checkEmail(memberEmail)) {
            Dialog.alertBox("Invalid Email");
            return;
        }

        if (!memberPhone.isEmpty() && !CustomUtil.checkPhoneNo(memberPhone)) {
            Dialog.alertBox("Invalid Phone number.");
            return;
        }

        // Code to add entry to database
        Member newMember = new Member(memberIC, memberName, memberPhone, memberEmail);
        if (!Main.mm.add(newMember)) {
            Dialog.alertBox("Same IC number have been registered before");
            return;
        }

        // If validated.
        Dialog.alertBox("The member has successfully added!\nThe new member ID assigned is " + newMember.getId());

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void confirmEditButton(Event event) throws IOException {
        getWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        int memID = Integer.parseInt(memberID.getText()); // hidden Text Field to store member ID
        String memberName = memberNameField.getText();
        String memberIC = memberICField.getText();
        String memberPhone = memberPhoneField.getText();
        String memberEmail = memberEmailField.getText();

        // validation
        // Input Validation Code here
        //     if you are reading this, code the validation for email, phone number and IC number here thanks
        if (memberName.trim().isEmpty() || memberIC.trim().isEmpty()) {
            Dialog.alertBox("Empty name or IC is not allowed");
            return;
        }

        if (!CustomUtil.checkIC(memberIC)) {
            Dialog.alertBox("Invalid IC");
            return;
        }

        if (!memberEmail.isEmpty() && !CustomUtil.checkEmail(memberEmail)) {
            Dialog.alertBox("Invalid Email");
            return;
        }

        if (!memberPhone.isEmpty() && !CustomUtil.checkPhoneNo(memberPhone)) {
            Dialog.alertBox("Invalid Phone number.");
            return;
        }

        // Modify the member
        Member m = Main.mm.getById(memID);
        m.setName(memberName);
        m.setIcNo(memberIC);
        m.setEmail(memberEmail);
        m.setPhoneNo(memberPhone);
        if (!Main.mm.update(m)) {
            Dialog.alertBox("Same IC number have been registered before");
            return;
        }

        //If validated.
        Dialog.alertBox("The member has successfully edited!");

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void clearButtonClicked(MouseEvent event) throws IOException {
        reloadTableView();
        searchByICField.setText("");
        searchByIDField.setText("");
        searchByNameField.setText("");
        Dialog.alertBox("Search query cleared.");
    }

    public void searchQueryOnKeyPressed(Event event) {
        if (((KeyEvent) event).getCode() == KeyCode.ENTER) {
            try {
                searchButtonClicked(event);
            } catch (IOException e) {
                Dialog.alertBox("Corrupted JAR file, consider reinstalling the program");
            }
        }
    }

    public void searchButtonClicked(Event event) throws IOException {
        // clear the current list items first
        if (memberTableView == null)
            return;
        memberTableView.getItems().clear();

        // load the cache from MemberManager
        Member[] mem = Main.mm.getCache();
        String nameQuery = searchByNameField.getText().toLowerCase();
        String icQuery = searchByICField.getText().toLowerCase();
        String idQuery = searchByIDField.getText().toLowerCase();

        boolean checkName = !nameQuery.isEmpty();
        boolean checkIC = !icQuery.isEmpty();
        boolean checkID = !idQuery.isEmpty();

        if (!checkIC && !checkID && !checkName) {
            Dialog.alertBox("Please insert search query");
            reloadTableView();
            return;
        }

        for (int a = 0; a < Main.mm.getMemberCount(); a++) {
            if (checkName) {
                if (!mem[a].getName().toLowerCase().startsWith(nameQuery)) {
                    continue;
                }
            }
            if (checkIC) {
                if (!mem[a].getIcNo().toLowerCase().startsWith(icQuery)) {
                    continue;
                }
            }
            if (checkID) {
                if (!(mem[a].getId() + "").toLowerCase().startsWith(idQuery)) {
                    continue;
                }
            }
            memberTableView.getItems().add(mem[a]);
        }
        reloadRecordsCountLabel();
        Dialog.alertBox(memberTableView.getItems().size() + " records found");
    }

    public void textFieldOnKeyPressed(KeyEvent event) throws IOException {
        // if escape key is pressed, close the window
        if (event.getCode() == KeyCode.ESCAPE) {
            cancelButton(event);
        }
    }

    /**
     * Referring to member pop up edit
     *
     * @param memToEdit member object reference
     */
    public void loadDataToEdit(Member memToEdit) {
        memberID.setText(memToEdit.getId() + "");
        memberNameField.setText(memToEdit.getName());
        memberEmailField.setText(memToEdit.getEmail());
        memberPhoneField.setText(memToEdit.getPhoneNo());
        memberICField.setText(memToEdit.getIcNo());
    }
}
