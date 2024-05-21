package algorithms

import model.graph.Graph

class Kosaraju(private val graph: Graph) {
    private val used = hashMapOf<Int, Boolean>()
    private val order = mutableListOf<Int>()
    private val component = mutableListOf<Int>()

    fun findStronglyConnectedComponents(): List<List<Int>> {
        if (!graph.isDirected) {
            return emptyList<List<Int>>()
        }

        // Step 1: Transpose the graph
        val transposedGraph = transposeGraph()

        // Step 2: Topology sort transposed graph
        for (vertexID in transposedGraph.vertices.keys) {
            if (used[vertexID] != true) {
                topologySort(transposedGraph, vertexID)
            }
        }

        // Step 3:  DFS to find strongly connected components
        val components = mutableListOf<List<Int>>()
        used.clear()
        for (vertexID in order.reversed()) {
            if (used[vertexID] != true) {
                component.clear()
                dfs(vertexID)
                components.add(component.toList())
            }
        }

        return components
    }

     private fun topologySort(graph: Graph, vertexID: Int) {
        used[vertexID] = true
        val vertex = graph.vertices[vertexID] ?: return
        for (edgeID in vertex.incidentEdges) {
            val edge = graph.edges[edgeID] ?: continue
            val nextVertexID = if (vertexID == edge.vertices.first) edge.vertices.second else edge.vertices.first
            if (used[nextVertexID] != true) {
                topologySort(graph, nextVertexID)
            }
        }
        order.add(vertexID)
    }

    fun test_TopologySort(graph: Graph, vertexID: Int): List<Int> {
        topologySort(graph, vertexID)
        return order
    }

    private fun dfs(vertexID: Int) {
        used[vertexID] = true
        component.add(vertexID)
        val vertex = graph.vertices[vertexID] ?: return
        for (edgeID in vertex.incidentEdges) {
            val edge = graph.edges[edgeID] ?: continue
            val nextVertexID = if (vertexID == edge.vertices.first) edge.vertices.second else continue
            if (used[nextVertexID] != true) {
                dfs(nextVertexID)
            }
        }
    }

    private fun transposeGraph(): Graph {
        val transposedGraph = Graph()
        transposedGraph.isDirected = true // Transposed graph is always directed

        // Add vertices to the transposed graph
        for ((id, vertex) in graph.vertices) {
            transposedGraph.addVertex(id, vertex.data)
        }

        // Add edges with reversed direction to the transposed graph
        for ((id, edge) in graph.edges) {
            val (firstVertexID, secondVertexID) = edge.vertices
            transposedGraph.addEdge(secondVertexID, firstVertexID, edge.weight, id)
        }

        return transposedGraph
    }
}