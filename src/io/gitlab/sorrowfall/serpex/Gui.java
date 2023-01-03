package io.gitlab.sorrowfall.serpex;

import javax.swing.*;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionListener;

public class Gui {
    private JFrame frame;
    private JProgressBar progressBar;
    private JButton pickButton;
    private int completedItems = 0;

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

    public void setItemCount(int itemCount)
    {
        progressBar.setMaximum(itemCount);
    }
    public synchronized void completeItem() {
        ++completedItems;
        progressBar.setValue(completedItems);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel();

        pickButton = new JButton("Drag 'n Drop or Select File");
        pickButton.setSize(300,100);
        panel.add(pickButton);

        progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        panel.add(progressBar);

        return panel;
    }
}
