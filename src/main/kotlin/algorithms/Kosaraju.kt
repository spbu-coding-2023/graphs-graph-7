package algorithms

import graph.model.Graph

class Kosaraju<V>(private val graph: Graph<V>) {
    private val used = hashMapOf<Int, Boolean>()
    private val order = mutableListOf<Int>()
    private val component = mutableListOf<Int>()

    fun findStronglyConnectedComponents() {
        graph.vertices.keys.forEach { used[it] = false }

        // 1-st dfs for topology sort
        for (vertexID in graph.vertices.keys) {
            if (used[vertexID] != true) {
                dfs1(vertexID)
            }
        }

        val transposedGraph = transposeGraph()

        // clear is visited
        used.keys.forEach { used[it] = false }

        // 2-nd dfs for component search
        for (vertexID in order.reversed()) {
            if (used[vertexID] != true) {
                component.clear()
                dfs2(transposedGraph,vertexID)
                    // todo println("Компонента сильной связности: $component")
            }
        }
    }

    private fun dfs1(vertexID: Int) {
        used[vertexID] = true
        val vertex = graph.vertices[vertexID] ?: return
        for (edgeID in vertex.incidentEdges) {
            val edge = graph.edges[edgeID] ?: continue
            val nextVertexID = if (vertexID == edge.vertices.first) edge.vertices.second else edge.vertices.first
            if (used[nextVertexID] != true) {
                dfs1(nextVertexID)
            }
        }
        order.add(vertexID)
    }

    fun Test_dfs1(vertexID: Int) : MutableList<Int> {
        dfs1(vertexID)
        return order
    }

    private fun dfs2(transposedGraph: Graph<V>, vertexID: Int) {
        used[vertexID] = true
        component.add(vertexID)
        val vertex = transposedGraph.vertices[vertexID] ?: return
        for (edgeID in vertex.incidentEdges) {
            val edge = transposedGraph.edges[edgeID] ?: continue
            val nextVertexID = if (vertexID == edge.vertices.first) edge.vertices.second else edge.vertices.first
            if (used[nextVertexID] != true) {
                dfs2(transposedGraph, nextVertexID)
            }
        }
    }

    private fun transposeGraph(): Graph<V> {
        val transposedGraph = Graph<V>()
        transposedGraph.isDirected = graph.isDirected

        // Добавление вершин
        graph.vertices.forEach { (id, vertex) ->
            transposedGraph.addVertex(id, vertex.data)
        }

        // Добавление рёбер с изменённым направлением
        graph.edges.forEach { (id, edge) ->
            transposedGraph.addEdge(edge.vertices.second, edge.vertices.first, edge.weight, id)
        }

        return transposedGraph
    }
}

fun createSampleGraph() : Graph<Int> {
    // Добавление вершин
    val graph = Graph<Int>()
    for (i in 0..11) {
        graph.addVertex(i, i)
    }

    // Добавление рёбер
    graph.addEdge(0, 7, 1L, 0)
    graph.addEdge(7, 6, 1L, 1)
    graph.addEdge(6, 0, 1L, 2)
    graph.addEdge(6, 1, 1L, 3)
    graph.addEdge(6, 4, 1L, 4)
    graph.addEdge(7, 1, 1L, 5)
    graph.addEdge(1, 9, 1L, 6)
    graph.addEdge(9, 4, 1L, 7)
    graph.addEdge(4, 2, 1L, 8)
    graph.addEdge(2, 9, 1L, 9)
    graph.addEdge(2, 10, 1L, 10)
    graph.addEdge(11, 9, 1L, 11)
    graph.addEdge(11, 2, 1L, 12)
    graph.addEdge(11, 5, 1L, 13)
    graph.addEdge(10, 8, 1L, 14)
    graph.addEdge(8, 5, 1L, 15)
    graph.addEdge(5, 10, 1L, 16)
    graph.addEdge(5, 3, 1L, 17)
    graph.addEdge(3, 8, 1L, 18)

    return graph
}

fun main() {
    val graph = createSampleGraph()
    val algorithm = Kosaraju(graph)
    algorithm.findStronglyConnectedComponents()
}
