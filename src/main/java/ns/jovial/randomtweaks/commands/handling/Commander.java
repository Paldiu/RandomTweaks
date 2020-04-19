package ns.jovial.randomtweaks.commands.handling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Commander {
    private final String commandName;
    private final Class<?> commandClass;
    private final String description;
    private final String usage;
    private final List<String> aliases;

    public Commander(Class<?> clazz, String name, String description, String usage, String aliases) {
        this.commandName = name;
        this.commandClass = clazz;
        this.description = description;
        this.usage = usage;
        this.aliases = ("".equals(aliases) ? new ArrayList<>() : Arrays.asList(aliases.split(",")));
    }

    public List<String> getAliases() {
        return Collections.unmodifiableList(aliases);
    }

    public Class<?> getCommandClass() {
        return commandClass;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("commandName: ").append(commandName);
        sb.append("\ncommandClass: ").append(commandClass.getName());
        sb.append("\ndescription: ").append(description);
        sb.append("\nusage: ").append(usage);
        sb.append("\naliases: ").append(aliases);
        return sb.toString();
    }
}
