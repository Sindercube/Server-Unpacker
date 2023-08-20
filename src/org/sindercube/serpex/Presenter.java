package org.sindercube.serpex;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Presenter {
    private PackExtractor packExtractor;
    private Gui gui;

    public Presenter(PackExtractor packExtractor, Gui gui) {
        this.packExtractor = packExtractor;
        this.gui = gui;
    }

    void start() {
        gui.addPickButtonActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        for (File file : fileChooser.getSelectedFiles()) {
                            PackExtractorWorker worker = new PackExtractorWorker(packExtractor, file);
                            worker.addPropertyChangeListener(new PropertyChangeListener() {
                                @Override
                                public void propertyChange(PropertyChangeEvent evt) {
                                    if ("progress".equals(evt.getPropertyName())) {
                                        gui.setProgress((Integer) evt.getNewValue());
                                    }
                                }
                            });
                            worker.execute();
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
                        PackExtractorWorker worker = new PackExtractorWorker(packExtractor, file);
                        worker.addPropertyChangeListener(new PropertyChangeListener() {
                            @Override
                            public void propertyChange(PropertyChangeEvent evt) {
                                if ("progress".equals(evt.getPropertyName())) {
                                    gui.setProgress((Integer) evt.getNewValue());
                                }
                            }
                        });
                        worker.execute();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class PackExtractorWorker extends SwingWorker<Object, Object> {
        PackExtractor packExtractor;
        File file;

        PackExtractorWorker(PackExtractor packExtractor, File file) {
            this.packExtractor = packExtractor;
            this.file = file;
        }

        @Override
        protected Object doInBackground() throws Exception {
            AtomicLong totalItemCount = new AtomicLong();
            AtomicLong currentItemCount = new AtomicLong(0);
            packExtractor.extractPack(
                file,
                totalItemCount::set,
                () -> {
                    final long currentCount = currentItemCount.incrementAndGet();
                    final float percentComplete = (float)currentCount / totalItemCount.get();
                    setProgress((int)(percentComplete * 100));
                }
            );
            return null;
        }
    }
}
