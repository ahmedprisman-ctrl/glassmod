package de.glassmod.mixin;

import de.glassmod.modules.Freecam;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Friert den echten Spieler ein, solange Freecam an ist. */
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerTickMixin {
    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    private void glassmod$freeze(CallbackInfo ci) {
        if (Freecam.active) ci.cancel();
    }
}
