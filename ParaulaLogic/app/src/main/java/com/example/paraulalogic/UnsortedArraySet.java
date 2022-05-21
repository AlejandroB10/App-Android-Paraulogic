package com.example.paraulalogic;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class UnsortedArraySet<E>{
    private E[] array;
    private int n;

    public UnsortedArraySet(int max) {
        this.array = (E[]) new Object[max];
        this.n = 0;
    }

    public boolean contains(E elem) {
        boolean encontrado = false;
        for (int i = 0; i < n; i++) {
            if (elem.equals(array[i])) {
                encontrado = true;
            }
        }
        return encontrado;
    }

    public boolean add(E elem) {
        if (n < array.length && !this.contains(elem)) {
            array[n] = elem;
            n++;
            return true;
        }
        return false;
    }

    public String toString() {
        String r = "";
        for (int i = 0; i < array.length; i++) {
            r += array[i].toString() + " ";
        }
        return r;
    }

    public boolean remove(E elem) {
        boolean econtrado = false;
        int i = 0;
        while (!econtrado && i < n) {
            econtrado = elem.equals(array[i]);
            i++;
        }
        if (econtrado) {
            array[i - 1] = array[n - 1];
            n--;
        }
        return econtrado;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public Iterator iterator() {
        Iterator it = new UnsortedArraySetIterator();
        return it;
    }
    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */

    private class UnsortedArraySetIterator implements Iterator {
        private int idxIterator;

        public UnsortedArraySetIterator() {
            this.idxIterator = 0;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return idxIterator < n;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws if the iteration has no more elements
         */
        @Override
        public E next() {
            idxIterator++;
            return array[idxIterator - 1];
        }
    }
}


