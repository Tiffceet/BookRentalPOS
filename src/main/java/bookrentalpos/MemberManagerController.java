package bookrentalpos;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import my.edu.tarc.dco.bookrentalpos.CustomUtil;
import my.edu.tarc.dco.bookrentalpos.Member;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MemberManagerController implements Initializable {
    public Button backButton;
    public static Stage getWindow;
    public JFXTextField memberNameField;
    public JFXTextField memberICField;
    public JFXTextField memberPhoneField;
    public JFXTextField memberEmailField;
    public TextField memberID; // used for edit popup
    public TextField searchByNameField;
    public TextField searchByICField;
    public TextField searchByIDField;
    public Button addMemberButton;
    public Button editMemberButton;
    public Button deleteMemberButton;
    public Button confirmAddButton;
    public Button cancelButton;
    public Button confirmEditButton;
    public Button cancelDeleteButton;
    public Button confirmDeleteButton;
    public Label dateTime;

    public TableView memberTableView;

    // Overriding initialize so that when MemberManager FXML is first loaded, it gets data from database
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        reloadTableView();
        Clock.display(dateTime);
    }

    public void reloadTableView() {
        // clear the current list items first
        if (memberTableView == null)
            return;
        memberTableView.getItems().clear();

        Member[] mem = Main.mm.getMemberListCache(); // get member List
        // As stated in getMemberListCache() javadoc, you could obtain the length of the array through getMemberCount
        for (int a = 0; a < Main.mm.getMemberCount(); a++) {
            memberTableView.getItems().add(mem[a]);
        }
    }

    public void backToMain(MouseEvent event) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/FXML/mainMenu.fxml"));
        Scene mainMenuScene = new Scene(mainMenuParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Main Menu - Huahee Library");
        window.setScene(mainMenuScene);
    }

    public void popAddMember() throws IOException {
        Parent addMemberParent = FXMLLoader.load(getClass().getResource("/FXML/MemberManager/memberManagerAdd.fxml"));
        Stage addMemberWindow = new Stage();

        addMemberWindow.initModality(Modality.APPLICATION_MODAL);
        addMemberWindow.setTitle("Add Member - Huahee Library");
        addMemberWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        addMemberWindow.setScene(new Scene(addMemberParent, 800, 600));
        addMemberWindow.showAndWait();
        reloadTableView(); // Glad showAndWait() is not asynchronous xd
    }

    public void popEditMember() throws IOException {
        Member memToDelete = (Member) memberTableView.getSelectionModel().getSelectedItem();
        if (memToDelete == null) {
            // User didnt select the row to delete
            System.out.println("You didnt tell me what to delete");
            return;
        }

        FXMLLoader fl = new FXMLLoader(getClass().getResource("/FXML/MemberManager/memberManagerEdit.fxml"));
        Parent editMemberParent = (Parent) fl.load();
        MemberManagerController mmc = fl.getController();
        mmc.memberID.setText(memToDelete.getId() + "");
        mmc.memberNameField.setText(memToDelete.getName());
        mmc.memberEmailField.setText(memToDelete.getEmail());
        mmc.memberPhoneField.setText(memToDelete.getPhoneNo());
        mmc.memberICField.setText(memToDelete.getIcNo());

        Stage editMemberWindow = new Stage();

        editMemberWindow.initModality(Modality.APPLICATION_MODAL);
        editMemberWindow.setTitle("Edit Member - Huahee Library");
        editMemberWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        editMemberWindow.setScene(new Scene(editMemberParent, 800, 600));
        editMemberWindow.showAndWait();
        reloadTableView();
    }

    public void popDeleteMember(MouseEvent event) throws IOException {

        Member memToDelete = (Member) memberTableView.getSelectionModel().getSelectedItem();
        if (memToDelete == null) {
            // User didnt select the row to delete
            System.out.println("You didnt tell me what to delete");
            return;
        }

        // I changed the code here so it uses YesNoDialogController to track the response
        // Parent deleteMemberParent = FXMLLoader.load(getClass().getResource("/FXML/MemberManager/memberManagerDelete.fxml"));
        FXMLLoader fl = new FXMLLoader(getClass().getResource("/FXML/MemberManager/memberManagerDelete.fxml"));
        Parent deleteMemberParent = (Parent) fl.load();
        YesNoDialogController ync = fl.getController(); // get the controller of this fxml

        Stage deleteMemberWindow = new Stage();

        deleteMemberWindow.initModality(Modality.APPLICATION_MODAL);
        deleteMemberWindow.setTitle("Delete Member - Huahee Library");
        deleteMemberWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        deleteMemberWindow.setScene(new Scene(deleteMemberParent, 400, 150));
        deleteMemberWindow.showAndWait();

        if (ync.response == 1) {
            if (Main.mm.removeMember(memToDelete.getId())) {
                showMemberDeleteSuccessPopUp();
            } else {
                // Somewhere in the database went wrong
            }
        }
        reloadTableView();
    }

    public void cancelButton(MouseEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void confirmAddButton(MouseEvent event) throws IOException {
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
            AlertBox.display("Empty name or IC is not allowed");
            return;
        }

        if (!CustomUtil.checkIC(memberIC)) {
            AlertBox.display("Invalid IC");
            return;
        }

        if (!memberEmail.isEmpty() && !CustomUtil.checkEmail(memberEmail)) {
            AlertBox.display("Invalid Email");
            return;
        }

        if (!memberPhone.isEmpty() && !CustomUtil.checkPhoneNo(memberPhone)) {
            AlertBox.display("Invalid Phone number.");
            return;
        }

        // Code to add entry to database
        if (!Main.mm.registerMember(new Member(memberIC, memberName, memberPhone, memberEmail))) {
            AlertBox.display("Same IC number have been registered before");
            return;
        }

        // If validated.
        AlertBox.display("The member has successfully added!");

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void confirmEditButton(MouseEvent event) throws IOException {
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
            AlertBox.display("Empty name or IC is not allowed");
            return;
        }

        if (!CustomUtil.checkIC(memberIC)) {
            AlertBox.display("Invalid IC");
            return;
        }

        if (!memberEmail.isEmpty() && !CustomUtil.checkEmail(memberEmail)) {
            AlertBox.display("Invalid Email");
            return;
        }

        if (!memberPhone.isEmpty() && !CustomUtil.checkPhoneNo(memberPhone)) {
            AlertBox.display("Invalid Phone number.");
            return;
        }

        // Modify the member
        Member m = Main.mm.getMember(memID);
        m.setName(memberName);
        m.setIcNo(memberIC);
        m.setEmail(memberEmail);
        m.setPhoneNo(memberPhone);
        if (!Main.mm.updateMember(m)) {
            AlertBox.display("Same IC number have been registered before");
            return;
        }

        //If validated.
        AlertBox.display("The member has successfully edited!");

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void showMemberDeleteSuccessPopUp() throws IOException {
        // Delete from database.
        AlertBox.display("The member has successfully deleted!");
    }

    public void clearButtonClicked(MouseEvent event) throws IOException {
        reloadTableView();
        searchByICField.setText("");
        searchByIDField.setText("");
        searchByNameField.setText("");
    }

    public void searchButtonClicked(MouseEvent event) throws IOException {
        // clear the current list items first
        if (memberTableView == null)
            return;
        memberTableView.getItems().clear();

        // load the cache from MemberManager
        Member[] mem = Main.mm.getMemberListCache();
        String nameQuery = searchByNameField.getText();
        String icQuery = searchByICField.getText();
        String idQuery = searchByIDField.getText();
        for (int a = 0; a < Main.mm.getMemberCount(); a++) {
            if (!nameQuery.trim().isEmpty()) {
                if (!mem[a].getName().equals(nameQuery)) {
                    continue;
                }
            }
            if (!icQuery.trim().isEmpty()) {
                if (!mem[a].getIcNo().equals(icQuery)) {
                    continue;
                }
            }
            if (!idQuery.trim().isEmpty()) {
                if (!(mem[a].getId() + "").equals(idQuery)) {
                    continue;
                }
            }
            memberTableView.getItems().add(mem[a]);
        }

    }
}
