package view.graph

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import viewmodel.graph.GraphViewModel
import view.graph.Gra

@Composable
fun GraphView(
    viewModel: GraphViewModel,
) {
    Box(modifier = Modifier
        .fillMaxSize()

    ) {
        viewModel.verticesViewValues.forEach { v ->
            VertexView(v, Modifier)
        }
        viewModel.edgesViewValues.forEach { e ->
            EdgeView(e, Modifier)
        }
    }
}