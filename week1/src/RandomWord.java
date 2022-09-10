import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class RandomWord {
    public static void main(String[] argv) {
        String s = "";
        String res = "";
        double p = 0.0;
        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            ++p;
            if (StdRandom.bernoulli(1/p)) {
                res = s;
            }
        }
        StdOut.print(res);
    }
}
