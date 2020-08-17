package bookrentalpos;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

class ReportInput {
    public GridPane inputGrid;
    public Label inputLabel;
    public DatePicker inputField;

    public ReportInput(String labelMessage) {
        inputGrid = new GridPane();
        inputLabel = new Label(labelMessage);
        inputField = new DatePicker();

        // Set Column size.
        ColumnConstraints defaultCC = new ColumnConstraints();
        defaultCC.setHgrow(Priority.SOMETIMES);
        defaultCC.setMinWidth(10.0);
        defaultCC.setPrefWidth(100.0);

        inputGrid.getColumnConstraints().add(defaultCC);
        inputGrid.getColumnConstraints().add(defaultCC);
        inputGrid.getRowConstraints().add(new RowConstraints());

        // Set stylesheet.
        inputLabel.getStyleClass().add("transactionFont");

        inputGrid.add(inputLabel, 0, 0);
        inputGrid.add(inputField, 1, 0);
    }

    public String value() {
        LocalDate localDate = inputField.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        return localDate.toString();
    }
}

public class ReportController {

    public Button backButton;
    public ChoiceBox<String> reportSelect;
    public VBox inputPanel;
    public Button generateButton;

    public static int reportIndex;
    public static ReportInput startDate;
    public static ReportInput endDate;
    public static ReportInput memberID;
    public static ReportInput staffID;

    public void initialize() {
        reportSelect.getItems().addAll("Member Point Report", "Member Transaction Report", "Monthly Report",
                "Staff Transaction Report", "Stock Level Report");
        reportSelect.getSelectionModel().selectedIndexProperty().addListener((v, oldValue, newValue) -> {
            reportIndex = newValue.intValue();
            reportChosen(newValue);
        });
        reportIndex = -1;
    }

    public void backToMain(MouseEvent event) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/FXML/mainMenu.fxml"));
        Scene mainMenuScene = new Scene(mainMenuParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Main Menu - HuaheeCheh");
        window.setScene(mainMenuScene);
    }

    public void reportChosen(Number index) {

        if (!inputPanel.getChildren().isEmpty()) {
            inputPanel.getChildren().clear();
        }

        // Stock report didn't need input.
        if (index.intValue() == 4) {
            return;
        }

        startDate = new ReportInput("Start Date");
        endDate = new ReportInput("End Date");
        inputPanel.getChildren().addAll(startDate.inputGrid, endDate.inputGrid);

        switch (index.intValue()) {
            case 1:
                memberID = new ReportInput("Customer ID");
                inputPanel.getChildren().add(memberID.inputGrid);
                break;

            case 3:
                staffID = new ReportInput("Staff ID");
                inputPanel.getChildren().add(staffID.inputGrid);
                break;
        }
    }

    public void showReport() throws IOException {
        // Validate everything first.

        Parent reportParent;
        Stage reportWindow = new Stage();

        switch (reportIndex) {
            case 0:
                reportParent = FXMLLoader.load(getClass().getResource("/FXML/Report/memberPointReport.fxml"));
                reportWindow.setTitle("Member Point Report - HuaheeCheh");
                break;
            case 1:
                reportParent = FXMLLoader.load(getClass().getResource("/FXML/Report/memberTransactionReport.fxml"));
                reportWindow.setTitle("Member Transaction Report - HuaheeCheh");
                break;
            case 2:
                reportParent = FXMLLoader.load(getClass().getResource("/FXML/Report/monthlyReport.fxml"));
                reportWindow.setTitle("Monthly Report - HuaheeCheh");
                break;
            case 3:
                reportParent = FXMLLoader.load(getClass().getResource("/FXML/Report/staffTransactionReport.fxml"));
                reportWindow.setTitle("Staff Transaction Report - HuaheeCheh");
                break;
            case 4:
                reportParent = FXMLLoader.load(getClass().getResource("/FXML/Report/stockLevelReport.fxml"));
                reportWindow.setTitle("Stock Level Report - HuaheeCheh");
                break;
            default:
                return;
        }

        reportWindow.initModality(Modality.APPLICATION_MODAL);
        reportWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        reportWindow.setScene(new Scene(reportParent, 1200, 800));
        reportWindow.setResizable(false);
        reportWindow.showAndWait();
    }
}