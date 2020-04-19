package ns.jovial.randomtweaks.commands.handling;

import ns.jovial.randomtweaks.RandomTweaks;
import org.bukkit.Bukkit;
import org.bukkit.command.*;

import java.util.*;

public class CommandLoader {
    private final List<Commander> commandList;

    private CommandLoader() {
        commandList = new ArrayList<>();
    }

    public void scan() {
        CommandMap map = getCommandMap();
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
               unregisterCommand(existing, map);
           }
           map.register(RandomTweaks.plugin.getDescription().getName(), dynamic);
        });
        Bukkit.getLogger().info("Successfully loaded all commands!");
    }

    public void unregisterCommand(String commandName) {
        CommandMap map = getCommandMap();
        if (map != null) {
            Command command = map.getCommand(commandName.toLowerCase());
            if (command != null) {
                unregisterCommand(command, map);
            }
        }
    }

    public void unregisterCommand(Command command, CommandMap commandMap) {
        try {
            command.unregister(commandMap);
            HashMap<String, Command> knownCommands= getKnownCommands(commandMap);
            if (knownCommands != null) {
                knownCommands.remove(command.getName());
                command.getAliases().forEach(knownCommands::remove);
            }
        } catch (Exception ex) {
            Bukkit.getLogger().severe(ex.getLocalizedMessage());
        }
    }

    public CommandMap getCommandMap() {
        Object commandMap = RandomTweaks.reflector.getField(Bukkit.getServer().getPluginManager(), "commandMap");
        if (commandMap != null) {
            if (commandMap instanceof CommandMap) {
                return (CommandMap) commandMap;
            }
        }
        return null;
    }

    private Collection<? extends Commander> getCommands() {
        List<Commander> commanderList = new ArrayList<>();
        RandomTweaks.reflector.getAnnotatedClasses(CommandParameters.class).forEach(clazz -> {
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
        return Collections.unmodifiableCollection(commanderList);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, Command> getKnownCommands(CommandMap commandMap) {
        Object knownCommands = RandomTweaks.reflector.getField(commandMap, "knownCommands");
        if (knownCommands != null) {
            if (knownCommands instanceof HashMap) {
                return (HashMap<String, Command>) knownCommands;
            }
        }
        return null;
    }

    public static CommandLoader getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        private static final CommandLoader INSTANCE = new CommandLoader();
    }
}
