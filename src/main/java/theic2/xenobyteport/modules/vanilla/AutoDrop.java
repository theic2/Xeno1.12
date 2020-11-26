package theic2.xenobyteport.modules.vanilla;

import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.api.config.Cfg;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.gui.click.elements.Button;
import theic2.xenobyteport.gui.click.elements.Panel;
import theic2.xenobyteport.gui.click.elements.ScrollSlider;
import theic2.xenobyteport.utils.TickHelper;

public class AutoDrop extends CheatModule {

    @Cfg("allStacks")
    private boolean allStacks;
    @Cfg("delay")
    private int delay;

    public AutoDrop() {
        super("AutoDrop", Category.PLAYER, PerformMode.TOGGLE);
    }

    @Override
    public int tickDelay() {
        return delay;
    }

    @Override
    public void onTick(boolean inGame) {
        if (inGame) {
            Xeno.utils.dropSlot(Xeno.utils.activeSlot(), allStacks);
        }
    }

    @Override
    public String moduleDesc() {
        return "Дроп предмета из активного слота";
    }

    @Override
    public Panel settingPanel() {
        return new Panel(
                new ScrollSlider("Delay", delay, 0, TickHelper.ONE_SEC) {
                    @Override
                    public void onScroll(int dir, boolean withShift) {
                        delay = processSlider(dir, withShift);
                    }

                    @Override
                    public String elementDesc() {
                        return "Задержка дропа предмета";
                    }
                },
                new Button("AllStacks", allStacks) {
                    @Override
                    public void onLeftClick() {
                        buttonValue(allStacks = !allStacks);
                    }

                    @Override
                    public String elementDesc() {
                        return "Дропать весь стак или по одному предмету";
                    }
                }
        );
    }

}