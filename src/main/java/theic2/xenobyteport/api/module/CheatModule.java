package theic2.xenobyteport.api.module;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import theic2.xenobyteport.api.config.Cfg;
import theic2.xenobyteport.api.gui.WidgetMessage;
import theic2.xenobyteport.api.gui.WidgetMode;
import theic2.xenobyteport.handlers.ModuleHandler;
import theic2.xenobyteport.modules.modded.GiveSelect;

import java.util.stream.Stream;

public abstract class CheatModule extends ModuleAbility {

    private final Category category;
    private final PerformMode mode;
    private final String name, id;
    @Cfg("cfgState")
    public boolean cfgState;
    private boolean forgeEvents, ticksStarted;
    private ModuleHandler handler;
    @Cfg("key")
    private int key;
    private int counter;

    public CheatModule(String name, Category category, PerformMode mode) {
        forgeEvents = Stream.of(getClass().getDeclaredMethods()).filter(f -> f.isAnnotationPresent(SubscribeEvent.class)).findFirst().isPresent();
        id = category + "_" + name;
        this.category = category;
        this.mode = mode;
        this.name = name;
        setLastCounter();
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PerformMode getMode() {
        return mode;
    }

    public Category getCategory() {
        return category;
    }

    public boolean hasCategory() {
        return category != Category.NONE;
    }

    public void resetKeyBind() {
        setKeyBind(Keyboard.KEY_NONE);
    }

    public int getKeyBind() {
        return key;
    }

    public void setKeyBind(int key) {
        this.key = key;
    }

    public boolean provideForgeEvents() {
        return forgeEvents;
    }

    public boolean hasKeyBind() {
        return getKeyBind() > Keyboard.KEY_NONE;
    }

    public String getKeyName() {
        return hasKeyBind() ? Keyboard.getKeyName(getKeyBind()) : null;
    }

    public void setLastCounter() {
        counter = tickDelay();
    }

    public void resetCounter() {
        counter = 0;
    }

    public void handleInit(ModuleHandler handler) {
        this.handler = handler;
        onHandlerInit();
    }

    public void handleTick() {
        if (!ticksStarted) {
            ticksStarted = true;
            onTicksStart();
        }
        if (counter >= tickDelay()) {
            resetCounter();
            onTick(utils.isInGame());
        }
        counter++;
    }

    protected ModuleHandler moduleHandler() {
        return handler;
    }

    protected void widgetMessage(String mess, WidgetMode mode) {
        handler.widgets().widgetMessage(new WidgetMessage(this, mess, mode));
    }

    protected void infoMessage(String mess, WidgetMode mode) {
        handler.widgets().infoMessage(new WidgetMessage(this, mess, mode));
    }

    protected void hideInfoMessage() {
        handler.widgets().hideInfoMessage(this);
    }

    protected GiveSelect giveSelector() {
        return (GiveSelect) handler.getModuleByClass(GiveSelect.class);
    }

    @Override
    public String toString() {
        return name;
    }

}