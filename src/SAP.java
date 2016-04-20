import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;


public class SAP {
    private Digraph digraph;
    private int anc;
    private int dist;

    public SAP(Digraph G) {
        digraph = G;
    }

    private void calcAll(int v, int w) {
        ArrayList<Integer> va = new ArrayList<>();
        ArrayList<Integer> wa = new ArrayList<>();
        va.add(v);
        wa.add(w);
        calcAll(va, wa);
    }

    private void calcAll(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        int minDistance = Integer.MAX_VALUE;
        int vertex = 0;

        for (int i = 0; i < digraph.V(); i++) {
            int lenV = bfsV.distTo(i);
            int lenW = bfsW.distTo(i);
            if (lenV != Integer.MAX_VALUE && lenW != Integer.MAX_VALUE) {
                if (lenV + lenW < minDistance) {
                    minDistance = lenV + lenW;
                    vertex = i;
                }
            }
        }
        if (minDistance == Integer.MAX_VALUE) {
            dist = -1;
            anc = -1;
        } else {
            dist = minDistance;
            anc = vertex;
        }

    }

    public int length(int v, int w) {
        calcAll(v, w);
        return dist;
    }

    public int ancestor(int v, int w) {
        calcAll(v, w);
        return anc;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        calcAll(v, w);
        return dist;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        calcAll(v, w);
        return anc;
    }

    public static void main(String[] args) {
        Digraph d = new Digraph(new In("./wordnet/digraph1.txt"));
        SAP sap = new SAP(d);
    }
}