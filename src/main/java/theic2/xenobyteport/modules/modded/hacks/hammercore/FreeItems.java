package theic2.xenobyteport.modules.modded.hacks.hammercore;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.api.module.PerformSource;
import theic2.xenobyteport.modules.modded.hacks.Hack;

public class FreeItems extends Hack {
    public FreeItems() {
        super("FreeItems", Category.MODS, PerformMode.SINGLE);
    }


    @Override
    public String moduleDesc() {
        return "Дроп айтемстака через HammerCore...";
    }

    @Override
    protected void hInit() throws Throwable {
        Class.forName("com.zeitheron.hammercore.net.internal.PacketDropItem");
    }

    @Override
    public void onPerform(PerformSource src) {
        EntityPlayer player = utils.player();
        try {
            EntityItem entityItem = new EntityItem(player.world, player.posX, player.posY, player.posZ,
                    giveSelector().givedItem());
            Object packet = Class.forName("com.zeitheron.hammercore.net.internal.PacketDropItem")
                    .getConstructor(EntityItem.class).newInstance(entityItem);
            Object ncNet = Class.forName("com.zeitheron.hammercore.net.HCNet").getField("INSTANCE").get(null);
            ncNet.getClass()
                    .getDeclaredMethod("sendToServer", Class.forName("com.zeitheron.hammercore.net.IPacket"))
                    .invoke(ncNet, packet);

        } catch (Exception e) {
        }
    }
}
