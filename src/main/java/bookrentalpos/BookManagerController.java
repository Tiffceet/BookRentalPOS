package bookrentalpos;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import my.edu.tarc.dco.bookrentalpos.Book;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookManagerController implements TableInterface, Initializable {
    public static Stage getWindow;
    @FXML
    private Label recordsCount;
    @FXML
    private Button backButton;
    @FXML
    private Button addBookButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField bookIDField;
    @FXML
    private TextField bookTitleField;
    @FXML
    private TextField bookAuthorField;
    @FXML
    private TextField retailPriceField;
    @FXML
    private Button confirmAddButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button editBookButton;
    @FXML
    private Button confirmEditButton;
    @FXML
    private Button cancelDeleteButton;
    @FXML
    private Button confirmDeleteButton;
    @FXML
    private Button deleteBookButton;
    @FXML
    private Label dateTime;
    @FXML
    private TableView bookTableView;
    @FXML
    private TextField searchByNameField;
    @FXML
    private TextField searchByAuthorField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (bookTableView != null) {
            bookTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            reloadTableView();
        }


        // Because some popup uses the controller class, we need to check if its null before allowing the clock to start
        if (dateTime != null) {
            Clock.display(dateTime);
        }
    }

    @Override
    public void tableOnClick(Event event) {
        if (((MouseEvent) event).getClickCount() == 2) {
            try {
                // only triger this event when there are row selected
                // prevents the error box saying "Please select a row of data to edit" when user click on blank area of the table view
                if (bookTableView.getSelectionModel().getSelectedItems().size() >= 1)
                    popEditBook();
            } catch (IOException e) {
                e.printStackTrace();
                Dialog.alertBox("Something went wrong internally");
            }
        }
    }

    @Override
    public void tableOnKeyPressed(Event event) {
        if (((KeyEvent) event).getCode() == KeyCode.DELETE) {
            try {
                popDeleteBook(event);
            } catch (IOException e) {
                e.printStackTrace();
                Dialog.alertBox("Somewhere went wrong internally. Please contact the devs");
            }
        } else if (((KeyEvent) event).getCode() == KeyCode.F2) {
            try {
                popEditBook();
            } catch (IOException e) {
                e.printStackTrace();
                Dialog.alertBox("Something went wrong internally");
            }
        }
    }

    @Override
    public void reloadTableView() {
        // clear the current list items first
        if (bookTableView == null)
            return;
        bookTableView.getItems().clear();

        Book[] books = Main.bm.getCache(); // get book List
        // As stated in getMemberListCache() javadoc, you could obtain the length of the array through getMemberCount
        for (int a = 0; a < Main.bm.getBookCount(); a++) {
            books[a].setReservedText(books[a].isReserved() ? "✓" : "✘");
            books[a].setRentedText((books[a].isRented() ? "✓" : "✘"));
            bookTableView.getItems().add(books[a]);
        }
        reloadRecordsCountLabel();
    }

    public void reloadRecordsCountLabel() {
        ObservableList ol = bookTableView.getItems();
        recordsCount.setText(ol.size() + " record(s) Found.");
    }

    public void backToMain(MouseEvent event) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/FXML/mainMenu.fxml"));
        Scene mainMenuScene = new Scene(mainMenuParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Main Menu - HuaheeCheh");
        window.setScene(mainMenuScene);
    }

    public void popAddBook() throws IOException {
        Parent addBookParent = FXMLLoader.load(getClass().getResource("/FXML/BookManager/bookManagerAdd.fxml"));
        Stage addBookWindow = new Stage();

        addBookWindow.initModality(Modality.APPLICATION_MODAL);
        addBookWindow.setTitle("Add Book - HuaheeCheh");
        addBookWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        addBookWindow.setScene(new Scene(addBookParent, 600, 350));
        addBookWindow.showAndWait();
        reloadTableView();
    }

    public void popEditBook() throws IOException {
        ObservableList ol = bookTableView.getSelectionModel().getSelectedItems();
        if (ol.size() == 0) {
            Dialog.alertBox("Please select a row of data to edit");
            return;
        }

        if (ol.size() > 1) {
            Dialog.alertBox("Sorry but batch edit is not supported.");
            return;
        }

        Book bookToEdit = (Book) ol.get(0);

        FXMLLoader fl = new FXMLLoader(getClass().getResource("/FXML/BookManager/bookManagerEdit.fxml"));
        Parent editBookParent = (Parent) fl.load();
        BookManagerController bmc = fl.getController();

        // Disable ID as it shouldnt be edited
        bmc.disableBookIDTextField();

        // load the data to be edited
        bmc.loadDataToEdit(bookToEdit);

        Stage editBookWindow = new Stage();

        editBookWindow.initModality(Modality.APPLICATION_MODAL);
        editBookWindow.setTitle("Add Book - HuaheeCheh");
        editBookWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        editBookWindow.setScene(new Scene(editBookParent, 600, 350));
        editBookWindow.showAndWait();
        reloadTableView();

    }

    public void popDeleteBook(Event event) throws IOException {
        ObservableList ol = bookTableView.getSelectionModel().getSelectedItems();
        if (ol.size() < 1) {
            Dialog.alertBox("Please select at least 1 row of data to remove");
            return;
        }

        if (Dialog.confirmBox("Are you sure you want to delete " + ol.size() + " record(s) ?")) {
            for (int a = 0; a < ol.size(); a++) {
                // if one of the books is rented
                if (((Book) ol.get(a)).isRented()) {
                    if (Dialog.confirmBox("The book(s) you are about to delete is currently rented, are you sure you still want to delete the book(s) ?")) {
                        break;
                    } else {
                        Dialog.alertBox("Delete canceled.");
                        return;
                    }
                }
            }
            for (int a = 0; a < ol.size(); a++) {
                if (Main.bm.remove(((Book) ol.get(a)).getId())) {

                } else {

                }
            }
            Dialog.alertBox(ol.size() + " row of data is deleted.");
        }
        reloadTableView();
    }

    public void cancelButton(Event event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void confirmAddButton(Event event) {

        getWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();

        String bookName = bookTitleField.getText();
        String bookAuthor = bookAuthorField.getText();
        double newRentPrice;
        try {
            newRentPrice = Double.parseDouble(retailPriceField.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Dialog.alertBox("Rental price need to be a number");
            return;
        }

        newRentPrice = Double.parseDouble(String.format("%.2f", newRentPrice));

        if (!Main.bm.add(new Book(bookName, bookAuthor, newRentPrice))) {
            Dialog.alertBox("Soemthing went wrong and your book was not added");
            return;
        }

        Dialog.alertBox("The book has successfully added!");
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void confirmEditButton(Event event) throws IOException {
        getWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        int bookID = Integer.parseInt(bookIDField.getText());
        String bookName = bookTitleField.getText();
        String bookAuthor = bookAuthorField.getText();
        double newRentPrice;
        try {
            newRentPrice = Double.parseDouble(retailPriceField.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Dialog.alertBox("Rental price need to be a number");
            return;
        }
        newRentPrice = Double.parseDouble(String.format("%.2f", newRentPrice));

        Book bookToEdit = Main.bm.getById(bookID);
        bookToEdit.setName(bookName);
        bookToEdit.setAuthor(bookAuthor);
        bookToEdit.setRetailPrice(newRentPrice);

        if (!Main.bm.update(bookToEdit)) {
            Dialog.alertBox("Something went wrong and the book was not deleted");
            return;
        }

        Dialog.alertBox("The book has been successfully edited!");
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void confirmBookDelete(MouseEvent event) throws IOException {
        // Delete from database.

        Dialog.alertBox("The book has successfully deleted!");
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void searchQueryOnKeyPressed(Event event) {
        if (((KeyEvent) event).getCode() == KeyCode.ENTER)
            searchOnPressed(event);
    }

    public void searchOnPressed(Event evt) {
        // clear the current list items first
        if (bookTableView == null)
            return;
        bookTableView.getItems().clear();
        // load the cache from MemberManager
        Book[] books = Main.bm.getCache();
        String nameQuery = searchByNameField.getText().toLowerCase();
        String authorQuery = searchByAuthorField.getText().toLowerCase();

        boolean checkName = !nameQuery.isEmpty();
        boolean checkAuthor = !authorQuery.isEmpty();

        if (!checkAuthor && !checkName) {
            Dialog.alertBox("Please insert search query");
            reloadTableView();
            return;
        }

        for (int a = 0; a < Main.bm.getBookCount(); a++) {
            if (checkName) {
                if (!books[a].getName().toLowerCase().startsWith(nameQuery)) {
                    continue;
                }
            }
            if (checkAuthor) {
                if (!books[a].getAuthor().toLowerCase().startsWith(authorQuery)) {
                    continue;
                }
            }
            bookTableView.getItems().add(books[a]);
        }
        reloadRecordsCountLabel();
        Dialog.alertBox(bookTableView.getItems().size() + " records found");
    }

    public void clearOnPressed(Event evt) {
        reloadTableView();
        if (searchByNameField != null) {
            searchByNameField.setText("");
        }
        if (searchByAuthorField != null) {
            searchByAuthorField.setText("");
        }
        Dialog.alertBox("Search query cleared.");
    }

    public void textFieldOnKeyPressed(Event event) {
        if (((KeyEvent) event).getCode() == KeyCode.ESCAPE) {
            cancelButton(event);
            return;
        }
        if (((KeyEvent) event).getCode() == KeyCode.ENTER) {
            try {
                if (bookIDField == null) {
                    confirmAddButton(event);
                } else
                    confirmEditButton(event);
            } catch (IOException e) {
                Dialog.alertBox("Corrupted jar file. Please redownload the program");
            }
        }
    }

    /**
     * Referring to Edit Pop Up Text Field
     */
    public void disableBookIDTextField() {
        if (bookIDField != null) {
            bookIDField.setStyle("-fx-text-inner-color: grey;");
            bookIDField.setEditable(false);
            bookIDField.setFocusTraversable(false);
        }
    }

    /**
     * Referring to the fields in the Edit Pop up
     */
    public void loadDataToEdit(Book bookToEdit) {
        bookTitleField.setText(bookToEdit.getName());
        bookAuthorField.setText(bookToEdit.getAuthor());
        bookIDField.setText(bookToEdit.getId() + "");
        retailPriceField.setText(bookToEdit.getRetailPrice() + "");
    }
}
