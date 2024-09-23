package com.sindercube.serverUnpacker.mixin;

import com.sindercube.serverUnpacker.ServerUnpacker;
import net.minecraft.client.resource.server.ReloadScheduler;
import net.minecraft.client.resource.server.ServerResourcePackLoader;
import net.minecraft.resource.ResourcePackProfile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;
import java.util.List;

@Mixin(ServerResourcePackLoader.class)
public class ModernExtractingMixin {

	@Inject(method = "toProfiles", at = @At("TAIL"))
	public void loadServerPacks(List<ReloadScheduler.PackInfo> packs, CallbackInfoReturnable<List<ResourcePackProfile>> cir) {
		packs.stream()
			.map(ReloadScheduler.PackInfo::path)
			.map(Path::toFile)
			.forEach(ServerUnpacker::extractServerPack);
    }

}
