package theic2.xenobyteport.modules.vanilla;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.api.config.Cfg;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.gui.click.elements.Button;
import theic2.xenobyteport.gui.click.elements.Panel;
import theic2.xenobyteport.gui.click.elements.ScrollSlider;
import theic2.xenobyteport.utils.Keys;

public class XenoFly extends CheatModule {

    @Cfg("noclip")
    private boolean noclip;
    @Cfg("inGui")
    private boolean inGui;
    @Cfg("vSpeed")
    private float vSpeed;
    @Cfg("hSpeed")
    private float hSpeed;

    public XenoFly() {
        super("XenoFly", Category.PLAYER, PerformMode.TOGGLE);
        vSpeed = 0.4F;
        hSpeed = 0.8F;
    }

    @Override
    public void onDisabled() {
        Xeno.utils.player().noClip = false;
    }

    @Override
    public void onTick(boolean inGame) {
        if (inGame) {
            EntityPlayer pl = Xeno.utils.player();
            float xm = 0;
            float ym = 0;
            float zm = 0;
            if (Keys.isPressed(Xeno.utils.mc().gameSettings.keyBindLeft)) {
                xm = hSpeed;
            }
            if (Keys.isPressed(Xeno.utils.mc().gameSettings.keyBindRight)) {
                xm = -hSpeed;
            }
            if (Keys.isPressed(Xeno.utils.mc().gameSettings.keyBindJump)) {
                ym = vSpeed;
            }
            if (Keys.isPressed(Xeno.utils.mc().gameSettings.keyBindSneak)) {
                ym = -vSpeed;
            }
            if (Keys.isPressed(Xeno.utils.mc().gameSettings.keyBindForward)) {
                zm = hSpeed;
            }
            if (Keys.isPressed(Xeno.utils.mc().gameSettings.keyBindBack)) {
                zm = -hSpeed;
            }
            float sin = MathHelper.sin(pl.rotationYaw * (float) Math.PI / 180.0F);
            float cos = MathHelper.cos(pl.rotationYaw * (float) Math.PI / 180.0F);
            if (inGui || Xeno.utils.isInGameGui()) {
                pl.motionX = xm * cos - zm * sin;
                pl.motionY = ym;
                pl.motionZ = zm * cos + xm * sin;
            } else {
                pl.motionY = 0;
            }
            pl.noClip = noclip;
        }
    }

    @Override
    public String moduleDesc() {
        return "Полёт с заданными параметрами";
    }

    @Override
    public Panel settingPanel() {
        return new Panel(
                new ScrollSlider("VSpeed", (int) (vSpeed * 10), 50) {
                    @Override
                    public void onScroll(int dir, boolean withShift) {
                        vSpeed = (float) processSlider(dir, withShift) / 10;
                    }

                    @Override
                    public String elementDesc() {
                        return "Вертикальная скорость полёта";
                    }
                },
                new ScrollSlider("HSpeed", (int) (hSpeed * 10), 100) {
                    @Override
                    public void onScroll(int dir, boolean withShift) {
                        hSpeed = (float) processSlider(dir, withShift) / 10;
                    }

                    @Override
                    public String elementDesc() {
                        return "Горизонтальная скорость полёта";
                    }
                },
                new Button("NoClip", noclip) {
                    @Override
                    public void onLeftClick() {
                        buttonValue(noclip = !noclip);
                    }

                    @Override
                    public String elementDesc() {
                        return "Сквозь стены (лучше работает в BlinkCam)";
                    }
                },
                new Button("InGui", inGui) {
                    @Override
                    public void onLeftClick() {
                        buttonValue(inGui = !inGui);
                    }

                    @Override
                    public String elementDesc() {
                        return "Полёт при открытом Gui";
                    }
                }
        );
    }

}