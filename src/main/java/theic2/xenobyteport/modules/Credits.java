package theic2.xenobyteport.modules;

import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.api.module.PerformSource;
import theic2.xenobyteport.gui.swing.InfoGui;

public class Credits extends CheatModule {

    public Credits() {
        super("Credits", Category.MISC, PerformMode.SINGLE);
    }

    @Override
    public void onPerform(PerformSource src) {
        new InfoGui().showFrame();
    }

    @Override
    public String moduleDesc() {
        return "Информация о продукте + ссылки";
    }

}
