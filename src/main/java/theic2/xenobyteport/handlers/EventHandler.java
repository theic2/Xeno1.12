package theic2.xenobyteport.handlers;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.render.GuiScaler;
import theic2.xenobyteport.utils.Config;
import theic2.xenobyteport.utils.EventRegisterer;
import theic2.xenobyteport.utils.Keys;

public class EventHandler {

    private ModuleHandler handler;

    public EventHandler(ModuleHandler handler) {
        this.handler = handler;
        handler.enabledModules().filter(CheatModule::provideForgeEvents).forEach(EventRegisterer::register);
        EventRegisterer.register(this);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void guiInit(InitGuiEvent.Pre e) {
        GuiScaler.updateResolution();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void drawGuiScreen(DrawScreenEvent.Pre e) {
        if (GuiScaler.isGuiCreated()) {
            GuiScaler.updateMouse(e.getMouseX(), e.getMouseY());
        }
    }

    @SubscribeEvent
    public void logOut(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        Config.save();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void logIn(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        new PacketHandler(handler, (NetHandlerPlayClient) e.getHandler());
    }

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent e) {
        if (Xeno.utils.isInGame()) {
            handler.workingModules().filter(CheatModule::hasKeyBind).filter(Keys::isPressed).findFirst().ifPresent(handler::perform);
        }
        handler.enabledModules().forEach(CheatModule::handleTick);
    }

    @SubscribeEvent
    public void drawGuiOverlay(RenderGameOverlayEvent.Post e) {
        if (e.getType() == ElementType.ALL && GuiScaler.isGuiCreated()) {
            GlStateManager.pushMatrix();
            GuiScaler.setOnTop();
            GuiScaler.setGuiScale();
            handler.enabledModules().forEach(CheatModule::onDrawGuiOverlay);
            GlStateManager.popMatrix();
        }
    }

    @SubscribeEvent
    public void renderTick(TickEvent.RenderTickEvent e) {
        switch (e.phase) {
            case START:
                GuiScaler.updateResolution();
                break;
            case END:
                if (GuiScaler.isGuiCreated()) {
                    GlStateManager.pushMatrix();
                    GuiScaler.setOnTop();
                    GuiScaler.setGuiScale();
                    GlStateManager.disableLighting();
                    handler.enabledModules().forEach(CheatModule::onDrawGuiLast);
                    GlStateManager.popMatrix();
                }
        }
    }

}