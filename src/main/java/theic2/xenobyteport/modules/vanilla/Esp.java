package theic2.xenobyteport.modules.vanilla;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import theic2.xenobyteport.api.config.Cfg;
import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;
import theic2.xenobyteport.api.render.IDraw;
import theic2.xenobyteport.gui.click.elements.Button;
import theic2.xenobyteport.gui.click.elements.Panel;
import theic2.xenobyteport.gui.click.elements.ScrollSlider;

import java.util.ArrayList;
import java.util.List;

public class Esp extends CheatModule {

    @Cfg("bindLines")
    private boolean bindLines;
    @Cfg("monsters")
    private boolean monsters;
    @Cfg("players")
    private boolean players;
    @Cfg("animals")
    private boolean animals;
    @Cfg("blocks")
    private boolean blocks;
    @Cfg("lines")
    private boolean lines;
    @Cfg("radius")
    private int radius;
    @Cfg("drop")
    private boolean drop;
    private List<EspObject> objects;
    private double[] startLines;
    private boolean bobbing;

    public Esp() {
        super("Esp", Category.WORLD, PerformMode.TOGGLE);
        objects = new ArrayList<EspObject>();
        startLines = new double[3];
        bindLines = true;
        players = true;
        blocks = true;
        lines = true;
        radius = 100;
    }

    @Override
    public void onTick(boolean inGame) {
        if (inGame) {
            List<EspObject> out = new ArrayList<EspObject>();
            utils.nearEntityes(radius)
                    .forEach(e -> {
                        if (players && utils.isPlayer(e)) {
                            out.add(new EspObject(e, 1, 0, 1));
                        } else if (monsters && utils.isMonster(e)) {
                            out.add(new EspObject(e, 1, 0, 0));
                        } else if (animals && utils.isAnimal(e)) {
                            out.add(new EspObject(e, 0, 1, 0));
                        } else if (drop && e instanceof EntityItem) {
                            out.add(new EspObject(e, 1, 1, 0));
                        }
                    });
            objects.clear();
            objects.addAll(out);
            utils.mc().gameSettings.viewBobbing = !lines || !bindLines || objects.isEmpty();
        }
    }

    @Override
    public void onDisabled() {
        utils.mc().gameSettings.viewBobbing = true;
        startLines = new double[3];
    }

    @SubscribeEvent
    public void worldRender(RenderWorldLastEvent e) {
        objects.forEach(EspObject::draw);
    }

    @Override
    public String moduleDesc() {
        return "Подсветка заданных объектов в мире";
    }

    @Override
    public Panel settingPanel() {
        return new Panel(
                new Button("EspBlock", blocks) {
                    @Override
                    public void onLeftClick() {
                        buttonValue(blocks = !blocks);
                    }

                    @Override
                    public String elementDesc() {
                        return "Отрисовка блока";
                    }
                },
                new Button("TracerLine", lines) {
                    @Override
                    public void onLeftClick() {
                        buttonValue(lines = !lines);
                    }

                    @Override
                    public String elementDesc() {
                        return "Отрисовка линий";
                    }
                },
                new Button("BindLines", bindLines) {
                    @Override
                    public void onLeftClick() {
                        buttonValue(bindLines = !bindLines);
                    }

                    @Override
                    public String elementDesc() {
                        return "Привязка линий к курсору";
                    }
                },
                new Button("Monsters", monsters) {
                    @Override
                    public void onLeftClick() {
                        buttonValue(monsters = !monsters);
                    }

                    @Override
                    public String elementDesc() {
                        return "Отображать монстров";
                    }
                },
                new Button("Animals", animals) {
                    @Override
                    public void onLeftClick() {
                        buttonValue(animals = !animals);
                    }

                    @Override
                    public String elementDesc() {
                        return "Отображать животных";
                    }
                },
                new Button("Players", players) {
                    @Override
                    public void onLeftClick() {
                        buttonValue(players = !players);
                    }

                    @Override
                    public String elementDesc() {
                        return "Отображать игроков";
                    }
                },
                new Button("Drop", drop) {
                    @Override
                    public void onLeftClick() {
                        buttonValue(drop = !drop);
                    }

                    @Override
                    public String elementDesc() {
                        return "Отображать выброшенные предметов";
                    }
                },
                new ScrollSlider("Radius", radius, 200) {
                    @Override
                    public void onScroll(int dir, boolean withShift) {
                        radius = processSlider(dir, withShift);
                    }

                    @Override
                    public String elementDesc() {
                        return "Радиус поиска объектов";
                    }
                }
        );
    }

    class EspObject implements IDraw {

        final double x, y, z;
        final float r, g, b;

        EspObject(Entity ent, float r, float g, float b) {
            x = ent.posX;
            y = ent.posY;
            z = ent.posZ;
            this.r = r;
            this.g = g;
            this.b = b;
        }

        @Override
        public void draw() {
            if ((bindLines) || (startLines[0] == 0D && startLines[1] == 0D && startLines[2] == 0D)) {
                startLines[0] = Minecraft.getMinecraft().getRenderManager().viewerPosX;
                startLines[1] = Minecraft.getMinecraft().getRenderManager().viewerPosY;
                startLines[2] = Minecraft.getMinecraft().getRenderManager().viewerPosZ;
            }
            if (blocks) {
                render.WORLD.drawEspBlock(x - 0.5, y, z - 0.5, r, g, b, 0.4F, 1);
            }
            if (lines) {
                render.WORLD.drawEspLine(startLines[0], startLines[1], startLines[2], x, y, z, r, g, b, 0.6F, 1.5F);
            }
        }

    }

}
