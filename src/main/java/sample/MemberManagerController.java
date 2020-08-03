package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MemberManagerController {
    public Button backButton;
    public static Stage getWindow;
    public JFXTextField memberNameField;
    public JFXTextField memberICField;
    public JFXTextField memberPhoneField;
    public JFXTextField memberEmailField;
    public Button addMemberButton;
    public Button editMemberButton;
    public Button deleteMemberButton;
    public Button confirmAddButton;
    public Button cancelButton;
    public Button confirmEditButton;
    public Button cancelDeleteButton;
    public Button confirmDeleteButton;

    public void backToMain(MouseEvent event) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/FXML/mainMenu.fxml"));
        Scene mainMenuScene = new Scene(mainMenuParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
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
    }

    public void popEditMember() throws IOException {
        Parent editMemberParent = FXMLLoader.load(getClass().getResource("/FXML/MemberManager/memberManagerEdit.fxml"));
        Stage editMemberWindow = new Stage();

        editMemberWindow.initModality(Modality.APPLICATION_MODAL);
        editMemberWindow.setTitle("Edit Member - Huahee Library");
        editMemberWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        editMemberWindow.setScene(new Scene(editMemberParent, 800, 600));
        editMemberWindow.showAndWait();
    }

    public void popDeleteMember(MouseEvent event) throws IOException {
        Parent deleteMemberParent = FXMLLoader.load(getClass().getResource("/FXML/MemberManager/memberManagerDelete.fxml"));
        Stage deleteMemberWindow = new Stage();

        deleteMemberWindow.initModality(Modality.APPLICATION_MODAL);
        deleteMemberWindow.setTitle("Delete Member - Huahee Library");
        deleteMemberWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        deleteMemberWindow.setScene(new Scene(deleteMemberParent, 400, 150));
        deleteMemberWindow.showAndWait();
    }

    public void cancelButton(MouseEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void confirmAddButton(MouseEvent event) throws IOException {
        // Add to database.
        // Need to do validation.
        getWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
        String memberName = memberNameField.getText();
        String memberIC = memberICField.getText();
        String memberPhone = memberPhoneField.getText();
        String memberEmail = memberEmailField.getText();


        //If validated.
        Parent memberAddedParent = FXMLLoader.load(getClass().getResource("/FXML/MemberManager/memberAdded.fxml"));
        Stage memberAddedWindow = new Stage();

        memberAddedWindow.initModality(Modality.APPLICATION_MODAL);
        memberAddedWindow.initStyle(StageStyle.UTILITY);
        memberAddedWindow.setTitle("");
        memberAddedWindow.setScene(new Scene(memberAddedParent, 400, 100));
        memberAddedWindow.showAndWait();

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void confirmEditButton(MouseEvent event) throws IOException {
        // Add to database.
        // Need to do validation.
        getWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
        String memberName = memberNameField.getText();
        String memberIC = memberICField.getText();
        String memberPhone = memberPhoneField.getText();
        String memberEmail = memberEmailField.getText();


        //If validated.
        Parent memberAddedParent = FXMLLoader.load(getClass().getResource("/FXML/MemberManager/memberEdited.fxml"));
        Stage memberAddedWindow = new Stage();

        memberAddedWindow.initModality(Modality.APPLICATION_MODAL);
        memberAddedWindow.initStyle(StageStyle.UTILITY);
        memberAddedWindow.setTitle("");
        memberAddedWindow.setScene(new Scene(memberAddedParent, 400, 100));
        memberAddedWindow.showAndWait();

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void confirmMemberDelete(MouseEvent event) throws IOException {
        // Delete from database.

        Parent bookAddedParent = FXMLLoader.load(getClass().getResource("/FXML/MemberManager/memberDeleted.fxml"));
        Stage bookAddedWindow = new Stage();

        bookAddedWindow.initModality(Modality.APPLICATION_MODAL);
        bookAddedWindow.initStyle(StageStyle.UTILITY);
        bookAddedWindow.setTitle("");
        bookAddedWindow.setScene(new Scene(bookAddedParent, 400, 100));
        bookAddedWindow.showAndWait();

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
}
