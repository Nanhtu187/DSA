public class Outcast {
    private WordNet wordNet;
    public Outcast(WordNet wordnet)  {
        this.wordNet = wordnet;
    }        // constructor takes a WordNet object
    public String outcast(String[] nouns){
        String res = "";
        int maxPath = 0;
        for (String noun : nouns) {
            int path = 0;
            for (String noun1: nouns) {
                path += this.wordNet.distance(noun, noun1);
            }
            if (path > maxPath) {
                maxPath = path;
                res = noun;
            }
        }
        return res;
    }   // given an array of WordNet nouns, return an outcast
    public static void main(String[] args){

    }  // see test client below
}