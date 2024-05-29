package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import kotlinx.coroutines.launch
import model.databases.neo4j.Neo4jHandler
import model.databases.neo4j.Neo4jRepository
import viewmodel.LoadGraphMenuViewModel

@Composable
fun LoadGraph(viewModel: LoadGraphMenuViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var isWeighted by remember { mutableStateOf(false) }
    var isDirected by remember { mutableStateOf(false) }
    var isSqlite by remember { mutableStateOf(false) }
    var isNeo4j by remember { mutableStateOf(false) }
    var isCSV by remember { mutableStateOf(false) }
    val storageType = remember { mutableStateOf(StorageType.FILE) }

    DialogWindow(
        onCloseRequest = { viewModel.canvasViewModel.isOpenLoadGraph = false },
        state = rememberDialogState(position = WindowPosition(Alignment.Center), size = DpSize(800.dp, 640.dp)),
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

            }
            Row(modifierRow, verticalAlignment = verticalRow) {
                Column ( modifier = Modifier
                    .height(450.dp)
                    .padding(16.dp),
                    verticalArrangement = Arrangement.Top
                ){
                    CustomRadioGroup(
                        options = listOf(
                            StorageType.FILE.toString(),
                            StorageType.NEO4J.toString(),
                            StorageType.SQLITE.toString()
                        ),
                        selectedOption = storageType.value.toString(),
                        onOptionSelected = { storageType.value = StorageType.valueOf(it) }
                    )
                    when (storageType.value) {
                        StorageType.FILE -> {
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

                        StorageType.NEO4J -> {
                            Text(
                                "URL:",
                                modifier = Modifier.weight(0.5f),
                                textAlign = TextAlign.Center,

                                )
                            OutlinedTextField(
                                modifier = Modifier.weight(1f),
                                value = viewModel.graphName.value,
                                onValueChange = { newValue -> viewModel.graphName.value = newValue },
                                label = { Text("URI") },
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                ),
                            )
                            Text(
                                "Login:",
                                modifier = Modifier.weight(0.5f),
                                textAlign = TextAlign.Center,

                                )
                            OutlinedTextField(
                                modifier = Modifier.weight(1f),
                                value = viewModel.graphName.value,
                                onValueChange = { newValue -> viewModel.graphName.value = newValue },
                                label = { Text("Login") },
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                ),
                            )
                            Text(
                                "Password:",
                                modifier = Modifier.weight(0.5f),
                                textAlign = TextAlign.Center,

                                )
                            OutlinedTextField(
                                modifier = Modifier.weight(1f),
                                value = viewModel.graphName.value,
                                onValueChange = { newValue -> viewModel.graphName.value = newValue },
                                label = { Text("Password") },
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                ),
                            )
                        }

                        StorageType.SQLITE -> {
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
                    }
                }

            }
            Row(modifierRow, verticalAlignment = verticalRow) {
                Checkbox(
                    checked = isWeighted,
                    onCheckedChange = { isWeighted = it }
                )
                Text("Weighted")
                Checkbox(
                    checked = isDirected,
                    onCheckedChange = { isDirected = it }
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
                Button(onClick = { viewModel.canvasViewModel.isOpenLoadGraph = false }

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
                        when (storageType.value) {
                            StorageType.FILE -> {
                                // Логика сохранения в файл с использованием fileName и isDirectedGraph
                            }
                            StorageType.NEO4J -> {
                                // Логика сохранения в Neo4j

                            }
                            StorageType.SQLITE -> {
                                // Логика сохранения в SQLite
                            }

                        }
                        viewModel.canvasViewModel.isOpenLoadGraph = false }
                ) {
                    Text("Load")
                }
            }
        }
    }
}
