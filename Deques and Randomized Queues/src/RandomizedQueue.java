import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private class ListIterator implements Iterator<Item> {
        private Item[] list;
        private int current = 0;

        public ListIterator() {
            int n = size();
            list = (Item[]) new Object[n];
            for (int i = 0; i < n; ++i) {
                list[i] = deque[i];
                int index = StdRandom.uniformInt(i + 1);
                Item temp = list[index];
                list[index] = list[i];
                list[i] = temp;
            }

        }

        public boolean hasNext() {
            return current != size();
        }

        public void remove() {
            throw new UnsupportedOperationException("unsupportted");
        }

        public Item next() {
            if (current >= size()) {
                throw new NoSuchElementException("no more item to return");
            }
            return list[current++];
        }
    }

    private void resize(int newSize) {
        Item[] newDeque = (Item[]) new Object[newSize];
        for (int i = 0; i < cnt; ++ i) newDeque[i] = deque[i];
        deque = newDeque;
    }
    private Item[] deque;
    private int cnt = 0;
    // construct an empty randomized queue
    public RandomizedQueue() {
        deque = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return cnt == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return cnt;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("invalid input");
        }
        int length = deque.length;
        if(cnt < length) {
            deque[cnt] = item;
        }
        else {
            resize(cnt * 2);
            deque[cnt] = item;
        }
        ++ cnt;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        int n = this.size();
        int position = StdRandom.uniformInt(n);
        Item temp = deque[position];
        deque[position] = deque[cnt - 1];
        -- cnt;
        if (cnt < deque.length/4) {
            resize(deque.length/2);
        }
        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        int n = this.size();
        int position = StdRandom.uniformInt(n);
        return deque[position];
    }


    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}