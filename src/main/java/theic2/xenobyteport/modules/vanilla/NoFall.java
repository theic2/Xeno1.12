package theic2.xenobyteport.modules.vanilla;

import net.minecraft.network.play.client.CPacketPlayer;
import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;

public class NoFall extends CheatModule {

    public NoFall() {
        super("NoFall", Category.PLAYER, PerformMode.TOGGLE);
    }

    @Override
    public void onTick(boolean inGame) {
        if (inGame && Xeno.utils.player().fallDistance > 2) {
            Xeno.utils.sendPacket(new CPacketPlayer(true));
        }
    }

    @Override
    public String moduleDesc() {
        return "Отключает урон от падения";
    }

}
