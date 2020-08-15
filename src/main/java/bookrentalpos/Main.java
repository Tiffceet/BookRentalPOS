package bookrentalpos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import my.edu.tarc.dco.bookrentalpos.*;

public class Main extends Application {

    public static DBManager db;
    public static BookManager bm;
    public static StaffManager sm;
    public static MemberManager mm;
    public static TransactionManager tm;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/loginInterface.fxml"));
        primaryStage.setTitle("Login Screen - Huahee Library");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    static void startProgram(String[] args) {
        Main.db = new DBManager();
        Main.bm = new BookManager(db);
        Main.sm = new StaffManager(db);
        Main.mm = new MemberManager(db);
        Main.tm = new TransactionManager(db, bm, mm);
//        bm.addBook(new Book("Book A", "Looz", 92.3));
//        bm.addBook(new Book("Book B", "Looz", 62.3));
//        bm.addBook(new Book("Book C", "Looz", 12.3));
//        mm.registerMember(new Member("010802070131", "Loz"));
//        mm.registerMember(new Member("010802070132", "Loz2"));
//        mm.registerMember(new Member("010802070133", "Loz3"));
//        System.exit(0);
        launch(args);
    }
}
