package BusinessLogic.Repositories;

import BusinessLogic.Interfaces.ProgressbarHandlerInterface;

import javax.swing.*;

public class ProgressbarHandler implements ProgressbarHandlerInterface {
    private JProgressBar progressBar;

    public ProgressbarHandler(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setValue(int value) {
        progressBar.setValue(value);
    }
}
