package theic2.xenobyteport.modules.vanilla;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;

public class BlinkCam extends CheatModule {

    private EntityOtherPlayerMP fake;

    public BlinkCam() {
        super("BlinkCam", Category.PLAYER, PerformMode.TOGGLE);
    }

    @Override
    public void onDisabled() {
        utils.world().removeEntity(fake);
        fake = null;
    }

    @Override
    public void onTick(boolean inGame) {
        if (inGame && fake == null) {
            EntityPlayer pl = utils.player();
            fake = new EntityOtherPlayerMP(utils.world(), pl.getGameProfile());
            fake.setPositionAndRotation(pl.posX, pl.getEntityBoundingBox().minY, pl.posZ, pl.rotationYaw, pl.rotationPitch);
            fake.setRotationYawHead(pl.rotationYawHead);
            fake.inventory = pl.inventory;
            utils.world().spawnEntity(fake);
        }
    }

    @Override
    public boolean doSendPacket(Packet packet) {
        return !(packet instanceof CPacketPlayer);
    }

    @Override
    public String moduleDesc() {
        return "Он же Blink, он же FreeCam";
    }

}
