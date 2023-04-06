public class Items<E> implements Comparable<Items> {
    private int priority;
    private E e;

    public Items(int priority, E e) {
        this.priority = priority;
        this.e = e;
    }

    public int getPriority() {
        return priority;
    }

    public E getE() {
        return e;
    }

    @Override
    public int compareTo(Items o) {
        if (this.priority < o.priority) return 1;
        else if (this.priority == o.priority) return 0;
        return -1;
    }
}
