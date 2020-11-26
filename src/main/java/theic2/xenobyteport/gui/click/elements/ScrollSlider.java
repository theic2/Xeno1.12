package theic2.xenobyteport.gui.click.elements;

import net.minecraft.client.gui.Gui;
import theic2.xenobyteport.render.Colors;
import theic2.xenobyteport.utils.Delimitter;

public class ScrollSlider extends Button {

    private int processed, shiftFactor;
    private Delimitter counter;

    public ScrollSlider(String text, int value, int max) {
        this(text, value, 1, max);
    }

    public ScrollSlider(String text, int value, int min, int max) {
        super(text, value);
        shiftFactor = (int) Math.ceil((float) (max - min) / 10);
        counter = new Delimitter(value, min, max);
        processSlider(0, false);
    }

    public int processSlider(int step, boolean withShift) {
        buttonValue(processed = counter.calc(withShift ? step * shiftFactor : step));
        return processed;
    }

    private int getSliderY() {
        return getY() + getHeight() - 1;
    }

    private int getSliderX() {
        return getX() + (int) ((float) processed * ((float) getWidth() / (float) counter.getMax()));
    }

    @Override
    public void playClick() {
    }

    @Override
    public void onDraw() {
        super.onDraw();
        Gui.drawRect(getX(), getSliderY(), getMaxX(), getMaxY(), Colors.BLACK);
        Gui.drawRect(getX(), getSliderY(), getSliderX(), getMaxY(), Colors.ORANGE);
    }


}