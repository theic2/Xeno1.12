package theic2.xenobyteport.modules.modded.hacks.loop;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.ModContainer;
import theic2.xenobyteport.api.gui.WidgetMode;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.api.module.PerformSource;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Credits: SenpaiGG, CloudFire25
 */
public class FuckZaloopa extends CheatModule {
    private boolean isUsed;

    public FuckZaloopa() {
        super("FuckLoop", Category.MODS, PerformMode.SINGLE);
    }

    @Override
    public void onPerform(PerformSource src) {
        if (!isUsed) {
            try {
                Field mods = MinecraftForge.EVENT_BUS.getClass().getDeclaredField("listenerOwners");
                mods.setAccessible(true);

                @SuppressWarnings("unchecked")
                Map<Object, ModContainer> listenerOwners = (Map<Object, ModContainer>) mods.get(MinecraftForge.EVENT_BUS);
                listenerOwners.entrySet().stream().filter((mod) -> mod.getValue().getName().equalsIgnoreCase("RichGuard")).forEach(mod -> MinecraftForge.EVENT_BUS.unregister(mod.getKey()));
                widgetMessage("LoopGuard disabled", WidgetMode.INFO);
                isUsed = false;


            } catch (Exception e) {
                widgetMessage("Error!", WidgetMode.FAIL);
            }
        } else {
            widgetMessage("Already used!", WidgetMode.FAIL);
        }
    }

    @Override
    public String moduleDesc() {
        return "Disables RichGuard in runtime, only for HZR/in testMode";
    }

    @Override
    public boolean isWorking() {
        try {
            Field mods = MinecraftForge.EVENT_BUS.getClass().getDeclaredField("listenerOwners");
            mods.setAccessible(true);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}
