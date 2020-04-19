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

    /**
     * Initializer!
     * This constructor prepares our reflector for numerous methods.
     * @param clazz -> The class in which to call the reflections from.
     */
    public Reflector(Class<?> clazz) {
        this.cls = clazz;
        aPackage = cls.getPackage();
        mutationLock = new Object();

        synchronized(mutationLock) {
            reflections = new Reflections(clazz.getPackage().getName());
        }
    }

    /**
     * A basic reflection
     * @return A reflective interface
      */
    public Reflections reflect() {
        return reflections;
    }

    /**
     * A simple getter for private Class<?> cls;
     * @return the class file initialized by the constructor
     */
    public Class<?> getDefClass() {
        return cls;
    }

    /**
     * A simple getter for private Package aPackage;
     * @return the package file initialized by the constructor.
     */
    public Package getDefPackage() {
        return aPackage;
    }

    /**
     * Gets any classes within the scope of the reflection package which has your provided type of @Annotation present.
     * @param annotation -> The annotation to check for
     */
    public Set<Class<?>> getAnnotatedClasses(Class<? extends Annotation> annotation) {
        return reflect().getTypesAnnotatedWith(annotation);
    }

    /**
     * Simple field reflection. Typically requires you to have predetermined knowledge of the field you are trying to receive.
     * @param from -> The object to get the field from
     * @param name -> The field name
     * @param <T> -> This allows you to call from generic types, this parameter isn't actually necessary to be supplied.
     * @return The field in question as an object.
     */
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
