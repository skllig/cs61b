package synthesizer;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ArrayRingBuffer extends AbstractBoundedQueue and uses an array as the actual
 * implementation of the BoundedQueue.
 * @param <T>
 * @author skllig
 */
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /**
     * Index for the next dequeue or peek.
     */
    private int first;
    /**
     * Index for the next enqueue.
     */
    private int last;
    /**
     *  Array for storing the buffer data.
     */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     * @param capacity capacity of buffer
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = last = 0;
        this.fillCount = 0;
        this.capacity = capacity;

    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     * @param x new item to enqueue
     */
    public void enqueue(T x) {
        if (this.isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last = (last + this.capacity() + 1) % this.capacity();
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     * @return the oldest item in buffer
     */
    public T dequeue() {
        if (this.isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T item = rb[first];
        rb[first] = null;
        first = (first + this.capacity() + 1) % this.capacity();
        fillCount -= 1;
        return item;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (this.isEmpty()) {
            throw new RuntimeException
            ("Peek operation is not allowed on empty buffer.");
        }
        return rb[first];
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new RingBufferIterator();
    }

    /**
     * Iterator of RingBuffer.
     */
    private class RingBufferIterator implements Iterator<T> {
        /**
         * cur pointer that indicate the current traversal position.
         */
        private int cur;
        /**
         * count the number of item in buffer.
         */
        private int count;

        /**
         * Constructor of RingBufferIterator.
         */
        RingBufferIterator() {
            count = 0;
            cur = first;
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
            return count < fillCount;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No elements in buffer.");
            }

            T item = rb[cur];
            cur = (cur + capacity + 1) % capacity;
            count += 1;
            return item;
        }
    }
}
