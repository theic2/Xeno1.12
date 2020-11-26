package theic2.xenobyteport.modules.modded.hacks.betterquesting;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.api.module.PerformSource;
import theic2.xenobyteport.modules.modded.hacks.Hack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class FreeItemsv3 extends Hack {
    private Object instance;
    private Method senToServer;
    private Constructor<?> packetConstructor;

    public FreeItemsv3() {
        super("FreeItemsv3", Category.MODS, PerformMode.SINGLE);
    }

    @Override
    public String moduleDesc() {
        return "Гив айтемстака через BetterQuest...";
    }

    @Override
    protected void hInit() throws Throwable {
        instance = Class.forName("betterquesting.network.PacketSender").getField("INSTANCE").get(null);
        senToServer = instance.getClass().getMethod("sendToServer",
                Class.forName("betterquesting.api.network.QuestingPacket"));
        packetConstructor = Class.forName("betterquesting.api.network.QuestingPacket")
                .getConstructor(ResourceLocation.class, NBTTagCompound.class);
        if (instance == null) throw new NullPointerException("Auch, not works");
    }

    @Override
    public void onPerform(PerformSource src) {
        if (giveSelector().givedItem() == null)
            return;
        try {
            BlockPos blockPos = utils.mc().objectMouseOver.getBlockPos();
            TileEntity entity = utils.mc().world.getTileEntity(blockPos);
            if (entity != null
                    && entity.getClass().equals(Class.forName("betterquesting.blocks.TileSubmitStation"))) {
                NonNullList<ItemStack> itemList = NonNullList.create();
                itemList.add(giveSelector().givedItem());
                NBTTagCompound payload = new NBTTagCompound();
                payload.setInteger("x", blockPos.getX());
                payload.setInteger("y", blockPos.getY());
                payload.setInteger("z", blockPos.getZ());
                payload.setTag("inventory", ItemStackHelper.saveAllItems(new NBTTagCompound(), itemList));
                payload.setString("owner", Minecraft.getMinecraft().player.getUniqueID().toString());
                payload.setInteger("questID", -1);
                payload.setInteger("task", -1);

                NBTTagCompound tile = new NBTTagCompound();
                tile.setTag("tile", payload);

                Object packet = packetConstructor.newInstance(new ResourceLocation("betterquesting:edit_station"),
                        tile);
                senToServer.invoke(instance, packet);
            }
        } catch (Exception ignored) {
        }
    }

}
