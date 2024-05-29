package algorithms

import model.graph.Graph
import model.graph.Vertex
import java.util.*

class AllCyclesInDirectedGraphJohnson {
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
        val tarjan = TarjanStronglyConnectedComponent()
        while (startIndex <= graph.vertices.size) {
            val subGraph: Graph = createSubGraph(startIndex, graph)
            val sccs: List<Set<Vertex>> = tarjan.scc(subGraph)
            val maybeLeastVertex: Vertex? = leastIndexSCC(sccs, subGraph)
            if (maybeLeastVertex != null) {
                val leastVertex: Vertex = maybeLeastVertex
                blockedSet.clear()
                blockedMap.clear()
                findCyclesInSCG(graph,leastVertex, leastVertex)
                startIndex = leastVertex.id + 1
            } else {
                break
            }
        }
        return allCycles
    }

    private fun leastIndexSCC(sccs: List<Set<Vertex>>, subGraph: Graph): Vertex? {
        var min = Int.MAX_VALUE
        var minVertex: Vertex? = null
        var minScc: Set<Vertex?> = mutableSetOf()
        for (scc in sccs) {
            if (scc.size == 1) {
                continue
            }
            for (vertex in scc) {
                if (vertex.id < min) {
                    min = vertex.id
                    minVertex = vertex
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
            if ((subGraph.vertices[edge.vertices.first] in minScc) && (subGraph.vertices[edge.vertices.second] in minScc)) {
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

        for (e in graph.edges.filterKeys{it in currentVertex.incidentEdges}.values) {
            val neighbor: Vertex = graph.vertices[e.vertices.second]!!
            if (neighbor === startVertex) {
                val cycle: MutableList<Vertex?> = ArrayList<Vertex?>()
                stack.push(startVertex)
                cycle.addAll(stack)
                cycle.reverse()
                stack.pop()
                allCycles.add(cycle)
                foundCycle = true
            }
            else if (!blockedSet.contains(neighbor)) {
                val gotCycle =
                    findCyclesInSCG(graph,startVertex, neighbor)
                foundCycle = foundCycle || gotCycle
            }
        }
        if (foundCycle) {
            unblock(currentVertex)
        } else {
            for (e in graph.edges.filterKeys{it in currentVertex.incidentEdges}.values) {
                val w: Vertex = graph.vertices[e.vertices.second]!!
                val bSet: MutableSet<Vertex> = getBSet(w)
                bSet.add(currentVertex)
            }
        }
        stack.pop()
        return foundCycle
    }

    private fun getBSet(v: Vertex): MutableSet<Vertex> {
        return (blockedMap.computeIfAbsent(v
        ) { HashSet<Vertex>() } as MutableSet<Vertex>?)!!
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
//fun main(args: Array<String>) {
//    val johnson = AllCyclesInDirectedGraphJohnson()
//    val graph = Graph()
//    graph.isDirected = true
//    for (i in 1..9){
//        graph.addVertex(i,"")
//    }
//    graph.addEdge(1, 2, edgeID = 0)
//    graph.addEdge(1, 8, edgeID = 1)
//    graph.addEdge(1, 5, edgeID = 2)
//    graph.addEdge(2, 9, edgeID = 3)
//    graph.addEdge(2, 7, edgeID = 4)
//    graph.addEdge(2, 3, edgeID = 5)
//    graph.addEdge(3, 1, edgeID = 6)
//    graph.addEdge(3, 2, edgeID = 7)
//    graph.addEdge(3, 6, edgeID = 8)
//    graph.addEdge(3, 4, edgeID = 9)
//    graph.addEdge(6, 4, edgeID = 10)
//    graph.addEdge(4, 5, edgeID = 11)
//    graph.addEdge(5, 2, edgeID = 12)
//    graph.addEdge(8, 9, edgeID = 13)
//    graph.addEdge(9, 8, edgeID = 14)
//
//    val allCycles: List<List<Vertex?>> = johnson.simpleCycles(graph)
//    val joiner = StringJoiner("->")
//    allCycles.forEach { cycle: List<Vertex?> ->
//        cycle.forEach { vertex: Vertex? ->
//            joiner.add("${vertex?.id}")
//        }
//        println(joiner)
//    }
//}