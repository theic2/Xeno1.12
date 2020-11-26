package theic2.xenobyteport.modules.vanilla;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import theic2.xenobyteport.api.config.Cfg;
import theic2.xenobyteport.api.gui.ColorPicker;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.api.module.PerformSource;
import theic2.xenobyteport.gui.click.elements.Button;
import theic2.xenobyteport.gui.swing.ColorPickerGui;
import theic2.xenobyteport.render.Colors;
import theic2.xenobyteport.utils.Config;
import theic2.xenobyteport.gui.click.elements.Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class XRaySelect extends CheatModule {

    @Cfg("guiHint")
    public boolean guiHint;
    public List<SelectedBlock> blocks;
    @Cfg("configBlocks")
    private List<String> configBlocks;
    private List<String> missingBlocks;
    private String neiSubset;

    public XRaySelect() {
        super("XRaySelect", Category.JEI, PerformMode.SINGLE);
        blocks = new CopyOnWriteArrayList<SelectedBlock>();
        missingBlocks = new ArrayList<String>();
        configBlocks = new ArrayList<String>();
        neiSubset = "X-Ray";
        guiHint = true;
    }

    public SelectedBlock getBlock(ItemStack stack) {
        return getBlock(b -> b.itemBlock.isItemEqual(stack));
    }

    public SelectedBlock getBlock(Block block, int meta) {
        return getBlock(b -> Block.isEqualTo(b.block, block));
    }

    private SelectedBlock getBlock(Predicate<SelectedBlock> predicate) {
        Optional<SelectedBlock> optional = blocks.stream().filter(predicate).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }


    @Override
    public void onPostInit() {
        for (String cBlock : configBlocks) {
            String[] data = cBlock.split(":");
            Block block = Block.REGISTRY.getObject(new ResourceLocation(data[0] + ":" + data[1]));
            if (block instanceof BlockAir) {
                missingBlocks.add(cBlock);
            } else {
                blocks.add(new SelectedBlock(new ItemStack(block, 1, Integer.parseInt(data[2])), Integer.parseInt(data[3]), Float.parseFloat(data[4])));
            }
        }
    }

    @Override
    public void onPerform(PerformSource src) {
        switch (src) {
            case BUTTON:
                //NEI.openGui("@" + neiSubset);
                break;
            case KEY:
                ItemStack stack = utils.getStackMouseOver();
                if (stack != null) {
                    if (stack.getItem() instanceof ItemBlock) {
                        SelectedBlock block = getBlock(stack);
                        if (block == null) {
                            block = new SelectedBlock(stack, Colors.BLACK, 1);
                        }
                        new XRaySettings(block).showFrame();
                    }
                } else {
                    if (utils.isInGameGui()) {
                        //NEI.openGui("@" + neiSubset);
                    }
                }
        }
    }

    @Override
    public boolean isWorking() {
        return true;
    }

    @Override
    public boolean inGuiPerform() {
        return true;
    }

    @Override
    public boolean allowStateMessages() {
        return false;
    }

    @Override
    public String moduleDesc() {
        return "Выбор блока в NEI по кейбинду для X-Ray (также добавляет вкладку @" + neiSubset + ")";
    }

    @Override
    public Panel settingPanel() {
        return new Panel(
                new Button("GuiHint", guiHint) {
                    @Override
                    public void onLeftClick() {
                        buttonValue(guiHint = !guiHint);
                    }

                    @Override
                    public String elementDesc() {
                        return "Подсветка выбранных предметов в инвентаре";
                    }
                }
        );
    }

    class XRaySettings extends ColorPickerGui {

        SelectedBlock block;
        JSlider s;

        XRaySettings(SelectedBlock block) {
            super(block.itemBlock.getDisplayName(), block);
            this.block = block;
            s = new JSlider(0, 100);
            s.setPreferredSize(new Dimension(350, 50));
            s.setBorder(customTitledBorder("Scale"));
            s.setValue((int) (block.scale * 100));
            s.addChangeListener((e) -> {
                block.scale = (float) s.getValue() / 100;
            });
            clear.setText("Удалить");
            buttonsBar.add(clear);
            sliders.add(s, GBC);
            pack();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == accept) {
                if (!blocks.contains(block)) {
                    blocks.add(block);
                }
            } else if (e.getSource() == clear) {
                blocks.remove(block);
            }
            configBlocks.clear();
            configBlocks.addAll(blocks.stream().map(SelectedBlock::toString).collect(Collectors.toList()));
            configBlocks.addAll(missingBlocks);
            Config.save();
            dispose();
        }

    }

    public class SelectedBlock extends ColorPicker {

        final ItemStack itemBlock;
        final Block block;
        final String id;
        public float scale;

        SelectedBlock(ItemStack itemBlock, int color, float scale) {

            super(color);
            this.scale = scale;
            this.itemBlock = itemBlock;
            id = itemBlock.getItem().getTranslationKey();
            block = Block.getBlockFromItem(itemBlock.getItem());
        }

        @Override
        public String toString() {
            return id + ":" + rgba + ":" + scale;
        }

    }

}
