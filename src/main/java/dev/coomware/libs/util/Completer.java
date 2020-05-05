package dev.coomware.libs.util;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Completer {
    // Constructor
    public Completer(){}

    public List<String> possibleCompletions(@NotNull String...complete) {
        List<String> comp = new ArrayList<>();
        for (String s : complete) {
            comp.add(s);
        }
        return comp;
    }

    public List<String> nameCompleter() {
        List<String> pNames = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(p -> {
            pNames.add(p.getName());
        });
        return pNames;
    }
}
