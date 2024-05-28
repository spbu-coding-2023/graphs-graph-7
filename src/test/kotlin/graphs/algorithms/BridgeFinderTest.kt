package graphs.algorithms

import model.algorithms.BridgeFinder
import graph.model.Graph
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class BridgeFinderTest {
    private lateinit var graph: Graph
    @BeforeEach
    fun setup() {
        graph = Graph().apply {
            addVertex(1,"Thomas Shelby")
            addVertex(2, "Andrew Tate")
            addVertex(3, "Iakov")
            addVertex(4, "John Shelby")
            addVertex(5, "Tristan Tate")
            addVertex(6, "Arthur Shelby")
            addVertex(7, "Ryan Gosling")

            addEdge(1, 2, 1,1)
            addEdge(3, 4, 2,2)
            addEdge(1, 3, 3,3)
            addEdge(2, 4, 4,4)
            addEdge(2, 5, 5,5)
            addEdge(5, 7, 6,6)

            addVertex(8, "Pudge")
            addVertex(9,"Tiny")
            addVertex(10, "Lycan")
            addVertex(11,"Io")
            addVertex(12,"Lion")
            addVertex(13,"Sniper")
            addVertex(14,"Roshan")

            addEdge(14, 8, 7,7)
            addEdge(14, 9, 8,8)
            addEdge(14, 10, 9,9)
            addEdge(14, 11, 10,10)
            addEdge(14, 12, 11,11)
            addEdge(14, 13, 12,12)

            addEdge(14, 3, 0,13)
        }
    }
    @Test
    @DisplayName("Multiple bridges, no multiple edges")
    public fun MultipleBridgesNoMultipleEdges(){
        val bridgeFinder = BridgeFinder(graph)
        bridgeFinder.findBridges()
        val result = bridgeFinder.bridges
        val expectedBridges = listOf(7, 8, 9, 10, 11, 12, 13, 6, 5)
        assertEquals(expectedBridges,result)
    }
}