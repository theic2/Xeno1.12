package theic2.xenobyteport.modules.modded;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;

public class AdvancedTooltip extends CheatModule {

    public AdvancedTooltip() {
        super("AdvancedTooltip", Category.MISC, PerformMode.TOGGLE);
    }

    @SubscribeEvent
    public void tooltipHook(ItemTooltipEvent e) {
        ItemStack item = e.getItemStack();
        e.getToolTip().add(item.getItem().getRegistryName() + ":" + item.getMetadata());
    }

    @Override
    public String moduleDesc() {
        return "Отображает в описании предмета его метаданные";
    }

}