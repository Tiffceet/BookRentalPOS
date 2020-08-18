package bookrentalpos;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import my.edu.tarc.dco.bookrentalpos.MemberManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    private Button transactionButton;
    @FXML
    private Button bookButton;
    @FXML
    private Button memberButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button logOutButton;
    @FXML
    private Button confirmLogOutButton;
    @FXML
    private Button cancelLogOutButton;
    @FXML
    private Button staffButton;
    @FXML
    private ImageView staffImage;
    @FXML
    private Label welcomeLabel;
    public static Stage mainWindow;
    @FXML
    private Button reportButton;

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
        FXMLLoader fl = new FXMLLoader(getClass().getResource("/FXML/BookManager/bookManager.fxml"));
        Parent manageBookParent = (Parent) fl.load();
        BookManagerController bmc = fl.getController();
        Scene manageBookScene = new Scene(manageBookParent);

        // Standard Keyboard shortcuts
        // Ctrl + N - New records
        manageBookScene.setOnKeyPressed(e -> {
            if (new KeyCodeCombination(KeyCode.N,
                    KeyCombination.CONTROL_DOWN).match((KeyEvent) e)) {
                try {
                    bmc.popAddBook();
                } catch (IOException err) {
                    err.printStackTrace();
                    Dialog.alertBox("Corrupted JAR file. Please re-download the program");
                }
            }
        });

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Book Manager - HuaheeCheh");
        window.setScene(manageBookScene);
    }

    public void toManageMember(MouseEvent event) throws IOException {
        Main.mm.reload();
        FXMLLoader fl = new FXMLLoader(getClass().getResource("/FXML/MemberManager/memberManager.fxml"));
        Parent manageBookParent = (Parent) fl.load();
        MemberManagerController mmc = fl.getController();
        Scene managerMemberScene = new Scene(manageBookParent);

        // Standard Keyboard shortcuts
        // Ctrl + N - New records
        managerMemberScene.setOnKeyPressed(e -> {
            if (new KeyCodeCombination(KeyCode.N,
                    KeyCombination.CONTROL_DOWN).match((KeyEvent) e)) {
                try {
                    mmc.popAddMember();
                } catch (IOException err) {
                    err.printStackTrace();
                    Dialog.alertBox("Corrupted JAR file. Please re-download the program");
                }
            }
        });

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Member Manager - HuaheeCheh");
        window.setScene(managerMemberScene);
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
        StaffManagerController smc = fl.getController();

        Scene managerStaffScene = new Scene(manageBookParent);

        // Standard Keyboard shortcuts
        // Ctrl + N - New records
        managerStaffScene.setOnKeyPressed(e -> {
            if (new KeyCodeCombination(KeyCode.N,
                    KeyCombination.CONTROL_DOWN).match((KeyEvent) e)) {
                smc.addButtonOnPressed(e);
            }
        });

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Staff Manager - HuaheeCheh");
        window.setScene(managerStaffScene);
    }

    public void popEditProfile() throws IOException {
        FXMLLoader fl = new FXMLLoader(getClass().getResource("/FXML/EditProfile/editProfile.fxml"));
        Parent editProfileParent = (Parent) fl.load();
        EditProfileController epc = fl.getController();

        epc.loadDataToEdit(Main.sm.getLogOnStaff());
        // admin will not be able to change username
        if (Main.sm.getLogOnStaff().getName().equals("root")) {
            epc.disableNameTextField();
        }

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
