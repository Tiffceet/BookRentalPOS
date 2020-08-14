package bookrentalpos;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import my.edu.tarc.dco.bookrentalpos.Book;
import my.edu.tarc.dco.bookrentalpos.Member;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReserveTransactionController implements Initializable, TableInterface {
    public Label dateTime;
    public TextField bookIDField;
    public TextArea bookDetailTextArea;
    public TextField memberIDField;
    public TextArea memberDetailTextArea;
    public Button backButton;
    public TableView reserveTransactionTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Clock.display(dateTime);
    }

    @Override
    public void reloadTableView() {

    }

    @Override
    public void tableOnClick(Event event) {

    }

    @Override
    public void tableOnKeyPressed(Event event) {

    }


    public void backToTransChoose(MouseEvent event) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/FXML/TransactionManager/transactionChoose.fxml"));
        Scene mainMenuScene = new Scene(mainMenuParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainMenuScene);
    }

    // =======================================
    // Event functions
    // =======================================
    public void bookIDFieldOnReleased(Event event) {
        reloadBookDetailTextArea();
    }

    public void memberIDFieldOnReleased(Event event) {
        reloadMemberDetailTextArea();
    }

    public void bookIDFieldOnPressed(Event event) {
        if (((KeyEvent) event).getCode() == KeyCode.ENTER)
            reloadBookDetailTextArea();
    }

    public void memberIDFieldOnPressed(Event event) {
        if (((KeyEvent) event).getCode() == KeyCode.ENTER)
            reloadMemberDetailTextArea();
    }

    public void checkReservationOnPressed(Event event) {
    }

    public void addReservationOnPressed(Event event) {
    }
    // =======================================
    // =======================================

    public void reloadMemberDetailTextArea() {
        String memID_str = memberIDField.getText();
        int memID;
        try {
            memID = Integer.parseInt(memID_str);
        } catch (NumberFormatException e) {
            return;
        }
        Member mem;
        if ((mem = Main.mm.getMember(memID)) != null) {
            memberDetailTextArea.setText(mem.toString());
        } else {
            memberDetailTextArea.setText("");
        }
    }

    public void reloadBookDetailTextArea() {
        String bookID_str = bookIDField.getText();
        int bookID;
        try {
            bookID = Integer.parseInt(bookID_str);
        } catch (NumberFormatException e) {
            return;
        }
        Book bk;
        if ((bk = Main.bm.getBookById(bookID)) != null) {
            bookDetailTextArea.setText(bk.toString());
        } else {
            bookDetailTextArea.setText("");
        }
    }
}


