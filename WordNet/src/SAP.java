import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;


public class SAP {
    private Digraph G;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if(G == null) {
            throw new IllegalArgumentException();
        }
        this.G = new Digraph((G));
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(this.G, w);
        int minPath = Integer.MAX_VALUE;
        boolean hasPath = false;
        for (int i = 0; i < this.G.V(); ++i) {
            if (bfs.hasPathTo(i) && bfs2.hasPathTo(i)) {
                minPath = Math.min(minPath, bfs.distTo(i) + bfs2.distTo(i));
                hasPath = true;
            }
        }
        if (!hasPath) {
            return -1;
        }
        return minPath;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(this.G, w);
        int minPath = Integer.MAX_VALUE;
        int root = 0;
        boolean hasPath = false;
        for (int i = 0; i < this.G.V(); ++i) {
            if (bfs.hasPathTo(i) && bfs2.hasPathTo(i)) {
                hasPath = true;
                if (minPath > bfs.distTo(i) + bfs2.distTo(i)) {
                    minPath = bfs.distTo(i) + bfs2.distTo(i);
                    root = i;
                }
            }
        }
        if (!hasPath) {
            return -1;
        }
        return root;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        if(!v.iterator().hasNext() || !w.iterator().hasNext()) {
            return -1;
        }
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(this.G, w);
        int minPath = Integer.MAX_VALUE;
        boolean hasPath = false;
        for (int i = 0; i < this.G.V(); ++i) {
            if (bfs.hasPathTo(i) && bfs2.hasPathTo(i)) {
                minPath = Math.min(minPath, bfs.distTo(i) + bfs2.distTo(i));
                hasPath = true;
            }
        }
        if (!hasPath) {
            return -1;
        }
        return minPath;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if(v == null || w == null) {
            throw new IllegalArgumentException();
        }
        if(!v.iterator().hasNext() || !w.iterator().hasNext()) {
            return -1;
        }
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(this.G, w);
        int minPath = Integer.MAX_VALUE;
        int root = 0;
        boolean hasPath = false;
        for (int i = 0; i < this.G.V(); ++i) {
            if (bfs.hasPathTo(i) && bfs2.hasPathTo(i)) {
                hasPath = true;
                if (minPath > bfs.distTo(i) + bfs2.distTo(i)) {
                    minPath = bfs.distTo(i) + bfs2.distTo(i);
                    root = i;
                }
            }
        }
        if (!hasPath) {
            return -1;
        }
        return root;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("wordnet/digraph-wordnet.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
       StdOut.printf("length = %d, ancestor = %d\n", sap.length(new ArrayList<Integer>(), new ArrayList<Integer>()));
//        while (!StdIn.isEmpty()) {
//            int v = StdIn.readInt();
//            int w = StdIn.readInt();
//            int length   = sap.length(v, w);
//            int ancestor = sap.ancestor(v, w);
//            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
//        }
    }
}