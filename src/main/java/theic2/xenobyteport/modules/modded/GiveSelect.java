package theic2.xenobyteport.modules.modded;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.api.config.Cfg;
import theic2.xenobyteport.api.gui.WidgetMode;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.api.module.PerformSource;
import theic2.xenobyteport.gui.click.elements.Button;
import theic2.xenobyteport.gui.click.elements.Panel;
import theic2.xenobyteport.gui.click.elements.ScrollSlider;
import theic2.xenobyteport.gui.swing.ItemChanter;

public class GiveSelect extends CheatModule {

    @Cfg("withChant")
    private boolean withChant;
    @Cfg("fillSlots")
    private boolean fillSlots;
    @Cfg("count")
    private int count;
    private ItemStack givedItem;
    private ItemChanter chanter;

    public GiveSelect() {
        super("GiveSelect", Category.JEI, PerformMode.SINGLE);
        givedItem = new ItemStack(Blocks.COMMAND_BLOCK);
        chanter = new ItemChanter();
        count = 1;
    }

    public ItemStack givedItem() {
        NBTTagCompound chant = givedNBT();
        ItemStack outItem = givedItem.copy();
        return Xeno.utils.item(outItem.splitStack(count), withChant && !chant.isEmpty() ? chant : null).copy();
    }

    public NBTTagCompound givedNBT() {
        return chanter.getOutNBT();
    }

    public int itemCount() {
        return count;
    }

    public boolean fillAllSlots() {
        return fillSlots;
    }

    @Override
    public void onPerform(PerformSource src) {
        switch (src) {
            case BUTTON:
                utils.openGui(new GuiInventory(utils.player()));
                break;
            case KEY:
                ItemStack checkItem = utils.getStackMouseOver();
                if (checkItem != null) {
                    givedItem = checkItem;
                    widgetMessage("выбран " + givedItem.getDisplayName() + " [x" + count + "]", WidgetMode.INFO);
                } else {
                    if (utils.isInGameGui()) {
                        ItemStack item = utils.item();
                        if (item != null && item.hasTagCompound()) {
                            chanter.loadCustomNBT(item.getTagCompound());
                        }
                        chanter.showFrame();
                    }
                }
        }
    }

    @Override
    public boolean isWorking() {
        return true;
    }

    @Override
    public boolean inGuiPerform() {
        return true;
    }

    @Override
    public boolean allowStateMessages() {
        return false;
    }

    @Override
    public String moduleDesc() {
        return "Выбор предмета в NEI по кейбинду для выдачи через эксплойты";
    }

    @Override
    public Panel settingPanel() {
        return new Panel(
                new ScrollSlider("Count", count, 64) {
                    @Override
                    public void onScroll(int dir, boolean withShift) {
                        count = processSlider(dir, withShift);
                    }

                    @Override
                    public String elementDesc() {
                        return "Количество выдаваемого предмета";
                    }
                },
                new Button("FillSlots", fillSlots) {
                    @Override
                    public void onLeftClick() {
                        buttonValue(fillSlots = !fillSlots);
                    }

                    @Override
                    public String elementDesc() {
                        return "По возможности заполнить все слоты в инвентаре при выдаче предмета";
                    }
                },
                new Button("WithChant", withChant) {
                    @Override
                    public void onLeftClick() {
                        buttonValue(withChant = !withChant);
                    }

                    @Override
                    public String elementDesc() {
                        return "С применением NBT из Chanter'a";
                    }
                },
                new Button("Chanter") {
                    @Override
                    public void onLeftClick() {
                        chanter.showFrame();
                    }

                    @Override
                    public String elementDesc() {
                        return "Зачаровыватель/редактор NBT предмета (по кейбинду GiveSelect'a откроется с NBT предмета в руке)";
                    }
                }
        );
    }

}