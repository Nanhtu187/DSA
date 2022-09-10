/* Nguyen Anh Tu */
import java.util.*;
import java.io.*;
import edu.princeton.cs.algs4.*;
//import UNF;

//package UNF;

class Main{

    public static void main(String[] argv){
        In in = new In("G:\\My Drive\\UET\\K1 2022-2023\\dsa\\algs4-data\\tinyUF.txt");
        Out out = new Out("G:\\My Drive\\UET\\K1 2022-2023\\dsa\\prj1\\.out");
        int n = in.readInt();
        UF uf = new UF(n);
        int cnt = n;
        int id = 0;
        while(in.hasNextChar()){
            ++ id;
            int u = in.readInt();
            int v = in.readInt();
            if(!uf.connected(u, v)) {
                uf.union(u, v);
                -- cnt;
            }
            if(cnt == 1)  {
                out.println(id);
                return;
            }
        }
        out.println("FAILED");
    }
    //Nguyen Anh Tu
   /* public static void main(String[] argv){
        In in = new In("D:\\hoc\\dsa\\prj1\\.inp");
        Out out = new Out("D:\\hoc\\dsa\\prj1\\.out");
        int n = in.readInt();
        UNF unf = new UNF(n);
        int cnt = n, i = 0;
        while(in.hasNextChar()){
            ++ i;
            int u = in.readInt();
            int v = in.readInt();
            if(unf.add(u, v)) -- cnt;
            if(cnt == 1) {
                out.println(i);
                return;
            }
        }
        out.println("FAILED");
    }*/
}