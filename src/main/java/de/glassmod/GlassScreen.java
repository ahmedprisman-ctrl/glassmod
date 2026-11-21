package de.glassmod;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class GlassScreen extends Screen {
    private static final int X = 20, Y = 20, W = 150, ROW = 18;

    public GlassScreen() { super(Text.literal("Glass")); }

    @Override
    public void render(DrawContext ctx, int mx, int my, float delta) {
        int h = 26 + GlassClient.MODULES.size() * ROW;
        ctx.fill(X, Y, X + W, Y + h, 0x99503A78);
        ctx.drawBorder(X, Y, W, h, 0xCCC8B4F0);
        ctx.drawTextWithShadow(textRenderer, "Glass", X + 8, Y + 7, 0xFFFFFFFF);
        int ry = Y + 24;
        for (Module m : GlassClient.MODULES) {
            boolean hover = mx >= X && mx <= X + W && my >= ry && my < ry + ROW;
            if (hover) ctx.fill(X + 2, ry, X + W - 2, ry + ROW, 0x33FFFFFF);
            int col = m.enabled ? 0xFFE0A0FF : 0xFFCCCCCC;
            ctx.drawTextWithShadow(textRenderer, m.name, X + 10, ry + 5, col);
            ry += ROW;
        }
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        int ry = Y + 24;
        for (Module m : GlassClient.MODULES) {
            if (click.x() >= X && click.x() <= X + W && click.y() >= ry && click.y() < ry + ROW) {
                m.toggle();
                return true;
            }
            ry += ROW;
        }
        return super.mouseClicked(click, doubled);
    }

    @Override public boolean shouldPause() { return false; }
}
