package theic2.xenobyteport.modules.vanilla;

import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.utils.Keys;

public class Spider extends CheatModule {

    public Spider() {
        super("Spider", Category.PLAYER, PerformMode.TOGGLE);
    }

    @Override
    public void onTick(boolean inGame) {
        if (inGame && Xeno.utils.player().collidedHorizontally && Keys.isPressed(Xeno.utils.mc().gameSettings.keyBindForward)) {
            Xeno.utils.player().motionY = 0.4;
        }
    }

    @Override
    public String moduleDesc() {
        return "Лазание по стенам";
    }

}
