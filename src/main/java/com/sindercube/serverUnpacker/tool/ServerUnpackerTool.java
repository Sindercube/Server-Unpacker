package com.sindercube.serverUnpacker.tool;

import com.sindercube.serverUnpacker.util.PackExtractor;

import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerUnpackerTool implements Runnable {

	public static final Logger LOGGER = Logger.getLogger("Server Unpacker");

    public static void main(String[] args) {
		LOGGER.info("Started!");
        if (args.length < 1) EventQueue.invokeLater(new ServerUnpackerTool());
        for (String filePath : args) {
            try {
				File file = new File(filePath);
				String name = file.getName().replaceFirst("[.][^.]+$", "");
                PackExtractor.extractPack(file.getParentFile().toPath(), file, name);
            } catch (Exception exception) {
				LOGGER.log(Level.WARNING, "an exception was thrown", exception);
            }
        }
    }

    @Override
    public void run() {
        Presenter presenter = new Presenter(new MainGui());
        presenter.start();
    }

}
