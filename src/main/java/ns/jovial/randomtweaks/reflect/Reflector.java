package ns.jovial.randomtweaks.reflect;

import ns.jovial.randomtweaks.RandomTweaks;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class Reflector {
    private final Reflections reflections;
    private final Package aPackage;
    private final Class<?> cls;
    private final Object mutationLock;
    private final List<Plugin> plugins;

    /**
     * Initializer!
     * This constructor prepares our reflector for the numerous defined methods.
     * The reflector should be limited to one single instance.
     * This WILL run on the main server thread.
     * This is mainly used for the Command Handler.
     * When registering commands, create a new instance of this using a command class
     * which is defined in its own folder.
     * PLEASE DO THIS IN YOUR MAIN CLASS!
     *
     * @param clazz -> The class in which to call the reflections from.
     */
    public Reflector(Class<?> clazz) {
        cls = clazz;
        aPackage = cls.getPackage();
        mutationLock = new Object();
        plugins = new ArrayList<>();
        for (Plugin plug : Bukkit.getServer().getPluginManager().getPlugins()) {
            if (plug.getDescription().getDepend().contains(RandomTweaks.pluginName)) {
                plugins.add(plug);
            }
        }
        plugins.add(RandomTweaks.plugin);
        synchronized(mutationLock) {
            reflections = new Reflections(clazz.getPackage().getName());
        }
    }

    /**
     * A basic reflection getter
     * @return A reflective interface
      */
    public Reflections reflect() {
        return reflections;
    }

    /**
     * Gets the first listed dependency.
     * @return The first listed plugin which uses this as a dependency.
     */
    public Plugin getFirstDepend() {
        return plugins.get(0);
    }

    /**
     * Gets a dependency based on its location on the list.
     * @param index The index at which to call from the list.
     * @return The retrieved plugin
     * @throws IndexOutOfBoundsException if the index provided is larger or smaller than the actual size of the list.
     */
    public Plugin getDepend(int index) throws IndexOutOfBoundsException {
        return plugins.get(index);
    }

    /**
     * Gets the entire list of plugins that are currently dependent on this library.
     * @return A list of plugins which have RandomTweaks as a dependency.
     */
    public List<Plugin> getPlugins() {
        return plugins;
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

    /**
     * @return the native minecraft version.
     */
    public String getNMSVersion() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }

    /**
     * Gets a file from the plugin.
     * @param plugin The plugin to call from
     * @param name The name of the file.
     * @return The plugin file.
     */
    public File getPluginFile(Plugin plugin, String name) {
        return new File(plugin.getDataFolder(), name);
    }
}
