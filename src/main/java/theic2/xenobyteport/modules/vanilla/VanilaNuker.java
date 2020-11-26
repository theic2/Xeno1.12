package theic2.xenobyteport.modules.vanilla;

import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import theic2.xenobyteport.api.config.Cfg;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.gui.click.elements.Button;
import theic2.xenobyteport.gui.click.elements.Panel;
import theic2.xenobyteport.gui.click.elements.ScrollSlider;

public class VanilaNuker extends CheatModule {

    @Cfg("handshake")
    private boolean handshake;
    @Cfg("onView")
    private boolean onView;
    @Cfg("radius")
    private int radius;

    public VanilaNuker() {
        super("VanilaNuker", Category.WORLD, PerformMode.TOGGLE);
        radius = 1;
    }

    @Override
    public void onTick(boolean inGame) {
        if (inGame) {
            utils.nukerList(onView ? utils.mop() : utils.myCoords(), radius).forEach(pos -> {
                if (utils.isInCreative()) {
                    utils.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.DOWN));
                } else {
                    utils.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.DOWN));
                    utils.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.DOWN));
                }
            });
            if (handshake) {
                //utils.player().getHeldItemMainhand().;
            }
        }
    }

    @Override
    public String moduleDesc() {
        return "Ломание блоков в радиусе (в тике)";
    }

    @Override
    public Panel settingPanel() {
        return new Panel(
                new ScrollSlider("Radius", radius, 6) {
                    @Override
                    public void onScroll(int dir, boolean withShift) {
                        radius = processSlider(dir, withShift);
                    }
                },
                new Button("OnView", onView) {
                    @Override
                    public void onLeftClick() {
                        buttonValue(onView = !onView);
                    }

                    @Override
                    public String elementDesc() {
                        return "По взгляду или вокруг игрока";
                    }
                },
                new Button("HandShake", handshake) {
                    @Override
                    public void onLeftClick() {
                        buttonValue(handshake = !handshake);
                    }

                    @Override
                    public String elementDesc() {
                        return "Взмах руки";
                    }
                }
        );
    }

}