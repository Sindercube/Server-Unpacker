package com.sindercube.serverUnpacker.tool;

import com.sindercube.serverUnpacker.util.PackExtractor;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;

public class Presenter {

    private final MainGui screen;

    public Presenter(MainGui screen) {
        this.screen = screen;
    }

    void start() {
        screen.addUploadButtonActionListener(this::actionPerformed);
        screen.setUploadButtonDropTarget(new PresenterDropTarget());
    }

    public void actionPerformed(ActionEvent ae) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                for (File file : fileChooser.getSelectedFiles()) {
                    PackExtractorWorker worker = new PackExtractorWorker(file);
                    worker.addPropertyChangeListener(evt -> {
                        if ("progress".equals(evt.getPropertyName())) {
                            screen.setProgress((Integer) evt.getNewValue());
                        }
                    });
                    worker.execute();
                }
            } catch (Exception exception) {
				ServerUnpackerTool.LOGGER.log(Level.WARNING, "an exception was thrown", exception);
            }
        }
    }

    public class PresenterDropTarget extends DropTarget {

		@SuppressWarnings("unchecked")
        public void drop(DropTargetDropEvent evt) {
            try {
                evt.acceptDrop(DnDConstants.ACTION_COPY);
                List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                evt.dropComplete(true);
                for (File file : droppedFiles) {
                    PackExtractorWorker worker = new PackExtractorWorker(file);
                    worker.addPropertyChangeListener(evt1 -> {
                        if ("progress".equals(evt1.getPropertyName())) {
                            screen.setProgress((Integer) evt1.getNewValue());
                        }
                    });
                    worker.execute();
                }
            } catch (Exception exception) {
				ServerUnpackerTool.LOGGER.log(Level.WARNING, "an exception was thrown", exception);
            }
        }

    }

    private static class PackExtractorWorker extends SwingWorker<Object, Object> {

        private final File file;

        PackExtractorWorker(File file) {
            this.file = file;
        }

        @Override
        protected Object doInBackground() throws Exception {
            AtomicLong totalItemCount = new AtomicLong();
            AtomicLong currentItemCount = new AtomicLong(0);
			PackExtractor.INSTANCE.extractPack(
				file.getParentFile().toPath(),
                file,
				file.getName().replaceFirst("[.][^.]+$", ""),
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
