package com.sindercube.serverUnpacker.mixin;

import com.sindercube.serverUnpacker.ServerUnpackerMod;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(TitleScreen.class)
public class InitMixin {
	@Unique
	private static boolean LOADED = false;
	@Inject(at = @At("HEAD"), method = "init")
	private void init(CallbackInfo i) {
		if (LOADED) return;
		ServerUnpackerMod.LOGGER.info("Server Unpacker Initialized!");
		LOADED = true;
	}
}