package view.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import viewmodel.graph.VertexViewModel

@Composable
fun VertexView(
    viewModel: VertexViewModel,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .size(viewModel.radius + 10.dp, viewModel.radius + 10.dp)
                .offset(viewModel.x, viewModel.y)
                .border(2.dp, Color.Black, CircleShape)
                .background(color = viewModel.color, shape = CircleShape)
                .pointerInput(viewModel) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        viewModel.onDrag(dragAmount)
                    }
                }
    ) {
        if (viewModel.labelVisible) {
            Text(
                modifier = Modifier.align(Alignment.Center).offset(0.dp, -viewModel.radius - 10.dp),
                text = viewModel.label,
            )
        }
    }
}
