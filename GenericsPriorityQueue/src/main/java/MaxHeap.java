public class MaxHeap {
  private int length;
  private int currentSize;
  private int[] heap;

  public MaxHeap(int length) {
    this.length = length;
    this.currentSize = 0;
    this.heap = new int[length];
  }

  public void increaseArray() {
    int[] temp = heap;
    length *= 2;
    heap = new int[length];
    for (int x = 0; x < temp.length; x++) {
      heap[x] = temp[x];
    }
  }

  protected int parent(int index) {
    if (index % 2 == 0) return (index / 2) - 1;
    return index / 2;
  }

  protected int leftChild(int index) {
    return (2 * index) + 1;
  }

  protected int rightChild(int index) {
    return (2 * index) + 2;
  }

  protected void swapItem(int index, int index2) {
    int temp = heap[index];
    heap[index] = heap[index2];
    heap[index2] = temp;
  }

  protected void fixHeapAfterAddingNewItem(int index) {
    while (index > 0 && (heap[parent(index)] < heap[index])) {
      swapItem(index, parent(index));
      index = parent(index);
    }
  }

  public void add(int item) {
    heap[currentSize] = item;
    currentSize++;
    fixHeapAfterAddingNewItem(currentSize - 1);
    if (currentSize == length) {
      increaseArray();
    }
  }

  protected int max(int index) {
    if (rightChild(index) <= currentSize) {
      if (heap[leftChild(index)] > heap[rightChild(index)]) {
        return leftChild(index);
      }
    } else {
      return index;
    }
    return rightChild(index);
  }

  protected void fixHeapAfterRemovingItem(int index) {
    int max = max(index);
    while (index < currentSize && heap[index] < heap[max] && max < currentSize) {
      int temp = max;
      swapItem(index, max);
      index = temp;
      max = max(index);
    }
  }

  public int deleteMax() {
    if (currentSize < 1) {
      throw new EmptyQueueException("Heap is empty");
    }
    int max = heap[0];
    swapItem(0, currentSize);
    heap[currentSize] = 0;
    fixHeapAfterRemovingItem(0);
    currentSize--;
    return max;
  }
}
