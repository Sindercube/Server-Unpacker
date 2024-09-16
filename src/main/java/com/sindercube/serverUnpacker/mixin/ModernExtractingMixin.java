package com.sindercube.serverUnpacker.mixin;

import com.sindercube.serverUnpacker.ServerUnpacker;
import net.minecraft.client.resource.server.ReloadScheduler;
import net.minecraft.resource.ResourcePackProfile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Pseudo
@Mixin(targets = "net.minecraft.class_1066", remap = false)
public class ModernExtractingMixin {

	@Inject(at = @At("TAIL"), method = "method_55519", remap = false, require = 0)
	public void loadServerPacks(List<ReloadScheduler.PackInfo> packs, CallbackInfoReturnable<List<ResourcePackProfile>> cir) throws IOException {
		packs.stream()
			.map(ReloadScheduler.PackInfo::path)
			.map(Path::toFile)
			.forEach(ServerUnpacker::extractServerPack);
    }

}
