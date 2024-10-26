package algorithms

import java.util.*
import model.algorithms.Kosaraju
import model.graph.Graph
import model.graph.Vertex

class FindCycles {
    private var blockedSet: MutableSet<Vertex> = mutableSetOf()
    private var blockedMap: MutableMap<Vertex, Set<Vertex>?> = mutableMapOf()
    private var stack: Deque<Vertex?> = LinkedList()
    private var allCycles: MutableList<List<Vertex?>> = mutableListOf()

    fun simpleCycles(graph: Graph): List<List<Vertex?>> {
        blockedSet = HashSet<Vertex>()
        blockedMap = HashMap<Vertex, Set<Vertex>?>()
        stack = LinkedList<Vertex?>()
        allCycles = ArrayList<List<Vertex?>>()
        var startIndex = 1
        val kosaraju = Kosaraju(graph)
        while (startIndex <= graph.vertices.size) {
            val subGraph: Graph = createSubGraph(startIndex, graph)
            val sccs: List<List<Int>> = kosaraju.findStronglyConnectedComponents()
            val maybeLeastVertex: Vertex? = leastIndexSCC(sccs, subGraph)
            if (maybeLeastVertex != null) {
                val leastVertex: Vertex = maybeLeastVertex
                blockedSet.clear()
                blockedMap.clear()
                findCyclesInSCG(graph, leastVertex, leastVertex)
                startIndex = leastVertex.id + 1
            } else {
                break
            }
        }
        return allCycles
    }

    private fun leastIndexSCC(sccs: List<List<Int>>, subGraph: Graph): Vertex? {
        var min = Int.MAX_VALUE
        var minVertex: Vertex? = null
        var minScc: List<Int?> = mutableListOf()
        for (scc in sccs) {
            if (scc.size == 1) {
                continue
            }
            for (vertexId in scc) {
                if (vertexId < min) {
                    min = vertexId
                    minVertex = subGraph.vertices[vertexId]
                    minScc = scc
                }
            }
        }

        if (minVertex == null) {
            return null
        }
        val graphScc = Graph()
        graphScc.isDirected = true
        for ((i, edge) in subGraph.getEdges().withIndex()) {
            if (
                (subGraph.vertices[edge.vertices.first]?.id in minScc) &&
                    (subGraph.vertices[edge.vertices.second]?.id in minScc)
            ) {
                graphScc.addVertex(edge.vertices.first, "")
                graphScc.addVertex(edge.vertices.second, "")
                graphScc.addEdge(edge.vertices.first, edge.vertices.second, edgeID = i)
            }
        }
        return graphScc.vertices[(minVertex.id)]
    }

    private fun unblock(u: Vertex) {
        blockedSet.remove(u)
        if (blockedMap[u] != null) {
            blockedMap[u]!!.forEach { v: Vertex ->
                if (blockedSet.contains(v)) {
                    unblock(v)
                }
            }
            blockedMap.remove(u)
        }
    }

    private fun findCyclesInSCG(
        graph: Graph,
        startVertex: Vertex,
        currentVertex: Vertex,
    ): Boolean {
        var foundCycle = false
        stack.push(currentVertex)
        blockedSet.add(currentVertex)

        for (e in graph.edges.filterKeys { it in currentVertex.incidentEdges }.values) {
            val neighbor: Vertex = graph.vertices[e.vertices.second]!!
            if (neighbor === startVertex) {
                val cycle: MutableList<Vertex?> = ArrayList<Vertex?>()
                stack.push(startVertex)
                cycle.addAll(stack)
                cycle.reverse()
                stack.pop()
                allCycles.add(cycle)
                foundCycle = true
            } else if (!blockedSet.contains(neighbor)) {
                val gotCycle = findCyclesInSCG(graph, startVertex, neighbor)
                foundCycle = foundCycle || gotCycle
            }
        }
        if (foundCycle) {
            unblock(currentVertex)
        } else {
            for (e in graph.edges.filterKeys { it in currentVertex.incidentEdges }.values) {
                val w: Vertex = graph.vertices[e.vertices.second]!!
                val bSet: MutableSet<Vertex> = getBSet(w)
                bSet.add(currentVertex)
            }
        }
        stack.pop()
        return foundCycle
    }

    private fun getBSet(v: Vertex): MutableSet<Vertex> {
        if (!blockedMap.containsKey(v)) {
            blockedMap[v] = HashSet<Vertex>()
        }
        return (blockedMap[v] as MutableSet<Vertex>?)!!
    }

    private fun createSubGraph(startVertex: Int, graph: Graph): Graph {
        val subGraph = Graph()
        subGraph.isDirected = true
        for ((i, edge) in graph.getEdges().withIndex()) {
            if (edge.vertices.first >= startVertex && edge.vertices.second >= startVertex) {
                subGraph.addVertex(edge.vertices.first, "")
                subGraph.addVertex(edge.vertices.second, "")
                subGraph.addEdge(edge.vertices.first, edge.vertices.second, edgeID = i)
            }
        }
        return subGraph
    }
}
