package bookrentalpos;

import javafx.scene.control.Label;

enum ReportType {
    MEMBER_POINT,
    MEMBER_TRANSACTION,
    MONTHLY_REPORT,
    STAFF_TRANSACTION,
    STOCK_LEVEL
}

public class GenerateReportController {
    // All report.
    public Label startDateLabel;
    public Label endDateLabel;

    // Member point report.
    public Label pointRentedLabel;
    public Label pointReturnLabel;
    public Label pointTotalLabel;

    // Member Transaction report.
    public Label transactionNumberLabel;
    public Label memberIDLabel;

    // Monthly report.
    public Label monthlyRented;
    public Label monthlyReturn;
    public Label monthlyTotal;

    // Staff transaction report.
    public Label staffIDLabel;
    public Label staffTransactionLabel;
    public Label stockLastMonthLabel;

    // Stock level report.
    public Label stockAddLabel;
    public Label stockLessLabel;
    public Label stockDeleteLabel;
    public Label stockQuantityLabel;
    public Label stockTotalLabel;

    private ReportType type;

    public void initialize() {

    }

    public void setReportType(ReportType rt) {
        this.type = rt;
    }

    public void loadDataIntoReport(String startDate, String endDate, String memberID, String staffID) {
        if(startDateLabel != null) {
            startDateLabel.setText(startDate);
        }
        if(endDateLabel != null) {
            endDateLabel.setText(endDate);
        }
        if(memberIDLabel != null) {
            memberIDLabel.setText(memberID);
        }
        if(staffIDLabel != null) {
            staffIDLabel.setText(staffID);
        }
    }
}
