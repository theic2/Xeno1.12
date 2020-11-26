package theic2.xenobyteport.modules.modded;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.api.config.Cfg;
import theic2.xenobyteport.api.gui.InputType;
import theic2.xenobyteport.api.gui.WidgetMode;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.gui.click.elements.Button;
import theic2.xenobyteport.gui.click.elements.Panel;
import theic2.xenobyteport.gui.click.elements.ScrollSlider;
import theic2.xenobyteport.gui.swing.UserInput;
import theic2.xenobyteport.handlers.ModuleHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScreenProtect extends CheatModule {

    @Cfg("channels")
    private List<String> channels;
    @Cfg("delay")
    private int delay;
    private boolean isReady;

    public ScreenProtect() {
        super("ScreenProtect", Category.MISC, PerformMode.TOGGLE);
        channels = new ArrayList<String>();
        channels.add("screener");
        channels.add("clienttweak");
        isReady = true;
        delay = 2;
    }

    @Override
    public boolean doReceivePacket(Packet packet) {
        if (packet instanceof FMLProxyPacket) {
            String channel = ((FMLProxyPacket) packet).channel();
            if (isReady && Xeno.utils.isInGame() && channels.contains(channel)) {
                Xeno.utils.closeGuis();
                new Thread(() -> {
                    isReady = false;
                    ModuleHandler hand = moduleHandler();
                    List<CheatModule> moduleHash = new ArrayList<CheatModule>();
                    moduleHash.addAll(hand.enabledModules().collect(Collectors.toList()));
                    hand.enabledModules().forEach(hand::disable);
                    try {
                        Thread.sleep(delay * 1000);
                    } catch (Exception e) {
                    }
                    moduleHash.forEach(hand::enable);
                    widgetMessage("Сработал скриншотер: " + channel, WidgetMode.SUCCESS);
                    isReady = true;
                }).start();
            }
        }
        return true;
    }

    @Override
    public String moduleDesc() {
        return "Отключит на заданное время активные модули при скриншоте";
    }

    @Override
    public Panel settingPanel() {
        return new Panel(
                new Button("Channels") {
                    @Override
                    public void onLeftClick() {
                        new UserInput("Каналы", channels, InputType.CUSTOM).showFrame();
                    }

                    @Override
                    public String elementDesc() {
                        return "Блэклист FMLProxy каналов (изменять только по назначению)";
                    }
                },
                new ScrollSlider("Delay", delay, 6) {
                    @Override
                    public void onScroll(int dir, boolean withShift) {
                        delay = processSlider(dir, withShift);
                    }

                    @Override
                    public String elementDesc() {
                        return "Время в секундах на которое отключатся активные модули при скриншоте";
                    }
                }
        );
    }

}
