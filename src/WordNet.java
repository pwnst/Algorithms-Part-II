import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;


public class WordNet {
    private Digraph digraph;
    private ArrayList<ArrayList<String>> s = new ArrayList<>();
    private HashSet<String> nouns = new HashSet<>();

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In inS = new In(synsets);
        In inH = new In(hypernyms);

        while (inS.hasNextLine()) {
            String[] lineData = inS.readLine().split(",");
            String[] synset = lineData[1].split(" ");
            nouns.addAll(Arrays.asList(synset));
            ArrayList<String> data = new ArrayList<>(Arrays.asList(synset));
            data.add(lineData[2]);
            s.add(data);
        }

        digraph = new Digraph(s.size());

        while (inH.hasNextLine()) {
            String[] lineData = inH.readLine().split(",");
            for (int i = 1; i < lineData.length; i++) {
                digraph.addEdge(Integer.parseInt(lineData[0]), Integer.parseInt(lineData[i]));
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nouns.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return null;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("./wordnet/synsets.txt", "./wordnet/hypernyms.txt");
    }
}
