package bookrentalpos;

import javafx.scene.control.Label;

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

    public void initialize() {
        if (ReportController.reportIndex != 4) {
            startDateLabel.setText(ReportController.startDate.value());
            endDateLabel.setText(ReportController.endDate.value());
        }

        switch (ReportController.reportIndex) {
            case 1:
                memberIDLabel.setText(ReportController.memberID.value());
            case 3:
                staffIDLabel.setText(ReportController.staffID.value());
        }
    }
}
