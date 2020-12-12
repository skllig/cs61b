package synthesizer;

import java.util.Iterator;

/**
 * The BoundedQueue is similar to our Deque from Project 1,
 * but with a more limited API. Specifically, items can only
 * be enqueued at the back of the queue, and can only be dequeued
 * from the front of the queue. Unlike our Deque, the BoundedDeque
 * has a fixed capacity, and nothing is allowed to enqueue if the
 * queue is full.
 * @param <T>
 * @author skllig
 */

public interface BoundedQueue<T> extends Iterable<T> {
    /**
     * Return the size of the buffer.
     * @return size of buffer
     */
    int capacity();

    /**
     * Return number of items currently in the buffer.
     * @return number of items
     */
    int fillCount();

    /**
     * Add item x to the end.
     * @param x item to be added
     */
    void enqueue(T x);

    /**
     * delete and return item from the front.
     * @return
     */
    T dequeue();

    /**
     * return (but do not delete) item from the front.
     * @return
     */
    T peek();

    /**
     * Return true if the buffer is empty, otherwise false.
     * @return
     */
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    /**
     * Return true if the buffer is full, otherwise false.
     * @return
     */
    default boolean isFull() {
        return fillCount() == capacity();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     * @return an Iterator.
     */
    Iterator<T> iterator();
}
