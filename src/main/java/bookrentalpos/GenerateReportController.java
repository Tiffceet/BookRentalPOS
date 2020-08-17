package bookrentalpos;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import my.edu.tarc.dco.bookrentalpos.Transaction;
import my.edu.tarc.dco.bookrentalpos.TransactionType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

enum ReportType {
    MEMBER_POINT,
    MEMBER_TRANSACTION,
    MONTHLY_REPORT,
    STAFF_TRANSACTION,
    STOCK_LEVEL
}

public class GenerateReportController implements TableInterface {
    // All report.
    @FXML
    private Label startDateLabel;
    @FXML
    private Label endDateLabel;
    @FXML
    private Label reportTitle;

    // Member point report.
    @FXML
    private Label pointRentedLabel;
    @FXML
    private Label pointReturnLabel;
    @FXML
    private Label pointTotalLabel;
    @FXML
    private TableView memberPointReportTable;

    // Member Transaction report.
    @FXML
    private Label transactionNumberLabel;
    @FXML
    private Label memberIDLabel;

    // Monthly report.
    @FXML
    private Label monthlyRented;
    @FXML
    private Label monthlyReturn;
    @FXML
    private Label monthlyTotal;

    // Staff transaction report.
    @FXML
    private Label staffIDLabel;
    @FXML
    private Label staffTransactionLabel;
    @FXML
    private Label stockLastMonthLabel;
    @FXML
    private TableView staffTransactionReportTable;

    // Stock level report.
    @FXML
    private TableView stockLevelReportTable;
    @FXML
    private Label booksInSystemLabel;
    @FXML
    private Label booksInStoreLabel;
    @FXML
    private Label booksNetWorthLabel;

    private ReportType type;

    @Override
    public void reloadTableView() {
        switch (type) {
            case MEMBER_POINT:
                // loadMemberPointsReportData();
                break;
            case STOCK_LEVEL:
                loadStockLevelReport();
                break;
            case MONTHLY_REPORT:
                break;
            case STAFF_TRANSACTION:
                loadStaffTransactionReport();
                break;
            case MEMBER_TRANSACTION:
                break;
        }
    }

    @Override
    public void tableOnClick(Event event) {

    }

    @Override
    public void tableOnKeyPressed(Event event) {

    }

    public void setReportType(ReportType rt) {
        this.type = rt;
    }

    public void loadDataIntoReport(String startDate, String endDate, String memberID, String staffID) {
        if (startDateLabel != null) {
            startDateLabel.setText(startDate);
        }
        if (endDateLabel != null) {
            endDateLabel.setText(endDate);
        }
        if (memberIDLabel != null) {
            memberIDLabel.setText(memberID);
        }
        if (staffIDLabel != null) {
            staffIDLabel.setText(staffID);
        }
    }

    public void loadMemberPointsReportData() {
        // rewrite this part after chen xiang re layout member points report
        int memID = 1;
        try {
            memID = Integer.parseInt(memberIDLabel.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Dialog.alertBox("Invalid memberID");
            return;
        }

        if (Main.mm.getById(memID) == null) {
            Dialog.alertBox("Member not found");
            return;
        }

        ObservableList ol = memberPointReportTable.getItems();
        ArrayList<Transaction> t;
        try {
            t = Main.tm.getTransactionsByMemberID(memID,
                    new SimpleDateFormat("yyyy-MM-dd").parse(startDateLabel.getText()),
                    new SimpleDateFormat("yyyy-MM-dd").parse(endDateLabel.getText()));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            Dialog.alertBox("This is not suppose to happen");
            return;
        }
        for (int a = 0; a < t.size(); a++)
            ol.add(new _memberPointReportTableData(t.get(a).getDateCreated(),
                    t.get(a).getId() + "",
                    t.get(a).getType() == TransactionType.RETURN ? "10" : "0")
            );
    }

    public void loadStockLevelReport() {
        int totalBooksInSystem = 0;
        int totalBooksInStore = 0;
        double finalAmount = 0;
        ObservableList ol = stockLevelReportTable.getItems();
        ArrayList<_stockLevelReportTableData> sllrtd = Main.bm.getBookCountInSystem();
        for (int a = 0; a < sllrtd.size(); a++) {
            ol.add(sllrtd.get(a));
            totalBooksInStore += Integer.parseInt(sllrtd.get(a).getAmountInStore());
            totalBooksInSystem += Integer.parseInt(sllrtd.get(a).getAmountInSystem());
            finalAmount += Double.parseDouble(sllrtd.get(a).getFinalAmount());
        }
        booksInStoreLabel.setText(totalBooksInStore + "");
        booksInSystemLabel.setText(totalBooksInSystem + "");
        booksNetWorthLabel.setText(String.format("RM %.2f", finalAmount));
    }

    public void loadStaffTransactionReport() {
        int staffID = 0;
        try {
            staffID = Integer.parseInt(staffIDLabel.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        ArrayList<Transaction> t;
        try {
            t = Main.tm.getTransactionByStaffID(staffID,
                    new SimpleDateFormat("yyyy-MM-dd").parse(startDateLabel.getText()),
                    new SimpleDateFormat("yyyy-MM-dd").parse(endDateLabel.getText()));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            Dialog.alertBox("This is not suppose to happen");
            return;
        }

        ObservableList ol = staffTransactionReportTable.getItems();
        for (int a = 0; a < t.size(); a++) {
            ol.add(
                    new _staffTransactionReportTableData(
                            t.get(a).getDateCreated(),
                            t.get(a).getId() + "",
                            Main.mm.getById(t.get(a).getMemberInvovled()).getName(),
                            Main.bm.getById(t.get(a).getBookInvovled()).getName(),
                            t.get(a).getType().toString(),
                            t.get(a).getCashFlow() + "")
            );
        }
        staffTransactionLabel.setText(ol.size() + "");
    }
}
