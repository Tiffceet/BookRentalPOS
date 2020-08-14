package bookrentalpos;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
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
import my.edu.tarc.dco.bookrentalpos.Transaction;

import java.io.IOException;
import java.util.ArrayList;

public class RentTransactionController {
    public Label dateTime;
    public Button backButton;
    public TextField bookIDField;
    public TextField rentDurationField;
    public TextField memberIDField;
    public TextArea bookDetailField;
    public TextArea memberDetailField;
    public TableView rentTransactionTable;
    public Label totalPriceLabel;

    private double totalPrice = 0;
    private ArrayList<Transaction> sessionTransactions;

    public void initialize() {
        if (dateTime != null) Clock.display(dateTime);
        sessionTransactions = new ArrayList<Transaction>();
    }

    public void backToTransChoose(MouseEvent event) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/FXML/TransactionManager/transactionChoose.fxml"));
        Scene mainMenuScene = new Scene(mainMenuParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainMenuScene);
    }

    // =====================================
    // Event Functions
    // =====================================
    public void bookIDOnKeyPressed(Event event) {
        if (((KeyEvent) event).getCode() == KeyCode.ENTER) {
            reloadBookDetailsField();
        }
    }

    public void bookIDOnKeyReleased(Event event) {
        reloadBookDetailsField();
    }

    public void memberIDOnKeyPressed(Event event) {
        if (((KeyEvent) event).getCode() == KeyCode.ENTER) {
            reloadMemberDetailsField();
        }
    }

    public void memberIDOnKeyReleased(Event event) {
        reloadMemberDetailsField();
    }

    public void addTransactionOnPressed(Event event) {
        String bookid_str = bookIDField.getText();
        String rentDuration_str = rentDurationField.getText();
        String memberID_str = memberIDField.getText();
        if (bookid_str.trim().isEmpty() || rentDuration_str.trim().isEmpty() || memberID_str.trim().isEmpty()) {
            Dialog.alertBox("Some of the fields is empty.");
            return;
        }
        int bookid;
        int rentDuration;
        int memberID;
        try {
            bookid = Integer.parseInt(bookid_str);
            rentDuration = Integer.parseInt(rentDuration_str);
            memberID = Integer.parseInt(memberID_str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Dialog.alertBox("Invalid ID / Rent Duration");
            return;
        }

        if (rentDuration <= 0) {
            Dialog.alertBox("Invalid Rent Duration");
            return;
        }

        Book book = Main.bm.getBookById(bookid);
        Member mem = Main.mm.getMember(memberID);

        if (book == null) {
            Dialog.alertBox("Book ID not found");
            return;
        }

        if (mem == null) {
            Dialog.alertBox("Member ID not found");
            return;
        }

        if (book.isRented()) {
            Dialog.alertBox("This book is already rented.");
            return;
        }

        if (book.isReserved()) {
            Dialog.alertBox("This book was reserved by " + Main.bm.getBookById(book.getLastReservedBy()).getName());
            return;
        }

        RentTransactionTableData data = new RentTransactionTableData(bookid, book.getName(), book.getAuthor(), book.getRetailPrice(), rentDuration + " weeks", String.format("%.2f", book.getRetailPrice() * rentDuration));
        ObservableList ol = rentTransactionTable.getItems();
        for (int a = 0; a < ol.size(); a++) {
            if (bookid == ((RentTransactionTableData) ol.get(a)).getBookId()) {
                Dialog.alertBox("The book is already added into the list.");
                return;
            }
        }
        ol.add(data);
        totalPrice += book.getRetailPrice() * rentDuration;
        setMemberIDFieldEnabled(false);
        reloadTotalPriceLabel();
    }

    public void clearTransactionButtonOnPressed(Event event) {
        setMemberIDFieldEnabled(true);
        rentTransactionTable.getItems().remove(0, rentTransactionTable.getItems().size());
        totalPrice = 0;
        reloadTotalPriceLabel();
    }
    // =====================================
    // =====================================

    public void setMemberIDFieldEnabled(boolean bool) {
        memberIDField.setEditable(bool);
        if (bool) {
            memberDetailField.setStyle("-fx-text-inner-color: black;");
            memberIDField.setStyle("-fx-text-inner-color: black;");
        } else {
            memberDetailField.setStyle("-fx-text-inner-color: grey;");
            memberIDField.setStyle("-fx-text-inner-color: grey;");
        }
    }

    public void reloadTotalPriceLabel() {
        totalPriceLabel.setText("Total Price: RM " + String.format("%.2f", totalPrice));
    }

    public void reloadBookDetailsField() {
        if (bookIDField == null) {
            return;
        }
        int bookID;
        try {
            bookID = Integer.parseInt(bookIDField.getText());
        } catch (NumberFormatException e) {
            // e.printStackTrace();
            bookDetailField.setText("");
            return;
        }
        Book bk;
        if ((bk = Main.bm.getBookById(bookID)) != null) {
            bookDetailField.setText(bk.toString());
        } else {
            bookDetailField.setText("");
        }
    }

    public void reloadMemberDetailsField() {
        if (memberIDField == null) return;
        int memberID;
        try {
            memberID = Integer.parseInt(memberIDField.getText());
        } catch (NumberFormatException e) {
            // e.printStackTrace();
            memberDetailField.setText("");
            return;
        }
        Member mem;
        if ((mem = Main.mm.getMember(memberID)) != null) {
            memberDetailField.setText(mem.toString());
        } else memberDetailField.setText("");
    }
}
