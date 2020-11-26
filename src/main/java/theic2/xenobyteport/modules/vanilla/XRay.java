package theic2.xenobyteport.modules.vanilla;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDirt;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import theic2.xenobyteport.api.config.Cfg;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.api.render.IDraw;
import theic2.xenobyteport.gui.click.elements.Panel;
import theic2.xenobyteport.gui.click.elements.ScrollSlider;
import theic2.xenobyteport.utils.TickHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class XRay extends CheatModule {

    @Cfg("radius")
    private int radius;
    @Cfg("height")
    private int height;
    private List<XRayBlock> blocks;
    private XRaySelect selector;

    public XRay() {
        super("XRay", Category.WORLD, PerformMode.TOGGLE);
        blocks = new CopyOnWriteArrayList<XRayBlock>();
        height = 100;
        radius = 25;
    }

    private void updateBlocks() {
        List<XRayBlock> out = new ArrayList<XRayBlock>();
        BlockPos pos = utils.myCoords();
        World world = utils.world();
        new Thread(() -> {
            for (int y = 0; y <= height; y++) {
                for (int x = pos.getX() - radius; x <= pos.getX() + radius; x++) {
                    for (int z = pos.getZ() - radius; z <= pos.getZ() + radius; z++) {
                        BlockPos blockPos = new BlockPos(x, y, z);
                        Block block = world.getBlockState(blockPos).getBlock();
                        if (block instanceof BlockAir || block instanceof BlockDirt) {
                            continue;
                        }
                        int meta = world.getBlockState(new BlockPos(x, y, z)).getBlock().getMetaFromState(world.getBlockState(blockPos));
                        XRaySelect.SelectedBlock selected = selector.getBlock(block, block.getMetaFromState(world.getBlockState(blockPos)));
                        if (selected != null) {
                            out.add(new XRayBlock(selected, blockPos));
                        }
                    }
                }
            }
            blocks.clear();
            blocks.addAll(out);
        }).start();
    }

    @Override
    public void onHandlerInit() {
        selector = (XRaySelect) moduleHandler().getModuleByClass(XRaySelect.class);
    }

    @Override
    public int tickDelay() {
        return TickHelper.ONE_SEC;
    }

    @Override
    public void onTick(boolean inGame) {
        if (inGame) {
            updateBlocks();
        }
    }

    @SubscribeEvent
    public void worldRender(RenderWorldLastEvent e) {
        blocks.forEach(XRayBlock::draw);
    }

    @Override
    public String moduleDesc() {
        return "Отрисовка блоков в мире выбранных в NEI/XRaySelect";
    }

    @Override
    public Panel settingPanel() {
        return new Panel(
                new ScrollSlider("Radius", radius, 100) {
                    @Override
                    public void onScroll(int dir, boolean withShift) {
                        radius = processSlider(dir, withShift);
                    }

                    @Override
                    public String elementDesc() {
                        return "Радиус проверки блоков";
                    }
                },
                new ScrollSlider("Height", height, 256) {
                    @Override
                    public void onScroll(int dir, boolean withShift) {
                        height = processSlider(dir, withShift);
                    }

                    @Override
                    public String elementDesc() {
                        return "Высота проверки блоков";
                    }
                }
        );
    }

    class XRayBlock implements IDraw {

        final XRaySelect.SelectedBlock sel;
        final BlockPos blockPos;

        XRayBlock(XRaySelect.SelectedBlock sel, BlockPos blockPos) {
            this.sel = sel;
            this.blockPos = blockPos;
        }

        @Override
        public void draw() {
            render.WORLD.drawEspBlock(blockPos.getX(), blockPos.getY(), blockPos.getZ(), sel.rf, sel.gf, sel.bf, sel.af, sel.scale);
        }

        @Override
        public String toString() {
            return sel + "[" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + "]";
        }

    }

}