package com.sindercube.serverUnpacker;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.LongConsumer;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PackExtractor {
    public static void extractPack(File pack) throws IOException {
        extractPack(pack, pack.getName());
    }
    public static void extractPack(File pack, String name) throws IOException {
        extractPack(pack, name, (l) -> {}, () -> {});
    }
    public static void extractPack(File pack, LongConsumer itemCountConsumer, Runnable onItemFinished) throws IOException {
        extractPack(pack, pack.getName(), itemCountConsumer, onItemFinished);
    }
    public static void extractPack(File pack, String name, LongConsumer itemCountConsumer, Runnable onItemFinished) throws IOException {
        String originDirectory = "";
        if (pack.getParent() != null) originDirectory += pack.getParent() + "/";
        String destDirectory = originDirectory + name + "-unpacked";

        ZipFile zipFile = new ZipFile(pack.toString());
        itemCountConsumer.accept(zipFile.size());

        Stream<? extends ZipEntry> zipStream = zipFile.stream().parallel();

        zipStream.forEach(zipEntry -> {
            File newFile = new File(destDirectory, zipEntry.getName());
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
                    onItemFinished.run();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
