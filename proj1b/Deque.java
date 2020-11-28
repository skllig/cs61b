/**
 * Deque Interface of the double ended queue.
 * @param <T> the type of elements in this list
 *
 * @author skllig
 */

public interface Deque<T> {

    /** Adds an item of type T to the front of the deque.
     * @param item item to be appended to this list
     */
    void addFirst(T item);

    /**
     * Adds an item of type T to the back of the deque.
     * @param item item to be added to the back of the deque.
     */
    void addLast(T item);

    /**
     * Returns true if deque is empty, false otherwise.
     * @return {@code true} if the deque contains no element.
     */
    boolean isEmpty();

    /**
     * Returns the number of items in deque.
     * @return the number of items in deque
     */
    int size();

    /** Prints the items in the deque. */
    void printDeque();

    /**
     * Removes and returns the item at the front of
     * the deque. If no such item exists, return null.
     * @return the item at the front of deque
     */
    T removeFirst();

    /**
     * Removes and returns the item at the back of the
     * deque. If not such item exists, returns null.
     * @return the item at the back of deque
     */
    T removeLast();

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque!
     * @param index index to being retrieving.
     * @return
     */
    T get(int index);
}
