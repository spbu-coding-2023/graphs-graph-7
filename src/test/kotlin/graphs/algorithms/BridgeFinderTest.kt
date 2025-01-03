package graphs.algorithms

import model.algorithms.BridgeFinder
import model.graph.Graph
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class BridgeFinderTest {
    private lateinit var graph: Graph

    @BeforeEach
    fun setup() {
        graph =
            Graph().apply {
                addVertex(1, "Thomas Shelby")
                addVertex(2, "Andrew Tate")
                addVertex(3, "Iakov")
                addVertex(4, "John Shelby")
                addVertex(5, "Tristan Tate")
                addVertex(6, "Arthur Shelby")
                addVertex(7, "Ryan Gosling")

                addEdge(1, 2, 1f, 1)
                addEdge(3, 4, 2f, 2)
                addEdge(1, 3, 3f, 3)
                addEdge(2, 4, 4f, 4)
                addEdge(2, 5, 5f, 5)
                addEdge(5, 7, 6f, 6)

                addVertex(8, "Pudge")
                addVertex(9, "Tiny")
                addVertex(10, "Lycan")
                addVertex(11, "Io")
                addVertex(12, "Lion")
                addVertex(13, "Sniper")
                addVertex(14, "Roshan")

                addEdge(14, 8, 7f, 7)
                addEdge(14, 9, 8f, 8)
                addEdge(14, 10, 9f, 9)
                addEdge(14, 11, 10f, 10)
                addEdge(14, 12, 11f, 11)
                addEdge(14, 13, 12f, 12)

                addEdge(14, 3, 0f, 13)
            }
    }

    @Test
    @DisplayName("Multiple bridges, no multiple edges")
    public fun MultipleBridgesNoMultipleEdges() {
        val bridgeFinder = BridgeFinder(graph)
        bridgeFinder.findBridges()
        val result = bridgeFinder.bridges
        val expectedBridges = listOf(7, 8, 9, 10, 11, 12, 13, 6, 5)
        assertEquals(expectedBridges, result)
    }
}
