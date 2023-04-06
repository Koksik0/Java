import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriorityQueueTest {
    @Test
    void testEmptyQueue() {
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(10);
        assertThrows(EmptyQueueException.class, () -> priorityQueue.get());
    }

    @Test
    void testPriorityQueueString() {
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(5);
        priorityQueue.add("qwerty", 3);
        priorityQueue.add("asdfgh", 7);
        priorityQueue.add("zxcvbn",10);
        priorityQueue.add("poiuyt", 9);
        assertEquals("zxcvbn", priorityQueue.get());
    }

    @Test
    void testPriorityQueueInteger() {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(5);
        priorityQueue.add(11, 90);
        priorityQueue.add(22, 100);
        priorityQueue.add(33,999);
        priorityQueue.add(44, 98765);
        assertEquals(44, priorityQueue.get());
    }
}
