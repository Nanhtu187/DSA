import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node head;
    private Node tail;
    private int cnt = 0;

    private class Node {
        Item item;
        Node next;
        Node prev;

        Node(Item data) {
            this.item = data;
            this.next = null;
            this.prev = null;
        }

        Node(Item item, Node next) {
            this.item = item;
            this.next = next;
        }

        Node(Item item, Node next, Node prev) {
            this.next = next;
            this.item = item;
            this.prev = prev;
        }
    }

    private class ListIterator implements Iterator<Item> {
        private Node current;

        public ListIterator() {
            current = head;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("unsupported");
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException("no more item to return");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    // construct an empty deque
    public Deque() {
        head = null;
        tail = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return head == null;
    }

    // return the number of items on the deque
    public int size() {
        return cnt;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("invalid input");
        }
        ++ cnt;
        if (head == null) {
            head = new Node(item);
            tail = head;
            return;
        }
        Node newHead = new Node(item, head);
        head.prev = newHead;
        head = newHead;

    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("invalid input");
        }
        ++ cnt;
        if (head == null) {
            head = new Node(item);
            tail = head;
            return;
        }
        Node newTail = new Node(item, null, tail);
        tail.next = newTail;
        tail = newTail;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        -- cnt;
        Item first = head.item;
        head = head.next;
        if (head != null) {
            head.prev = null;
        }
        else {
            tail = head;
        }
        return first;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        -- cnt;
        Item item = tail.item;
        Node temp = tail;
        tail = tail.prev;
        temp = null;
        if (tail != null) {
            tail.next = null;
        }
        else {
            head = tail;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}