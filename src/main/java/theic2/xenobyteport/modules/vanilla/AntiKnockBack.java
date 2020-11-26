package theic2.xenobyteport.modules.vanilla;

import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;

public class AntiKnockBack extends CheatModule {

    public AntiKnockBack() {
        super("AntiKnockBack", Category.PLAYER, PerformMode.TOGGLE);
    }

    @Override
    public boolean doReceivePacket(Packet packet) {
        if (packet instanceof SPacketEntityVelocity) {
            return !Xeno.utils.isInGame() || Xeno.utils.myId() != ((SPacketEntityVelocity) packet).getEntityID();
        }
        return true;
    }

    @Override
    public String moduleDesc() {
        return "Выключает эфект отбрасывания у игрока";
    }

}