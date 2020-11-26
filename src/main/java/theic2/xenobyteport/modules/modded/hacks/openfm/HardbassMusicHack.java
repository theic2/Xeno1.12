package theic2.xenobyteport.modules.modded.hacks.openfm;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.api.module.PerformSource;
import theic2.xenobyteport.modules.modded.hacks.Hack;

import java.lang.reflect.Constructor;

public class HardbassMusicHack extends Hack {
    private Constructor<?> constructor;

    private boolean prevState;

    public HardbassMusicHack() {
        super("HardbassMusic", Category.FUN, PerformMode.SINGLE);
    }

    @Override
    public String moduleDesc() {
        return "Открыть гуи радио опенфма...";
    }

    @Override
    protected void hInit() throws Throwable {
        constructor = Class.forName("pcl.OpenFM.GUI.GuiRadio").getConstructor(InventoryPlayer.class,
                Class.forName("pcl.OpenFM.TileEntity.TileEntityRadio"));
    }

    @Override
    public void onPerform(PerformSource src) {
        BlockPos blockPos = utils.mc().objectMouseOver.getBlockPos();
        TileEntity entity = utils.mc().world.getTileEntity(blockPos);
        try {
            if (entity != null
                    && entity.getClass().equals(Class.forName("pcl.OpenFM.TileEntity.TileEntityRadio"))) {
                GuiContainer container = (GuiContainer) constructor
                        .newInstance(utils.player().inventory, entity);
                utils.openGui(container, true);
            }
        } catch (Exception ignored) {
        }
    }
}