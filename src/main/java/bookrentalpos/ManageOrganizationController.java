package bookrentalpos;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;

public class ManageOrganizationController implements Initializable {
    @FXML
    private Button backButton;

    @FXML
    private ChoiceBox<String> systemLangComboBox;
    @FXML
    private ChoiceBox<String> currencyComboBox;
    @FXML
    private ChoiceBox<String> bookType1;
    @FXML
    private ChoiceBox<String> bookType2;
    @FXML
    private ChoiceBox<String> bookType3;

    public void backToMain(MouseEvent event) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/FXML/mainMenu.fxml"));
        Scene mainMenuScene = new Scene(mainMenuParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Main Menu - HuaheeCheh");
        window.setScene(mainMenuScene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        systemLangComboBox.setItems(FXCollections.observableArrayList("English", "Malay", "Chinese"));
        currencyComboBox.setItems(FXCollections.observableArrayList("MYR", "USD", "GBP"));
        bookType1.setItems(FXCollections.observableArrayList("String", "Number", "Decimal", "Dropdown"));
        bookType2.setItems(FXCollections.observableArrayList("String", "Number", "Decimal", "Dropdown"));
        bookType3.setItems(FXCollections.observableArrayList("String", "Number", "Decimal", "Dropdown"));
    }
}
