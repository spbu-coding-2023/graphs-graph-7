package viewmodel.layouts

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import graph.model.Graph
import layout.tsnet.tsNET
import viewmodel.graph.VertexViewModel
import kotlin.random.Random

class TsNETLayout(graph: Graph,perplexity: Double, learningRate: Double):RepresentationStrategy {
    val perplexity = perplexity
    val learningRate = learningRate
    val graph = graph
    override fun place(
        width: Double,
        height: Double,
        vertices: Collection<VertexViewModel>,
    ){
        val coords = tsNET(graph, vertices, perplexity, learningRate)
        var i = 0
        vertices.forEach { vertex ->
            vertex.x = (coords[i][0]*1000.0).dp
            vertex.y = (coords[i][1]*1000.0).dp
            i++
        }
    }
    override fun highlight(vertices: Collection<VertexViewModel>) {
        vertices
            .onEach {
                it.color = if (Random.nextBoolean()) Color.Green else Color.Blue
            }
    }
}