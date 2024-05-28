package algorithms

import model.graph.Graph
import model.graph.Vertex
import java.util.*
import kotlin.math.min


/**
 * Date 08/16/2015
 * @author Tushar Roy
 *
 * Find strongly connected components of directed graph.
 *
 * Time complexity is O(E + V)
 * Space complexity  is O(V)
 *
 * Reference - https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm
 */
class TarjanStronglyConnectedComponent {
    private var visitedTime: MutableMap<Vertex, Int> = mutableMapOf()
    private var lowTime: MutableMap<Vertex, Int> = mutableMapOf()
    private var onStack: MutableSet<Vertex> = mutableSetOf()
    private var stack: Deque<Vertex> = LinkedList()
    private var visited: MutableSet<Vertex> = mutableSetOf()
    private val result: MutableList<Set<Vertex>> = mutableListOf()
    private var time = 0

    fun scc(graph: Graph): List<Set<Vertex>> {
        //start from any vertex in the graph.
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
            //if child is not visited then visit it and see if it has link back to vertex's ancestor. In that case update
            //low time to ancestor's visit time
            if (child !in visited) {
                sccUtil(child)
                //sets lowTime[vertex] = min(lowTime[vertex], lowTime[child]);
                lowTime[vertex] = min(lowTime[vertex]!!, lowTime[child]!!)
            } //if child is on stack then see if it was visited before vertex's low time. If yes then update vertex's low time to that.
            else if (child in onStack) {
                //sets lowTime[vertex] = min(lowTime[vertex], visitedTime[child]);
                lowTime[vertex] = min(lowTime[vertex]!!, visitedTime[child]!!)
            }
        }

        //if vertex low time is same as visited time then this is start vertex for strongly connected component.
        //keep popping vertices out of stack still you find current vertex. They are all part of one strongly
        //connected component.
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
fun main(args: Array<String>) {
    val graph: Graph = Graph()
    graph.isDirected = true
    graph.addVertex(1,"")
    graph.addVertex(2,"")
    graph.addVertex(3,"")
    graph.addVertex(4,"")
    graph.addVertex(5,"")
    graph.addVertex(6,"")
    graph.addVertex(7,"")
    graph.addVertex(8,"")
    graph.addVertex(9,"")
    graph.addVertex(10,"")

    graph.addEdge(1, 2, edgeID = 1)
    graph.addEdge(2, 3, edgeID = 2)
    graph.addEdge(3, 1, edgeID = 3)
    graph.addEdge(3, 4, edgeID = 4)
    graph.addEdge(4, 5, edgeID = 5)
    graph.addEdge(5, 6, edgeID = 6)
    graph.addEdge(6, 4, edgeID = 7)
    graph.addEdge(7, 6, edgeID = 8)
    graph.addEdge(7, 8, edgeID = 9)
    graph.addEdge(8, 7, edgeID = 10)

    val tarjanStronglyConnectedComponent = TarjanStronglyConnectedComponent()
    val result: List<Set<Vertex?>> = tarjanStronglyConnectedComponent.scc(graph)

    result.forEach { scc: Set<Vertex?> ->
        scc.forEach { vertex: Vertex? ->
            print(
                vertex?.id.toString() + " "
            )
        }
        println()
    }
}