package model.community

import graph.model.Graph
import nl.cwts.networkanalysis.Clustering
import nl.cwts.networkanalysis.LeidenAlgorithm
import nl.cwts.networkanalysis.Network

class CommunityFinder {

    fun findCommunity(graph: Graph, nIterations: String, resolution: String): Boolean {
        val doubleResolution = resolution.toDoubleOrNull()
        val intNIterations = nIterations.toIntOrNull()
        if (doubleResolution == null || intNIterations == null || graph.getVertices().isEmpty()) {
            return false
        }
        val network = graph.toNetwork()

        val algorithm = LeidenAlgorithm(doubleResolution, intNIterations, LeidenAlgorithm.DEFAULT_RANDOMNESS, Random())
        val ans = algorithm.findClustering(network)

        graph.setCommunity(ans)
        return true
    }
}
