package ns.jovial.randomtweaks.reflect;

import java.util.stream.IntStream;

// A custom reflective map.
public class ReflectorMap<K, V>{
    private Entry<K, V>[] table;
    private int capacity = 1024;

    public ReflectorMap() { table = new Entry[capacity]; }

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

    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
