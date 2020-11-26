package theic2.xenobyteport.gui.click.elements;

import net.minecraft.client.gui.Gui;
import theic2.xenobyteport.api.gui.ElementAligment;
import theic2.xenobyteport.api.gui.TextElement;
import theic2.xenobyteport.api.gui.WidgetMode;
import theic2.xenobyteport.render.Colors;

public class GuiWidget extends TextElement {

    private static int indicatorWidth = 2;
    private final int bgColor;
    public int delay;
    private ElementAligment aligment;
    private WidgetMode mode;

    public GuiWidget(String text, WidgetMode mode, ElementAligment indicatorAligment, int bgColor, int delay) {
        super(text, indicatorAligment == ElementAligment.LEFT ? ElementAligment.RIGHT : ElementAligment.LEFT, indicatorWidth + (indicatorAligment == ElementAligment.LEFT ? 1 : 0), 0);
        aligment = indicatorAligment;
        this.bgColor = bgColor;
        this.delay = delay;
        this.mode = mode;
    }

    @Override
    public void draw() {
        Gui.drawRect(getX(), getY(), getMaxX(), getMaxY(), bgColor);
        int indicatorX = aligment == ElementAligment.LEFT ? getX() : getMaxX() - indicatorWidth;
        Gui.drawRect(indicatorX, getY(), indicatorX + indicatorWidth, getMaxY(), mode.getColor());
        render.GUI.xenoFont().drawString(getText(), getTextX(), getY(), Colors.WHITE);
    }

}