package theic2.xenobyteport.modules.modded.hacks.flood;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import theic2.xenobyteport.api.config.Cfg;
import theic2.xenobyteport.api.gui.InputType;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.gui.click.elements.Button;
import theic2.xenobyteport.gui.click.elements.Panel;
import theic2.xenobyteport.gui.swing.UserInput;
import theic2.xenobyteport.modules.modded.hacks.Hack;

import java.util.ArrayList;
import java.util.List;

public class CustomFloodHack extends Hack {
    private SimpleNetworkWrapper networkWrapper;
    @Cfg("commands")
    private List<String> commands;

    public CustomFloodHack() {
        super("CustomFloodAstral", Category.FUN, PerformMode.TOGGLE);
        commands = new ArrayList<String>();
        commands.add("LOX!");
    }

    @Override
    public String moduleDesc() {
        return "Спам ошибками через астралку.";
    }

    @Override
    protected void hInit() throws Throwable {
        networkWrapper = (SimpleNetworkWrapper) Class
                .forName("hellfirepvp.astralsorcery.common.network.PacketChannel").getField("CHANNEL").get(null);
    }

    @Override
    public void onTick(boolean inGame) {
        commands.forEach(sp -> {
            try {
                networkWrapper.sendToServer((IMessage) Class
                        .forName("hellfirepvp.astralsorcery.common.network.packet.client.PktDiscoverConstellation")
                        .getConstructor(String.class).newInstance(sp));
            } catch (Throwable e) {
            }
        });
    }

    @Override
    public Panel settingPanel() {
        return new Panel(
                new Button("Commands") {
                    @Override
                    public void onLeftClick() {
                        new UserInput("Команды", commands, InputType.CUSTOM).showFrame();
                    }

                    @Override
                    public String elementDesc() {
                        return "Список команд";
                    }
                }
        );
    }
}
