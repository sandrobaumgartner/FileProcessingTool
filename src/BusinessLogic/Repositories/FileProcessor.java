package BusinessLogic.Repositories;

import BusinessLogic.Interfaces.FileProcessorInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileProcessor implements FileProcessorInterface {

    private final String delimiter;
    private boolean isCanceled = false;
    private ProgressbarHandler progressbarHandler;

    public FileProcessor(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public HashMap<String, Integer> processFile(File file) throws IOException {
        HashMap<String, Integer> countedWords = new HashMap<>();
        int countMaxLines = countLines(file);
        int countProcessedLines = 0;
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while((line = br.readLine()) != null) {
            if(isCanceled) {
                return new HashMap<>();
            }
            String[] parts = line.split(delimiter);

            for(int i = 0; i < parts.length; i++) {
                if(!countedWords.containsKey(parts[i])) {
                    countedWords.put(parts[i], 1);
                } else {
                    countedWords.put(parts[i], countedWords.get(parts[i]) + 1);
                }
            }
            countProcessedLines++;
            if(this.progressbarHandler != null) {
                updateProgressbar(countMaxLines, countProcessedLines);
            }
        }

        br.close();
        return countedWords;
    }

    @Override
    public LinkedHashMap<String, Integer> sortHashMapByValues(HashMap<String, Integer> hashMap) {
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        hashMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(entry -> sortedMap.put(entry.getKey(), entry.getValue()));
        return sortedMap;
    }

    @Override
    public void setProgressbarHandler(ProgressbarHandler progressbarHandler) {
        this.progressbarHandler = progressbarHandler;
    }

    @Override
    public void updateProgressbar(int maxLines, int processedLines) {
        double percentProgressed = (double) processedLines / maxLines * 100;
        this.progressbarHandler.setValue((int) Math.round(percentProgressed));
    }

    @Override
    public int countLines(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        int countMaxLines = (int) br.lines().count();
        br.close();
        return countMaxLines;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }
}
