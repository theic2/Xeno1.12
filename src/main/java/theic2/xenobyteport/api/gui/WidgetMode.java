package theic2.xenobyteport.api.gui;

import theic2.xenobyteport.render.Colors;

public enum WidgetMode {

    INFO(Colors.SKY), SUCCESS(Colors.GREEN), FAIL(Colors.RED);

    private int color;

    WidgetMode(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

}