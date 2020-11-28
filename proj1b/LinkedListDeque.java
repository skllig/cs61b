/**
 * Link-list based deque.
 * @param <T>
 * @author skllig
 */
public class LinkedListDeque<T> {

    /**
     * Item that will be stored in deque.
     */
    private class Item {
        /**
         * val value stores in item.
         */
        private T val;
        /**
         * next next item of the current item.
         */
        private Item next;
        /**
         * prev previous item of the current item.
         */
        private Item prev;

        /**
         * Constructor of item that will be stored in deque.
         */
        Item() { }

        /**
         * Constructor of item.
         * @param v values of item.
         * @param n next item of the current item.
         * @param p previous item of the current item.
         */
        Item(T v, Item n, Item p) {
            val = v;
            next = n;
            prev = p;
        }
    }

    /**
     * first item of deque.
     */
    private Item first;
    /**
     * last item of deque.
     */
    private Item last;
    /**
     * size of deque.
     */
    private int size;

    /**
     * Create an empty deque.
     */
    public LinkedListDeque() {
        first = new Item();
        last = new Item();
        first.next = last;
        last.prev = first;
        size = 0;
    }

    /**
     * Adds an item of type T to the front of the deque.
     * @param item item to be added at the front of the deque.
     */
    public void addFirst(T item) {
        Item rest = first.next;
        Item newNode = new Item(item, rest, first);
        first.next = newNode;
        rest.prev = newNode;
        size += 1;
    }

    /**
     * Adds an item of type T to the back of the deque.
     * @param item item to be added to the back of the deque.
     */
    public void addLast(T item) {
        Item prev = last.prev;
        Item newNode = new Item(item, last, prev);
        prev.next = newNode;
        last.prev = newNode;
        size += 1;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the deque.
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to
     * last, separated by space.
     */
    public void printDeque() {
        String res = new String();
        Item node = first;
        while (node.next != last) {
            node = node.next;
            res += " " + node.val;
        }
        System.out.println(res.strip());
    }

    /**
     * Removes and returns the item at the front of
     * the deque. If no such item exists, return null.
     * @return
     */
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

    /**
     * Removes and returns the item at the back of the
     * deque. If not such item exists, returns null.
     * @return
     */
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

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque!
     * @param index index to being retrieving.
     * @return
     */
    public T get(int index) {
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

    /**
     * Gets the item at the given index recursively, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque!
     * @param index index to being retrieving.
     * @return
     */
    public T getRecursive(int index) {
        if (0 <= index && index < size()) {
            return getNodeRecursive(first.next, index);
        }
        return null;
    }

    /**
     *
     * @param node the new node to be added in deque.
     * @param index the index where node will be stored.
     * @return
     */
    private T getNodeRecursive(Item node, int index) {
        if (index == 0) {
            return node.val;
        }
        return getNodeRecursive(node.next, index - 1);
    }
}
