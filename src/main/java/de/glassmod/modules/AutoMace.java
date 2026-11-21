package de.glassmod.modules;

import de.glassmod.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/** Nur Singleplayer und nur Fake-Player-Bots (Name beginnt mit "bot_", z.B. Carpet: /player bot_1 spawn). */
public class AutoMace extends Module {
    private static final double RANGE = 4.5;
    private static final String BOT_PREFIX = "bot_";

    public AutoMace() { super("Auto Mace"); }

    @Override
    public void tick(MinecraftClient mc) {
        if (!mc.isInSingleplayer()) return;
        if (!mc.player.getMainHandStack().isOf(Items.MACE)) return;

        PlayerEntity target = null;
        double best = RANGE * RANGE;
        for (PlayerEntity p : mc.world.getPlayers()) {
            if (p == mc.player || !p.isAlive()) continue;
            if (!p.getName().getString().toLowerCase().startsWith(BOT_PREFIX)) continue;
            double d = mc.player.squaredDistanceTo(p);
            if (d < best) { best = d; target = p; }
        }
        if (target == null) return;

        Vec3d diff = target.getEyePos().subtract(mc.player.getEyePos());
        float yaw = (float) Math.toDegrees(Math.atan2(diff.z, diff.x)) - 90f;
        float pitch = (float) -Math.toDegrees(Math.atan2(diff.y, Math.hypot(diff.x, diff.z)));
        mc.player.setYaw(MathHelper.lerpAngleDegrees(0.35f, mc.player.getYaw(), yaw));
        mc.player.setPitch(MathHelper.lerp(0.35f, mc.player.getPitch(), pitch));

        if (mc.player.getAttackCooldownProgress(0f) >= 1f) {
            mc.interactionManager.attackEntity(mc.player, target);
            mc.player.swingHand(Hand.MAIN_HAND);
        }
    }
}
