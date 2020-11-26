package theic2.xenobyteport.api.gui;

import java.awt.*;

public abstract class ColorPicker {

    public int r, g, b, a, rgb, rgba;
    public float rf, gf, bf, af;
    public Color color;

    public ColorPicker(int c) {
        this(new Color(c, true));
    }

    public ColorPicker(Color c) {
        setColor(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
    }

    public void setColor(int r, int g, int b, int a) {
        rgb = new Color(r, g, b).getRGB();
        color = new Color(r, g, b, a);
        rgba = color.getRGB();
        rf = r / 255F;
        gf = g / 255F;
        bf = b / 255F;
        af = a / 255F;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        onColorUpdate();
    }

    protected void onColorUpdate() {
    }

}
