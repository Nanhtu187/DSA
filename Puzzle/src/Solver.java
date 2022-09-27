import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private Node lastNode;
    private class Node implements Comparable<Node> {
        private final Board board;
        private final int moves;
        private final int priority;
        private Node preNode = null;

        public Node(Board board, Node preNode) {
            this.board = board;
            this.preNode = preNode;
            if(this.preNode == null) {
                this.moves = 0;
            } else {
              this.moves = preNode.getMoves() + 1;
            }
            this.priority = this.moves + this.board.manhattan();
        }

        public int getMoves() {
            return this.moves;
        }

        public int getPriority() {
            return this.priority;
        }

        public int compareTo(Node that) {
            return Integer.compare(this.getPriority(), that.getPriority());
        }

        public Board getBoard() {
            return this.board;
        }

        public Node getPreNode() {
            return this.preNode;
        }
    }

    private Node process(MinPQ<Node> pq) {
        Node top = pq.delMin();
        if(top.board.isGoal()) {
            return top;
        }
        for (Board board: top.board.neighbors()) {
            if(top.preNode == null || !top.preNode.getBoard().equals(board)) {
                pq.insert(new Node(board, top));
            }
        }
        return null;
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw  new IllegalArgumentException();
        }
        MinPQ<Node> mnPq = new MinPQ<>();
        MinPQ<Node> mnTwinPq = new MinPQ<>();
        mnPq.insert(new Node(initial, null));
        mnTwinPq.insert(new Node(initial.twin(), null));
        while (true) {
            lastNode = process(mnPq);
            if(lastNode != null || process(mnTwinPq) != null) {
                break;
            }
        }
    }

    public boolean isSolvable() {
        return (lastNode != null);
    }

    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return lastNode.getMoves();
    }

    public Iterable<Board> solution() {
        if (!this.isSolvable()) {
            return null;
        }
        Stack<Board> q = new Stack<>();
        Node tempLastNode = lastNode;
        while (tempLastNode != null) {
            q.push(tempLastNode.getBoard());
            tempLastNode = tempLastNode.getPreNode();
        }
        return q;
    }

    public static void main(String[] args) {

    }


}
