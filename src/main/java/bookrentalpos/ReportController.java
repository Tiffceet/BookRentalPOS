package bookrentalpos;

import javafx.fxml.FXML;
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

/**
 * Class containing Label and DatePicker Component
 *
 * @author Caferatte89
 */
class DatePickerInput {
    @FXML
    private GridPane inputGrid;
    @FXML
    private Label inputLabel;
    @FXML
    private DatePicker inputField;

    /**
     * @param labelMessage text to show on Label component
     */
    public DatePickerInput(String labelMessage) {
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

    /**
     * @return a string representation of the value in input
     */
    public String value() {
        LocalDate localDate = inputField.getValue();
        return localDate.toString();
    }

    public GridPane getInputGrid() {
        return inputGrid;
    }
}

/**
 * Class containing Label and TextField component
 *
 * @author Caferatte89
 */
class TextFieldInput {
    @FXML
    private GridPane inputGrid;
    @FXML
    private Label inputLabel;
    @FXML
    private TextField inputField;

    /**
     * @param labelMessage Text for the label component
     */
    public TextFieldInput(String labelMessage) {
        inputGrid = new GridPane();
        inputLabel = new Label(labelMessage);
        inputField = new TextField();

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

    /**
     * @return a string representation of the value in input
     */
    public String value() {
        return inputField.getText();
    }

    public GridPane getInputGrid() {
        return inputGrid;
    }
}

public class ReportController {

    @FXML
    private Button backButton;
    @FXML
    private ChoiceBox<String> reportSelect;
    @FXML
    private VBox inputPanel;
    @FXML
    private Button generateButton;

    @FXML
    private DatePickerInput startDate;
    @FXML
    private DatePickerInput endDate;
    @FXML
    private TextFieldInput memberID;
    @FXML
    private TextFieldInput staffID;

    private int reportIndex;

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

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

        startDate = new DatePickerInput("Start Date");
        endDate = new DatePickerInput("End Date");
        inputPanel.getChildren().addAll(startDate.getInputGrid(), endDate.getInputGrid());

        switch (index.intValue()) {
            case 1:
                memberID = new TextFieldInput("Customer ID");
                inputPanel.getChildren().add(memberID.getInputGrid());
                break;

            case 3:
                staffID = new TextFieldInput("Staff ID");
                inputPanel.getChildren().add(staffID.getInputGrid());
                break;
        }
    }

    public void showReport() throws IOException {
        // Validate everything first.

        Parent reportParent;
        Stage reportWindow = new Stage();

        FXMLLoader fl;
        GenerateReportController grc;
        switch (reportIndex) {
            case 0:
                fl = new FXMLLoader(getClass().getResource("/FXML/Report/memberPointReport.fxml"));
                reportParent = (Parent) fl.load();
                grc = fl.getController();
                grc.loadDataIntoReport(startDate.value(), endDate.value(), "", "");
                grc.setReportType(ReportType.MEMBER_POINT);
                reportWindow.setTitle("Member Point Report - HuaheeCheh");
                break;
            case 1:
                fl = new FXMLLoader(getClass().getResource("/FXML/Report/memberTransactionReport.fxml"));
                reportParent = (Parent) fl.load();
                grc = fl.getController();
                grc.loadDataIntoReport(startDate.value(), endDate.value(), memberID.value(), "");
                grc.setReportType(ReportType.MEMBER_TRANSACTION);
                reportWindow.setTitle("Member Transaction Report - HuaheeCheh");
                break;
            case 2:
                fl = new FXMLLoader(getClass().getResource("/FXML/Report/monthlyReport.fxml"));
                reportParent = (Parent) fl.load();
                grc = fl.getController();
                grc.setReportType(ReportType.MONTHLY_REPORT);
                grc.loadDataIntoReport(startDate.value(), endDate.value(), "", "");
                reportWindow.setTitle("Monthly Report - HuaheeCheh");
                break;
            case 3:
                fl = new FXMLLoader(getClass().getResource("/FXML/Report/staffTransactionReport.fxml"));
                reportParent = (Parent) fl.load();
                grc = fl.getController();
                grc.setReportType(ReportType.STAFF_TRANSACTION);
                grc.loadDataIntoReport(startDate.value(), endDate.value(), "", staffID.value());
                reportWindow.setTitle("Staff Transaction Report - HuaheeCheh");
                break;
            case 4:
                fl = new FXMLLoader(getClass().getResource("/FXML/Report/stockLevelReport.fxml"));
                reportParent = (Parent) fl.load();
                grc = fl.getController();
                grc.setReportType(ReportType.STOCK_LEVEL);
                grc.loadDataIntoReport("", "", "", "");
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