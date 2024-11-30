package com.sindercube.serverUnpacker;

import com.sindercube.serverUnpacker.util.NativePackExtractor;
import com.sindercube.serverUnpacker.util.PackExtractor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;

public class ServerUnpacker implements ClientModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("Server Unpacker");

	@Override
	public void onInitializeClient() {
		ServerUnpacker.LOGGER.info("Initialized!");
	}


	public static void extractServerPack(File file) {
		ServerUnpacker.LOGGER.info("Extracting server pack {}", file);

		var info = MinecraftClient.getInstance().getCurrentServerEntry();
		String name = info == null ? file.getName() : info.address;
		Path destination = FabricLoader.getInstance().getGameDir().resolve("extracted-packs/");
		try {
			NativePackExtractor.INSTANCE.extractPack(destination, file, name);
		} catch (Exception exception) {
			LOGGER.error(exception);
		}
	}

}
