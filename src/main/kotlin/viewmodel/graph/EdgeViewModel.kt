package viewmodel.graph

import androidx.compose.runtime.State
import graph.model.Edge

class EdgeViewModel(
    val u: VertexViewModel,
    val v: VertexViewModel,
    private val e: Edge,
    private val _labelVisible: State<Boolean>,
) {
    val weight
        get() = e.weight.toString()

    val labelVisible
        get() = _labelVisible.value
}