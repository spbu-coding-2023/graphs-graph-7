package model.community

import model.graph.Graph

class Louvain(private val graph: Graph) {
    private val currCommunities = mutableMapOf<Int, Int>()
    private val n = getNeighbours()
    private var targetCommunity = currCommunities
    private var modularity = calculateModularity(currCommunities)

    fun findCommunities(): List<Set<Int>> {
        do {
            var stop = false

            for (anchor in n.neighbours.keys) {

                for (neighbourCommunity in n.neighbours.keys) {
                    if (
                        anchor != neighbourCommunity &&
                            currCommunities[anchor] != neighbourCommunity
                    ) {
                        val newCommunities = targetCommunity.toMutableMap()

                        newCommunities[anchor] = currCommunities[neighbourCommunity]!!
                        val newModularity = calculateModularity(newCommunities)

                        if (newModularity > modularity) {
                            modularity = newModularity
                            targetCommunity = newCommunities
                            currCommunities[anchor] = currCommunities[neighbourCommunity]!!
                            stop = true
                            break
                        }
                    }
                }

                if (stop) break
            }
        } while (stop)

        val answer = mutableListOf<Set<Int>>()
        for (value in targetCommunity.values.toSet()) {
            val keys = mutableSetOf<Int>()
            for ((key, value1) in targetCommunity) {
                if (value1 == value) keys.add(key)
            }

            answer.add(keys)
        }

        return paintGraph(answer)
    }

    private fun getNeighbours(): Neighbours {
        val graphEdges = graph.getEdges()
        val n = Neighbours()

        for (vertex in graph.getVertices()) {
            n.neighbours[vertex.id] = mutableSetOf()
        }

        // initialize network
        for (edge in graphEdges) {
            val neighbour1 = edge.vertices.first
            val neighbour2 = edge.vertices.second
            val closeness = edge.weight
            if (graph.isDirected) {
                n.neighbours
                    .getOrPut(neighbour1) { mutableSetOf() }
                    .add(Relation(closeness, neighbour2))
            } else {
                n.neighbours
                    .getOrPut(neighbour1) { mutableSetOf() }
                    .add(Relation(closeness, neighbour2))
                n.neighbours
                    .getOrPut(neighbour2) { mutableSetOf() }
                    .add(Relation(closeness, neighbour1))
            }
        }

        // initialize start community position
        for (id in n.neighbours.keys) {
            currCommunities[id] = id
        }

        return n
    }

    private fun calculateModularity(communities: Map<Int, Int>): Double {
        var link = 0.0
        val closeness = n.neighbours.values.sumOf { it.size }.toDouble() / 2

        for (anchor in n.neighbours.keys) {
            for (relation in n.neighbours[anchor]!!) {
                val neighbour = relation.id
                if (communities[anchor] == communities[neighbour]) {
                    link +=
                        1.0 -
                            (n.neighbours[anchor]!!.size * n.neighbours[neighbour]!!.size) /
                                (2 * closeness)
                }
            }
        }

        return link / (2 * closeness)
    }

    private fun paintGraph(comm: List<Set<Int>>): List<Set<Int>> {
        for ((newCommunityIndex, community) in comm.withIndex()) {
            for (vertexIdx in community) {
                graph.vertices[vertexIdx]!!.community = newCommunityIndex
            }
        }

        return comm
    }
}
