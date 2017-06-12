import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

public class TestArrayDeque1B {
    @Test
    public void TestArrayDeque() {
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();

        ArrayList<DequeOperation> dequeOp = new ArrayList<>();
        OperationSequence fs = new OperationSequence();

        // Create random sequence
        for (int i = 0; i < 7; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                student.addLast(i);
                solution.addLast(i);
                // Record Operation
                dequeOp.add(new DequeOperation("addLast", i));
                fs.addOperation(dequeOp.get(dequeOp.size()-1));
            } else {
                student.addFirst(i);
                solution.addFirst(i);

                dequeOp.add(new DequeOperation("addFirst", i));
                fs.addOperation(dequeOp.get(dequeOp.size()-1));
            }
        }

        student.printDeque();

        // Begin test
        for (int i = 0; i < 7; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                dequeOp.add(new DequeOperation("removeFirst"));
                fs.addOperation(dequeOp.get(dequeOp.size()-1));

                int expected = solution.removeFirst();
                int actual = student.removeFirst();
                assertEquals(fs.toString(), expected, actual);
            }
            else {
                dequeOp.add(new DequeOperation("removeLast"));
                fs.addOperation(dequeOp.get(dequeOp.size()-1));

                int expected = solution.removeLast();
                int actual = student.removeLast();
                assertEquals(fs.toString(), expected, actual);
            }

        }

    }
}
