package model.community

import graph.model.Graph

class Louvain(private val graph: Graph) {
    private val currCommunities = mutableMapOf<Int, Int>()
    private val n = getNeighbours()
    private var targetCommunity = currCommunities
    private var modularity = calculateModularity(currCommunities)


}