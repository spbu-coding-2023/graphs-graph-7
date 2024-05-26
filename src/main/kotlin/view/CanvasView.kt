package view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import view.graph.GraphView
import viewmodel.CanvasViewModel

@Composable
fun Canvas(viewModel: CanvasViewModel) {
    MyNavigationDrawer(viewModel)
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(modifier = Modifier.width(370.dp)) {
        }

        Surface(
            modifier = Modifier.weight(1f),
        ) {
            GraphView(viewModel.graphViewModel)
        }

    }
}