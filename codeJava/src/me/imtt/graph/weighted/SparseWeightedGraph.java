package me.imtt.graph.weighted;

import java.util.Vector;

/**
 * 带权图的稀疏图——邻接表
 */
public class SparseWeightedGraph<Weight extends Number & Comparable> implements WeightedGraph {
    /**
     * 节点数
     */
    private int n;

    /**
     * 边数
     */
    private int m; 

    /**
     * 是否为有向图
     */
    private boolean directed; 

    /**
     * 图的具体数据
     */
    private Vector<Edge<Weight>>[] g; 

    public SparseWeightedGraph(int n, boolean directed) {
        this.n = n;

        ///初始化没有任何边
        this.m = 0;  

        ///true 表示有向图，false 表示无向图
        this.directed = directed;

        /// g 初始化为 n 个空的 vector, 表示每一个 g[i] 都为空, 即没有任何边
        this.g = (Vector<Edge<Weight>>[]) new Vector[n];
        for (int i = 0; i < n; i++) {
            g[i] = new Vector<>();
        }
    }

    @Override
    public int getV() {
        return n;
    }

    @Override
    public int getE() {
        return m;
    }

    @Override
    public void addEdge(Edge e) {
        assert e.getV() >= 0 && e.getV() < n;
        assert e.getW() >= 0 && e.getW() < n;
        g[e.getV()].add(new Edge(e));

        ///如果为无向图
        if (e.getV() != e.getW() && !directed) {
            g[e.getW()].add(new Edge(e.getW(), e.getV(), e.getWeight()));
        }

        m ++;
    }

    @Override
    public boolean hasEdge(int v, int w) {
        assert v >= 0 && v < n;
        assert w >= 0 && w < n;

        for (int i = 0; i < g[v].size(); i++) {
            if (g[v].elementAt(i).other(i) == w) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterable<Edge<Weight>> adj(int v) {
        assert v >= 0 && v < n;
        return g[v];
    }

    @Override
    public void show() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < g[i].size(); j++) {
                Edge e = g[i].elementAt(j);
                System.out.print("(to:" + e.other(i) + ", weight:" + e.getWeight() + ")\t");
            }
            System.out.println();
        }
    }
}
