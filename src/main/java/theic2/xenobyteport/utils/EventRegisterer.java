package theic2.xenobyteport.utils;

import com.google.common.reflect.TypeToken;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EventRegisterer {

    public static void register(Object o) {
        registerBus(MinecraftForge.EVENT_BUS, o);
    }

    public static void unregister(Object o) {
        MinecraftForge.EVENT_BUS.unregister(o);
    }

    private static ConcurrentHashMap<Object, List<IEventListener>> getListeners(EventBus bus) {
        return Reflections.getPrivateValue(EventBus.class, bus, "listeners");
    }

    private static void registerBus(EventBus bus, Object target) {
        Map<Object, ModContainer> listenerOwners = Reflections.getPrivateValue(EventBus.class, bus, "listenerOwners");
        if (getListeners(bus).containsKey(target)) {
            return;
        }
        ModContainer activeModContainer = Loader.instance().getMinecraftModContainer();
        listenerOwners.put(target, activeModContainer);
        Reflections.setPrivateValue(EventBus.class, bus, listenerOwners, "listenerOwners");
        Set<? extends Class<?>> supers = TypeToken.of(target.getClass()).getTypes().rawTypes();
        for (Method method : target.getClass().getMethods()) {
            for (Class<?> cls : supers) {
                try {
                    Method real = cls.getDeclaredMethod(method.getName(), method.getParameterTypes());
                    if (real.isAnnotationPresent(SubscribeEvent.class)) {
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        Class<?> eventType = parameterTypes[0];
                        int busID = Reflections.getPrivateValue(EventBus.class, bus, "busID");
                        ConcurrentHashMap<Object, List<IEventListener>> listeners = getListeners(bus);
                        Constructor<?> ctr = eventType.getConstructor();
                        ctr.setAccessible(true);
                        Event event = (Event) ctr.newInstance();
                        ASMEventHandler listener = new ASMEventHandler(target, method, activeModContainer, false);
                        event.getListenerList().register(busID, listener.getPriority(), listener);
                        List<IEventListener> others = listeners.get(target);
                        if (others == null) {
                            others = new ArrayList<>();
                            listeners.put(target, others);
                            Reflections.setPrivateValue(EventBus.class, bus, listeners, "listeners");
                        }
                        others.add(listener);
                        break;
                    }
                } catch (Exception e) {
                }
            }
        }
    }

}
