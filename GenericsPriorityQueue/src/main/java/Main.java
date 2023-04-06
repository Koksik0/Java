

public class Main {
  public static void main(String[] args) throws IndexOutOfBoundsException, EmptyQueueException {

    PriorityQueue<String> pQueue = new PriorityQueue<>(10);
    pQueue.add("a", 1);
    pQueue.add("b", 2);
    pQueue.add("c", 3);
    pQueue.add("d", 4);
    pQueue.add("e", 5);
    pQueue.add("f", 6);
    for (int x = 0; x < 6; x++) {
      System.out.println(pQueue.get());
    }
  }
}
