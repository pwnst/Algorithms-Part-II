import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;


public class SAP {
    private final Digraph digraph;

    public SAP(Digraph G) {
        digraph = new Digraph(G);
    }

    private int[] calcAll(int v, int w) {
        ArrayList<Integer> va = new ArrayList<>();
        ArrayList<Integer> wa = new ArrayList<>();
        va.add(v);
        wa.add(w);
        return calcAll(va, wa);
    }

    private int[] calcAll(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new java.lang.NullPointerException();
        for (int vv: v) {
            validateV(vv);
        }
        for (int ww: w) {
            validateV(ww);
        }

        int[] results = new int[2];

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
            results[0] = -1;
            results[1] = -1;
            return results;
        } else {
            results[0] = minDistance;
            results[1] = vertex;
            return results;
        }
    }

    public int length(int v, int w) {
        return calcAll(v, w)[0];
    }

    public int ancestor(int v, int w) {
        return calcAll(v, w)[1];
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return calcAll(v, w)[0];
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return calcAll(v, w)[1];
    }

    private void validateV(int v) {
        if (v < 0 || v >= digraph.V())
            throw new IndexOutOfBoundsException("vertex is not in bounds");
    }

    public static void main(String[] args) {
        Digraph d = new Digraph(new In("./wordnet/digraph1.txt"));
        SAP sap = new SAP(d);
    }
}