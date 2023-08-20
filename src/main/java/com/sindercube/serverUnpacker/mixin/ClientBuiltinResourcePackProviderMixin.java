package com.sindercube.serverUnpacker.mixin;

import com.sindercube.serverUnpacker.PackExtractor;
import com.sindercube.serverUnpacker.ServerUnpackerMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.resource.ClientBuiltinResourcePackProvider;
import net.minecraft.resource.ResourcePackSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Mixin(ClientBuiltinResourcePackProvider.class)
public class ClientBuiltinResourcePackProviderMixin {
    @Inject(at = @At("TAIL"), method = "loadServerPack")
    public void loadServerPack(File file, ResourcePackSource rps, CallbackInfoReturnable<CompletableFuture<Void>> cir) throws IOException {
        ServerUnpackerMod.LOGGER.info("Extracting Server Resource Pack: " + file.getName());
        ServerInfo serverInfo = MinecraftClient.getInstance().getCurrentServerEntry();
        if (serverInfo == null) {
            PackExtractor.extractPack(file);
        } else {
            PackExtractor.extractPack(file, serverInfo.address);
        }
    }
}
