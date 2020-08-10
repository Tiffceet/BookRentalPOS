package bookrentalpos;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class BookManagerController {
    public static Stage getWindow;
    public Button backButton;
    public Button addBookButton;
    public Button cancelButton;
    public TextField bookNameField;
    public TextField bookPriceField;
    public TextField bookQuantityField;
    public Button confirmAddButton;
    public Button closeButton;
    public Button editBookButton;
    public Button confirmEditButton;
    public Button cancelDeleteButton;
    public Button confirmDeleteButton;
    public Button deleteBookButton;
    public Label dateTime;

    public void initialize() {
        Clock.display(dateTime);
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
    }

    public void popEditBook() throws IOException {
        Parent editBookParent = FXMLLoader.load(getClass().getResource("/FXML/BookManager/bookManagerEdit.fxml"));
        Stage editBookWindow = new Stage();

        editBookWindow.initModality(Modality.APPLICATION_MODAL);
        editBookWindow.setTitle("Add Book - Huahee Library");
        editBookWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        editBookWindow.setScene(new Scene(editBookParent, 600, 350));
        editBookWindow.showAndWait();
    }

    public void popDeleteBook(MouseEvent event) throws IOException {
        Parent deleteBookParent = FXMLLoader.load(getClass().getResource("/FXML/BookManager/bookManagerDelete.fxml"));
        Stage deleteBookWindow = new Stage();

        deleteBookWindow.initModality(Modality.APPLICATION_MODAL);
        deleteBookWindow.setTitle("Delete Book - Huahee Library");
        deleteBookWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        deleteBookWindow.setScene(new Scene(deleteBookParent, 400, 150));
        deleteBookWindow.showAndWait();
    }

    public void cancelButton(MouseEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void confirmAddButton(MouseEvent event) {
        // Add to database.
        // Need to do validation.
        getWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String bookName = bookNameField.getText();
        String bookPrice = bookPriceField.getText();
        String bookQuantity = bookQuantityField.getText();

        Dialog.alertBox("The book has successfully added!");
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void confirmEditButton(MouseEvent event) throws IOException {
        // Add to database.
        // Need to do validation.
        getWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String bookName = bookNameField.getText();
        String bookPrice = bookPriceField.getText();
        String bookQuantity = bookQuantityField.getText();


        Dialog.alertBox("The book has successfully edited!");
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void confirmBookDelete(MouseEvent event) throws IOException {
        // Delete from database.

        Dialog.alertBox("The book has successfully deleted!");
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }
}
