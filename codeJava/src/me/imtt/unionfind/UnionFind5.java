package me.imtt.unionfind;

/**
 * 并查集——版本五（对版本四的路径压缩）
 * 使用一个数组构建一棵指向父节点的树
 * 并查集时间复杂度近乎是 O(1)
 */
public class UnionFind5 {
    /**
     * 数据个数
     */
    private int count; 

    /**
     * parent[i]表示元素所指向的父节点 
     */
    private int[] parent;

    /**
     * rank[i]表示以i为根的集合所表示的树的层数
     */
    private int[] rank; 

    public UnionFind5(int count) {
        parent = new int[count];
        rank = new int[count];
        this.count = count;
        for (int i = 0; i < count; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    /**
     * 查找元素 p 所对应的集合编号
     * 复杂度为 O(h), h 为树的高
     */
    public int find(int p) {
        assert p >= 0 && p < count;

        ///不断去查询父节点, 直到到达根节点
        //根节点的特点: parent[p] == p
        //路径压缩版本一，实践性更好
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;

        ///路径压缩版本二，理论上更好
        //if (p != parent[p])
        //parent[p] = find(parent[p]);
        //return parent[p];
    }

    /**
     * 查看元素 p 和元素 q 是否所属一个集合
     * 复杂度为 O(h), h 为树的高度
     */
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    /**合并元素 p 和元素 q 所属的集合
     * 复杂度为 O(h), h 为树的高度
     */
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot) {
            return;
        }

        ///根据两个元素所在树的元素个数不同判断合并方向
        //将元素个数少的集合合并到元素个数多的集合上
        if (rank[pRoot] > rank[qRoot]) {
            parent[qRoot] = pRoot;
        } else if (rank[pRoot] < rank[qRoot]) {
            parent[pRoot] = qRoot;
        } else {
            parent[pRoot] = qRoot;
            rank[qRoot]++;
        }
    }
}
