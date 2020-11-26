package theic2.xenobyteport.modules.vanilla;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import theic2.xenobyteport.api.gui.WidgetMode;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;

public class TextRadar extends CheatModule {

    public TextRadar() {
        super("TextRadar", Category.MISC, PerformMode.TOGGLE);
    }

    @Override
    public int tickDelay() {
        return 10;
    }

    @Override
    public void onTick(boolean inGame) {
        if (inGame) {
            BlockPos my = utils.myCoords();
            StringBuilder out = new StringBuilder();
            utils.nearEntityes(200)
                    .filter(e -> e instanceof EntityPlayer && !e.isDead)
                    .forEach(e -> {
                        BlockPos pl = utils.coords(e);
                        double dist = my.getDistance(pl.getX(), pl.getY(), pl.getZ());
                        out.append("[" + utils.name(e) + " " + dist + "]");
                    });
            boolean empty = out.length() == 0;
            infoMessage(empty ? "пусто" : out.toString(), empty ? WidgetMode.SUCCESS : WidgetMode.FAIL);
        }
    }

    @Override
    public String moduleDesc() {
        return "Выводит на инфопанель ближайших игроков и расстояние до них";
    }

}