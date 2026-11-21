package de.glassmod;

import net.minecraft.client.MinecraftClient;

public abstract class Module {
    public final String name;
    public boolean enabled;
    protected Module(String name) { this.name = name; }
    public void tick(MinecraftClient mc) {}
    public void onEnable() {}
    public void onDisable() {}
    public void toggle() {
        enabled = !enabled;
        if (enabled) onEnable(); else onDisable();
    }
}
