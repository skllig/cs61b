import java.util.LinkedList;

public class LinkedListDeque<T> {
    private class Item {
        public T val;
        public Item next;
        public Item prev;

        public Item() {}
        public Item(T val, Item next, Item prev) {
            this.val = val;
            this.next = next;
            this.prev = prev;
        }
    }

    private Item first;
    private Item last;
    private int size;

    public LinkedListDeque(){
        first = new Item();
        last = new Item();
        first.next = last;
        last.prev = first;
        size = 0;
    }

    public void addFirst(T item){
        Item rest = first.next;
        Item newNode = new Item(item, rest, first);
        first.next = newNode;
        rest.prev = newNode;
        size += 1;
    }

    public void addLast(T item){
        Item prev = last.prev;
        Item newNode = new Item(item, last, prev);
        prev.next = newNode;
        last.prev = newNode;
        size += 1;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque() {
        String res = new String();
        Item node = first;
        while (node.next != last) {
            node = node.next;
            res += " " + node.val;
        }
        System.out.println(res.strip());
    }

    public T removeFirst() {
        if (first.next != last) {
            Item p = first.next;
            Item rest = first.next.next;
            rest.prev = first;
            first.next = rest;
            p.prev = null;
            p.next = null;
            size -= 1;
            return p.val;
        }
        return null;
    }

    public T removeLast() {
        if (first.next != last) {
            Item p = last.prev;
            Item front = p.prev;
            front.next = last;
            last.prev = front;
            p.next = null;
            p.prev = null;
            size -= 1;
            return p.val;
        }
        return null;
    }

    public T get(int index){
        if (0 <= index && index < size()) {
            Item node = first.next;
            while (node != last && index > 0) {
                node = node.next;
                index -= 1;
            }
            return node.val;
        }
        return null;
    }

    public T getRecursive(int index){
        if (0 <= index && index < size()) {
            return getNodeRecursive(first.next, index);
        }
        return null;
    }

    private T getNodeRecursive(Item node, int index) {
        if (index == 0) {
            return node.val;
        }
        return getNodeRecursive(node.next, index - 1);
    }
}
