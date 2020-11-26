package theic2.xenobyteport.modules.vanilla;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import theic2.xenobyteport.api.config.Cfg;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.gui.click.elements.Button;
import theic2.xenobyteport.gui.click.elements.Panel;

public class RenderControl extends CheatModule {

    @Cfg("living")
    private boolean living;

    public RenderControl() {
        super("RenderControl", Category.WORLD, PerformMode.TOGGLE);
    }

    @SubscribeEvent
    public void worldRender(RenderLivingEvent.Pre e) {
        if (!living && !(e.getEntity() instanceof EntityPlayer)) {
            e.setCanceled(true);
        }
    }

    @Override
    public String moduleDesc() {
        return "Откоючение рендера объектов в мире";
    }

    @Override
    public Panel settingPanel() {
        return new Panel(
                new Button("LivingBase", living) {
                    @Override
                    public void onLeftClick() {
                        buttonValue(living = !living);
                    }

                    @Override
                    public String elementDesc() {
                        return "Рендер живности";
                    }
                }
        );
    }

}
