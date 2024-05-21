package viewmodel.graph

import androidx.compose.runtime.State
import graph.model.Edge

class EdgeViewModel(
    val u: VertexViewModel,
    val v: VertexViewModel,
    private val e: Edge,
) {
    val weight
        get() = e.weight.toString()
}