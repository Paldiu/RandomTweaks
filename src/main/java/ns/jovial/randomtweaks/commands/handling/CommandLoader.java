package ns.jovial.randomtweaks.commands.handling;

import ns.jovial.randomtweaks.RandomTweaks;
import ns.jovial.randomtweaks.reflect.ReflectorList;
import ns.jovial.randomtweaks.reflect.ReflectorMap;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class CommandLoader {
    private final List<Commander> commandList;

    /**
     * Create a new instance. Only accessed through the instance holder.
     */
    private CommandLoader() {
        commandList = new ArrayList<>();
    }

    /**
     * Scans Spigots' provided command map for any commands which match commands provided by this plugin
     * Then forces this plugin to take control. This also registers our commands with the command map.
     */
    @SuppressWarnings("unchecked")
    public void scan() {
        CommandMap map = getCommandMap();
        Plugin plug = RandomTweaks.reflect.getPlugin();
        if (map == null) {
            Bukkit.getLogger().severe("Error loading command map!");
            return;
        }
        commandList.clear();
        commandList.addAll(getCommands());

        commandList.stream().forEach(command -> {
           Dynamic dynamic = new Dynamic(command);
           Command existing = map.getCommand(dynamic.getName());
           if (existing != null) {
               dynamic.getAliases().forEach(alias -> {
                  if (existing.getAliases().contains(alias)) {
                      existing.setAliases(new ArrayList<>());
                  }
               });
               unregisterCommand(existing, map);
           }
           map.register(plug.getDescription().getName(), dynamic);
        });
        Bukkit.getLogger().info("Successfully loaded all commands!");
    }

    /**
     * This unregisters a command from the server command map based on the name of the command.
     * @param commandName The command to remove from the Command Map.
     */
    public void unregisterCommand(String commandName) {
        CommandMap map = getCommandMap();
        if (map != null) {
            Command command = map.getCommand(commandName.toLowerCase());
            if (command != null) {
                unregisterCommand(command, map);
            }
        }
    }

    /**
     * Unregisters a command with the actual Command instance, in relation to the supplied command map.
     * @param command The command to be unregistered (specific instance)
     * @param commandMap The command map to access (which should contain the Command instance).
     */
    public void unregisterCommand(Command command, CommandMap commandMap) {
        try {
            command.unregister(commandMap);
            Map<String, Command> knownCommands = getKnownCommands(commandMap);
            if (knownCommands != null) {
                knownCommands.remove(command.getName());
                command.getAliases().forEach(knownCommands::remove);
            }
        } catch (Exception ex) {
            Bukkit.getLogger().severe(ex.getLocalizedMessage());
        }
    }

    /**
     * Gets the server command map.
     * @return The server's command map.
     */
    public CommandMap getCommandMap() {
        Object commandMap = RandomTweaks.reflect.getField(Bukkit.getServer().getPluginManager(), "commandMap");
        if (commandMap != null) {
            if (commandMap instanceof CommandMap) {
                return (CommandMap) commandMap;
            }
        }
        return null;
    }

    /**
     * Gets all commands which have been annotated with CommandParameters.class
     * These commands should be listed in their own folder, and all should extend CommandBase
     * @return A new list containing new commander instances, which holds all the information for each command.
     */
    private List<? extends Commander> getCommands() {
        List<Commander> commanderList = new ArrayList<>();
        RandomTweaks.reflect.getAnnotatedClasses(CommandParameters.class).forEach(clazz -> {
            CommandParameters cp = clazz.getAnnotation(CommandParameters.class);
            if (cp != null) {
                Commander commander = new Commander(
                        clazz,
                        clazz.getSimpleName(),
                        cp.description(),
                        cp.usage(),
                        cp.aliases());
                commanderList.add(commander);
            }
        });
        return commanderList;
    }

    /**
     * A method to retrieve all the commands that are already registered with the provided command map.
     * @param commandMap The command map to check
     * @return A new map containing all commands that have already been registered.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Command> getKnownCommands(CommandMap commandMap) {
        Object knownCommands = RandomTweaks.reflect.getField(commandMap, "knownCommands");
        if (knownCommands != null) {
            if (knownCommands instanceof HashMap) {
                return (Map<String, Command>) knownCommands;
            }
        }
        return null;
    }

    /**
     * This ensures there is only ever one instance of the Command Loader, provided by the command loader.
     * @return This
     */
    public static CommandLoader getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Simple instance holder to ensure continuity.
     */
    private static class InstanceHolder {
        private static final CommandLoader INSTANCE = new CommandLoader();
    }
}
