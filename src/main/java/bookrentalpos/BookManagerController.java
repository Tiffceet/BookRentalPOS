package bookrentalpos;

import javafx.collections.ObservableList;
import javafx.event.Event;
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
    public Button backButton;
    public Button addBookButton;
    public Button cancelButton;
    public TextField bookIDField;
    public TextField bookTitleField;
    public TextField bookAuthorField;
    public TextField retailPriceField;
    public Button confirmAddButton;
    public Button closeButton;
    public Button editBookButton;
    public Button confirmEditButton;
    public Button cancelDeleteButton;
    public Button confirmDeleteButton;
    public Button deleteBookButton;
    public Label dateTime;
    public TableView bookTableView;

    public TextField searchByNameField;
    public TextField searchByAuthorField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (bookTableView != null) {
            bookTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
        reloadTableView();

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
                if(bookTableView.getSelectionModel().getSelectedItems().size() >= 1)
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

        Book[] books = Main.bm.getBooKListCache(); // get book List
        // As stated in getMemberListCache() javadoc, you could obtain the length of the array through getMemberCount
        for (int a = 0; a < Main.bm.getBookCount(); a++) {
            bookTableView.getItems().add(books[a]);
        }
    }

    public void backToMain(MouseEvent event) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/FXML/mainMenu.fxml"));
        Scene mainMenuScene = new Scene(mainMenuParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Main Menu - Huahee Library");
        window.setScene(mainMenuScene);
    }

    public void popAddBook() throws IOException {
        Parent addBookParent = FXMLLoader.load(getClass().getResource("/FXML/BookManager/bookManagerAdd.fxml"));
        Stage addBookWindow = new Stage();

        addBookWindow.initModality(Modality.APPLICATION_MODAL);
        addBookWindow.setTitle("Add Book - Huahee Library");
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
        bmc.bookIDField.setStyle("-fx-text-inner-color: grey;");
        bmc.bookIDField.setEditable(false);
        bmc.bookIDField.setFocusTraversable(false);

        // load the data to be edited
        bmc.bookTitleField.setText(bookToEdit.getName());
        bmc.bookAuthorField.setText(bookToEdit.getAuthor());
        bmc.bookIDField.setText(bookToEdit.getId() + "");
        bmc.retailPriceField.setText(bookToEdit.getRetailPrice() + "");

        Stage editBookWindow = new Stage();

        editBookWindow.initModality(Modality.APPLICATION_MODAL);
        editBookWindow.setTitle("Add Book - Huahee Library");
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
        FXMLLoader fl = new FXMLLoader(getClass().getResource("/FXML/BookManager/bookManagerDelete.fxml"));
        Parent deleteBookParent = (Parent) fl.load();
        YesNoDialogController ync = fl.getController();

        Stage deleteBookWindow = new Stage();

        deleteBookWindow.initModality(Modality.APPLICATION_MODAL);
        deleteBookWindow.setTitle("Delete Book - Huahee Library");
        deleteBookWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        deleteBookWindow.setScene(new Scene(deleteBookParent, 400, 150));
        deleteBookWindow.showAndWait();
        if (ync.response == 1) {
            for (int a = 0; a < ol.size(); a++) {
                if (Main.bm.removeBook(((Book) ol.get(a)).getId())) {

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

    public void confirmAddButton(MouseEvent event) {

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

        if (!Main.bm.addBook(new Book(bookName, bookAuthor, newRentPrice))) {
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
        Book bookToEdit = Main.bm.getBookById(bookID);
        bookToEdit.setName(bookName);
        bookToEdit.setAuthor(bookAuthor);
        bookToEdit.setRetailPrice(newRentPrice);

        if (!Main.bm.updateBook(bookToEdit)) {
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

    public void searchOnPressed(Event evt) {
        // clear the current list items first
        if (bookTableView == null)
            return;
        bookTableView.getItems().clear();
        // load the cache from MemberManager
        Book[] books = Main.bm.getBooKListCache();
        String nameQuery = searchByNameField.getText();
        String authorQuery = searchByAuthorField.getText();

        for (int a = 0; a < Main.bm.getBookCount(); a++) {
            if (!nameQuery.trim().isEmpty()) {
                if (!books[a].getName().equals(nameQuery)) {
                    continue;
                }
            }
            if (!authorQuery.trim().isEmpty()) {
                if (!books[a].getAuthor().equals(authorQuery)) {
                    continue;
                }
            }
            bookTableView.getItems().add(books[a]);
        }
    }

    public void clearOnPressed(Event evt) {
        reloadTableView();
        if (searchByNameField != null) {
            searchByNameField.setText("");
        }
        if (searchByAuthorField != null) {
            searchByAuthorField.setText("");
        }
    }

    public void textFieldOnKeyPressed(Event event) {
        if (((KeyEvent) event).getCode() == KeyCode.ESCAPE) {
            cancelButton(event);
        }
    }
}
