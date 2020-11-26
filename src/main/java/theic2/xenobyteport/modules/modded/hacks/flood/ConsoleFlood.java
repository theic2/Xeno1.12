package theic2.xenobyteport.modules.modded.hacks.flood;

import net.minecraftforge.fml.common.Loader;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;

public class ConsoleFlood extends CheatModule {

    public ConsoleFlood() {
        super("ConsoleFlood", Category.FUN, PerformMode.TOGGLE);
    }

    @Override
    public void onTick(boolean inGame) {
        if (inGame) {
            utils.sendPacket("AdvSolarPanels", (byte) 1);
            utils.sendPacket("CarpentersBlocks", -1);
            utils.sendPacket("Ztones", -1);
        }
    }

    @Override
    public boolean isWorking() {
        return Loader.isModLoaded("AdvancedSolarPanel") || Loader.isModLoaded("CarpentersBlocks") || Loader.isModLoaded("Ztones");
    }

    @Override
    public String moduleDesc() {
        return "Спам в консоль краш-логами ASP, Carpenters, Ztones";
    }

}
