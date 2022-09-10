import java.util.*;
import java.io.*;

public class UNF {
    int[] root;

    public UNF(int n) {
        root = new int[n];
        for(int i = 0; i < n; ++ i) root[i] = -1;
    }

    public int get_root(int u) {
        if(root[u] == -1) return u;
        return root[u] = get_root(root[u]);
    }
    public boolean add(int u, int v) {
        u = get_root(u);
        v = get_root(v);
        if(u != v) {
            root[u] = v;
            return true;
        }
        else return false;
    }
}
