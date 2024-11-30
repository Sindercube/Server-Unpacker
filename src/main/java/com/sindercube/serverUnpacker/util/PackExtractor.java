package com.sindercube.serverUnpacker.util;

import java.io.*;
import java.nio.file.Path;
import java.util.function.LongConsumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PackExtractor {

	public static final PackExtractor INSTANCE = new PackExtractor();

	public void extractPack(Path destination, File pack, String name) throws IOException {
        extractPack(destination, pack, name, c -> {}, () -> {});
    }

    public void extractPack(Path destination, File pack, String name, LongConsumer itemCountConsumer, Runnable onItemFinished) {
		try {
			ZipFile zip = new ZipFile(pack.toString());
			itemCountConsumer.accept(zip.size());

			for (ZipEntry entry : zip.stream().toList()) {
				File newFile = new File(destination.resolve(name).toFile(), entry.getName());
				if (entry.isDirectory()) return;

				newFile.getParentFile().mkdirs();
				BufferedInputStream inputStream = new BufferedInputStream(zip.getInputStream(entry));
				this.writeFile(inputStream, newFile);
				inputStream.close();
				onItemFinished.run();
			}
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
    }

	public void writeFile(InputStream stream, File file) throws IOException {
		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			byte[] buffer = new byte[4096];
			int read;
			while ((read = stream.read(buffer)) >= 0) {
				outputStream.write(buffer, 0, read);
			}
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

}
