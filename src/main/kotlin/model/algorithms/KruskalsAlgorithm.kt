package model.algorithms

import model.graph.Edge
import model.graph.Graph

class KruskalsMST {
    var resultsId: List<Int> = emptyList()

    internal fun kruskals(graph: Graph) {
        var j = 0
        var noOfEdges = 0
        val V = graph.getVertices().size
        if (V == 1 || V == 0) return
        val results = arrayOfNulls<Edge>(V - 1)
        val subsets = arrayOfNulls<Subset>(V)
        for (i in 0 ..< V) {
            subsets[i] = Subset(i, 0)
        }
        val edgesList = graph.edges.values
        val sortedEdges = edgesList.sortedWith(compareBy { it.weight })

        while (noOfEdges < V - 1) {

            val nextEdge = sortedEdges[j]
            val x = findRoot(subsets, nextEdge.vertices.first)
            val y = findRoot(subsets, nextEdge.vertices.second)

            if (x != y) {
                results[noOfEdges] = nextEdge
                union(subsets, x, y)
                noOfEdges++
            }
            j++
        }
        resultsId = results.map { it!!.id }.sorted()
    }

    private fun union(
        subsets: Array<Subset?>,
        x: Int,
        y: Int,
    ) {
        val rootX = findRoot(subsets, x)
        val rootY = findRoot(subsets, y)

        if (subsets[rootY]!!.rank < subsets[rootX]!!.rank) {
            subsets[rootY]!!.parent = rootX
        } else if ((subsets[rootX]!!.rank < subsets[rootY]!!.rank)) {
            subsets[rootX]!!.parent = rootY
        } else {
            subsets[rootY]!!.parent = rootX
            subsets[rootX]!!.rank++
        }
    }

    private fun findRoot(subsets: Array<Subset?>, i: Int): Int {
        if (subsets[i]!!.parent != i) subsets[i]!!.parent = findRoot(subsets, subsets[i]!!.parent)
        return subsets[i]!!.parent
    }

    internal class Subset(var parent: Int, var rank: Int)
}
