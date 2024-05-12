package graphs

import algorithms.Kosaraju
import graph.model.Graph
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

//               graph sample
//
//    7              11 → → → → → → → 5
//  ↗ ↓ ↘          ↙    ↘           ↙ ↑ ↘
// 0  ↓  1 → → → 9 ← ← ← 2 → → →  10  ↑   3
//  ↖ ↓ ↗          ↘   ↗            ↘ ↑ ↙
//    6 → → → → → →  4                8
//
// components: (0,7,1,6) (11) (9,2,4) (10,8,3,5)

fun createSampleGraph() : Graph<Int> {
    // Добавление вершин
    val graph = Graph<Int>()
    for (i in 0..11) {
        graph.addVertex(i, i)
    }

    // Добавление рёбер
    graph.addEdge(0, 7, 1L, 0)
    graph.addEdge(7, 6, 1L, 1)
    graph.addEdge(6, 0, 1L, 2)
    graph.addEdge(6, 1, 1L, 3)
    graph.addEdge(6, 4, 1L, 4)
    graph.addEdge(7, 1, 1L, 5)
    graph.addEdge(1, 9, 1L, 6)
    graph.addEdge(9, 4, 1L, 7)
    graph.addEdge(4, 2, 1L, 8)
    graph.addEdge(2, 9, 1L, 9)
    graph.addEdge(2, 10, 1L, 10)
    graph.addEdge(11, 9, 1L, 11)
    graph.addEdge(11, 2, 1L, 12)
    graph.addEdge(11, 5, 1L, 13)
    graph.addEdge(10, 8, 1L, 14)
    graph.addEdge(8, 5, 1L, 15)
    graph.addEdge(5, 10, 1L, 16)
    graph.addEdge(5, 3, 1L, 17)
    graph.addEdge(3, 8, 1L, 18)

    return graph
}

class KosarajuTest {
    @Test
    fun `test dfs1 with sample graph start from 0 component`() {
        // graph and algo initialization
        val graph = createSampleGraph()
        val algo = Kosaraju(graph)
        val expected = mutableListOf(11, 3, 5, 8, 10, 2, 4, 9, 1, 6, 7, 0)

        // algo start from different positions
        val currently = algo.Test_dfs1(0)

        // Проверьте результаты
        assertTrue(expected == currently)
    }

    @Test
    fun `test dfs1 with sample graph start from 5 component`() {
        // graph and algo initialization
        val graph = createSampleGraph()
        val algo = Kosaraju(graph)
        val expected = mutableListOf(0, 7, 3, 8, 10, 2, 4, 6, 1, 9, 11, 5)

        // algo start from different positions
        val currently = algo.Test_dfs1(5)

        // Проверьте результаты
        assertTrue(expected == currently)
    }
}