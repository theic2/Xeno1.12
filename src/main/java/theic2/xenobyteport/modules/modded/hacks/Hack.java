package theic2.xenobyteport.modules.modded.hacks;

import theic2.xenobyteport.api.module.Category;
import theic2.xenobyteport.api.module.CheatModule;
import theic2.xenobyteport.api.module.PerformMode;

public abstract class Hack extends CheatModule {
    private final boolean isWorking;

    public Hack(String name, Category category, PerformMode mode) {
        super(name, category, mode);
        boolean works = true;
        try {
            hInit();
        } catch (Throwable e) {
            works = false;
        }
        isWorking = works;
    }

    @Override
    public boolean isWorking() {
        return isWorking;
    }

    protected abstract void hInit() throws Throwable;
}
