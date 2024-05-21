package viewmodel.graph

import androidx.compose.runtime.State
import model.graph.Edge

class EdgeViewModel(
    val u: VertexViewModel,
    val v: VertexViewModel,
    private val e: Edge,
) {
    val weight
        get() = e.weight.toString()
}