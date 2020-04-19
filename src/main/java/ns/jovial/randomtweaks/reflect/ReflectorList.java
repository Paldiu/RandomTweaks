package ns.jovial.randomtweaks.reflect;

import java.util.ArrayList;
import java.util.Arrays;

public class ReflectorList<E> {
    private int size = 0;
    private int default_capacity = 10;
    private Object elements[];

    public ReflectorList() {
        elements = new Object[default_capacity];
    }

    public ReflectorList(int capacity) {
        elements = new Object[capacity];
    }

    public void add(E e) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = e;
    }

    public void addAll(ArrayList<E> eList) {
        eList.stream().forEach(e -> {
           add(e);
        });
    }

    public E get(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + 1 + ", Size " + i);
        }
        return (E) elements[i];
    }

    public E remove(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + 1 + ", Size " + i);
        }

        Object item = elements[i];
        int numElts = elements.length - ( i + 1 );
        System.arraycopy(elements, i + 1, elements, i, numElts);
        size--;
        return (E) item;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for(int i = 0; i < size; i++) {
            sb.append(elements[i].toString());
            if (i < size - 1) {
                sb.append(",");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    private void ensureCapacity() {
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
    }
}
