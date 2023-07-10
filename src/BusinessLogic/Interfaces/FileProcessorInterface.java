package BusinessLogic.Interfaces;

import BusinessLogic.Repositories.ProgressbarHandler;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public interface FileProcessorInterface {

    HashMap<String, Integer> processFile(File file) throws IOException;
    LinkedHashMap<String, Integer> sortHashMapByValues(HashMap<String, Integer> hashMap);
    void setProgressbarHandler(ProgressbarHandler progressbarHandler);
    void updateProgressbar(int maxLines, int processedLines);
    int countLines(File file) throws IOException;
}
