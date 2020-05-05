package dev.coomware.libs.reflect;

// A custom reflective map.
public class ReflectorMap<K, V>{
    private Entry<K, V>[] table;
    private int capacity = 1024;

    /**
     * Constructor. Initialized in the exact same way as a regular Map.
     */
    public ReflectorMap() { table = new Entry[capacity]; }

    /**
     * Adds a new entry to the map.
     * K and V are both generic type parameters.
     * These can be replaced with just about anything.
     * @param key The object to use as a reference key for a relative stored value.
     * @param value The value to store in reference to the object key.
     */
    public void put(K key, V value) {
        if (key == null) return;

        int hash = hash(key);
        Entry<K, V> newEntry = new Entry<K,V>(key, value, null);
        if (table[hash] == null) {
            table[hash] = newEntry;
        } else {
            Entry<K,V> previous = null;
            Entry<K,V> current = table[hash];

            while (current != null) {
                if (current.key.equals(key)) {
                    newEntry.next = current.next;
                    if (previous == null) {
                        table[hash] = newEntry;
                    } else {
                        previous.next = newEntry;
                    }
                    return;
                }
                previous = current;
                current = current.next;
            }
            previous.next = newEntry;
        }
    }

    /**
     * Gets the stored value relative to the Object key.
     * @param key The object key to reference for the stored value
     * @return The value stored.
     */
    public V get(K key) {
        int hash = hash(key);
        if (table[hash] != null) {
            Entry<K, V> temp = table[hash];
            while (temp != null) {
                if (temp.key.equals(key)) return temp.value;
                temp = temp.next;
            }
        }
        return null;
    }

    /**
     * Removes the specified Key and it's relative Value.
     * @param key The key to remove.
     * @return true if the key has been removed, false if the key was null (never existed to begin with).
     */
    public boolean remove(K key) {
        int hash = hash(key);
        if (table[hash] != null) {
            Entry<K, V> previous = null;
            Entry<K, V> current = table[hash];

            while (current != null) {
                if (current.key.equals(key)) {
                    if (previous == null) {
                        table[hash] = table[hash].next;
                    } else {
                        previous.next = current.next;
                    }
                    return true;
                }
                previous = current;
                current = current.next;
            }
        }
        return false;
    }

    /**
     * Displays all stored keys and values in a StringBuilder object.
     * @return the StringBuilder object as a string.
     */
    public String display() {
        StringBuilder sb = new StringBuilder();
        int bound = capacity;
        sb.append("Collection of all keys to values displayed as \\{Key=Value\\}\n");
        for (int i = 0; i < bound; i++) {
            if (table[i] != null) {
                Entry<K, V> entry = table[i];
                while (entry != null) {
                    sb.append("{")
                            .append(entry.key)
                            .append("=")
                            .append(entry.value)
                            .append("}\n");
                    entry = entry.next;
                }
            }
        }
        return sb.toString();
    }

    /**
     * This generates a new hash code for the map based on the key received,
     * and the capacity of the hashmap.
     * @param key The key to be hashed
     * @return The hashed key.
     */
    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    /**
     * A container class which holds physical representations of the entries of the hashmap.
     * @param <K> A key type object. Can be any non abstract element.
     * @param <V> A value to return. Can be just about any type of non abstract element.
     */
    static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        /**
         * A new instance of the HashMap entry
         * @param key The new entry key to add.
         * @param value The new value to add.
         * @param next The next entry (queued)
         */
        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
