package BusinessLogic.Interfaces;

import java.util.HashMap;
import java.util.Map;

public interface TableHandlerInterface {

    void setupTable();
    void fillTable(Map<String, Integer> data);
    void clearTable();
}
