package theic2.xenobyteport.modules.vanilla;

import theic2.xenobyteport.api.config.Cfg;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.gui.click.elements.Panel;
import theic2.xenobyteport.gui.click.elements.ScrollSlider;

public class Step extends CheatModule {

    @Cfg("stepHeight")
    private int stepHeight;

    public Step() {
        super("Step", Category.PLAYER, PerformMode.TOGGLE);
        stepHeight = 1;
    }

    @Override
    public void onTick(boolean inGame) {
        if (inGame) {
            utils.player().stepHeight = stepHeight;
        }
    }

    @Override
    public void onDisabled() {
        utils.player().stepHeight = 0.5F;
    }

    @Override
    public String moduleDesc() {
        return "Мгновенное взбирание на заданное количество блоков";
    }

    @Override
    public Panel settingPanel() {
        return new Panel(
                new ScrollSlider("Height", stepHeight, 64) {
                    @Override
                    public void onScroll(int dir, boolean withShift) {
                        stepHeight = processSlider(dir, withShift);
                    }
                }
        );
    }

}
