package theic2.xenobyteport.modules.vanilla;

import net.minecraft.block.BlockLiquid;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;

public class FluidWalk extends CheatModule {

    public FluidWalk() {
        super("FluidWalk", Category.PLAYER, PerformMode.TOGGLE);
    }

    @Override
    public void onTick(boolean inGame) {
        if (inGame && utils.block(new BlockPos(utils.myCoords().getX(), utils.myCoords().getY() - 1, utils.myCoords().getZ())) instanceof BlockLiquid) {
            utils.player().motionY = 0;
            if (Keyboard.isKeyDown(utils.mc().gameSettings.keyBindJump.getKeyCode())) {
                utils.player().motionY = 0.4;
            }
        }
    }

    @Override
    public String moduleDesc() {
        return "Прогулки по жидкостям";
    }

}
