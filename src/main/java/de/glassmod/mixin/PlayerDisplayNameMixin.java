package de.glassmod.mixin;

import de.glassmod.modules.Nametags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerDisplayNameMixin {
    @Inject(method = "getDisplayName", at = @At("RETURN"), cancellable = true)
    private void glassmod$health(CallbackInfoReturnable<Text> cir) {
        if (!Nametags.active) return;
        PlayerEntity self = (PlayerEntity) (Object) this;
        float hp = self.getHealth() + self.getAbsorptionAmount();
        cir.setReturnValue(Text.empty().append(cir.getReturnValue())
                .append(Text.literal(String.format(" \u2764 %.1f", hp)).styled(s -> s.withColor(0xFF5555))));
    }
}
