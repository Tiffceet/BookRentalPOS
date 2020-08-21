package bookrentalpos;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import my.edu.tarc.dco.bookrentalpos.Book;
import my.edu.tarc.dco.bookrentalpos.Member;
import my.edu.tarc.dco.bookrentalpos.Transaction;
import my.edu.tarc.dco.bookrentalpos.TransactionType;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

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
    @FXML
    private Label totalRecordsLabel;

    // Member point report.
    @FXML
    private Label pointMemberIDLabel;
    @FXML
    private Label totalPointLabel;
    @FXML
    private TableView memberPointReportTable;

    // Member Transaction report.
    // There should be a memberID Label here but since Member point report also have memberID label
    // Im reusing that label
    @FXML
    private Label transactionNumberLabel;
    @FXML
    private TableView memberTransactionTable;

    // Monthly report.
    @FXML
    private Label monthlyRented;
    @FXML
    private Label monthlyReturn;
    @FXML
    private Label monthlyTotal;
    @FXML
    private TableView monthlyReportTable;
    @FXML
    private Label monthlyReportTitle;

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
                loadMemberPointsReportData();
                break;
            case STOCK_LEVEL:
                loadStockLevelReport();
                break;
            case MONTHLY_REPORT:
                LocalDate ld = LocalDate.parse(startDateLabel.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Month m = ld.getMonth();
                loadMonthlyReport(ld.getYear() + "", m.toString());
                break;
            case STAFF_TRANSACTION:
                loadStaffTransactionReport();
                break;
            case MEMBER_TRANSACTION:
                loadMemberTransactionReport();
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
        if (pointMemberIDLabel != null) {
            pointMemberIDLabel.setText(memberID);
        }
        if (staffIDLabel != null) {
            staffIDLabel.setText(staffID);
        }
    }

    public void loadMemberPointsReportData() {
        int memID;
        try {
            memID = Integer.parseInt(pointMemberIDLabel.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Dialog.alertBox("Invalid memberID");
            return;
        }
        Member mem;
        if ((mem = Main.mm.getById(memID)) == null) {
            Dialog.alertBox("Member not found");
            return;
        }

        ObservableList ol = memberPointReportTable.getItems();
        ArrayList<Transaction> t;
        t = Main.tm.getTransactionsByMemberID(memID);
        for (int a = 0; a < t.size(); a++) {
            if (t.get(a).getType() == TransactionType.DISCOUNT) {
                ol.add(new _MemberPointReportTableData(t.get(a).getDateCreated(),
                        t.get(a).getId() + "",
                        "-500")
                );
            }
            if (t.get(a).getType() == TransactionType.RETURN) {
                ol.add(new _MemberPointReportTableData(t.get(a).getDateCreated(),
                        t.get(a).getId() + "",
                        "+10")
                );
            }
        }
        pointMemberIDLabel.setText(mem.getId() + " (" + mem.getName() + ")");
        totalPointLabel.setText(mem.getMemberPoints() + "");
        totalRecordsLabel.setText(ol.size() + " Record(s)");
    }

    public void loadMemberTransactionReport() {
        int memID;
        try {
            memID = Integer.parseInt(pointMemberIDLabel.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Dialog.alertBox("Invalid memberID");
            return;
        }
        Member mem;
        if ((mem = Main.mm.getById(memID)) == null) {
            Dialog.alertBox("Member not found");
            return;
        }
        Date strDate, endDate;
        try {
            strDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateLabel.getText());
            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateLabel.getText());
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            Dialog.alertBox("This is not suppose to happen");
            return;
        }

        ObservableList ol = memberTransactionTable.getItems();
        ArrayList<Transaction> t = Main.tm.getTransactionsByMemberID(memID, strDate, endDate);
        for (int a = 0; a < t.size(); a++) {
            Book b = Main.bm.getById(t.get(a).getBookInvovled());
            ol.add(
                    new _MemberTransactionTableData(
                            t.get(a).getDateCreated(),
                            t.get(a).getId() + "",
                            b == null ? "<removed>" : b.getName(),
                            t.get(a).getType().toString(),
                            String.format("%.2f", t.get(a).getCashFlow())
                    )
            );
        }
        transactionNumberLabel.setText(t.size() + "");
        pointMemberIDLabel.setText(pointMemberIDLabel.getText() + " (" + mem.getName() + ")");
        totalRecordsLabel.setText(ol.size() + " Record(s)");
    }

    public void loadMonthlyReport(String year, String month) {
        int rentCounter = 0;
        int returnsCounter = 0;
        double finalRevenue = 0;
        ObservableList ol = monthlyReportTable.getItems();
        monthlyReportTitle.setText(year + " " + month + " Monthly Report");
        Transaction[] t = Main.tm.getCache();
        for (int a = 0; a < Main.tm.getTransactionCount(); a++) {
            Member m = Main.mm.getById(t[a].getMemberInvovled());
            Book b = Main.bm.getById(t[a].getBookInvovled());
            ol.add(
                    new _MonthlyReportTableData(
                            t[a].getDateCreated(),
                            m == null ? "<removed>" : m.getName(),
                            t[a].getType().toString(),
                            b == null ? "<removed>" : b.getName(),
                            String.format("%.2f", t[a].getCashFlow())
                    )
            );
            rentCounter += t[a].getType() == TransactionType.RENT ? 1 : 0;
            returnsCounter += t[a].getType() == TransactionType.RETURN ? 1 : 0;
            finalRevenue += t[a].getCashFlow();
        }
        monthlyRented.setText(rentCounter + "");
        monthlyReturn.setText(returnsCounter + "");
        monthlyTotal.setText(String.format("%.2f", finalRevenue));
        totalRecordsLabel.setText(ol.size() + " Record(s)");
    }

    public void loadStockLevelReport() {
        int totalBooksInSystem = 0;
        int totalBooksInStore = 0;
        double finalAmount = 0;
        ObservableList ol = stockLevelReportTable.getItems();
        ArrayList<_StockLevelReportTableData> sllrtd = Main.bm.getBookCountInSystem();
        for (int a = 0; a < sllrtd.size(); a++) {
            ol.add(sllrtd.get(a));
            totalBooksInStore += Integer.parseInt(sllrtd.get(a).getAmountInStore());
            totalBooksInSystem += Integer.parseInt(sllrtd.get(a).getAmountInSystem());
            finalAmount += Double.parseDouble(sllrtd.get(a).getFinalAmount());
        }
        booksInStoreLabel.setText(totalBooksInStore + "");
        booksInSystemLabel.setText(totalBooksInSystem + "");
        booksNetWorthLabel.setText("RM " + String.format("RM %.2f", finalAmount));
        totalRecordsLabel.setText(ol.size() + " Record(s)");
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
            Member mem = Main.mm.getById(t.get(a).getMemberInvovled());
            Book b = Main.bm.getById(t.get(a).getBookInvovled());
            ol.add(
                    new _StaffTransactionReportTableData(
                            t.get(a).getDateCreated(),
                            t.get(a).getId() + "",
                            mem == null ? "<removed>" : mem.getName(),
                            b == null ? "<removed>" : b.getName(),
                            t.get(a).getType().toString(),
                            t.get(a).getCashFlow() + "")
            );
        }
        staffTransactionLabel.setText(ol.size() + "");
        staffIDLabel.setText(staffIDLabel.getText() + " (" + Main.sm.getById(staffID).getName() + ")");
        totalRecordsLabel.setText(ol.size() + " Record(s)");
    }
}
