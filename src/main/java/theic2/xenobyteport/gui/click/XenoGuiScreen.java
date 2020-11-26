package theic2.xenobyteport.gui.click;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.api.gui.ElementAligment;
import theic2.xenobyteport.api.gui.GuiElement;
import theic2.xenobyteport.api.gui.PanelLayout;
import theic2.xenobyteport.api.gui.PanelSorting;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.gui.click.elements.Button;
import theic2.xenobyteport.gui.click.elements.Panel;
import theic2.xenobyteport.handlers.ModuleHandler;
import theic2.xenobyteport.render.Colors;
import theic2.xenobyteport.render.GuiScaler;
import theic2.xenobyteport.utils.Config;
import theic2.xenobyteport.utils.Keys;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class XenoGuiScreen extends GuiScreen {

    private Panel mainPanel, tempSetting;
    private List<GuiElement> elements;

    public XenoGuiScreen(ModuleHandler HAND) {
        elements = new CopyOnWriteArrayList<GuiElement>();
        mainPanel = new Panel(new Button(Xeno.mod_name, HAND.xenoGui().getKeyName(), ElementAligment.CENTER, Colors.ORANGE, Colors.ORANGE, Colors.ORANGE, Colors.WHITE, Colors.WHITE, Colors.WHITE) {
            @Override
            public void playClick() {
            }

            @Override
            public void onHovered() {
                hideElements();
            }

            @Override
            public void onKeyTyped(char symb, int key) {
                HAND.bind(HAND.xenoGui(), this, key);
            }
        }, PanelLayout.HORIZONTAL, PanelSorting.DEFAULT);
        for (Category CAT : Category.values()) {
            List<CheatModule> categoryed = HAND.categoryedModules().filter(m -> m.getCategory() == CAT).collect(Collectors.toList());
            if (!categoryed.isEmpty()) {
                Panel categoryPanel = new Panel(PanelLayout.VERTICAL, PanelSorting.ALPHABET);
                categoryed.forEach(MOD -> {
                    Panel settingsPanel = MOD.settingPanel();
                    Button modButton = new Button(MOD.getName(), MOD.getKeyName()) {
                        @Override
                        public void onInit() {
                            setSelected(HAND.isEnabled(MOD));
                        }

                        @Override
                        public void onKeyTyped(char symb, int key) {
                            HAND.bind(MOD, this, key);
                        }

                        @Override
                        public void onLeftClick() {
                            HAND.perform(MOD, this);
                        }

                        @Override
                        public String elementDesc() {
                            return MOD.moduleDesc();
                        }

                        @Override
                        public void onDraw() {
                            super.onDraw();
                            if (settingsPanel != null) {
                                int startX = isShowing(settingsPanel) ? getMaxX() + settingsPanel.getWidth() : getMaxX() - 1;
                                drawRect(startX, getY(), startX + 1, isShowing(settingsPanel) ? settingsPanel.getMaxY() : getMaxY(), Colors.ORANGE);
                            }
                        }

                        @Override
                        public void onHovered() {
                            if (tempSetting != null && tempSetting != settingsPanel) {
                                hideElement(tempSetting);
                            }
                            if (settingsPanel != null && !isShowing(settingsPanel)) {
                                tempSetting = settingsPanel;
                                settingsPanel.setPos(getMaxX() + settingsPanel.getWidth() > GuiScaler.scaledScreenWidth() ? getX() - settingsPanel.getWidth() : getMaxX(), getY() + settingsPanel.getHeight() > GuiScaler.scaledScreenHeight() ? getMaxY() - settingsPanel.getHeight() : getY());
                                showElement(settingsPanel);
                            }
                        }
                    };
                    categoryPanel.add(modButton);
                });
                categoryPanel.pack();
                mainPanel.add(new Button(CAT.toString()) {
                    @Override
                    public void playClick() {
                    }

                    @Override
                    public void onHovered() {
                        if (!isShowing(categoryPanel)) {
                            hideElements();
                            setSelected(true);
                            categoryPanel.setPos(getX(), getMaxY());
                            showElement(categoryPanel);
                        }
                    }
                });
            }
        }
        mainPanel.pack();
        showElement(mainPanel);
    }

    private boolean isShowing(GuiElement element) {
        return elements.contains(element);
    }

    private void showElement(GuiElement element) {
        elements.add(element);
        element.onShow();
    }

    private void hideElement(GuiElement element) {
        elements.remove(element);
        element.onHide();
    }

    private void hideElements() {
        elements.stream().filter(e -> e != mainPanel).forEach(this::hideElement);
        mainPanel.setSelected(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float ticks) {
        drawGradientRect(0, 0, width, height, Colors.WHITE_BG, Colors.NONE);
        GlStateManager.pushMatrix();
        GuiScaler.setGuiScale();
        elements.forEach(GuiElement::draw);
        if (Keys.isShiftDown()) {
            elements.forEach(GuiElement::drawDesc);
        }
        GlStateManager.popMatrix();
    }

    @Override
    public void keyTyped(char ch, int key) throws IOException {
        super.keyTyped(ch, key);
        if (Keys.isAvalible(key)) {
            elements.forEach(e -> e.keyTyped(ch, key));
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int key) {
        if (!elements.stream().anyMatch(p -> p.click(key))) {
            Xeno.utils.closeGuis();
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int scroll = Mouse.getEventDWheel();
        if (scroll != 0) {
            int dir = scroll > 0 ? 1 : -1;
            elements.forEach(e -> e.scroll(dir, isShiftKeyDown()));
        }
    }

    @Override
    public void initGui() {
        mainPanel.setPos(GuiScaler.scaledScreenWidth() / 2 - mainPanel.getWidth() / 2, 0);
        hideElements();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onGuiClosed() {
        Config.save();
    }

}