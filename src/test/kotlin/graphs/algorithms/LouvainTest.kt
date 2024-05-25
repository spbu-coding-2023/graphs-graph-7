package graphs.algorithms

import algorithms.Kosaraju
import graph.model.Graph
import model.community.Louvain
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
        val expected = mutableListOf(setOf(0,7,1,6), setOf(9,11,2,4), setOf(10,5,3,8))

        val currently = algo.findCommunities()

        assertTrue(expected == currently)
    }

}