import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class WordNet {
    private Digraph digraph;
    private ArrayList<ArrayList<String>> synArray = new ArrayList<>();
    private HashMap<String, ArrayList<Integer>> nouns = new HashMap<>();
    private SAP sap;

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

        while (inH.hasNextLine()) {
            String[] lineData = inH.readLine().split(",");
            for (int i = 1; i < lineData.length; i++) {
                digraph.addEdge(Integer.parseInt(lineData[0]), Integer.parseInt(lineData[i]));
            }
        }

        sap = new SAP(digraph);
    }

    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    public boolean isNoun(String word) {
        return nouns.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        ArrayList<Integer> nA = nouns.get(nounA);
        ArrayList<Integer> nB = nouns.get(nounB);
        return sap.length(nA, nB);
    }

    public String sap(String nounA, String nounB) {
        ArrayList<Integer> nA = nouns.get(nounA);
        ArrayList<Integer> nB = nouns.get(nounB);
        int ancestor = sap.ancestor(nA, nB);
        StringBuilder sb = new StringBuilder();
        for (String s : synArray.get(ancestor)) {
            sb.append(s + " ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        WordNet wordNet = new WordNet("./wordnet/synsets.txt", "./wordnet/hypernyms.txt");
        System.out.println(wordNet.distance("bird", "worm"));
        System.out.println(wordNet.sap("bird", "worm"));
    }
}
