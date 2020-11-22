/**
 * Array based deque.
 * @param <T>
 * @author skllig
 */
public class ArrayDeque<T> {
    /**
     * The array in which the elements of the deque are stored.
     */
    private  T[] data;

    /**
     * The first index to add item when addFirst method is called.
     * The last index to add item when addLast method is called.
     * The size of items in deque.
     * The capacity of the deque.
     */
    private int first, last, size, capacity;

    /**
     * Create an empty deque.
     */
    public ArrayDeque() {
        data = (T[]) new Object[8];
        first = 0;
        last = 1;
        size = 0;
        capacity = 8;
    }

    /**
     * Adds an item of type T to the front of the deque.
     * @param item item to be added at the front of the deque.
     */
    public void addFirst(T item) {
        data[first] = item;
        size += 1;
        first -= 1;
        if (first < 0) {
            first = capacity - 1;
        }
        if (size > capacity / 2) {
            resize(2);
        }
    }

    /**
     * Resize array by factor.
     * @param factor resize array by factor
     */
    private void resize(int factor) {
        int newCapacity;
        if (factor > 0) {
            newCapacity = capacity * factor;
        } else {
            newCapacity = capacity / Math.abs(factor);
        }

        T[] newData = (T[]) new Object[newCapacity];

        int start = (first + 1) % capacity;
        int end = last;
        if (start < end) {
            System.arraycopy(data, start, newData, 0, end - start + 1);
        } else {
            System.arraycopy(data, start, newData, 0, capacity - start);
            System.arraycopy(data, 0, newData, capacity - start, end);
        }
        data = newData;
        capacity = newCapacity;
        first = capacity - 1;
        last = size;
    }

    /**
     * Adds an item of type T to the back of the deque.
     * @param item item to be added to the back of the deque.
     */
    public void addLast(T item) {
        data[last] = item;
        size += 1;
        last += 1;
        if (last == capacity) {
            last = 0;
        }
        if (size > capacity / 2) {
            resize(2);
        }
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
        String res = "";
        if (size > 0) {
            int cur = (first + 1) % capacity;
            while (cur != last) {
                res += data[cur] + " ";
                cur += 1;
                if (cur == capacity) {
                    cur = 0;
                }
            }
        }
        System.out.println(res.strip());
    }

    /**
     * Removes and returns the item at the front of
     * the deque. If no such item exists, return null.
     * @return
     */
    public T removeFirst() {
        if (size > 0) {
            int target = (first + 1) % capacity;
            T res = data[target];
            data[target] = null;
            first = target;
            size -= 1;
            if (capacity >= 16 && size < capacity / 4) {
                resize(-2);
            }
            return res;
        }
        return null;
    }

    /**
     * Removes and returns the item at the back of the
     * deque. If not such item exists, returns null.
     * @return
     */
    public T removeLast() {
        if (size > 0) {
            int target = last - 1;
            if (target < 0) {
                target = capacity - 1;
            }
            T res = data[target];
            data[target] = null;
            last = target;
            size -= 1;

            if (capacity >= 16 && size < capacity / 4) {
                resize(-2);
            }
            return res;
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
        if (size > 0) {
            return data[(first + 1 + index) % capacity];
        }
        return null;
    }



}
