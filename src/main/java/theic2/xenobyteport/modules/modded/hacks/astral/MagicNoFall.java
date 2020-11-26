package theic2.xenobyteport.modules.modded.hacks.astral;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.modules.modded.hacks.Hack;

public class MagicNoFall extends Hack {
    private SimpleNetworkWrapper networkWrapper;

    public MagicNoFall() {
        super("MagicNoFall", Category.PLAYER, PerformMode.TOGGLE);
    }


    @Override
    public String moduleDesc() {
        return "NoFall через AstralSorcery.";
    }

    @Override
    protected void hInit() throws Throwable {
        if (networkWrapper == null)
            networkWrapper = (SimpleNetworkWrapper) Class
                    .forName("hellfirepvp.astralsorcery.common.network.PacketChannel").getField("CHANNEL")
                    .get(null);
        if (networkWrapper == null) throw new NullPointerException("Not works module!");
    }

    @Override
    public void onTick(boolean inGame) {
        if (utils.player().fallDistance > 2)
            try {
                networkWrapper.sendToServer((IMessage) Class
                        .forName("hellfirepvp.astralsorcery.common.network.packet.client.PktElytraCapeState")
                        .getMethod("resetFallDistance").invoke(null));
            } catch (Exception e) {
            }
    }
}
