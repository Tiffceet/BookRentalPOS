package bookrentalpos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import my.edu.tarc.dco.bookrentalpos.*;

import java.time.LocalDate;

public class Main extends Application {

    public static DBManager db;
    public static BookManager bm;
    public static StaffManager sm;
    public static MemberManager mm;
    public static TransactionManager tm;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/loginInterface.fxml"));
        primaryStage.setTitle("Login Screen - HuaheeCheh");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void loadDB() {
        Main.db = new DBManager();
        Main.bm = new BookManager(db);
        Main.sm = new StaffManager(db);
        Main.mm = new MemberManager(db);
        Main.tm = new TransactionManager(db, bm, mm);
    }

    static void startProgram(String[] args) {
        loadDB();
        launch(args);
    }
}
