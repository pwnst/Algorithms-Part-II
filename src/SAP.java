import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;

public class SAP {
    private Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        digraph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        int[] path = findPath(v, w);
        if (path == null) {
            return -1;
        }
        int count = 1;
        int cur = path[w];
        while (cur != v) {
            cur = path[cur];
            count++;
        }
        return count;
    }

    private int[] findPath(int v, int w) {
        int[] path = new int[digraph.V()];
        boolean[] visited = new boolean[digraph.V()];

        LinkedQueue<Integer> queue = new LinkedQueue<>();
        queue.enqueue(v);

        while (!queue.isEmpty()) {
            int current = queue.dequeue();
            visited[current] = true;
            if (current == w) {
                return path;
            }
            Iterable<Integer> adj = digraph.adj(current);
            for (Integer a : adj) {
                if (!visited[a]) {
                    queue.enqueue(a);
                    path[a] = current;
                }
            }
        }
        return null;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        boolean[] visited = new boolean[digraph.V()];
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        int common = -1;

        queue.enqueue(v);
        while (!queue.isEmpty()) {
            int current = queue.dequeue();
            visited[current] = true;
            Iterable<Integer> adj = digraph.adj(current);
            for (Integer a : adj) {
                if (!visited[a]) {
                    queue.enqueue(a);
                }
            }
        }

        queue.enqueue(w);
        while (!queue.isEmpty()) {
            int current = queue.dequeue();
            if (visited[current]) {
                common = current;
                break;
            }
            Iterable<Integer> adj = digraph.adj(current);
            for (Integer a : adj) {
                queue.enqueue(a);
            }
        }

        return common;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return 1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return 1;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        Digraph d = new Digraph(new In("./wordnet/digraph1.txt"));
        SAP sap = new SAP(d);
        System.out.println(sap.ancestor(4, 8));
    }
}