package com.sindercube.serverUnpacker.mixin;

import com.sindercube.serverUnpacker.ServerUnpacker;
import net.minecraft.resource.ResourcePackSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@Pseudo
@Mixin(targets = "net.minecraft.class_1066")
public class ClassicExtractingMixin {

	@Inject(at = @At("TAIL"), method = "method_4638", remap = false)
    public void loadServerPack(File file, ResourcePackSource source, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
		ServerUnpacker.extractServerPack(file);
    }

}
