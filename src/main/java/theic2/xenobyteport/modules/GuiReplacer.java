package theic2.xenobyteport.modules;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.utils.Rand;
import theic2.xenobyteport.utils.Reflections;

public class GuiReplacer extends CheatModule {

    public GuiReplacer() {
        super("GuiReplacer", Category.NONE, PerformMode.ON_START);
    }

    private void replaceSplash(GuiScreen gui) {
        if (gui instanceof GuiMainMenu) {
            try {
                Reflections.setPrivateValue(GuiMainMenu.class, (GuiMainMenu) gui, Rand.splash(), "field_73975_c", "splashText", "i");
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onPostInit() {
        replaceSplash(Xeno.utils.currentScreen());
    }

    @Override
    public boolean provideStateEvents() {
        return false;
    }

    @SubscribeEvent
    public void guiOpen(GuiOpenEvent e) {
        replaceSplash(e.getGui());
    }

}