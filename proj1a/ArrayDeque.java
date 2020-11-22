import java.util.Objects;

public class ArrayDeque<T> {
    private  T[] data;
    int first, last, size, capacity;

    public ArrayDeque() {
        data = (T[]) new Object[8];
        first = 0;
        last = 1;
        size = 0;
        capacity = 8;
    }

    public void addFirst(T item){
        if (size >= capacity / 2) {
            resize(2);
        }
        data[first] = item;
        size += 1;
        first -= 1;
        if (first < 0) {
            first = capacity - 1;
        }
    }

    private void resize(int factor) {
        int newCapacity;
        if (factor > 0) {
            newCapacity = capacity * factor;
        } else {
            newCapacity = capacity / Math.abs(factor);
        }

        T[] new_data = (T[]) new Object[newCapacity];

        int start = (first + 1) % capacity;
        int end = (last - 1) > 0 ? (last - 1) : (capacity - 1);
        if (start < end) {
            System.arraycopy(data, start, new_data, 0, end - start + 1);
        } else {
            System.arraycopy(data, start, new_data, 0, capacity - start);
            System.arraycopy(data, 0, new_data, capacity - first, end + 1);
        }

        data = new_data;
        capacity = newCapacity;
        first = capacity - 1;
        last = size;
    }

    public void addLast(T item){
        if (size >= capacity / 2) {
            resize(2);
        }
        data[last] = item;
        size += 1;
        last += 1;
        if (last == capacity) {
            last = 0;
        }
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

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

    public T get(int index){
        if (size > 0) {
            return data[(first + 1 + index) % capacity];
        }
        return null;
    }



}
