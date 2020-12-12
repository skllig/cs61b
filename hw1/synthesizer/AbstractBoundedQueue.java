package synthesizer;

/**
 * An abstract class which implements BoundedQueue.
 * @param <T>
 * @author skllig
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    /**
     * fillCount the number of T in Queue.
     */
    protected int fillCount;
    /**
     * capacity capacity of Queue.
     */
    protected int capacity;

    /**
     * Return the capacity of queue.
     * @return capacity
     */
    public int capacity() {
        return capacity;
    }

    /**
     * Return the fill count of item in queue.
     * @return fill count
     */
    public int fillCount() {
        return fillCount;
    }

}
