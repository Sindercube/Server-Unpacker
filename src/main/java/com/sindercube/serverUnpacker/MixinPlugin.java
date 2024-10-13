package com.sindercube.serverUnpacker;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {

	private static final String GAME_VERSION = FabricLoader.getInstance()
		.getModContainer("minecraft")
		.orElseThrow(() -> null)
		.getMetadata()
		.getVersion()
		.getFriendlyString();


	@Override
	public void onLoad(String mixinPackage) {}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String target, String mixin) {
		return true;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

	@Override
	public List<String> getMixins() {
		List<String> result = new ArrayList<>();

		int majorVersion;
		int minorVersion;
		try {
			String[] versions = GAME_VERSION.split("\\.");
			majorVersion = Integer.parseInt(versions[1]);
			minorVersion = Integer.parseInt(versions[2]);
		} catch (ArrayIndexOutOfBoundsException exception) {
			majorVersion = 0;
			minorVersion = 0;
		}
		boolean isModern = majorVersion >= 21 || (majorVersion == 20 && minorVersion >= 3);

		if (isModern) result.add("ModernExtractingMixin");
		else result.add("ClassicExtractingMixin");
		return result;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

}
