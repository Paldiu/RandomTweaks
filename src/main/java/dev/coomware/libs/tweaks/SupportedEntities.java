package dev.coomware.libs.tweaks;

import org.bukkit.block.Block;
import org.bukkit.entity.*;

import java.util.HashMap;
import java.util.Map;

public final class SupportedEntities {

    public static Map<String, EntityType> getAllEntities() {
        Map<String, EntityType> typeMap = new HashMap<>();
        typeMap.putAll(getBoats());
        typeMap.putAll(getMinecarts());
        typeMap.putAll(getAllProjectiles());
        typeMap.putAll(getItems());
        typeMap.putAll(getCreatures());
        return typeMap;
    }

    public static Map<String, EntityType> getFallingBlocks() {
        Map<String, EntityType> typeMap = new HashMap<>();
        for (EntityType type : EntityType.values()) {
            try {
                if (type.name() != null) {
                    if (FallingBlock.class.isAssignableFrom(type.getEntityClass())) {
                        typeMap.put(type.name().toLowerCase(), type);
                    }
                }
            } catch (Exception ignored) { }
        }
        return typeMap;
    }

    public static Map<String, EntityType> getOtherBlocks() {
        Map<String, EntityType> typeMap = new HashMap<>();
        for (EntityType type : EntityType.values()) {
            try {
                if (type.name() != null) {
                    if (Block.class.isAssignableFrom(type.getEntityClass())) {
                        typeMap.put(type.name().toLowerCase(), type);
                    }
                }
            } catch (Exception ignored) { }
        }
        return typeMap;
    }

    public static Map<String, EntityType> getAllBlocks() {
        Map<String, EntityType> typeMap = new HashMap<>();
        typeMap.putAll(getFallingBlocks());
        typeMap.putAll(getOtherBlocks());
        return typeMap;
    }

    public static Map<String, EntityType> getItems() {
        Map<String, EntityType> typeMap = new HashMap<>();
        for (EntityType type : EntityType.values()) {
            try {
                if (type.name() != null) {
                    if (Item.class.isAssignableFrom(type.getEntityClass())) {
                        typeMap.put(type.name().toLowerCase(), type);
                    }
                }
            } catch (Exception ignored) { }
        }
        return typeMap;
    }

    public static Map<String, EntityType> getThrowableProjectiles() {
        Map<String, EntityType> typeMap = new HashMap<>();
        for (EntityType type : EntityType.values()) {
            try {
                if (type.name() != null) {
                    if (ThrowableProjectile.class.isAssignableFrom(type.getEntityClass())) {
                        typeMap.put(type.name().toLowerCase(), type);
                    }
                }
            } catch (Exception ignored) { }
        }
        return typeMap;
    }

    public static Map<String, EntityType> getProjectiles() {
        Map<String, EntityType> typeMap = new HashMap<>();
        for (EntityType type : EntityType.values()) {
            try {
                if (type.name() != null) {
                    if (Projectile.class.isAssignableFrom(type.getEntityClass())) {
                        typeMap.put(type.name().toLowerCase(), type);
                    }
                }
            } catch (Exception ignored) { }
        }
        return typeMap;
    }

    public static Map<String, EntityType> getAnimals() {
        Map<String, EntityType> typeMap = new HashMap<>();
        for (EntityType type : EntityType.values()) {
            try {
                if (type.name() != null) {
                    if (Animals.class.isAssignableFrom(type.getEntityClass())) {
                        typeMap.put(type.name().toLowerCase(), type);
                    }
                }
            } catch (Exception ignored) { }
        }
        return typeMap;
    }

    public static Map<String, EntityType> getCreatures() {
        Map<String, EntityType> typeMap = new HashMap<>();
        for (EntityType type : EntityType.values()) {
            try {
                if (type.name() != null) {
                    if (Creature.class.isAssignableFrom(type.getEntityClass())) {
                        typeMap.put(type.name().toLowerCase(), type);
                    }
                }
            } catch (Exception ignored) { }
        }
        return typeMap;
    }

    public static Map<String, EntityType> getMinecarts() {
        Map<String, EntityType> typeMap = new HashMap<>();
        for (EntityType type : EntityType.values()) {
            try {
                if (type.name() != null) {
                    if (Minecart.class.isAssignableFrom(type.getEntityClass())) {
                        typeMap.put(type.name().toLowerCase(), type);
                    }
                }
            } catch (Exception ignored) { }
        }
        return typeMap;
    }

    public static Map<String, EntityType> getBoats() {
        Map<String, EntityType> typeMap = new HashMap<>();
        for (EntityType type : EntityType.values()) {
            try {
                if (type.name() != null) {
                    if (Boat.class.isAssignableFrom(type.getEntityClass())) {
                        typeMap.put(type.name().toLowerCase(), type);
                    }
                }
            } catch (Exception ignored) { }
        }
        return typeMap;
    }

    public static Map<String, EntityType> getAllClearableItems() {
        Map<String, EntityType> typeMap = new HashMap<>();
        typeMap.putAll(getItems());
        typeMap.putAll(getAllProjectiles());
        return typeMap;
    }

    public static Map<String, EntityType> getAllProjectiles() {
        Map<String, EntityType> typeMap = new HashMap<>();
        typeMap.putAll(getProjectiles());
        typeMap.putAll(getThrowableProjectiles());
        return typeMap;
    }
}
