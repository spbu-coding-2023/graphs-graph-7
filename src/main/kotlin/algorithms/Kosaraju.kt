package algorithms

import graph.model.Graph

class Kosaraju<V>(private val graph: Graph<V>) {
    private val used = hashMapOf<Int, Boolean>()
    private val order = mutableListOf<Int>()
    private val component = mutableListOf<Int>()
}
