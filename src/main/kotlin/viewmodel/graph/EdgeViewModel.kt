package viewmodel.graph

import model.graph.Edge

class EdgeViewModel(
    val u: VertexViewModel,
    val v: VertexViewModel,
    val e: Edge,
) {
    val weight
        get() = e.weight.toString()
}
