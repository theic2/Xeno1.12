package theic2.xenobyteport.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class GuiScaler {

    private static ScaledResolution resolution;
    private static int mouseX, mouseY;

    public static boolean isGuiCreated() {
        return resolution != null;
    }

    public static int mouseX() {
        return mouseX;
    }

    public static int mouseY() {
        return mouseY;
    }

    public static int scaleFactor() {
        return resolution.getScaleFactor();
    }

    public static int screenHeight() {
        return resolution.getScaledHeight();
    }

    public static int screenWidth() {
        return resolution.getScaledWidth();
    }

    public static int scaledScreenWidth() {
        return scaledValue(resolution.getScaledWidth());
    }

    public static int scaledScreenHeight() {
        return scaledValue(resolution.getScaledHeight());
    }

    public static int scaledValue(int in) {
        return (int) (in / guiElementScale());
    }

    public static double guiElementScale() {
        return 2.0D / scaleFactor();
    }

    public static void setGuiScale() {
        GlStateManager.scale(guiElementScale(), guiElementScale(), 0);
    }

    public static void setOnTop() {
        GlStateManager.translate(0, 0, 999);
    }

    public static void updateResolution() {
        resolution = new ScaledResolution(Minecraft.getMinecraft());
    }

    public static void updateMouse(int mX, int mY) {
        mouseX = scaledValue(mX);
        mouseY = scaledValue(mY);
    }

}