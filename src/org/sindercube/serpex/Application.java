package org.sindercube.serpex;

import java.awt.EventQueue;
import java.io.File;

public class Application implements Runnable {
    @Override
    public void run() {
        PackExtractor packExtractor = new PackExtractor();
        Gui gui = new Gui();
        Presenter presenter = new Presenter(packExtractor, gui);
        presenter.start();
    }

    public static void main(String[] args) {
        if (args.length < 1) { // open the GUI without any arguments
            EventQueue.invokeLater(new Application());
        }

        for (String fileName : args) {
            try {
                PackExtractor packExtractor = new PackExtractor();
                packExtractor.extractPack(new File(fileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
