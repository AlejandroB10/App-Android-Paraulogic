package com.example.paraulalogic;

import java.util.Iterator;
import java.util.Stack;

public class BSTSetMapping<K extends Comparable, V> {

    /**
     * @author alebo
     */

    public Iterator iterator() {
        return new IteratorBSTSetMapping();
    }

    private class IteratorBSTSetMapping implements Iterator {
        // La implementació de l’iterador serà una pila de nodes
        private Stack<Node> iterator;

        // Quin és el primer node a visitar
        public IteratorBSTSetMapping() {

            Node p;
            iterator = new Stack();
            if (root != null) {
                p = root;
                while (p.left != null) {
                    iterator.push(p);
                    p = p.left;
                }
                iterator.push(p);
            }

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
            return !iterator.isEmpty();

        }


        @Override
        public Object next() {
            Node p = iterator.pop();
            Pair pair = new Pair(p.key, p.valor);
            if (p.right != null) {
                p = p.right;
                while (p.left != null) {
                    iterator.push(p);
                    p = p.left;
                }
                iterator.push(p);
            }
            return pair;
        }
    }

    protected class Pair {

        protected V valor;
        protected K key;

        protected Pair(K key, V valor) {
            this.valor = valor;
            this.key = key;
        }

        public V getValor() {
            return valor;
        }

        public K getKey() {
            return key;
        }

        @Override
        public String toString() {
            return valor + " ("+ key +")";
        }
    }

    private class Node {

        //Valor del node
        private V valor;
        //Clau del node
        private K key;
        private Node left, right;

        public Node(K key, V valor, Node left, Node right) {
            this.valor = valor;
            this.key = key;
            this.left = left;
            this.right = right;
        }

        public void setValor(V valor) {
            this.valor = valor;
        }
    }

    private class Cerca {

        boolean trobat;

        public Cerca(boolean trobat) {
            this.trobat = trobat;
        }

    }

    // CLASE BSTSetMapping
    private Node root;

    public BSTSetMapping() {
        root = null;
    }

    boolean isEmpty() {
        return root == null;
    }


    public V get(K key) {
        return get(key, root);
    }

    private V get(K key, Node current) {
        if (current == null) { // Si l’arbre és buit: no trobat
            return null;
        } else {
            if (current.key.equals(key)) {// Si el node conté l’element: retornam el valor
                return current.valor;
            }
            // Si l’element és inferior a l’element del node:
            if (key.compareTo(current.key) < 0) {
                return get(key, current.left); // cercar al fill esquerra
            } else {
                return get(key, current.right); // cercar al fill dret
            }
        }
    }

    public boolean put(K key, V valor) {
        Cerca cerca = new Cerca(false);
        this.root = put(key, valor, root, cerca);
        return !cerca.trobat;
    }

    private Node put(K key, V valor, Node current, Cerca cerca) {
        if (current == null) {
            return new Node(key, valor, null, null);
        } else {
            if (current.key.equals(key)) {
                cerca.trobat = true;
                current.setValor(valor);
                return current;
            }
            if (key.compareTo(current.key) < 0) {
                current.left = put(key, valor, current.left, cerca);
            } else {
                current.right = put(key, valor, current.right, cerca);
            }
            return current;
        }
    }
}


