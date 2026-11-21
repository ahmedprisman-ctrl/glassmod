package de.glassmod.mixin;

import de.glassmod.modules.Freecam;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Shadow protected abstract void setPos(Vec3d pos);

    @Inject(method = "update", at = @At("TAIL"))
    private void glassmod$freecam(BlockView area, Entity focusedEntity, boolean thirdPerson,
                                  boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (Freecam.active && Freecam.pos != null) setPos(Freecam.pos);
    }
}
