package com.sindercube.serverUnpacker.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.LongConsumer;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PackExtractor {

    public static void extractPack(Path destination, File pack, String name) throws IOException {
        extractPack(destination, pack, name, c -> {}, () -> {});
    }

    public static void extractPack(Path destination, File pack, String name, LongConsumer itemCountConsumer, Runnable onItemFinished) throws IOException {
        ZipFile zipFile = new ZipFile(pack.toString());
        itemCountConsumer.accept(zipFile.size());

        Stream<? extends ZipEntry> zipStream = zipFile.stream().parallel();

        zipStream.forEach(zipEntry -> {
			File newFile = new File(destination.resolve(name).toFile(), zipEntry.getName());
            newFile.getParentFile().mkdirs();

            if (!zipEntry.isDirectory()) {
                try (FileOutputStream outputStream = new FileOutputStream(newFile)) {
                    BufferedInputStream inputStream = new BufferedInputStream(zipFile.getInputStream(zipEntry));

                    byte[] buffer = new byte[4096];
                    int read;
                    while ((read = inputStream.read(buffer)) >= 0) {
                        outputStream.write(buffer, 0, read);
                    }
                    inputStream.close();
                    onItemFinished.run();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
