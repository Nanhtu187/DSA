import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class ThreeSum {
    public static int count(int[] a) {
        Arrays.sort(a);
        int cnt = 0;
        int n = a.length;
        for(int i = 0; i < n; ++ i) {
            int run = n - 1;
            for(int j = i + 1; j < n; ++ j) {
                while(run > j && a[run] > -(a[i] + a[j])) {
                    -- run;
                }
                if(run > j && a[run] + a[i] + a[j] == 0) {
                    ++ cnt;
                }
            }
        }
        return cnt;
    }

    public static void main(String[] argv) {
        int n;
        n = StdIn.readInt();
        int[] a = new int[n];
        for(int i = 0; i < n; ++ i) a[i] = StdIn.readInt();
        StdOut.print(count(a));
    }
}
