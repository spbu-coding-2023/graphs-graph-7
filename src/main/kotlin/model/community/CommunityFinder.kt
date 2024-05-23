package model.community

import graph.model.Graph
import nl.cwts.networkanalysis.Clustering
import nl.cwts.networkanalysis.LeidenAlgorithm
import nl.cwts.networkanalysis.Network

class CommunityFinder(private val graph: Graph) {

    // main algorithm steps
    fun findCommunity(nIterations: String, resolution: String): Boolean {
        val doubleResolution = resolution.toDoubleOrNull()
        val intNIterations = nIterations.toIntOrNull()
        if (doubleResolution == null || intNIterations == null || graph.getVertices().isEmpty()) {
            return false
        }
        val network = graph.toNetwork()

        val random = java.util.Random()
        val algorithm = LeidenAlgorithm(doubleResolution, intNIterations, LeidenAlgorithm.DEFAULT_RANDOMNESS, random)
        val ans = algorithm.findClustering(network)

        graph.setCommunity(ans)
        return true
    }

    // we need to create network from our graph
    private fun Graph.toNetwork(): Network {

        val edgesList = graph.getEdges()

        val edges = Array(2) { IntArray(edgesList.size) { 0 } }
        for ((i, edge) in edgesList.withIndex()) {
            edges[0][i] = edge.vertices.first
            edges[1][i] = edge.vertices.second
        }

        return Network(graph.getVertices().size, false, edges, false, false)
    }

    private fun Graph.setCommunity(clustering: Clustering) {
        for ((i, vertex) in graph.getVertices().withIndex()) {
            vertex.community = clustering.clusters[i]
        }
    }
}
