package bookrentalpos;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class TransactionManagerController {
    public Button backButton;
    public Button toRentButton;
    public Button toReserveButton;
    public Button toReturnButton;

    public void backToMain(MouseEvent event) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/FXML/mainMenu.fxml"));
        Scene mainMenuScene = new Scene(mainMenuParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Main Menu - Huahee Library");
        window.setScene(mainMenuScene);
    }

    public void toRentTransaction(MouseEvent event) throws IOException {
        Parent rentTranParent = FXMLLoader.load(getClass().getResource("/FXML/TransactionManager/rentTransaction.fxml"));
        Scene rentTranScene = new Scene(rentTranParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(rentTranScene);
    }

    public void toReserveTransaction(MouseEvent event) throws IOException {
        Parent reserveTranParent = FXMLLoader.load(getClass().getResource("/FXML/TransactionManager/reserveTransaction.fxml"));
        Scene reserveTranScene = new Scene(reserveTranParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(reserveTranScene);
    }

    public void toReturnTransaction(MouseEvent event) throws IOException {
        Parent returnTranParent = FXMLLoader.load(getClass().getResource("/FXML/TransactionManager/returnTransaction.fxml"));
        Stage returnTranWindow = new Stage();

        returnTranWindow.initModality(Modality.APPLICATION_MODAL);
        returnTranWindow.setTitle("Return Transaction Manager - Huahee Library");
        returnTranWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        returnTranWindow.setScene(new Scene(returnTranParent, 600, 600));
        returnTranWindow.setResizable(false);
        returnTranWindow.showAndWait();
    }
}
