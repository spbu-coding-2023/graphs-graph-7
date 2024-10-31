package graphs.algorithms

import model.algorithms.KruskalsMST
import model.graph.Graph
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
        graph.addEdge(0, 1, 4f, 0)
        graph.addEdge(0, 7, 8f, 1)
        graph.addEdge(1, 2, 8f, 2)
        graph.addEdge(1, 7, 11f, 3)
        graph.addEdge(2, 3, 7f, 4)
        graph.addEdge(2, 8, 2f, 5)
        graph.addEdge(2, 5, 4f, 6)
        graph.addEdge(3, 4, 9f, 7)
        graph.addEdge(3, 5, 14f, 8)
        graph.addEdge(4, 5, 10f, 9)
        graph.addEdge(5, 6, 2f, 10)
        graph.addEdge(6, 7, 1f, 11)
        graph.addEdge(6, 8, 6f, 12)
        graph.addEdge(7, 8, 7f, 13)
        val expected = setOf(0, 1, 4, 5, 6, 7, 10, 11).sorted()
        val algo = KruskalsMST()
        val resultsId = algo.kruskals(graph)
        assertEquals(expected, resultsId)
    }

    @Test
    fun testSingleVertexGraph() {
        // Test with a single vertex to ensure the algorithm handles this case correctly
        val graph = Graph()
        graph.addVertex(0, "")
        val expected = emptyList<Int>()
        val algo = KruskalsMST()
        val resultsId = algo.kruskals(graph)
        assertEquals(expected, resultsId)
    }

    @Test
    fun testEmptyGraph() {
        // Test with an empty graph to ensure the algorithm handles this case correctly
        val graph = Graph()
        val expected = emptyList<Int>()
        val algo = KruskalsMST()
        val resultsId = algo.kruskals(graph)
        assertEquals(expected, resultsId)
    }
}
