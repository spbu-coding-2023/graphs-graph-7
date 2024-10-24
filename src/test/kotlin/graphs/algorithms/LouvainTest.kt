package graphs.algorithms

import model.community.Louvain
import model.graph.Graph
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

//               graph sample
//
//    7              11 → → → → → → → 5
//  ↗ ↓ ↘          ↙    ↘           ↙ ↑ ↘
// 0  ↓  1 → → → 9 ← ← ← 2 → → →  10  ↑   3
//  ↖ ↓ ↗          ↘   ↗            ↘ ↑ ↙
//    6 → → → → → →  4                8
//

//                  graph sample 2
//
//      1 → → → 2
//       ↘     ↙
//          3
//          ↓
//          ↓
//          4
//       ↙     ↘
//      5 → → → 6
//

fun createSampleGraph2(): Graph {
    val graph = Graph()
    graph.isDirected = true

    graph.addVertex(1, "A")
    graph.addVertex(2, "B")
    graph.addVertex(3, "C")
    graph.addVertex(4, "D")
    graph.addVertex(5, "E")
    graph.addVertex(6, "F")

    graph.addEdge(1, 2, 10L, 1)
    graph.addEdge(1, 3, 5L, 2)
    graph.addEdge(2, 3, 5L, 3)
    graph.addEdge(3, 4, 15L, 4)
    graph.addEdge(4, 5, 5L, 5)
    graph.addEdge(4, 6, 5L, 6)
    graph.addEdge(5, 6, 10L, 7)
    return graph
}

class LouvainTest {
    @Test
    fun `test louvain set output directed`() {
        // graph and algo initialization
        val graph = createSampleGraph()
        val algo = Louvain(graph)
        val expected = mutableListOf(setOf(0, 7, 1, 6), setOf(9, 11, 2, 4), setOf(10, 5, 3, 8))

        val currently = algo.findCommunities()

        assertTrue(expected == currently)
    }

    @Test
    fun `test louvain set output non directed`() {
        // graph and algo initialization
        val graph = createSampleGraph()
        graph.isDirected = false
        val algo = Louvain(graph)
        val expected = mutableListOf(setOf(0, 1, 2, 4, 6, 7, 9, 11), setOf(10, 5, 3, 8))

        val currently = algo.findCommunities()

        assertTrue(expected == currently)
    }

    @Test
    fun `test louvain graph community color`() {
        // graph and algo initialization
        val graph = createSampleGraph()
        val algo = Louvain(graph)

        val expectedCommunities = mutableListOf(0, 0, 1, 2, 1, 2, 0, 0, 2, 1, 2, 1)

        algo.findCommunities()

        val currentlyCommunities = mutableListOf<Int>()
        for (vertex in graph.getVertices()) {
            currentlyCommunities.add(vertex.community)
        }

        assertTrue(expectedCommunities == currentlyCommunities)
    }

    @Test
    fun `test louvain graph sample 2 directed`() {
        // graph and algo initialization
        val graph = createSampleGraph2()
        val algo = Louvain(graph)

        val expected = mutableListOf(setOf(1, 2, 3), setOf(4, 5, 6))

        algo.findCommunities()

        val currentlyCommunities = algo.findCommunities()

        assertTrue(expected == currentlyCommunities)
    }

    @Test
    fun `test louvain graph sample 2 not directed`() {
        // graph and algo initialization
        val graph = createSampleGraph2()
        graph.isDirected = false
        val algo = Louvain(graph)

        val expected = mutableListOf(setOf(1, 2, 3), setOf(4, 5, 6))

        algo.findCommunities()

        val currentlyCommunities = algo.findCommunities()

        assertTrue(expected == currentlyCommunities)
    }

    @Test
    fun `test louvain graph is empty`() {
        // graph and algo initialization
        val graph = Graph()
        graph.isDirected = false
        val algo = Louvain(graph)

        val expected = mutableListOf<Int>()

        algo.findCommunities()

        val currentlyCommunities = algo.findCommunities()

        assertTrue(expected == currentlyCommunities)
    }

    @Test
    fun `test louvain graph has no edges`() {
        // graph and algo initialization
        val graph = Graph()
        graph.addVertex(1, "A")
        graph.addVertex(2, "B")
        graph.addVertex(3, "C")

        val algo = Louvain(graph)

        val expected = mutableListOf(setOf(1), setOf(2), setOf(3))

        algo.findCommunities()

        val currentlyCommunities = algo.findCommunities()

        assertTrue(expected == currentlyCommunities)
    }
}
