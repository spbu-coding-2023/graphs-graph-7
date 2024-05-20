package algorithms

import graph.model.Graph

class Djikstra(private val graph: Graph) {
    private val distance = hashMapOf<Int, Long>()
    private val visited = hashMapOf<Int, Boolean>()
    private val from = hashMapOf<Int, Int>()
}