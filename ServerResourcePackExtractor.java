package io.github.serverpackextractor;

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

public class ServerResourcePackExtractor {
    public void ExtractResourcePack(String zipFilePath, String destDirectory) throws IOException {
        ZipFile zipFile = new ZipFile(zipFilePath);
        Enumeration<? extends ZipEntry> zipEnum = zipFile.entries();
        zipEnum.nextElement();
        while (zipEnum.hasMoreElements()) {
            ZipEntry zipEntry = zipEnum.nextElement();
            File newFile = new File(destDirectory, zipEntry.getName());

            System.out.println(newFile);

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

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Not enough arguments");
            return;
        }
        ServerResourcePackExtractor serverPackExtractor = new ServerResourcePackExtractor();
        for (String packPath : args) {
            try {
                Path p = Paths.get(packPath);
                String packName = p.getFileName().toString();
                serverPackExtractor.ExtractResourcePack(p.toString(), "./extracted/" + packName);
            }
            catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
 }
