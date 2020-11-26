package theic2.xenobyteport.modules.vanilla;

import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.api.module.PerformSource;

public class FakeCreative extends CheatModule {

    public FakeCreative() {
        super("FakeCreative", Category.PLAYER, PerformMode.SINGLE);
    }

    @Override
    public void onPerform(PerformSource src) {
        if (utils.mc().playerController.getCurrentGameType().isCreative()) {
            utils.mc().playerController.setGameType(GameType.SURVIVAL);
            WorldSettings.getGameTypeById(0).configurePlayerCapabilities(utils.player().capabilities);
            utils.player().sendPlayerAbilities();
        } else {
            utils.mc().playerController.setGameType(GameType.CREATIVE);
            WorldSettings.getGameTypeById(1).configurePlayerCapabilities(utils.player().capabilities);
            utils.player().sendPlayerAbilities();
        }
    }

    @Override
    public String moduleDesc() {
        return "Визуальный Gamemode 1";
    }

}
