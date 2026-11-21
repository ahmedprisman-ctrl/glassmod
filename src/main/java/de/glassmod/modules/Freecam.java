package de.glassmod.modules;

import de.glassmod.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

public class Freecam extends Module {
    public static boolean active;
    public static Vec3d pos;

    public Freecam() { super("Freecam"); }

    @Override
    public void onEnable() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player != null) pos = mc.player.getEyePos();
        active = pos != null;
    }

    @Override
    public void tick(MinecraftClient mc) {
        if (mc.currentScreen != null || pos == null) return;
        float yaw = mc.player.getYaw();
        float pitch = mc.player.getPitch();
        Vec3d look = Vec3d.fromPolar(pitch, yaw);
        Vec3d right = Vec3d.fromPolar(0, yaw + 90);
        Vec3d move = Vec3d.ZERO;
        if (mc.options.forwardKey.isPressed()) move = move.add(look);
        if (mc.options.backKey.isPressed()) move = move.subtract(look);
        if (mc.options.rightKey.isPressed()) move = move.add(right);
        if (mc.options.leftKey.isPressed()) move = move.subtract(right);
        if (mc.options.jumpKey.isPressed()) move = move.add(0, 1, 0);
        if (mc.options.sneakKey.isPressed()) move = move.add(0, -1, 0);
        if (move.lengthSquared() > 0) pos = pos.add(move.normalize().multiply(0.6));
    }

    @Override
    public void onDisable() { active = false; pos = null; }
}
