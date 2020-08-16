package bookrentalpos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Dialog {
    private static boolean choice = false;


    /**
     * Alert Box.<br>
     * Recommended Usage: <code>Dialog.alertBox("message");</code>
     *
     * @param message pop up message to show to user
     */
    public static void alertBox(String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UTILITY);
        window.setTitle("");
        window.setResizable(false);

        Label label = new Label(message);
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setFont(Font.font("Berlin Sans FB", 20));
        label.setWrapText(true);
        VBox.setMargin(label, new Insets(10, 5, 0, 5));
        label.setFocusTraversable(false);

        Button closeButton = new Button("Ok");
        closeButton.setPrefWidth(60.0);
        closeButton.setOnAction(event -> window.close());
        closeButton.setDefaultButton(true);
        VBox.setMargin(closeButton, new Insets(15, 0, 0, 170));

        VBox layout = new VBox(0);
        int windowHeight = 100 + (message.length() / 41) * 30;
        layout.setPrefSize(400, windowHeight);
        layout.getStyleClass().add("upper");
        layout.getChildren().addAll(label, closeButton);

        Scene scene = new Scene(layout, 400, windowHeight);
        scene.getStylesheets().add("/ManagerStyle.css");
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * Confirmation Box<br>
     * Recommended Usage: <code>choice = Dialog.confirmBox("message");</code>
     *
     * @param message message you wish to put on the confirmation box
     * @return true if user answered yes, false if no
     */
    public static boolean confirmBox(String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UTILITY);
        window.setTitle("");
        window.setResizable(false);

        // Set choice to false to make sure it is false when click close button.
        choice = false;

        // Upper layout.
        HBox upperLayout = new HBox();
        upperLayout.getStyleClass().add("upper");
        Label label = new Label(message);
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Berlin Sans FB", 20));
        label.setWrapText(true);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        HBox.setMargin(label, new Insets(5, 5, 0, 5));
        upperLayout.getChildren().add(label);

        // Lower layout.
        GridPane lowerLayout = new GridPane();
        lowerLayout.getColumnConstraints().add(new ColumnConstraints(230));
        lowerLayout.getColumnConstraints().add(new ColumnConstraints(230));
        lowerLayout.getRowConstraints().add(new RowConstraints(75));

        Button cancel = new Button("Cancel");
        cancel.setPrefWidth(80);
        cancel.setOnAction(event -> {
            choice = false;
            window.close();
        });
        cancel.setFocusTraversable(false);


        Button okay = new Button("Okay");
        okay.setPrefWidth(80);
        okay.setOnAction(event -> {
            choice = true;
            window.close();
        });
        okay.setDefaultButton(true);

        GridPane.setMargin(cancel, new Insets(0, 0, 0, 75));
        GridPane.setMargin(okay, new Insets(0, 0, 0, 75));
        lowerLayout.add(okay, 1, 0);
        lowerLayout.add(cancel, 0, 0);



        // Set both to layout.
        int expendHeight = (message.length() / 46) * 30;

        GridPane layout = new GridPane();
        layout.getColumnConstraints().add(new ColumnConstraints(460));
        layout.getRowConstraints().add(new RowConstraints(75 + expendHeight));
        layout.getRowConstraints().add(new RowConstraints(75));

        layout.add(upperLayout, 0, 0);
        layout.add(lowerLayout, 0, 1);

        Scene scene = new Scene(layout, 460, 150 + expendHeight);
        scene.getStylesheets().add("/ManagerStyle.css");
        window.setScene(scene);
        window.showAndWait();

        return choice;
    }
}
