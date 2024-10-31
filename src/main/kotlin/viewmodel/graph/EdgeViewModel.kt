package viewmodel.graph

import model.graph.Edge
import androidx.compose.ui.graphics.Color

class EdgeViewModel(
    val u: VertexViewModel,
    val v: VertexViewModel,
    val e: Edge,
    var color: Color,
) {
    val weight
        get() = e.weight.toString()
}
