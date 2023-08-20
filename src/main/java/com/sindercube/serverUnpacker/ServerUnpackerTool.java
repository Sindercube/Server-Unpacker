package com.sindercube.serverUnpacker;

import java.awt.*;
import java.io.File;

public class ServerUnpackerTool implements Runnable {
    public static void main(String[] args) {
        if (args.length < 1) EventQueue.invokeLater(new ServerUnpackerTool());
        for (String fileName : args) {
            try {
                PackExtractor.extractPack(new File(fileName), fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void run() {
        Presenter presenter = new Presenter(new Gui());
        presenter.start();
    }
}
