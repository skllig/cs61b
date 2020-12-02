import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDequeGold {

    @Test
    public void testSAD() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();

        String message = "";
        for (int i = 0; i < 31; i++) {
            double flag = StdRandom.uniform();
            if (flag > 0.5) {
                sad.addFirst(i);
                solution.addFirst(i);
                message += "addFirst" + "(" + i + ")" + "\n";
            } else {
                sad.addLast(i);
                solution.addLast(i);
                message += "addLast" + "(" + i + ")" + "\n";
            }
        }

        for (int i = 0; i < 15; i++) {
            double flag = StdRandom.uniform();
            Integer a, b;
            if (flag > 0.5) {
                a = sad.removeFirst();
                b = solution.removeFirst();
                message += "removeFirst" + "(" + i + ")" + "\n";
            } else {
                a = sad.removeLast();
                b = solution.removeLast();
                message += "removeLast" + "(" + i + ")" + "\n";
            }
            assertEquals(message, a, b);
        }
    }
}
