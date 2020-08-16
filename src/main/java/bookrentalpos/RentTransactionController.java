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

public class RentTransactionController implements TableInterface {
    public Label dateTime;
    public Button backButton;
    public TextField bookIDField;
    public TextField rentDurationField;
    public TextField memberIDField;
    public TextArea bookDetailField;
    public TextArea memberDetailField;
    public TableView rentTransactionTable;
    public Label totalDepositLabel;
    public Label totalChargesLabel;
    public Label discountLabel;
    public Label netAmountToPayLabel;

    private double totalCharges = 0;
    private double totalDeposit = 0;
    private double discount = 0;
    private double netAmountToPay = 0;
    private boolean haveDiscount = false;
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

    // ================================================================================================================
    // Event Functions
    // ================================================================================================================


    @Override
    public void reloadTableView() {

    }

    @Override
    public void tableOnClick(Event event) {

    }

    @Override
    public void tableOnKeyPressed(Event event) {
        if (((KeyEvent) event).getCode() == KeyCode.DELETE) {
            ObservableList ol = rentTransactionTable.getSelectionModel().getSelectedItems();
            if (ol.size() != 1) {
                return;
            }
            if (Dialog.confirmBox("Are you sure you want to remove this entry ? ")) {
                RentTransactionTableData rttd = (RentTransactionTableData) ol.get(0);
                totalCharges -= Double.parseDouble(rttd.getCharges());
                totalDeposit -= Double.parseDouble(rttd.getDeposit());
                reloadTotalPriceLabel();
                rentTransactionTable.getItems().removeIf((data) -> {
                    return ((RentTransactionTableData) data).getBookId() == rttd.getBookId();
                });

                sessionTransactions.removeIf((data) -> {
                    return ((Transaction) data).getBookInvovled() == rttd.getBookId();
                });
            }
        }
    }

    public void bookIDOnKeyPressed(Event event) {
        reloadBookDetailsField();

    }

    public void bookIDOnKeyReleased(Event event) {
        reloadBookDetailsField();
    }

    public void memberIDOnKeyPressed(Event event) {
        reloadMemberDetailsField();
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

        if (book.isReserved() && book.getLastReservedBy() != mem.getId()) {
            Dialog.alertBox("This book was reserved by " + Main.mm.getMember(book.getLastReservedBy()).getName());
            return;
        }

        // Add entry to table for view
        RentTransactionTableData data = new RentTransactionTableData(
                bookid,
                book.getName(),
                book.getAuthor(),
                book.getRetailPrice() * 2 + "",
                String.format("%.2f", book.getRetailPrice() * (Main.tm.DEPOSIT_RATES.get(rentDuration > 4 ? 4 : rentDuration) / 100.0)),
                rentDuration + " weeks",
                String.format("%.2f", book.getRetailPrice() + book.getRetailPrice() * (100 + Main.tm.DEPOSIT_RATES.get(rentDuration > 4 ? 4 : rentDuration)) / 100.0)
        );
        ObservableList ol = rentTransactionTable.getItems();
        for (int a = 0; a < ol.size(); a++) {
            if (bookid == ((RentTransactionTableData) ol.get(a)).getBookId()) {
                Dialog.alertBox("The book is already added into the list.");
                return;
            }
        }
        ol.add(data);

        // add to sessionTransaction
        sessionTransactions.add(
                new Transaction(
                        Main.sm.getLogOnStaff().getId(),
                        mem.getId(),
                        book.getId(),
                        rentDuration * 7,
                        book.getRetailPrice() + book.getRetailPrice() * (100 + Main.tm.DEPOSIT_RATES.get(rentDuration > 4 ? 4 : rentDuration)) / 100.0
                )
        );

        totalCharges += book.getRetailPrice() * (Main.tm.DEPOSIT_RATES.get(rentDuration > 4 ? 4 : rentDuration) / 100.0);
        totalDeposit += book.getRetailPrice() * 2;
        setMemberIDFieldEnabled(false);
        reloadTotalPriceLabel();
    }

    public void clearTransactionButtonOnPressed(Event event) {
        setMemberIDFieldEnabled(true);
        sessionTransactions.clear(); // clear transaction
        rentTransactionTable.getItems().remove(0, rentTransactionTable.getItems().size());
        totalDeposit = 0;
        totalCharges = 0;
        discount = 0;
        haveDiscount = false;
        netAmountToPay = 0;
        reloadTotalPriceLabel();
    }

    public void applyDiscountOnPressed(Event event) {
        int memID;
        try {
            memID = Integer.parseInt(memberIDField.getText());
        } catch (NumberFormatException e) {
            Dialog.alertBox("Please has at least one transaction before applying the discount");
            return;
        }
        Member mem;
        if ((mem = Main.mm.getMember(memID)) == null) {
            Dialog.alertBox("Please has at least one transaction before applying the discount");
            return;
        }

        if (rentTransactionTable.getItems().size() <= 0) {
            Dialog.alertBox("Please has at least one transaction before applying the discount");
            return;
        }

        if (mem.getMemberPoints() >= Main.tm.MEMBER_POINTS_NEEDED_TO_CLAIM_DISCOUNT) {
            Dialog.alertBox("5% discount is applied for the charges.");
            haveDiscount = true;
        } else {
            Dialog.alertBox("This member don't have enough points to claim discount.");
        }
        reloadTotalPriceLabel();
    }

    public void rentButtonOnPressed(Event event) {
        if (rentTransactionTable.getItems().size() <= 0) {
            Dialog.alertBox("There are no books to be rented.");
            return;
        }
        for (int a = 0; a < sessionTransactions.size(); a++) {
            if (Main.tm.addTransaction(sessionTransactions.get(a))) {

            } else {
                // if something went wrong this will happen
            }
        }
        if (haveDiscount) {
            Main.tm.addTransaction(new Transaction(sessionTransactions.get(0).getMemberInvovled(), -discount));
        }

        Dialog.alertBox("Transaction completed.");

        clearTransactionButtonOnPressed(null);
        clearInputFields();

    }
    // ================================================================================================================
    // ================================================================================================================

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

    public void clearInputFields() {
        bookDetailField.setText("");
        bookIDField.setText("");
        memberIDField.setText("");
        memberDetailField.setText("");
    }

    public void reloadTotalPriceLabel() {
        discount = totalCharges * (haveDiscount ? 0.05 : 0);
        netAmountToPay = totalCharges + totalDeposit - discount;
        totalChargesLabel.setText("RM " + String.format("%.2f", totalCharges));
        totalDepositLabel.setText("RM " + String.format("%.2f", totalDeposit));
        discountLabel.setText("RM " + String.format("%.2f", discount));
        netAmountToPayLabel.setText("RM " + String.format("%.2f", netAmountToPay));
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
