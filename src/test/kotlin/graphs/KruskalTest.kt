package graphs

import algorithms.KruskalsMST
import graph.model.Graph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

// Assuming the Edge class and kruskal function are defined here

class KruskalTest {

    @Test
    fun testSimpleGraph() {
        val graph = Graph()
        for (i in 0..8) {
            graph.addVertex(i, "")
        }
        graph.addEdge(0, 1, 4L, 0)
        graph.addEdge(0, 7, 8L, 1)
        graph.addEdge(1, 2, 8L, 2)
        graph.addEdge(1, 7, 11L, 3)
        graph.addEdge(2, 3, 7L, 4)
        graph.addEdge(2, 8, 2L, 5)
        graph.addEdge(2, 5, 4L, 6)
        graph.addEdge(3, 4, 9L, 7)
        graph.addEdge(3, 5, 14L, 8)
        graph.addEdge(4, 5, 10L, 9)
        graph.addEdge(5, 6, 2L, 10)
        graph.addEdge(6, 7, 1L, 11)
        graph.addEdge(6, 8, 6L, 12)
        graph.addEdge(7, 8, 7L, 13)
        val expected = setOf(0, 1, 4, 5, 6, 7, 10, 11).sorted()
        val algo = KruskalsMST()
        algo.kruskals(graph)
        assertEquals(expected, algo.resultsId)
    }
    @Test
    fun testSingleVertexGraph() {
        // Test with a single vertex to ensure the algorithm handles this case correctly
        val graph = Graph()
        graph.addVertex(0,"")
        val expected = emptyList<Int>()
        val algo = KruskalsMST()
        algo.kruskals(graph)
        assertEquals(expected, algo.resultsId)
    }

    @Test
    fun testEmptyGraph() {
        // Test with an empty graph to ensure the algorithm handles this case correctly
        val graph = Graph()
        val expected = emptyList<Int>()
        val algo = KruskalsMST()
        algo.kruskals(graph)
        assertEquals(expected, algo.resultsId)
    }
}
