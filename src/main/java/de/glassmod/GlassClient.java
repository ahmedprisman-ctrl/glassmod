package de.glassmod;

import de.glassmod.modules.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class GlassClient implements ClientModInitializer {
    public static final List<Module> MODULES = List.of(new Freecam(), new Nametags(), new AutoMace());
    private static KeyBinding openGui;

    @Override
    public void onInitializeClient() {
        KeyBinding.Category cat = KeyBinding.Category.create(Identifier.of("glassmod", "main"));
        openGui = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.glassmod.gui", GLFW.GLFW_KEY_F12, cat));

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            while (openGui.wasPressed()) {
                if (mc.currentScreen instanceof GlassScreen) mc.setScreen(null);
                else mc.setScreen(new GlassScreen());
            }
            if (mc.player == null || mc.world == null) return;
            for (Module m : MODULES) if (m.enabled) m.tick(mc);
        });
    }
}
