package graphs.algorithms

import model.algorithms.FordBellman
import graph.model.Graph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class FordBellmanTest {
    private lateinit var graph: Graph

    @Test
    @DisplayName("Path without negative cycles")
    public fun PathWithoutNegativeCycles(){
        graph = Graph().apply {
            addVertex(1, "Thomas Shelby")
            addVertex(2, "Andrew Tate")
            addVertex(3, "Iakov")
            addVertex(4, "John Shelby")
            addVertex(5, "Arthur Shelby")
            addEdge(1, 2, 3, 5)
            addEdge(2, 3, 6, 2)
            addEdge(1, 4, 2, 4)
            addEdge(5, 3, 1, 3)
            addEdge(1, 3, 5, 1)
            addEdge(4, 5, 1, 6)
        }
        val fordBellman = FordBellman(graph)
        fordBellman.shortestPath(1,3)
        val resultVertices = fordBellman.resultPathVertices
        val resultEdges = fordBellman.resultPathEdges
        val resultCycles = fordBellman.cycleFlag
        val resultConnectionFlag = fordBellman.disconnectedGraphFlag
        val expectedCycles = false
        val expectedEdges = listOf(3,6,4)
        val expectedVertices = listOf(3,5,4,1)
        val expectedConnectionFlag = false
        assertEquals(expectedConnectionFlag, resultConnectionFlag)
        assertEquals(expectedCycles, resultCycles)
        assertEquals(expectedEdges, resultEdges)
        assertEquals(expectedVertices, resultVertices)
    }
    @Test
    @DisplayName("Path with negative cycles")
    public fun PathWithNegativeCycles(){
        graph = Graph().apply {
            addVertex(1, "Thomas Shelby")
            addVertex(2, "Andrew Tate")
            addVertex(3, "Iakov")
            addVertex(4, "John Shelby")
            addVertex(5, "John Shelby")

            addEdge(1, 2, -2, 3)
            addEdge(2, 3, 1, 2)
            addEdge(4, 1, -1, 1)
            addEdge(3, 4, 1, 4)
            addEdge(4, 5, 5, 5)
        }
        val fordBellman = FordBellman(graph)
        fordBellman.shortestPath(1,5)
        val resultVertices = fordBellman.resultPathVertices
        val resultEdges = fordBellman.resultPathEdges
        val resultCycles = fordBellman.cycleFlag
        val resultConnectionFlag = fordBellman.disconnectedGraphFlag
        val expectedCycles = true
        val expectedEdges = listOf(3,1,4,2)
        val expectedVertices = listOf(2,1,4,3)
        val expectedConnectionFlag = false
        assertEquals(expectedConnectionFlag, resultConnectionFlag)
        assertEquals(expectedCycles, resultCycles)
        assertEquals(expectedEdges, resultEdges)
        assertEquals(expectedVertices, resultVertices)
    }
    @Test
    @DisplayName("Disconnected graph")
    public fun DisconnectedGraph(){
        graph = Graph().apply {
            addVertex(1, "Thomas Shelby")
            addVertex(2, "Andrew Tate")
            addVertex(3, "Iakov")
            addVertex(4, "John Shelby")
            addVertex(5, "John Shelby")

            addEdge(1, 2, 2, 3)
            addEdge(2, 3, 1, 2)
            addEdge(4, 1, -1, 1)
            addEdge(3, 4, 1, 4)

        }
        val fordBellman = FordBellman(graph)
        fordBellman.shortestPath(1,5)
        val resultVertices = fordBellman.resultPathVertices
        val resultEdges = fordBellman.resultPathEdges
        val resultCycles = fordBellman.cycleFlag
        val resultConnectionFlag = fordBellman.disconnectedGraphFlag
        val expectedCycles = false
        val expectedEdges = listOf<Int>()
        val expectedVertices = listOf<Int>()
        val expectedConnectionFlag = true
        assertEquals(expectedConnectionFlag, resultConnectionFlag)
        assertEquals(expectedCycles, resultCycles)
        assertEquals(expectedEdges, resultEdges)
        assertEquals(expectedVertices, resultVertices)
    }
}