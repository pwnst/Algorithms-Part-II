import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class WordNet {
    private Digraph digraph;
    private ArrayList<ArrayList<String>> synArray = new ArrayList<>();
    private HashMap<String, ArrayList<Integer>> nouns = new HashMap<>();
    private SAP sap;
    private DirectedCycle finder;
    private Topological topological;


    public WordNet(String synsets, String hypernyms) {
        In inS = new In(synsets);
        In inH = new In(hypernyms);

        while (inS.hasNextLine()) {
            String[] lineData = inS.readLine().split(",");
            String[] synset = lineData[1].split(" ");
            for (String s : synset) {
                int id = Integer.parseInt(lineData[0]);
                if (nouns.containsKey(s)) {
                    nouns.get(s).add(id);
                } else {
                    ArrayList<Integer> idArrayList = new ArrayList<>();
                    idArrayList.add(id);
                    nouns.put(s, idArrayList);
                }
            }
            ArrayList<String> data = new ArrayList<>(Arrays.asList(synset));
            data.add(lineData[2]);
            synArray.add(data);
        }

        digraph = new Digraph(synArray.size());
        boolean[] pointToOther = new boolean[digraph.V()];

        while (inH.hasNextLine()) {
            String[] lineData = inH.readLine().split(",");
            pointToOther[Integer.parseInt(lineData[0])] = true;
            for (int i = 1; i < lineData.length; i++) {
                digraph.addEdge(Integer.parseInt(lineData[0]), Integer.parseInt(lineData[i]));
            }
        }

        finder = new DirectedCycle(digraph);

        if (finder.hasCycle()) {
            throw new java.lang.IllegalArgumentException("cycle");
        }

        int count = 0;
        for (boolean bool : pointToOther) {
            if (!bool) count++;
        }

        if (count > 1) {
            throw new java.lang.IllegalArgumentException("multi root");
        }

        sap = new SAP(digraph);
    }

    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    public boolean isNoun(String word) {
        if (word == null)
            throw new java.lang.NullPointerException();
        return nouns.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        checkNoun(nounA);
        checkNoun(nounB);
        ArrayList<Integer> nA = nouns.get(nounA);
        ArrayList<Integer> nB = nouns.get(nounB);
        return sap.length(nA, nB);
    }

    public String sap(String nounA, String nounB) {
        checkNoun(nounA);
        checkNoun(nounB);
        ArrayList<Integer> nA = nouns.get(nounA);
        ArrayList<Integer> nB = nouns.get(nounB);
        int ancestor = sap.ancestor(nA, nB);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < synArray.get(ancestor).size()-1; i++) {
            sb.append(synArray.get(ancestor).get(i) + " ");
        }
        return sb.toString();
    }

    private void checkNoun(String noun) {
        if (noun == null) {
            throw new NullPointerException();
        }
        if (!isNoun(noun)) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        WordNet wordNet = new WordNet("./wordnet/synsets.txt", "./wordnet/hypernyms.txt");
        System.out.println(wordNet.distance("bird", "worm"));
        System.out.println(wordNet.sap("bird", "worm"));
    }
}
