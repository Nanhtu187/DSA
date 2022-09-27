import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

public class WordNet {
    private List<String> synset = new ArrayList<>();
    private Map< String, ArrayList<Integer> > isNoun = new HashMap<>();
    private Digraph G;
    private SAP sap;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
        In synsetFile = new In(synsets);
        In hypernymFile = new In(hypernyms);
        int cnt = 0;
        while (synsetFile.hasNextLine()) {
            String[] line =  synsetFile.readLine().split(",");
            String[] names = line[1].split(" ");
            for (String name: names) {
                if (!isNoun.containsKey(name)) {
                    ArrayList<Integer> a = new ArrayList<>();
                    a.add(Integer.parseInt(line[0]));
                    isNoun.put(name, a);
                }
                else {
                    isNoun.get(name).add(Integer.parseInt(line[0]));
                }
            }
            synset.add(line[1]);
            ++cnt;
        }
        this.G = new Digraph(cnt);
        while (hypernymFile.hasNextLine()) {
            String[] ids =  hypernymFile.readLine().split(",");
            for (int i = 1; i < ids.length; ++i) {
                G.addEdge(Integer.parseInt(ids[0]), Integer.parseInt(ids[i]));
            }
        }
        this.sap = new SAP(this.G);
        if (hasCycle()) {
            throw new IllegalArgumentException();
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return isNoun.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return isNoun.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        if (!isNoun.containsKey(nounA) || !isNoun.containsKey(nounB)) {
            throw new IllegalArgumentException();
        }
        List<Integer> a = isNoun.get(nounA);
        List<Integer> b = isNoun.get(nounB);
        return this.sap.length(a, b);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun.containsKey(nounA) || !isNoun.containsKey(nounB)) {
            throw new IllegalArgumentException();
        }
        List<Integer> a = isNoun.get(nounA);
        List<Integer> b = isNoun.get(nounB);
        return synset.get(this.sap.ancestor(a, b));
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("wordnet/synsets100-subgraph.txt", "wordnet/hypernyms100-subgraph.txt");
        System.out.println(wordNet.distance("thing", "horror"));
        System.out.println(wordNet.sap("thing", "horror"));
    }

    private boolean hasCycle() {
        ArrayList<Integer> rootArr = new ArrayList<Integer>();
        for (int i = 0; i < this.G.V(); i++) {
            if (!this.G.adj(i).iterator().hasNext()) {
                rootArr.add(i);
            }
        }
        if (rootArr.size() == 0 || rootArr.size() > 1) {
            return true;
        }
        DirectedCycle diCycle = new DirectedCycle(this.G);
        return diCycle.hasCycle();
    }
}