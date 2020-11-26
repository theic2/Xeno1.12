package theic2.xenobyteport.modules.vanilla;

import net.minecraft.entity.Entity;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.utils.Reflections;

public class NoWeb extends CheatModule {

    public NoWeb() {
        super("NoWeb", Category.PLAYER, PerformMode.TOGGLE);
    }

    @Override
    public void onTick(boolean inGame) {
        if (inGame) {
            // "isInWeb", "field_70134_J", "E"
            Reflections.setPrivateValue(Entity.class, utils.player(), false, "isInWeb", "field_70134_J", "E");
        }
    }

    @Override
    public String moduleDesc() {
        return "Паутина больше не цепляет";
    }

}
