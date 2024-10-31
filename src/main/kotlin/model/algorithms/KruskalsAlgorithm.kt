package model.algorithms

import model.graph.Edge
import model.graph.Graph

class KruskalsMST {
    internal fun kruskals(graph: Graph): List<Int> {
        val numVertices = graph.getVertices().size
        if (numVertices <= 1) return emptyList()

        val results = mutableListOf<Edge>()
        val subsets = mutableMapOf<Int, Subset>()

        for (vertex in 0 until numVertices) {
            subsets[vertex] = Subset(vertex, 0)
        }

        val edgesList = graph.edges.values
        val sortedEdges = edgesList.sortedWith(compareBy { it.weight })

        var edgeIndex = 0
        var noOfEdgesAdded = 0

        while (noOfEdgesAdded < numVertices - 1 && edgeIndex < sortedEdges.size) {
            val nextEdge = sortedEdges[edgeIndex]
            val x = findRoot(subsets, nextEdge.vertices.first)
            val y = findRoot(subsets, nextEdge.vertices.second)

            if (x != y && x != null && y != null) {
                results.add(nextEdge)
                union(subsets, x, y)
                noOfEdgesAdded++
            }
            edgeIndex++
        }

        return results.filterNotNull().map { it.id }.sorted()
    }

    private fun union(subsets: MutableMap<Int, Subset>, x: Int, y: Int) {
        val rootX = findRoot(subsets, x)
        val rootY = findRoot(subsets, y)

        if (subsets[rootY]?.rank ?: 0 < subsets[rootX]?.rank ?: 0) {
            subsets[rootY]?.parent = rootX
        } else if (subsets[rootX]?.rank ?: 0 < subsets[rootY]?.rank ?: 0) {
            subsets[rootX]?.parent = rootY
        } else {
            subsets[rootY]?.parent = rootX
            subsets[rootX]?.rank = (subsets[rootX]?.rank ?: 0) + 1
        }
    }

    private fun findRoot(subsets: MutableMap<Int, Subset>, i: Int): Int {
        if (subsets[i]?.parent != i) subsets[i]?.parent = findRoot(subsets, subsets[i]?.parent ?: i)
        return subsets[i]?.parent ?: i
    }

    internal class Subset(var parent: Int, var rank: Int)
}
