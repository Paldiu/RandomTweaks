package ns.jovial.randomtweaks;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.jetbrains.annotations.NotNull;

public abstract class HoverLibrary {

    private HoverLibrary() {
        throw new AssertionError();
    }

    public abstract TextComponent hovering(HoverEvent event, ChatColor color, String hoverLabel, String hoverBody);

    public static TextComponent HoverLibrary(HoverEvent event, ChatColor color, String fuzzy, @NotNull String...fuzzies) {
        TextComponent textComponent = new TextComponent(fuzzy);
        textComponent.setColor(color);
        StringBuilder sb = new StringBuilder();
        for (String fuzz : fuzzies) {
            sb.append(fuzz + "\n");
        }
        textComponent.setHoverEvent(new HoverEvent(event.getAction(), new ComponentBuilder(sb.toString()).create()));
        return textComponent;
    }
}