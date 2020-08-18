package bookrentalpos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.util.Callback;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
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

    /**
     * @return LocalDate representation of the value in input
     */
    public LocalDate getDate() {
        return inputField.getValue();
    }

    /**
     * @param dayCellFactory Callback function for the dayCellFactory
     */
    public void setDayCellFactory(Callback<DatePicker, DateCell> dayCellFactory) {
        this.inputField.setDayCellFactory(dayCellFactory);
    }

    public GridPane getInputGrid() {
        return inputGrid;
    }

    public DatePicker getInputField() {
        return inputField;
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

class DropDownInput {
    @FXML
    private GridPane inputGrid;
    @FXML
    private Label inputLabel;
    @FXML
    private ChoiceBox<String> inputDropDown;

    public DropDownInput(String labelMessage, String[] dropDownList) {

        inputGrid = new GridPane();
        inputLabel = new Label(labelMessage);
        inputDropDown = new ChoiceBox<>();

        // Add list to drop down menu.
        for (String value : dropDownList) {
            inputDropDown.getItems().add(value);
        }

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
        inputGrid.add(inputDropDown, 1, 0);

    }

    public String value() {
        return inputDropDown.getSelectionModel().getSelectedItem();
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

    @FXML
    private TextFieldInput monthlyYear;
    @FXML
    private DropDownInput monthlyMonth;

    private int reportIndex;
    private String[] monthList = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};

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

        if (index.intValue() == 1 || index.intValue() == 3) {
            startDate = new DatePickerInput("Start Date (Inclusive)");
            endDate = new DatePickerInput("End Date (Exclusive)");

            // I will be honest, i got this part from somewhere
            // This part is suppose to prevent endDate from picking dates before startDate
            final Callback<DatePicker, DateCell> dayCellFactory =
                    new Callback<DatePicker, DateCell>() {
                        @Override
                        public DateCell call(final DatePicker datePicker) {
                            return new DateCell() {
                                @Override
                                public void updateItem(LocalDate item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (startDate.getDate() == null) {
                                        return;
                                    }
                                    // The checks is being done here
                                    if (item.isBefore(
                                            startDate.getDate())
                                    ) {
                                        setDisable(true);
                                        setStyle("-fx-background-color: #ffc0cb;");
                                    }
                                }
                            };
                        }
                    };
            endDate.getInputField().setDisable(true); // disabled by default
            endDate.setDayCellFactory(dayCellFactory); // apply the day cell factory

            // Once a date is being picked, end Date will be enabled
            startDate.getInputField().valueProperty().addListener((ov, oldValue, newValue) -> {
                endDate.getInputField().setDisable(false);
            });

            inputPanel.getChildren().addAll(startDate.getInputGrid(), endDate.getInputGrid());
        }

        switch (index.intValue()) {
            case 0:
            case 1:
                memberID = new TextFieldInput("Member ID");
                inputPanel.getChildren().add(memberID.getInputGrid());
                break;

            case 2:
                monthlyYear = new TextFieldInput("Year");
                monthlyMonth = new DropDownInput("Month", monthList);
                inputPanel.getChildren().addAll(monthlyYear.getInputGrid(), monthlyMonth.getInputGrid());
                break;

            case 3:
                staffID = new TextFieldInput("Staff ID");
                inputPanel.getChildren().add(staffID.getInputGrid());
                break;
        }
    }

    public void showReport() throws IOException {
        // Pretty much hard-coded, im tired
        if (reportIndex == -1) {
            Dialog.alertBox("Please select a report.");
            return;
        }

        // Report at index 4 and 0 do not need dates
        if (reportIndex == 1 || reportIndex == 3) {
            if (startDate.getInputField().getValue() == null || endDate.getInputField().getValue() == null) {
                Dialog.alertBox("Date has not been picked :(");
                return;
            }
        }

        Parent reportParent;
        Stage reportWindow = new Stage();

        FXMLLoader fl;
        GenerateReportController grc;
        int memID;
        switch (reportIndex) {
            case 0:
                // member ID validation before showing the report
                try {
                    memID = Integer.parseInt(memberID.value());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Dialog.alertBox("Invalid Member ID");
                    return;
                }
                if (Main.mm.getById(memID) == null) {
                    Dialog.alertBox("Member ID not found");
                    return;
                }
                fl = new FXMLLoader(getClass().getResource("/FXML/Report/memberPointReport.fxml"));
                reportParent = (Parent) fl.load();
                grc = fl.getController();
                grc.loadDataIntoReport("", "", memberID.value(), "");
                grc.setReportType(ReportType.MEMBER_POINT);
                grc.reloadTableView();
                reportWindow.setTitle("Member Point Report - HuaheeCheh");
                break;
            case 1:
                // member ID validation before showing the report
                try {
                    memID = Integer.parseInt(memberID.value());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Dialog.alertBox("Invalid Member ID");
                    return;
                }
                if (Main.mm.getById(memID) == null) {
                    Dialog.alertBox("Member ID not found");
                    return;
                }
                fl = new FXMLLoader(getClass().getResource("/FXML/Report/memberTransactionReport.fxml"));
                reportParent = (Parent) fl.load();
                grc = fl.getController();
                grc.loadDataIntoReport(startDate.value(), endDate.value(), memberID.value(), "");
                grc.setReportType(ReportType.MEMBER_TRANSACTION);
                grc.reloadTableView();
                reportWindow.setTitle("Member Transaction Report - HuaheeCheh");
                break;
            case 2:
                int year;
                int month;
                // Validate Year here
                try {
                    year = Integer.parseInt(monthlyYear.value());
                } catch (NumberFormatException e) {
                    Dialog.alertBox("Invalid Date");
                    return;
                }
                month = Arrays.asList(monthList).indexOf(monthlyMonth.value()) + 1;
                if (month == 0) {
                    Dialog.alertBox("Please select a month");
                    return;
                }

                fl = new FXMLLoader(getClass().getResource("/FXML/Report/monthlyReport.fxml"));
                reportParent = (Parent) fl.load();
                grc = fl.getController();
                grc.setReportType(ReportType.MONTHLY_REPORT);

                // Set customer start date and end date for the selected month
                LocalDate startingDate = LocalDate.of(year, month, 1);
                LocalDate endingDate = LocalDate.of(month == 12 ? year + 1 : year, month == 12 ? 1 : month + 1, 1);

                grc.loadDataIntoReport(startingDate.toString(), endingDate.toString(), "", "");
                grc.reloadTableView();
                reportWindow.setTitle("Monthly Report - HuaheeCheh");
                break;
            case 3:
                // Staff ID validation before showing the report
                int stfID;
                try {
                    stfID = Integer.parseInt(staffID.value());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Dialog.alertBox("Invalid staff ID");
                    return;
                }
                if (Main.sm.getById(stfID) == null) {
                    Dialog.alertBox("Staff ID not found");
                    return;
                }
                fl = new FXMLLoader(getClass().getResource("/FXML/Report/staffTransactionReport.fxml"));
                reportParent = (Parent) fl.load();
                grc = fl.getController();
                grc.setReportType(ReportType.STAFF_TRANSACTION);
                grc.loadDataIntoReport(startDate.value(), endDate.value(), "", staffID.value());
                grc.reloadTableView();
                reportWindow.setTitle("Staff Transaction Report - HuaheeCheh");
                break;
            case 4:
                fl = new FXMLLoader(getClass().getResource("/FXML/Report/stockLevelReport.fxml"));
                reportParent = (Parent) fl.load();
                grc = fl.getController();
                grc.setReportType(ReportType.STOCK_LEVEL);
                grc.loadDataIntoReport("", "", "", "");
                grc.reloadTableView();
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