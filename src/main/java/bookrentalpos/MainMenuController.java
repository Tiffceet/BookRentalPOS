package bookrentalpos;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    public Button transactionButton;
    public Button bookButton;
    public Button memberButton;
    public Button profileButton;
    public Button logOutButton;
    public Button confirmLogOutButton;
    public Button cancelLogOutButton;
    public Button staffButton;
    public ImageView staffImage;
    public Label welcomeLabel;
    public static Stage mainWindow;
    public Button reportButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (staffButton == null) {
            return;
        }
        if (Main.sm.getLogOnStaff().isAdmin() && staffButton != null) {
            setStaffVisible();
        }

        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome back, " + Main.sm.getLogOnStaff().getName() + "."); // set welcome msg
        }
    }

    public void toManageTransaction(MouseEvent event) throws IOException {
        Main.tm.reload();
        Parent manageBookParent = FXMLLoader.load(getClass().getResource("/FXML/TransactionManager/transactionChoose.fxml"));
        Scene manageBookScene = new Scene(manageBookParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Transaction Manager - HuaheeCheh");
        window.setScene(manageBookScene);
    }

    public void toManageBook(MouseEvent event) throws IOException {
        Main.bm.reload();
        Parent manageBookParent = FXMLLoader.load(getClass().getResource("/FXML/BookManager/bookManager.fxml"));
        Scene manageBookScene = new Scene(manageBookParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Book Manager - HuaheeCheh");
        window.setScene(manageBookScene);
    }

    public void toManageMember(MouseEvent event) throws IOException {
        Main.mm.reload();
        Parent manageBookParent = FXMLLoader.load(getClass().getResource("/FXML/MemberManager/memberManager.fxml"));
        Scene manageBookScene = new Scene(manageBookParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Member Manager - HuaheeCheh");
        window.setScene(manageBookScene);
    }

    public void toGenerateReport(MouseEvent event) throws IOException {
        FXMLLoader fl = new FXMLLoader(getClass().getResource("/FXML/Report/reportInterface.fxml"));
        Parent generateReportParent = (Parent) fl.load();

        Scene generateReportScene = new Scene(generateReportParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Report Menu - HuaheeCheh");
        window.setScene(generateReportScene);
    }

    public void toManageStaff(MouseEvent event) throws IOException {
        FXMLLoader fl = new FXMLLoader(getClass().getResource("/FXML/StaffManager/staffManager.fxml"));
        Parent manageBookParent = (Parent) fl.load();

        Scene manageBookScene = new Scene(manageBookParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Staff Manager - HuaheeCheh");
        window.setScene(manageBookScene);
    }

    public void popEditProfile() throws IOException {
        FXMLLoader fl = new FXMLLoader(getClass().getResource("/FXML/EditProfile/editProfile.fxml"));
        Parent editProfileParent = (Parent) fl.load();
        EditProfileController epc = fl.getController();
        epc.nameField.setText(Main.sm.getLogOnStaff().getName());
        // admin will not be able to change username
        if (Main.sm.getLogOnStaff().getName().equals("root")) {
            epc.nameField.setStyle("-fx-text-inner-color: grey;");
            epc.nameField.setEditable(false);
            epc.nameField.setFocusTraversable(false);
            epc.passwordField.requestFocus();
        }
        epc.staffIDLabel.setText(Main.sm.getLogOnStaff().getId() + "");

        Stage profileWindow = new Stage();

        profileWindow.initModality(Modality.APPLICATION_MODAL);
        profileWindow.setTitle("Edit Profile - HuaheeCheh");
        profileWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        profileWindow.setScene(new Scene(editProfileParent, 600, 350));
        profileWindow.showAndWait();
    }

    public void popLogOut(MouseEvent event) throws IOException {
        Parent logOutParent = FXMLLoader.load(getClass().getResource("/FXML/logOut.fxml"));
        Stage logOutWindow = new Stage();
        mainWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();

        logOutWindow.initModality(Modality.APPLICATION_MODAL);
        logOutWindow.setTitle("Logout - HuaheeCheh");
        logOutWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        logOutWindow.setScene(new Scene(logOutParent, 400, 150));
        logOutWindow.setResizable(false);
        logOutWindow.showAndWait();
    }

    public void confirmLogOut(MouseEvent event) throws IOException {
        Main.sm.logOut();
        Parent loginScreenParent = FXMLLoader.load(getClass().getResource("/FXML/loginInterface.fxml"));
        Scene loginScreenScene = new Scene(loginScreenParent, 800, 600);

        mainWindow.setScene(loginScreenScene);
        mainWindow.centerOnScreen();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void cancelLogOut(MouseEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void setStaffVisible() {
        staffButton.setDisable(false);
        staffButton.setVisible(true);
        staffImage.setVisible(true);
    }
}
