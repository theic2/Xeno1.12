package theic2.xenobyteport.modules.vanilla;

import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.api.config.Cfg;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.api.module.PerformSource;
import theic2.xenobyteport.gui.click.elements.Button;
import theic2.xenobyteport.gui.click.elements.Panel;

public class CreativeGive extends CheatModule {

    @Cfg("drop")
    boolean drop;

    public CreativeGive() {
        super("CreativeGive", Category.PLAYER, PerformMode.SINGLE);
    }

    @Override
    public void onPerform(PerformSource src) {
        Xeno.utils.creativeGive(drop ? -1 : Xeno.utils.activeSlot(), giveSelector().givedItem());
    }

    @Override
    public String moduleDesc() {
        return "Выдача предмета (только в креативе)";
    }

    @Override
    public Panel settingPanel() {
        return new Panel(
                new Button("DropItem", drop) {
                    @Override
                    public void onLeftClick() {
                        buttonValue(drop = !drop);
                    }

                    @Override
                    public String elementDesc() {
                        return "Выбрасывание предмета при выдаче, иначе в активный слот";
                    }
                }
        );
    }

}