package theic2.xenobyteport.utils;

import net.minecraft.client.settings.KeyBinding;
import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.api.module.CheatModule;

import static org.lwjgl.input.Keyboard.*;

public class Keys {

    private static final boolean[] pressed = new boolean[256];

    public static boolean isPressed(int key) {
        return isKeyDown(key) != pressed[key] && (pressed[key] = !pressed[key]);
    }

    public static boolean isShiftDown() {
        return isKeyDown(KEY_LSHIFT) || isKeyDown(KEY_RSHIFT);
    }

    public static boolean isPressed(KeyBinding key) {
        return isKeyDown(key.getKeyCode());
    }

    public static boolean isPressed(CheatModule module) {
        if (isPressed(module.getKeyBind())) {
            return module.inGuiPerform() || Xeno.utils.isInGameGui();
        }
        return false;
    }

    public static boolean isAvalible(int key) {
        switch (key) {
            case KEY_NONE:
            case KEY_ESCAPE:
            case KEY_SYSRQ:
            case KEY_LSHIFT:
            case KEY_LCONTROL:
            case KEY_LMENU:
            case KEY_BACK:
                return false;
        }
        return true;
    }

}