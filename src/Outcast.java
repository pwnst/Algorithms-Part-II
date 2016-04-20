import edu.princeton.cs.algs4.In;

public class Outcast {
    private WordNet wordNet;

    public Outcast(WordNet wordnet) {
        wordNet = wordnet;
    }

    public String outcast(String[] nouns) {
        String blacksheep = null;
        int distance = 0;
        for (String n : nouns) {
            int currentDist = 0;
            String currentNoun = n;
            for (String c : nouns) {
                if (!c.equals(currentNoun)) {
                    currentDist += wordNet.distance(c, currentNoun);
                }
            }
            if (currentDist > distance) {
                distance = currentDist;
                blacksheep = currentNoun;
            }
        }
        return blacksheep;
    }

    public static void main(String[] args) {
        WordNet wordNet = new WordNet("./wordnet/synsets.txt", "./wordnet/hypernyms.txt");
        Outcast outcast = new Outcast(wordNet);
        In in = new In("./wordnet/outcast11.txt");
        String[] ooo = in.readAllLines();
        System.out.println(outcast.outcast(ooo));
    }
}