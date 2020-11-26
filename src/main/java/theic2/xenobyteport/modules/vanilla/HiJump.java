package theic2.xenobyteport.modules.vanilla;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.api.config.Cfg;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.gui.click.elements.Panel;
import theic2.xenobyteport.gui.click.elements.ScrollSlider;
import theic2.xenobyteport.utils.TickHelper;

public class HiJump extends CheatModule {

    @Cfg("power")
    private int power;

    public HiJump() {
        super("HiJump", Category.PLAYER, PerformMode.TOGGLE);
        power = 5;
    }

    private void removePotion() {
        Xeno.utils.player().removePotionEffect(MobEffects.JUMP_BOOST);
    }

    @Override
    public void onDisabled() {
        removePotion();
    }

    @Override
    public int tickDelay() {
        return TickHelper.ONE_SEC;
    }

    @Override
    public void onTick(boolean inGame) {
        if (inGame) {
            removePotion();
            Xeno.utils.player().addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, Integer.MAX_VALUE, power));
        }
    }

    @Override
    public String moduleDesc() {
        return "Прыжок с заданной мощностью";
    }

    @Override
    public Panel settingPanel() {
        return new Panel(
                new ScrollSlider("Power", power, 20) {
                    @Override
                    public void onScroll(int dir, boolean withShift) {
                        power = processSlider(dir, withShift);
                    }
                }
        );
    }

}
