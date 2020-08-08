package bookrentalpos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import my.edu.tarc.dco.bookrentalpos.*;

public class Main extends Application {

    static DBManager db;
    static BookManager bm;
    static StaffManager sm;
    static MemberManager mm;
    static TransactionManager tm;
///123
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/loginInterface.fxml"));
        primaryStage.setTitle("Login Screen - Huahee Library");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        db = new DBManager();
        bm = new BookManager(db);
        sm = new StaffManager(db);
        mm = new MemberManager(db);
        tm = new TransactionManager(db, bm);
        launch(args);
    }
}
