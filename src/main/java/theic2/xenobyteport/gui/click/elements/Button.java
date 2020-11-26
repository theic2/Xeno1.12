package theic2.xenobyteport.gui.click.elements;

import net.minecraft.client.gui.Gui;
import net.minecraft.init.SoundEvents;
import theic2.xenobyteport.api.gui.ElementAligment;
import theic2.xenobyteport.api.gui.TextElement;
import theic2.xenobyteport.render.Colors;

public class Button extends TextElement {

    private int bgColor, hoverColor, selectColor, textColor, selectTextColor, hoverTextColor;
    private String value;

    public Button(String text) {
        this(text, null);
    }

    public Button(String text, Object value) {
        this(text, value, ElementAligment.CENTER, Colors.NONE, Colors.SKY, Colors.SKY, Colors.SKY, Colors.WHITE, Colors.WHITE);
    }

    public Button(String text, Object value, ElementAligment aligment, int bgColor, int hoverColor, int selectColor, int textColor, int selectTextColor, int hoverTextColor) {
        super(text, aligment, 4, 2);
        this.selectTextColor = selectTextColor;
        this.hoverTextColor = hoverTextColor;
        this.selectColor = selectColor;
        this.hoverColor = hoverColor;
        this.textColor = textColor;
        this.bgColor = bgColor;
        buttonValue(value);
    }

    public void buttonValue(Object value) {
        if (value instanceof Boolean) {
            setSelected((Boolean) value);
            this.value = null;
        } else {
            this.value = value != null ? "[" + value + "]" : null;
        }
    }

    public void playClick() {
        utils.playSound(SoundEvents.UI_BUTTON_CLICK);
    }

    @Override
    public boolean click(int key) {
        if (isHovered() && key == 0) {
            playClick();
        }
        return super.click(key);
    }

    @Override
    public void onDraw() {
        Gui.drawRect(getX(), getY(), getMaxX(), getMaxY(), isSelected() ? selectColor : isHovered() ? hoverColor : bgColor);
        String out = value != null && isHovered() ? value : getText();
        render.GUI.xenoFont().drawString(out, getTextX(out), getTextY(), isSelected() ? selectTextColor : isHovered() ? hoverTextColor : textColor);
    }

    @Override
    public String toString() {
        return getText().concat(value == null ? "" : value);
    }

}