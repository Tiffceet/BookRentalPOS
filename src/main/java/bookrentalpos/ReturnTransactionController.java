package bookrentalpos;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import my.edu.tarc.dco.bookrentalpos.Book;
import my.edu.tarc.dco.bookrentalpos.CustomUtil;
import my.edu.tarc.dco.bookrentalpos.Member;
import my.edu.tarc.dco.bookrentalpos.Transaction;

import java.io.IOException;
import java.util.Date;

public class ReturnTransactionController {
    public Label dateTime;
    public Button closeButton;
    public TextArea bookDetailTextArea;
    public TextArea memberDetailTextArea;
    public TextField bookIDTextField;

    public Label depositPaidLabel;
    public Label bookReturnStatus;
    public Label penaltyLabel;
    public Label netDepositReturnLabel;

    private double depositPaid;
    private long daysSinceRented;
    private long daysLate;
    private double penalty;
    private double netDepositReturn = 0;


    public void initialize() {
        Clock.display(dateTime);
    }

    public void closeReturn(MouseEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }
    // ================================================================================================================
    // Events Function
    // ================================================================================================================

    public void bookIDOnKeyPressed(Event event) {
        if(((KeyEvent) event).getCode() == KeyCode.ENTER) {
            bookIDOnKeyReleased(event);
        }
    }

    public void bookIDOnKeyReleased(Event event) {
        int bookID;
        try {
            bookID = Integer.parseInt(bookIDTextField.getText());
        } catch (NumberFormatException e) {
            return;
        }
        Book bk;
        Transaction t;
        int weeksRented = 0;
        if ((bk = Main.bm.getBookById(bookID)) != null) {
            t = Main.tm.getBookLastRentTransaction(bookID);
            if (t != null) {
                weeksRented = t.getRentDurationInDays() / 7;
                bookDetailTextArea.setText(String.format("Book Title: %s\nRented on: %s\nRent Duration: %s",
                        bk.getName(),
                        t.getDateCreated(),
                        weeksRented + " week(s)"
                ));
            } else {
                bookDetailTextArea.setText(bk.toString());
            }
        } else {
            bookDetailTextArea.setText("");
            memberDetailTextArea.setText("");
            resetPriceLabels();
            return;
        }

        if (bk.isRented()) {
            Member member;
            if ((member = Main.mm.getMember(bk.getLastRentedBy())) != null) // if member was not deleted
                memberDetailTextArea.setText(Main.mm.getMember(bk.getLastRentedBy()).toString());
            else memberDetailTextArea.setText("");
            double depositPaid = (t.getCashFlow() / (2.0 + (Main.tm.DEPOSIT_RATES.get(weeksRented > 4 ? 4 : weeksRented) / 100.0))) * 2;
            long daysSinceRented = CustomUtil.daysDifference(new Date(), CustomUtil.stringToDate(t.getDateCreated()));
            long daysLate = daysSinceRented - t.getRentDurationInDays();
            double penalty = (daysLate * Main.tm.PENALTY_RATES) > 0 ? (daysLate * Main.tm.PENALTY_RATES) : 0;

            depositPaidLabel.setText("RM " + String.format("%.2f", depositPaid));

            if (daysSinceRented == t.getRentDurationInDays()) {
                bookReturnStatus.setText("In-time");
            } else if (daysSinceRented > t.getRentDurationInDays()) {
                bookReturnStatus.setText(daysLate + " days late");
                penaltyLabel.setText("RM " + String.format("%.2f", (double) daysLate * Main.tm.PENALTY_RATES));
            } else {
                bookReturnStatus.setText("Not yet returned.");
            }

            netDepositReturn = depositPaid - penalty;
            if (netDepositReturn < 0) {
                netDepositReturn = 0;
            }
            netDepositReturnLabel.setText("RM " + String.format("%.2f", netDepositReturn));
        } else {
            memberDetailTextArea.setText("");
            resetPriceLabels();
        }
    }

    public void returnButtonOnPressed(Event event) {
        int bookID;
        try {
            bookID = Integer.parseInt(bookIDTextField.getText());
        } catch (NumberFormatException e) {
            // e.printStackTrace();
            Dialog.alertBox("Invalid / Empty BookID");
            return;
        }

        Book bk = Main.bm.getBookById(bookID);
        if (bk == null) {
            Dialog.alertBox("BookID do not exist.");
            return;
        }

        if (!bk.isRented()) {
            Dialog.alertBox("The book is not rented hence cant be returned.");
            return;
        }
        Transaction rentTrans = Main.tm.getBookLastRentTransaction(bookID);
        if (rentTrans == null) {
            Dialog.alertBox("Unable to find who rented this book");
            return;
        }
        Transaction t = new Transaction(Main.sm.getLogOnStaff().getId(), rentTrans.getMemberInvovled(), bookID, -netDepositReturn);
        if (Main.tm.addTransaction(t)) {
            Member member;
            if ((member = Main.mm.getMember(bk.getLastRentedBy())) != null && daysLate <= 0) { // if member was not removed
                Dialog.alertBox("Book returned successfully\n" + member.getName() + " gained 10 points for returning the book in time.");
                member.setMemberPoints(member.getMemberPoints() + 10);
                if (!Main.mm.updateMember(member)) {
                    Dialog.alertBox("Something went wrong when trying to add points to member");
                }
            } else {
                Dialog.alertBox("Book returned successfully");
            }
            resetPriceLabels();
            clearInputFields();
        } else {
            Dialog.alertBox("Something went wrong internally while trying to return book.");
        }
    }


    // ================================================================================================================
    // ================================================================================================================

    public void clearInputFields() {
        bookIDTextField.setText("");
        bookDetailTextArea.setText("");
        memberDetailTextArea.setText("");
    }

    public void resetPriceLabels() {
        depositPaidLabel.setText("RM 0.00");
        bookReturnStatus.setText("Undefined");
        penaltyLabel.setText("RM 0.00");
        netDepositReturnLabel.setText("RM 0.00");
        depositPaid = 0;
        daysSinceRented = 0;
        daysLate = 0;
        penalty = 0;
        netDepositReturn = 0;
    }
}

