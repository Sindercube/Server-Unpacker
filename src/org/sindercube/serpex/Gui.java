package org.sindercube.serpex;

import javax.swing.*;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionListener;

public class Gui {
    private JFrame frame;
    private JProgressBar progressBar;
    private JButton pickButton;

    public Gui() {
        frame = new JFrame("Server Resource Pack Extractor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.add(createMainPanel());
        frame.setVisible(true);
    }
    public void addPickButtonActionListener(ActionListener actionListener) {
        pickButton.addActionListener(actionListener);
    }

    public void setPickButtonDropTarget(DropTarget dropTarget) {
        pickButton.setDropTarget(dropTarget);
    }

    public void setProgress(int progress) {
        progressBar.setValue(progress);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel();

        pickButton = new JButton("Drag 'n Drop or Select File");
        pickButton.setSize(300,100);
        panel.add(pickButton);

        progressBar = new JProgressBar();
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        panel.add(progressBar);

        return panel;
    }
}
