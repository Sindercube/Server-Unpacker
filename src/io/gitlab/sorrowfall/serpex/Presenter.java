package io.gitlab.sorrowfall.serpex;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

public class Presenter {
    private PackExtractor packExtractor;
    private Gui gui;

    public Presenter(PackExtractor packExtractor, Gui gui) {
        this.packExtractor = packExtractor;
        this.gui = gui;
    }

    void start() {
        int itemCount = 1; // TODO: get from packExtractor
        gui.setItemCount(itemCount);

        gui.addPickButtonActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        for (File file : fileChooser.getSelectedFiles()) {
                            packExtractor.extractPack(file);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        gui.setPickButtonDropTarget(new DropTarget() {
            public void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>)
                            evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    evt.dropComplete(true);
                    for (File file : droppedFiles) {
                        packExtractor.extractPack(file);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
