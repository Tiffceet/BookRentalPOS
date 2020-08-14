package bookrentalpos;

import javafx.event.Event;

/**
 * @author Looz
 * Interface used for controller that invovled TableView
 */
public interface TableInterface {
    void reloadTableView();

    void tableOnClick(Event event);

    void tableOnKeyPressed(Event event);
}
