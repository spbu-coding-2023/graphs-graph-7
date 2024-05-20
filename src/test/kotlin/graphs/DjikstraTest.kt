package graphs

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

fun createSampleGraphDjikstra(): Graph {
    val graph = Graph()

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
    private val graph = createSampleGraphDjikstra()

    @Test
    fun `test findShortestPaths with sample graph start from 1 to 5 started from 1`() {
        // graph and algo initialization
        val expected = mutableListOf(1,4,3,5)
        val algorithm = Djikstra(graph)
        // path created from 1
        algorithm.findShortestPaths(1)


        // algo start from different positions
        val currently = algorithm.reconstructPath(1,5)

        // Проверьте результаты
        assertTrue(expected == currently)
    }

    @Test
    fun `test findShortestPaths with sample graph start from 1 to 2`() {
        // graph and algo initialization
        val expected = mutableListOf(1,2)
        val algorithm = Djikstra(graph)
        algorithm.findShortestPaths(1)


        // algo start from different positions
        val currently = algorithm.reconstructPath(1,2)

        // Проверьте результаты
        assertTrue(expected == currently)
    }

}