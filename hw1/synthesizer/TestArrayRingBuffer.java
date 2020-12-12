package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            arb.enqueue(i);
        }
        assertEquals((Integer) 0, arb.peek());
        assertTrue(arb.isFull());
        assertEquals((Integer) 9, arb.dequeue());
        assertEquals((Integer) 8, arb.dequeue());
        assertEquals((Integer) 7, arb.dequeue());
        for (int i = 0; i < 7; i++) {
            arb.dequeue();
        }
        assertTrue(arb.isEmpty());
    }

    @Test
    public void testIteration() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            arb.enqueue(i);
        }
        String res = "";
        for (Integer each : arb) {
            res += each;
        }
        assertEquals("0123456789", res);
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
}

