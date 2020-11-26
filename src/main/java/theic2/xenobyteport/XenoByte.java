package theic2.xenobyteport;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.handlers.ModuleHandler;

@Mod(modid = Xeno.mod_id, name = Xeno.mod_name, version = Xeno.mod_version)
public class XenoByte {
    public XenoByte() {

    }

    public XenoByte(int code) {
        init(null);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        if (e == null) starter(null);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
    }

    @Mod.EventHandler
    public void starter(FMLLoadCompleteEvent e) {
        new ModuleHandler();
    }
}