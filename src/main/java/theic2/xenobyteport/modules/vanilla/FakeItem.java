package theic2.xenobyteport.modules.vanilla;

import net.minecraft.network.play.client.CPacketHeldItemChange;
import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.api.config.Cfg;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.gui.click.elements.Panel;
import theic2.xenobyteport.gui.click.elements.ScrollSlider;

public class FakeItem extends CheatModule {

    @Cfg("slot")
    private int slot;

    public FakeItem() {
        super("FakeItem", Category.PLAYER, PerformMode.TOGGLE);
        slot = 1;
    }

    @Override
    public void onTick(boolean inGame) {
        if (inGame) {
            Xeno.utils.sendPacket(new CPacketHeldItemChange(slot - 1));
        }
    }

    @Override
    public String moduleDesc() {
        return "Игроки будут видеть в руке предмет из заданного слота";
    }

    @Override
    public Panel settingPanel() {
        return new Panel(
                new ScrollSlider("Slot", slot, 9) {
                    @Override
                    public void onScroll(int dir, boolean withShift) {
                        slot = processSlider(dir, withShift);
                    }
                }
        );
    }

}
