package theic2.xenobyteport.modules.base;

import theic2.xenobyteport.api.config.Cfg;
import theic2.xenobyteport.api.gui.InputType;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.api.module.PerformSource;
import theic2.xenobyteport.gui.click.elements.Button;
import theic2.xenobyteport.gui.click.elements.Panel;
import theic2.xenobyteport.gui.swing.UserInput;

import java.util.ArrayList;
import java.util.List;

public class ChatBind extends CheatModule {

    @Cfg("commands")
    private List<String> commands;

    public ChatBind() {
        super("ChatBind", Category.MISC, PerformMode.SINGLE);
        commands = new ArrayList<String>();
    }

    @Override
    public void onPerform(PerformSource src) {
        commands.forEach(utils::serverChatMessage);
    }

    @Override
    public String moduleDesc() {
        return "Выполнение заданных команд по кейбинду";
    }

    @Override
    public Panel settingPanel() {
        return new Panel(
                new Button("Commands") {
                    @Override
                    public void onLeftClick() {
                        new UserInput("Команды", commands, InputType.CUSTOM).showFrame();
                    }
                }
        );
    }

}
