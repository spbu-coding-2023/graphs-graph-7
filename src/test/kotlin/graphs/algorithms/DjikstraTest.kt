package graphs.algorithms

import algorithms.Djikstra
import graph.model.Graph
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

//                        table of path's
// _________________________________________________________
// |_iteration_|____S_____|__w__|_D[2]_|_D[3]_|_D[4]_|_D[5]_|
// |___start___|___{1}____|__-__|__10__|_+00__|__30__|_100__|
// |_____1_____|__{1,2}___|__2__|__10__|__60__|__30__|_100__|
// |_____2_____|_{1,2,4}__|__4__|__10__|__50__|__30__|__90__|
// |_____3_____|{1,2,4,3}_|__3__|__10__|__50__|__30__|__60__|
// |_____4_____|_1,2,4,3,5|__5__|__10__|__50__|__30__|__60__|
//

fun createSampleGraphDjikstraDirected(): Graph {
    val graph = Graph()
    graph.isDirected = true

    graph.addVertex(1, "A")
    graph.addVertex(2, "B")
    graph.addVertex(3, "C")
    graph.addVertex(4, "D")
    graph.addVertex(5, "E")

    graph.addEdge(1, 2, 10L, 0)
    graph.addEdge(1, 5, 100L, 1)
    graph.addEdge(1, 4, 30L, 2)
    graph.addEdge(2, 3, 50L, 3)
    graph.addEdge(3, 5, 10L, 4)
    graph.addEdge(4, 3, 20L, 5)
    graph.addEdge(4, 5, 60L, 6)

    return graph
}

fun createSampleGraphDjikstra(): Graph {
    val graph = Graph()
    graph.isDirected = false

    graph.addVertex(1, "A")
    graph.addVertex(2, "B")
    graph.addVertex(3, "C")
    graph.addVertex(4, "D")
    graph.addVertex(5, "E")

    graph.addEdge(1, 2, 10L, 0)
    graph.addEdge(1, 5, 100L, 1)
    graph.addEdge(1, 4, 30L, 2)
    graph.addEdge(2, 3, 50L, 3)
    graph.addEdge(3, 5, 10L, 4)
    graph.addEdge(4, 3, 20L, 5)
    graph.addEdge(4, 5, 60L, 6)

    return graph
}

class DjikstraTest {
    private val graphD = createSampleGraphDjikstraDirected()
    private val graph = createSampleGraphDjikstra()

    @Test
    fun `test findShortestPaths with sample graph start from 1 to 5 started from 1`() {
        // graph and algo initialization
        val expected = mutableListOf(1,4,3,5)
        val algorithm = Djikstra(graphD,1)
        // path created from 1
        algorithm.findShortestPaths()


        val currently = algorithm.reconstructPath(5)

        assertTrue(expected == currently)
    }

    @Test
    fun `test findShortestPaths with sample graph start from 1 to 2`() {
        // graph and algo initialization
        val expected = mutableListOf(1,2)
        val algorithm = Djikstra(graphD, 1)
        algorithm.findShortestPaths()

        val currently = algorithm.reconstructPath(2)

        assertTrue(expected == currently)
    }

    @Test
    fun `test findShortestPaths with sample graph start from 2 to 3 started from 2`() {
        // graph and algo initialization
        val expected = mutableListOf(2,3)
        val algorithm = Djikstra(graphD,2)
        // path created from 2
        algorithm.findShortestPaths()


        val currently = algorithm.reconstructPath(3)

        assertTrue(expected == currently)
    }

    @Test
    fun `test findShortestPaths path does not exist`() {
        // graph and algo initialization
        val expected = mutableListOf<Int>()
        val algorithm = Djikstra(graphD,2)
        // path created from 2
        algorithm.findShortestPaths()

        val currently = algorithm.reconstructPath(1)

        assertTrue(expected == currently)
    }

    @Test
    fun `test findShortestPaths from 2 to 1 not directed graph`() {
        // graph and algo initialization
        val expected = mutableListOf(2,1)
        val algorithm = Djikstra(graph,2)
        // path created from 2
        algorithm.findShortestPaths()

        val currently = algorithm.reconstructPath(1)

        assertTrue(expected == currently)
    }

    @Test
    fun `test findShortestPaths from 5 to 1 not directed graph`() {
        // graph and algo initialization
        val expected = mutableListOf(5,3,4,1)
        val algorithm = Djikstra(graph,5)
        // path created from 2
        algorithm.findShortestPaths()

        val currently = algorithm.reconstructPath(1)

        assertTrue(expected == currently)
    }

    @Test
    fun `test findShortestPaths empty-edged graph`() {
        // graph and algo initialization
        val emptyGraph = Graph()
        val expected = mutableListOf<Int>()
        val algorithm = Djikstra(emptyGraph)

        // path created from 2
        algorithm.findShortestPaths()

        val currently = algorithm.reconstructPath(1)

        assertTrue(expected == currently)
    }

    @Test
    fun `test findShortestPaths no start position`() {
        // graph and algo initialization
        val expected = mutableListOf<Int>()
        val algorithm = Djikstra(graph, -11)

        // path created from 2
        algorithm.findShortestPaths()

        val currently = algorithm.reconstructPath(5)

        assertTrue(expected == currently)
    }

    @Test
    fun `test findShortestPaths no end position and start position`() {
        // graph and algo initialization
        val expected = mutableListOf<Int>()
        val algorithm = Djikstra(graph)

        // path created from 2
        algorithm.findShortestPaths()

        val currently = algorithm.reconstructPath(-4)

        assertTrue(expected == currently)
    }

    @Test
    fun `test findShortestPaths no end position start exist`() {
        // graph and algo initialization
        val expected = mutableListOf<Int>()
        val algorithm = Djikstra(graph, 2)

        // path created from 2
        algorithm.findShortestPaths()

        val currently = algorithm.reconstructPath(-4)

        assertTrue(expected == currently)
    }

    @Test
    fun `test findShortestPaths start and end pos equals`() {
        // graph and algo initialization
        val expected = mutableListOf(2)
        val algorithm = Djikstra(graph, 2)

        // path created from 2
        algorithm.findShortestPaths()

        val currently = algorithm.reconstructPath(2)

        assertTrue(expected == currently)
    }
}