package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import kotlinx.coroutines.launch
import viewmodel.LoadGraphMenuViewModel

@Composable
fun LoadGraph(viewModel: LoadGraphMenuViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var isWeighted by remember { mutableStateOf(false) }
    var isDirected by remember { mutableStateOf(false) }

    DialogWindow(
        onCloseRequest = { viewModel.canvasViewModel.isOpenLoadGraph = false},
        state = rememberDialogState(position = WindowPosition(Alignment.Center), size = DpSize(500.dp, 350.dp)),
        title = "Load New Graph",
        resizable = false
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            val modifierRow = Modifier.padding(0.dp, 5.dp, 0.dp, 5.dp)
            val verticalRow = Alignment.CenterVertically

            Row(modifierRow, verticalAlignment = verticalRow) {
                Text(
                    "Path to Database:",
                    modifier = Modifier.weight(0.5f),
                    textAlign = TextAlign.Center,

                )
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = viewModel.graphName.value,
                    onValueChange = { newValue -> viewModel.graphName.value = newValue },
                    label = { Text("Path") },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                    ),
                )
            }
            Row(modifierRow, verticalAlignment = verticalRow) {
//                ComboBox(
//                    items = VertexIDType.entries.toTypedArray(),
//                    modifier = Modifier.weight(1f),
//                    onItemClick = { item: VertexIDType -> viewModel.selectedVertexTypeID.value = item },
//                    textAlign = TextAlign.Center
//                )
            }
            Row(modifierRow, verticalAlignment = verticalRow) {
                Checkbox(
                    checked = isWeighted,
                    onCheckedChange = { isWeighted = it}
                )
                Text("Weighted")
                Checkbox(
                    checked = isDirected,
                    onCheckedChange = { isDirected = it}
                )
                Text("Directed")
//                Button(
//                    //selected = viewModel.isGraphDirected.value,
//                    onClick = { viewModel.isGraphDirected.value = !viewModel.isGraphDirected.value },
//
//                    //verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier.weight(1f),
//                ){Text("Directed graph")}
            }
            Row(modifierRow, verticalAlignment = verticalRow) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    colors = ButtonDefaults.buttonColors(
//                        backgroundColor = JetTheme.colors.tertiaryBackground,
//                        contentColor = JetTheme.colors.secondaryText,
//                        disabledContentColor = JetTheme.colors.secondaryText,
//                        disabledBackgroundColor = JetTheme.colors.tertiaryBackground
                    ),
                    onClick = { viewModel.canvasViewModel.isOpenLoadGraph = false }
                ) {
                    Text("Cancel")
                }
                Spacer(modifier = Modifier.weight(0.01f))
                Button(
                    colors = ButtonDefaults.buttonColors(
//                        backgroundColor = JetTheme.colors.tertiaryBackground,
//                        contentColor = JetTheme.colors.secondaryText,
//                        disabledContentColor = JetTheme.colors.secondaryText,
//                        disabledBackgroundColor = JetTheme.colors.tertiaryBackground
                    ),
                    onClick = {
                        if (viewModel.graphName.value.trim() == "") {
                            println("Invalid value")
                        } else {
                            viewModel.canvasViewModel.isOpenLoadGraph = false
                            coroutineScope.launch {
//                                val graph = viewModel.canvasViewModel.createGraph(
//                                    viewModel.graphName.value,
////                                    viewModel.selectedVertexTypeID.value,
//                                    viewModel.isGraphDirected.value,
//                                    viewModel.isGraphWeighted.value
//                                )
//                                viewModel.homePageViewModel.settings.saveGraph(
//                                    graph,
//                                    viewModel.selectedVertexTypeID.value,
//                                    viewModel.selectedSaveType.value
//                                )
                            }
                        }
                    }
                ) {
                    Text("Load")
                }
            }
        }
    }
}
