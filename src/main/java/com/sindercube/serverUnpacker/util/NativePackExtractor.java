package com.sindercube.serverUnpacker.util;

import net.minecraft.client.texture.NativeImage;

import java.io.*;

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
