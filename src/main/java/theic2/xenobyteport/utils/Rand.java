package theic2.xenobyteport.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Rand {
    private static final String[] splashes = {
            "Жизнь тлен, когда в жопе член",
            "Хыыы BalastTm ыыыыы",
            "ЗаЛупа и двор стреляю в упор",
            "МАЙНКРАФТ СУКА ЭТО МОЯ ЖИЗНЬ",
            "Не фикс, а заглушка (с)4epB9Ik",
            "Иди python учи добоиб",
            "Ты прав, я пидорас (с)lLuffy",
            "Forge Fuck Fuck Team",
            "ХУЛИ ВЧЕРА НЕ ЛАГАЛО, А ТЕПЕРЬ ЛАГАЕТ? (с) extrimPenetration(LoaL)",
            "ПАСИБА ПАПАША ЧЕРВЯК ЗА НОТЧИТ 21 ВЕКА 20 ГОДА",
            "похуй на игроков (c) SyperYA",
            "what_is_it??",
            "lLuffy ne pidor",
            "Продам NotHack V5",
            "Цыгани выкроли коня",
            "LUAnotSUCC",
            "ЧервИк йбобаный, ля ты крыса!(c)CloudFire25/XSide14/Maleficarum",
            "Славик, сенобут  опять украли",
            "Деду эта нада(с)Зябалс",
            "Хочу что то кисленькое , например LSD",
            "@theic2 (telegram) жив..."
    };
    private static String[] formatColors = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static int num() {
        return num(-1337, 1337);
    }

    public static int num(int max) {
        return num(0, max);
    }

    public static int num(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static String str(String in) {
        return DigestUtils.md5Hex(in);
    }

    public static String str() {
        return str(UUID.randomUUID().toString());
    }

    public static boolean bool() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    public static int[] coords(int[] c, int r) {
        return new int[]{num(c[0] - r, c[0] + r), num(c[1] - r, c[1] + r), num(c[2] - r, c[2] + r)};
    }

    public static String formatColor() {
        return "§" + formatColors[num(formatColors.length)];
    }

    public static String formatSplash() {
        return formatColor().concat(splash());
    }

    public static String splash() {
        return splashes[num(splashes.length)];
    }

}
