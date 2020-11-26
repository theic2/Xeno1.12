package theic2.xenobyteport.modules.vanilla;

import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;

public class AutoSprint extends CheatModule {

    public AutoSprint() {
        super("AutoSprint", Category.PLAYER, PerformMode.TOGGLE);
    }

    @Override
    public void onTick(boolean inGame) {
        if (inGame) {
            Xeno.utils.player().setSprinting(true);
        }
    }

    @Override
    public String moduleDesc() {
        return "Постоянный спринт";
    }

}
