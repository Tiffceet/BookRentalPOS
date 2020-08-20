package bookrentalpos;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
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
import my.edu.tarc.dco.bookrentalpos.CustomUtil;
import my.edu.tarc.dco.bookrentalpos.Member;
import my.edu.tarc.dco.bookrentalpos.Transaction;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReserveTransactionController implements Initializable, TableInterface {
    @FXML
    private Label dateTime;
    @FXML
    private TextField bookTitleField;
    @FXML
    private TextField bookAuthorField;
    @FXML
    private TableView reserveBookTable;
    @FXML
    private TextField memberIDField;
    @FXML
    private TextArea memberDetailTextArea;
    @FXML
    private Button backButton;
    @FXML
    private TableView memberReservationsTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Clock.display(dateTime);
        Main.tm.updateBookReservationStatus();
    }

    @Override
    public void reloadTableView() {
        reloadBookTable();
        reloadMemberTable();
    }

    public void reloadBookTable() {
        ObservableList ol = reserveBookTable.getItems();
        ol.remove(0, ol.size());
        String bookTitleQuery = bookTitleField.getText();
        String bookAuthorQuery = bookAuthorField.getText();

        boolean checkTitle = !bookTitleQuery.isEmpty();
        boolean checkAuthor = !bookAuthorQuery.isEmpty();

        Book[] bk = Main.bm.getCache();
        for (int a = 0; a < Main.bm.getBookCount(); a++) {
            if (checkTitle && !bk[a].getName().contains(bookTitleQuery)) {
                continue;
            }
            if (checkAuthor && !bk[a].getAuthor().contains(bookAuthorQuery)) {
                continue;
            }
            Transaction t = Main.tm.getBookLastRentTransaction(bk[a].getId());


            // Very dangerous way of parsing date
            LocalDate rentDate = t == null ? null : LocalDate.parse(t.getDateCreated().split(" ")[0]);
            ol.add(
                    new _ReserveTransactionBookTableData(
                            bk[a].getId() + "",
                            bk[a].getName(),
                            bk[a].getAuthor(),
                            t == null ? " - " : CustomUtil.daysIncrement(rentDate, t.getRentDurationInDays()).toString()
                    )
            );
        }
        Dialog.alertBox(ol.size() + " record(s) found.");
    }

    public void reloadMemberTable() {
        ObservableList ol = memberReservationsTable.getItems();
        ol.remove(0, ol.size());
        String memID_str = memberIDField.getText();
        if (memID_str.trim().isEmpty()) {
            ol.remove(0, ol.size());
            return;
        }

        int memID;
        try {
            memID = Integer.parseInt(memID_str);
        } catch (NumberFormatException e) {
            ol.remove(0, ol.size());
            return;
        }

        Member mem = Main.mm.getById(memID);
        if (mem == null) {
            ol.remove(0, ol.size());
            return;
        }

        ArrayList<Book> reservedBooks = Main.tm.getMemberActiveReservations(memID);
        for (int a = 0; a < reservedBooks.size(); a++) {
            _ReserveTransactionMemberTableData rttd = new _ReserveTransactionMemberTableData(
                    Main.tm.getBookLastReservedTransaction(reservedBooks.get(a).getId()).getDateCreated(),
                    reservedBooks.get(a).getId() + "",
                    reservedBooks.get(a).getName(),
                    reservedBooks.get(a).getAuthor()
            );
            ol.add(rttd);
        }
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

    // =================================================================================================================
    // Event functions
    // =================================================================================================================
    public void bookFieldOnKeyPressed(Event event) {
        if(((KeyEvent) event).getCode() == KeyCode.ENTER) {
            searchButtonOnAction(event);
        }
    }

    public void searchButtonOnAction(Event event) {
        if (bookTitleField.getText().isEmpty() && bookAuthorField.getText().isEmpty()) {
            Dialog.alertBox("Please insert search query");
            return;
        }
        reloadTableView();
    }

    public void clearButtonOnAction(Event event) {
        reserveBookTable.getItems().clear();
        bookTitleField.setText("");
        bookAuthorField.setText("");
    }

    public void memberIDFieldOnReleased(Event event) {
        reloadMemberDetailTextArea();
    }


    public void memberIDFieldOnPressed(Event event) {
        if (((KeyEvent) event).getCode() == KeyCode.ENTER)
            reloadMemberDetailTextArea();
    }

    public void checkReservationOnPressed(Event event) {
        int memID;
        try {
            memID = Integer.parseInt(memberIDField.getText());
        } catch (NumberFormatException e) {
            Dialog.alertBox("Invalid member ID");
            return;
        }
        Member mem = Main.mm.getById(memID);
        if (mem == null) {
            Dialog.alertBox("Member ID not found");
            return;
        }
        reloadMemberTable();
    }

    public void addReservationOnPressed(Event event) {
        int bookID;
        if(reserveBookTable.getItems().size() <= 0) {
            Dialog.alertBox("Please search for book in the table above");
            return;
        }
        ObservableList ol = reserveBookTable.getSelectionModel().getSelectedItems();
        if(ol.size() <= 0) {
            Dialog.alertBox("Please select book to reserve from the table above.");
            return;
        }

        bookID = Integer.parseInt(((_ReserveTransactionBookTableData) ol.get(0)).getId());

        int memID;
        try {
            memID = Integer.parseInt(memberIDField.getText());
        } catch (NumberFormatException e) {
            Dialog.alertBox("Invalid bookID or memID");
            return;
        }
        Book bk = Main.bm.getById(bookID);
        Member mem = Main.mm.getById(memID);
        if (bk == null) {
            Dialog.alertBox("BookID do not exist");
            return;
        }

        if (mem == null) {
            Dialog.alertBox("BookID do not exist");
            return;
        }

        if (!bk.isRented()) {
            Dialog.alertBox("Only books that is currently rented can be reserved.");
            return;
        }

        if (bk.isReserved()) {
            Dialog.alertBox("Sorry but this book was reserved by " + Main.mm.getById(bk.getLastReservedBy()).getName());
            return;
        }

        if (bk.getLastRentedBy() == mem.getId()) {
            Dialog.alertBox("Uhh, but you are the one renting this book... (._.)");
            return;
        }

        Transaction t = new Transaction(Main.sm.getLogOnStaff().getId(), mem.getId(), bk.getId());
        if (Main.tm.add(t)) {
            Dialog.alertBox("Book reserved successfully;\nNote: book reservation will be cancelled if the book is not rented 7 days after its returned.");
        } else Dialog.alertBox("Something went wrong when trying to reserve the book");
        reloadMemberTable();
    }
    // =================================================================================================================
    // =================================================================================================================

    public void reloadMemberDetailTextArea() {
        String memID_str = memberIDField.getText();
        int memID;
        try {
            memID = Integer.parseInt(memID_str);
        } catch (NumberFormatException e) {
            return;
        }
        Member mem;
        if ((mem = Main.mm.getById(memID)) != null) {
            memberDetailTextArea.setText(mem.toString());
        } else {
            memberDetailTextArea.setText("");
        }
    }
}


