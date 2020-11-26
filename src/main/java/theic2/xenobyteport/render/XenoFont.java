package theic2.xenobyteport.render;

import net.minecraft.client.renderer.GlStateManager;
import theic2.xenobyteport.api.Xeno;

import java.awt.*;

public class XenoFont {

    private int rows, charWidth, fontHeight, shadowShift;
    private String letters;
    private int[][] poses;

    public XenoFont() {
        letters = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~ЁёАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя";
        poses = new int[fontLetters().length()][2];
        shadowShift = 2;
        fontHeight = 26;
        charWidth = 13;
        rows = 19;
        for (int ch = 0; ch < fontLetters().length(); ch++) {
            poses[ch][0] = (ch % rows) * charWidth;
            poses[ch][1] = (ch / rows) * fontHeight;
        }
    }

    public int textWidth(String text) {
        int outLen = 0;
        for (char ch : text.toCharArray()) {
            if (fontLetters().indexOf(ch) > -1) {
                outLen++;
            }
        }
        return ((outLen * charWidth) / 2) + 1;
    }

    public int fontHeight() {
        return fontHeight / 2;
    }

    public String fontLetters() {
        return letters;
    }

    public void drawString(String text, int x, int y, int color) {
        int sub = 0;
        float[] rgba = new Color(color, true).getRGBComponents(null);
        GlStateManager.bindTexture(Textures.FONT);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.scale(0.5F, 0.5F, 0);
        for (char ch : text.toCharArray()) {
            int index = fontLetters().indexOf(ch);
            if (index > -1) {
                int[] pos = poses[index];
                int xStart = x * 2 + sub;
                int yStart = y * 2;
                GlStateManager.color(0, 0, 0, rgba[3]);
                Xeno.render.GUI.drawTexturedModalRect(xStart + shadowShift, yStart + shadowShift, pos[0], pos[1], charWidth, fontHeight);
                GlStateManager.color(rgba[0], rgba[1], rgba[2], rgba[3]);
                Xeno.render.GUI.drawTexturedModalRect(x * 2 + sub, y * 2, pos[0], pos[1], charWidth, fontHeight);
                sub += charWidth;
            }
        }
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();

    }

}