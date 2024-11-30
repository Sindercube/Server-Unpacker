package com.sindercube.serverUnpacker.util;

import net.minecraft.client.texture.NativeImage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.LongConsumer;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class NativePackExtractor extends PackExtractor {

	public static final PackExtractor INSTANCE = new NativePackExtractor();

	@Override
	public void writeFile(InputStream stream, File file) throws IOException {
		if (file.getPath().endsWith(".png")) {
			try {
				NativeImage image = NativeImage.read(stream);
				image.writeTo(file);
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		} else {
			super.writeFile(stream, file);
		}
	}

}
