package theic2.xenobyteport.modules.vanilla;

import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;

public class NoRain extends CheatModule {

    public NoRain() {
        super("NoRain", Category.WORLD, PerformMode.TOGGLE);
    }

    @Override
    public void onTick(boolean inGame) {
        if (inGame) {
            utils.world().setRainStrength(0);
        }
    }

    @Override
    public String moduleDesc() {
        return "Когда комп говно";
    }

}
