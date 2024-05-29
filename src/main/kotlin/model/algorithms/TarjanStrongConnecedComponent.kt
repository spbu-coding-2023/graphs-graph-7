package algorithms

import model.graph.Graph
import model.graph.Vertex
import java.util.*
import kotlin.math.min

class TarjanStronglyConnectedComponent {
    private var visitedTime: MutableMap<Vertex, Int> = mutableMapOf()
    private var lowTime: MutableMap<Vertex, Int> = mutableMapOf()
    private var onStack: MutableSet<Vertex> = mutableSetOf()
    private var stack: Deque<Vertex> = LinkedList()
    private var visited: MutableSet<Vertex> = mutableSetOf()
    private val result: MutableList<Set<Vertex>> = mutableListOf()
    private var time = 0

    fun scc(graph: Graph): List<Set<Vertex>> {
        for (vertex in graph.getVertices()) {
            if (visited.contains(vertex)) {
                continue
            }
            sccUtil(vertex)
        }

        return result
    }

    private fun sccUtil(vertex: Vertex) {
        visited.add(vertex)
        visitedTime[vertex] = time
        lowTime[vertex] = time
        time++
        stack.addFirst(vertex)
        onStack.add(vertex)

        for (child in vertex.adjacentVertices) {
            if (child !in visited) {
                sccUtil(child)
                lowTime[vertex] = min(lowTime[vertex]!!, lowTime[child]!!)
            }
            else if (child in onStack) {
                lowTime[vertex] = min(lowTime[vertex]!!, visitedTime[child]!!)
            }
        }

        if (visitedTime[vertex] === lowTime[vertex]) {
            val stronglyConnectedComponent: MutableSet<Vertex> = HashSet<Vertex>()
            var v: Vertex
            do {
                v = stack.pollFirst()
                onStack.remove(v)
                stronglyConnectedComponent.add(v)
            } while (vertex != v)
            result.add(stronglyConnectedComponent)
        }
    }
}