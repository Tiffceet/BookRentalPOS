package sample;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class YesNoDialogController {

    /**
     * will be 1 if yes event was triggered<br>
     * will be 0 if nothing happened<br>
     * will be -1 if no event was triggered
     */
    public int response;

    public void yesEvent(MouseEvent event) throws IOException {
        this.response = 1;
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }
    public void noEvent(MouseEvent event) throws IOException {
        this.response = -1;
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

}
