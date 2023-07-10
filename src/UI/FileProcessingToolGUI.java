package UI;

import BusinessLogic.Repositories.FileProcessor;
import BusinessLogic.Repositories.ProgressbarHandler;
import BusinessLogic.Repositories.TableHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class FileProcessingToolGUI extends JFrame {
    private final FileProcessor fileProcessor = new FileProcessor(" ");
    private final DefaultTableModel resultTableModel = new DefaultTableModel();
    private final JFileChooser fileChooser = new JFileChooser();
    private ProgressbarHandler progressbarHandler;
    private TableHandler tableHandler;
    private File uploadedFile;
    private JPanel mainPanel;
    private JTextField fileNameTextField;
    private JButton uploadButton;
    private JTable resultTable;
    private JButton processButton;
    private JButton cancelButton;
    private JProgressBar fileProgressbar;

    public FileProcessingToolGUI() {

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files (*.txt) ", "txt");
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showOpenDialog(mainPanel);

                if (result == JFileChooser.APPROVE_OPTION) {
                    uploadedFile = fileChooser.getSelectedFile();
                    if(!uploadedFile.getName().endsWith(".txt")) {
                        JOptionPane.showMessageDialog(mainPanel, "This program only supports .txt files", "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        fileNameTextField.setText(uploadedFile.getName());
                        processButton.setEnabled(true);
                        cancelButton.setEnabled(true);
                    }
                }
            }
        });

        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileProcessor.setCanceled(false);
                tableHandler.clearTable();
                Thread thread = new Thread(() -> {
                    HashMap<String, Integer> resultHashMap = null;
                    try {
                        progressbarHandler.setValue(0);
                        resultHashMap = fileProcessor.processFile(uploadedFile);
                        LinkedHashMap<String, Integer> sortedResultMap = fileProcessor.sortHashMapByValues(resultHashMap);
                        tableHandler.fillTable(sortedResultMap);
                    } catch (IOException ioException) {
                        throw new RuntimeException(ioException);
                    } catch (OutOfMemoryError outOfMemoryError) {
                        JOptionPane.showMessageDialog(mainPanel,
                                "Please assign more RAM to this program!",
                                "Out of memory error!",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                thread.start();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileProcessor.setCanceled(true);
                tableHandler.clearTable();
            }
        });
    }

    public void setup() {
        this.setContentPane(this.mainPanel);
        this.setSize(600, 400);
        this.setTitle("File Processing Tool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.tableHandler = new TableHandler(resultTable, resultTableModel);
        this.progressbarHandler = new ProgressbarHandler(fileProgressbar);
        this.fileProcessor.setProgressbarHandler(progressbarHandler);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        FileProcessingToolGUI gui = new FileProcessingToolGUI();
        gui.setup();
    }
}
