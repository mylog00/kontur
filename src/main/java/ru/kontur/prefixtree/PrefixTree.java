package ru.kontur.prefixtree;

import java.util.*;

/**
 * @author Dmitry
 * @since 18.02.2016
 */
public class PrefixTree<Value extends Comparator<Value>> {

    private Node<Value> root;

    public PrefixTree() {
        this.root = new Node<>();
    }

    public void put(String key, Value value) {
        final char[] chars = key.toCharArray();
        Node<Value> currentNode = root;
        for (char keyChar : chars) {
            currentNode = currentNode.getBranchSafe(keyChar);
        }
        if (currentNode.isEmpty()) {
            currentNode.setValue(value);
        }
    }

    public Collection<Value> find(String prefix) {
        final char[] chars = prefix.toCharArray();
        Node<Value> currentNode = root;
        for (char key : chars) {
            currentNode = currentNode.findBrunch(key);
            if (currentNode == null) {
                return Collections.emptyList();
            }
        }

        return getAllLeafs(currentNode);
    }

    private SortedSet<Value> getAllLeafs(Node<Value> node) {
        final SortedSet<Value> res = new TreeSet<>();
        if (node.getValue() != null) {
            res.add(node.getValue());
        }
        for (Node<Value> branch : node.getAllBranches()) {
            res.addAll(getAllLeafs(branch));
        }
        return res;
    }

    public boolean isEmpty() {
        return this.root.getAllBranches().isEmpty();
    }

    private static class Node<Value> {

        private final Map<Character, Node<Value>> branches = new HashMap<>(2);

        private Value value = null;

        public Node() {
        }

        public Node(Value value) {
            this.value = value;
        }

        public Node<Value> getBranchSafe(char key) {
            Node<Value> branch = branches.getOrDefault(key, null);
            if (branch == null) {
                branch = new Node<>();
            }
            branches.put(key, branch);
            return branch;
        }

        public Node<Value> findBrunch(char key) {
            return branches.getOrDefault(key, null);
        }

        public Collection<Node<Value>> getAllBranches() {
            return branches.values();
        }

        //Getters/Setters
        public Value getValue() {
            return value;
        }

        public void setValue(Value value) {
            this.value = value;
        }

        public boolean isEmpty() {
            return value == null;
        }
    }
}
