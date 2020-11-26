package theic2.xenobyteport;

import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.modules.Credits;
import theic2.xenobyteport.modules.GuiReplacer;
import theic2.xenobyteport.modules.Widgets;
import theic2.xenobyteport.modules.XenoGui;
import theic2.xenobyteport.modules.base.ChatBind;
import theic2.xenobyteport.modules.modded.AdvancedTooltip;
import theic2.xenobyteport.modules.modded.CmdTest;
import theic2.xenobyteport.modules.modded.GiveSelect;
import theic2.xenobyteport.modules.modded.ScreenProtect;
import theic2.xenobyteport.modules.modded.hacks.astral.MagicNoFall;
import theic2.xenobyteport.modules.modded.hacks.betterquesting.FreeItemsv3;
import theic2.xenobyteport.modules.modded.hacks.flood.ConsoleFlood;
import theic2.xenobyteport.modules.modded.hacks.flood.CustomFloodHack;
import theic2.xenobyteport.modules.modded.hacks.flood.ErrorFloodHack;
import theic2.xenobyteport.modules.modded.hacks.hammercore.FreeItems;
import theic2.xenobyteport.modules.modded.hacks.loop.AutoFuckZaloopa;
import theic2.xenobyteport.modules.modded.hacks.loop.FuckZaloopa;
import theic2.xenobyteport.modules.modded.hacks.openfm.HardbassMusicHack;
import theic2.xenobyteport.modules.modded.hacks.openpc.OCShutDown;
import theic2.xenobyteport.modules.modded.hacks.railcraft.OneWayTicket;
import theic2.xenobyteport.modules.vanilla.*;

import java.util.ArrayList;

public class ModulesList extends ArrayList<CheatModule> {
    {
        add(new AdvancedTooltip());
        add(new RenderControl());
        add(new AntiKnockBack());
        add(new CreativeGive());
        add(new FakeCreative());
        add(new GuiReplacer());
        add(new FullBright());
        add(new AutoSprint());
        add(new TextRadar());
        add(new FluidWalk());
        add(new FakeItem());
        add(new ChatBind());
        add(new BlinkCam());
        add(new AutoDrop());
        add(new Credits());
        add(new XenoGui());
        add(new Widgets());
        add(new CmdTest());
        add(new HiJump());
        add(new Spider());
        add(new NoFall());
        add(new NoRain());
        add(new NoWeb());
        add(new Step());
        add(new XRay());
        add(new XRaySelect());
        add(new GiveSelect());
        add(new VanilaNuker());
        add(new Esp());
        add(new XenoFly());
        add(new ScreenProtect());

        add(new ConsoleFlood());
        add(new MagicNoFall());
        add(new FreeItemsv3());
        add(new CustomFloodHack());
        add(new ErrorFloodHack());
        add(new FreeItems());
        add(new HardbassMusicHack());
        add(new OneWayTicket());
        add(new OCShutDown());

        add(new FuckZaloopa());
        add(new AutoFuckZaloopa());
    }
}