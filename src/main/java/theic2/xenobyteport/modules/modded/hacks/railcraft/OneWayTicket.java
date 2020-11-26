package theic2.xenobyteport.modules.modded.hacks.railcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;
import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.api.module.PerformSource;

public class OneWayTicket extends CheatModule {

    public OneWayTicket() {
        super("OneWayTicket", Category.MODS, PerformMode.SINGLE);
    }

    @Override
    public void onPerform(PerformSource src) {
        ItemStack ckeckItem = Xeno.utils.item();
        try {
            if (Class.forName("mods.railcraft.common.util.network.IEditableItem").isInstance(ckeckItem.getItem())) {
                NBTTagCompound nbt = giveSelector().givedNBT();
                if (!nbt.isEmpty())
                    Class.forName("mods.railcraft.common.util.network.PacketDispatcher").getMethod("sendToServer", Class.forName("mods.railcraft.common.util.network.RailcraftPacket")).invoke(null, Class.forName("mods.railcraft.common.util.network.PacketItemNBT").getConstructor(EntityPlayer.class, ItemStack.class).newInstance(Xeno.utils.player(), Xeno.utils.item(ckeckItem, nbt)));
            }
        } catch (Exception e) {
        }
    }

    @Override
    public String moduleDesc() {
        return "Применение NBT из Chanter'a к Билету или Таблице Маршрутизации находящейся в руке";
    }

    @Override
    public boolean isWorking() {
        return Loader.isModLoaded("Railcraft");
    }

}