package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertBox {

    public static void display(String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UTILITY);
        window.setTitle("");
        window.setResizable(false);

        Label label = new Label(message);
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(1.7976931348623157E308);
        label.setFont(Font.font("Bodoni MT", 20));
        VBox.setMargin(label, new Insets(5, 0, 0, 0));

        Button closeButton = new Button("Ok");
        closeButton.setPrefWidth(60.0);
        closeButton.setOnAction(event -> window.close());
        VBox.setMargin(closeButton, new Insets(15, 0, 0, 170));

        VBox layout = new VBox(0);
        layout.setPrefSize(400, 100);
        layout.getStyleClass().add("upper");
        layout.getChildren().addAll(label, closeButton);

        Scene scene = new Scene(layout, 400, 100);
        scene.getStylesheets().add("@../../ManagerStyle.css");
        window.setScene(scene);
        window.showAndWait();
    }

}
