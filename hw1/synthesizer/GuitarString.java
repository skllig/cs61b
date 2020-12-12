package synthesizer;

/** GuitarString uses an ArrayRingBuffer<Double> to implement
 * the Karplus-Strong algorithm to synthesize a guitar string
 * sound.
 * @author skllig
 */
public class GuitarString {
    /** Sampling Rate. */
    private static final int SR = 44100;
    /** energy decay factor. */
    private static final double DECAY = .996;

    /** Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /**
     * Create a buffer with capacity = SR / frequency.
     * @param frequency frequency
     */
    public GuitarString(double frequency) {
        int capacity = (int) Math.round(SR / frequency);
        buffer = new ArrayRingBuffer<Double>(capacity);
        for (int i = 0; i < capacity; i++) {
            buffer.enqueue(0.0);
        }
    }


    /**
     * Pluck the guitar string by replacing the buffer with white noise.
     */
    public void pluck() {
        for (int i = 0; i < buffer.capacity(); i++) {
            Double tmp = Math.random() - 0.5;
            buffer.dequeue();
            buffer.enqueue(tmp);
        }
    }

    /** Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm. Dequeue the front sample and enqueue
     * a new sample that is the average of the two multiplied by the DECAY
     * factor.
     */
    public void tic() {
        Double front = buffer.dequeue();
        Double top = buffer.peek();
        buffer.enqueue((front + top) * 0.5 * DECAY);
    }

    /**
     * Return the double at the front of the buffer.
     */
    public double sample() {
        return buffer.peek();
    }
}
