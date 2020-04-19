package ns.jovial.randomtweaks.reflect;

import ns.jovial.randomtweaks.RandomTweaks;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

public class Reflector {
    private final Reflections reflections;
    private final Package aPackage;
    private final Class<?> cls;
    private final Object mutationLock;

    public Reflector(Class<?> clazz) {
        this.cls = clazz;
        aPackage = cls.getPackage();
        mutationLock = new Object();

        synchronized(mutationLock) {
            reflections = new Reflections(clazz.getPackage().getName());
        }
    }

    public Reflections reflect() {
        return reflections;
    }

    public interface ListAndMap {
        ReflectorList<?> list();
        ReflectorMap<?, ?> map();
    }

    public Set<Class<?>> getAnnotatedClasses(Class<? extends Annotation> annotation) {
        return reflect().getTypesAnnotatedWith(annotation);
    }

    @SuppressWarnings("unchecked")
    public <T> T getField(Object from, String name) {
        Class<?> clazz = from.getClass();
        do {
            try {
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                return (T) field.get(from);
            } catch (NoSuchFieldException | IllegalAccessException ignored) {}
        } while (clazz.getSuperclass() != Object.class
                && ((clazz = clazz.getSuperclass()) != null));

        return null;
    }
}
