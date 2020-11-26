package theic2.xenobyteport.modules;

import org.lwjgl.input.Keyboard;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.api.module.PerformSource;
import theic2.xenobyteport.gui.click.XenoGuiScreen;

public class XenoGui extends CheatModule {

    public XenoGui() {
        super("XenoGui", Category.NONE, PerformMode.SINGLE);
        setKeyBind(Keyboard.KEY_B);
    }

    @Override
    public void onPerform(PerformSource type) {
        utils.openGui(new XenoGuiScreen(moduleHandler()), true);
    }

    @Override
    public boolean allowStateMessages() {
        return false;
    }

}