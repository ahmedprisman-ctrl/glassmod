package de.glassmod.modules;

import de.glassmod.Module;

public class Nametags extends Module {
    public static boolean active;
    public Nametags() { super("NameTags"); }
    @Override public void onEnable() { active = true; }
    @Override public void onDisable() { active = false; }
}
