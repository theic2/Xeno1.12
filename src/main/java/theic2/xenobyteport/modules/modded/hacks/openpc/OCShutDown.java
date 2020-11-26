package theic2.xenobyteport.modules.modded.hacks.openpc;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.Loader;
import theic2.xenobyteport.api.config.Cfg;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.api.module.PerformSource;
import theic2.xenobyteport.gui.click.elements.Button;
import theic2.xenobyteport.gui.click.elements.Panel;

public class OCShutDown extends CheatModule {

    @Cfg("inRadius")
    private boolean inRadius;
    private boolean state;

    public OCShutDown() {
        super("OCShutDown", Category.MODS, PerformMode.SINGLE);
    }

    private boolean shutDownPacket(TileEntity comp) {
        try {
            if (Class.forName("li.cil.oc.common.tileentity.Case").isInstance(comp)) {
                Class.forName("li.cil.oc.client.PacketSender$").getMethod("sendComputerPower", Class.forName("li.cil.oc.common.tileentity.traits.Computer"), boolean.class).invoke(Class.forName("li.cil.oc.client.PacketSender$").getField("MODULE$").get(null), comp, state);
                return true;
            } else if (Class.forName("li.cil.oc.common.tileentity.Rack").isInstance(comp)) {
                for (int rack = 0; rack <= 3; rack++) {
                    Class.forName("li.cil.oc.client.PacketSender$").getMethod("sendServerPower", Class.forName("li.cil.oc.common.tileentity.Rack"), int.class, boolean.class).invoke(Class.forName("li.cil.oc.client.PacketSender$").getField("MODULE$").get(null), comp, rack, state);
                }
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public void onPerform(PerformSource src) {
        state = !state;
        if (inRadius) {
            utils.nearTiles().forEach(this::shutDownPacket);
        } else {
            shutDownPacket(utils.tile());
        }
    }

    @Override
    public boolean isWorking() {
        return Loader.isModLoaded("OpenComputers");
    }

    @Override
    public String moduleDesc() {
        return "Перезагрузка компов из OpenComputers по кейбинду";
    }

    @Override
    public Panel settingPanel() {
        return new Panel(
                new Button("InRadius", inRadius) {
                    @Override
                    public void onLeftClick() {
                        buttonValue(inRadius = !inRadius);
                    }

                    @Override
                    public String elementDesc() {
                        return "По радиусу или взгляду";
                    }
                }
        );
    }

}