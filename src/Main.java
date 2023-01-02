import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.awt.dnd.DropTarget;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main {

    static JProgressBar bar;

    public static void extractPack(File pack) throws IOException {

        String destDirectory = pack.getParent() + "/" + pack.getName();

        ZipFile zipFile = new ZipFile(pack.toString());
        Enumeration<? extends ZipEntry> zipEnum = zipFile.entries();
        zipEnum.nextElement();

        int i = 0;

        try {
            bar.setMaximum(zipFile.size()); // TODO: get this to actually work
            bar.setValue(0);
        } catch (Exception e) {
            System.out.println("test");
        }

        while (zipEnum.hasMoreElements()) {

            i += 1;

            try {
                bar.setValue(i);
            } catch (Exception e) {}

            ZipEntry zipEntry = zipEnum.nextElement();
            File newFile = new File(destDirectory+"-extracted", zipEntry.getName());

            //System.out.println(newFile);

            // create sub directories
            newFile.getParentFile().mkdirs();

            if (!zipEntry.isDirectory()) {
                try (FileOutputStream outputStream = new FileOutputStream(newFile)) {
                    BufferedInputStream inputStream = new BufferedInputStream(zipFile.getInputStream(zipEntry));
                    String filepath = newFile.toString();

                    byte[] buffer = new byte[4096];
                    int read;
                    while ((read = inputStream.read(buffer)) >= 0) {
                        outputStream.write(buffer, 0, read);
                    }
                    inputStream.close();
                }
            }
        }
    }



    public static void openGUI() {

        JFrame frame = new JFrame("Server Resource Pack Extractor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,300);

        JPanel panel = new JPanel();

        JButton button = new JButton("Drag 'n Drop or Select File");
        frame.setSize(300,100);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        for (File file : fileChooser.getSelectedFiles()) {
                            extractPack(file); 
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        panel.add(button);


        bar = new JProgressBar();
        bar.setValue(0);
        bar.setStringPainted(true);
        panel.add(bar);


        frame.setDropTarget(new DropTarget() {

            public synchronized void drop(DropTargetDropEvent evt) {

                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>)
                        evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {

                        extractPack(file);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        frame.setVisible(true);
        frame.add(panel);

    }

    public static void main(String[] args) {

        if (args.length < 1) { // open the GUI without any arguments
            openGUI();
            return;
        }

        for (String fileName : args) {

            try {
                extractPack(new File(fileName));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

}
