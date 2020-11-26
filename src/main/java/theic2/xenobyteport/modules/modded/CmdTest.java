package theic2.xenobyteport.modules.modded;

import io.netty.buffer.Unpooled;
import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.api.module.PerformSource;

public class CmdTest extends CheatModule {

    public CmdTest() {
        super("CmdTest", Category.MISC, PerformMode.SINGLE);
    }

    @Override
    public String moduleDesc() {
        return "Проверка командного блока на работоспособность";
    }

    @Override
    public void onPerform(PerformSource src) {
        Xeno.utils.sendPacket("MC|AdvCmd", Unpooled.buffer());
    }

}
