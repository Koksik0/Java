import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriorityQueue<E> {
  private MaxHeap maxHeapOfPriority;
  private int length;
  private List<Items>[] arrayOfList;
  private int currentSize;

  public PriorityQueue(int length) {
    this.length = length;
    this.currentSize = 0;
    this.maxHeapOfPriority = new MaxHeap(length);
    this.arrayOfList = new List[length];
  }

  public void increaseArray() {
    List<Items>[] temp = arrayOfList;
    this.length *= 2;
    this.arrayOfList = new List[length];
    for(int x = 0; x<temp.length;x++){
      if(temp[x]!=null){
        for(int y = 0;y<temp[x].size();y++){
          int index = temp[x].get(y).getPriority();
          if(arrayOfList[index%length]==null){
            arrayOfList[index%length] = new ArrayList<>();
          }
          arrayOfList[index%length].add(temp[x].get(y));
        }
      }
    }
  }

  public void add(E e, int priority) {
    if (arrayOfList[priority % length] == null) {
      arrayOfList[priority % length] = new ArrayList<>();
      currentSize++;
    }
    arrayOfList[priority % length].add(new Items(priority, e));
    Collections.sort(arrayOfList[priority % length]);
    maxHeapOfPriority.add(priority);
    if(currentSize>length*7/10){
      increaseArray();
    }
  }

  public E get() {
    int maxPriority;
    try {
      maxPriority = maxHeapOfPriority.deleteMax();
    } catch (EmptyQueueException emptyQueueException) {
      throw new EmptyQueueException("Queue is empty");
    }
    return (E) arrayOfList[maxPriority % length].remove(0).getE();
  }

  public int getLength() {
    return length;
  }
}
