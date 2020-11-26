package theic2.xenobyteport.modules.modded.hacks.loop;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.ModContainer;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.utils.XenoLogger;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Credits: SenpaiGG, CloudFire25
 */
public class AutoFuckZaloopa extends CheatModule {
    private Map<Object, ModContainer> listenerOwners;

    public AutoFuckZaloopa() {
        super("AutoFuckLoop", Category.MODS, PerformMode.ON_START);
    }

    @Override
    public void onHandlerInit() {
        try {
            this.listenerOwners.entrySet().stream().filter((mod) -> mod.getValue().getName().equalsIgnoreCase("RichGuard")).forEach(mod -> MinecraftForge.EVENT_BUS.unregister(mod.getKey()));
            XenoLogger.info("Zalopa was fucked successfully :)");
        } catch (Exception e) {
            XenoLogger.info("FUCKZALOOPA ERROR,MAY BE FIXED");
            e.printStackTrace();
        }
    }

    @Override
    public String moduleDesc() {
        return "Disables RichGuard-a";
    }

    @Override
    public boolean isWorking() {
        try {
            Field mods = MinecraftForge.EVENT_BUS.getClass().getDeclaredField("listenerOwners");
            mods.setAccessible(true);

            this.listenerOwners = (Map<Object, ModContainer>) mods.get(MinecraftForge.EVENT_BUS);
            return listenerOwners.entrySet().stream().anyMatch((mod) -> mod.getValue().getName().equalsIgnoreCase("RichGuard"));
        } catch (Exception e) {
            XenoLogger.info("FUCKZALOOPA ERROR,MAY BE FIXED");
            e.printStackTrace();
            return false;
        }
    }
}
