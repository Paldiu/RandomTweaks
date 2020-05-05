package dev.coomware.libs.reflect;

import java.util.ArrayList;
import java.util.Arrays;

public class ReflectorList<E> {
    private int size = 0;
    private int default_capacity = 10;
    private Object elements[];

    /**
     * Constructor. This is initialized like any other Listed object.
     */
    public ReflectorList() {
        elements = new Object[default_capacity];
    }

    /**
     * Another constructor, this one allows you to set your own initial capacity.
     * @param capacity The initial capacity of the list. Default is 10.
     */
    public ReflectorList(int capacity) {
        elements = new Object[capacity];
    }

    /**
     * A simple add method to add entries to the list.
     * @param e Any Element type. This can be literally anything that isn't a generic type.
     */
    public void add(E e) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = e;
    }

    /**
     * A simple collector to add everything from an array list.
     * @param eList
     */
    public void addAll(ArrayList<E> eList) {
        eList.stream().forEach(e -> {
           add(e);
        });
    }

    /**
     * A method to get elements based on their index.
     * A stack of 10 contains 10 accessible slots, starting at 0 and ending at 9.
     * @param i Where in the list to index from.
     * @return The stored element.
     */
    public E get(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + 1 + ", Size " + i);
        }
        return (E) elements[i];
    }

    /**
     * A method to remove elements based on their index.
     * A stack of 10 contains 10 accessible slots, starting at 0 and ending at 9.
     * @param i Where in the list to index from
     * @return The element which was removed.
     */
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

    /**
     * Gets the size of the populated list.
     * @return The size of the populated list.
     */
    public int size() {
        return size;
    }

    /**
     * Custom toString() method.
     * This lets us customize exactly what prints when converting the List to String.
     * @return A new stringbuilder object with the contents of the list.
     */
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

    /**
     * Ensures that the list will never be added to when it has no empty slots remaining.
     */
    private void ensureCapacity() {
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
    }
}
