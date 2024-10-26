package view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import model.databases.neo4j.Neo4jHandler
import model.databases.neo4j.Neo4jRepository
import model.databases.sqlite.SQLiteDBHandler
import viewmodel.SaveGraphMenuViewModel
import viewmodel.graph.GraphViewModel
import androidx.compose.ui.text.input.PasswordVisualTransformation
import java.io.File

@Composable
fun SaveGraph(viewModel: SaveGraphMenuViewModel) {
    var isWeighted by remember { mutableStateOf(false) }
    var isDirected by remember { mutableStateOf(false) }

    DialogWindow(
        onCloseRequest = { viewModel.canvasViewModel.isOpenSaveGraph.value = false },
        state =
            rememberDialogState(
                position = WindowPosition(Alignment.Center),
                size = DpSize(800.dp, 640.dp)
            ),
        title = "Save New Graph",
        resizable = false
    ) {
        Column(Modifier.fillMaxSize().padding(4.dp)) {
            val modifierRow = Modifier.padding(0.dp, 5.dp, 0.dp, 5.dp)
            val verticalRow = Alignment.CenterVertically

            Row(modifierRow, verticalAlignment = verticalRow) {}

            Row(modifierRow, verticalAlignment = verticalRow) {
                Column(
                    modifier = Modifier.height(450.dp).padding(16.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    CustomRadioGroup(
                        options =
                            listOf(
                                StorageType.FILE.toString(),
                                StorageType.NEO4J.toString(),
                                StorageType.SQLITE.toString()
                            ),
                        selectedOption = viewModel.storageType.value.toString(),
                        onOptionSelected = { viewModel.storageType.value = StorageType.valueOf(it) }
                    )

                    when (viewModel.storageType.value) {
                        StorageType.FILE -> {
                            TextField(
                                value = viewModel.fileName.value,
                                onValueChange = { viewModel.fileName.value = it },
                                label = { Text("File Name") }
                            )
                            Text("Ориентированный ли граф")
                            Checkbox(checked = isDirected, onCheckedChange = { isDirected = it })
                            Spacer(modifier = Modifier.width(8.dp))

                            Text("Взвешанные ли рёбра")
                            Checkbox(checked = isWeighted, onCheckedChange = { isWeighted = it })
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        StorageType.NEO4J -> {
                            TextField(
                                value = viewModel.uri.value,
                                onValueChange = { viewModel.uri.value = it },
                                label = { Text("URI") }
                            )
                            TextField(
                                value = viewModel.login.value,
                                onValueChange = { viewModel.login.value = it },
                                label = { Text("Login") }
                            )
                            TextField(
                                value = viewModel.password.value,
                                onValueChange = { viewModel.password.value = it },
                                label = { Text("Password") },
                                visualTransformation = PasswordVisualTransformation()
                            )
                            Text("Ориентированный ли граф")
                            Checkbox(checked = isDirected, onCheckedChange = { isDirected = it })
                            Spacer(modifier = Modifier.width(8.dp))

                            Text("Взвешанные ли рёбра")
                            Checkbox(checked = isWeighted, onCheckedChange = { isWeighted = it })
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        StorageType.SQLITE -> {
                            TextField(
                                value = viewModel.fileName.value,
                                onValueChange = { viewModel.fileName.value = it },
                                label = { Text("File Name") }
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text("Взвешанные ли рёбра")
                            Checkbox(checked = isWeighted, onCheckedChange = { isWeighted = it })
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                }
            }

            Row(modifierRow, verticalAlignment = verticalRow) {
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { viewModel.canvasViewModel.isOpenSaveGraph.value = false }) {
                    Text("Cancel")
                }
                Spacer(modifier = Modifier.weight(0.01f))
                Button(
                    onClick = {
                        when (viewModel.storageType.value) {
                            StorageType.FILE -> {
                                // Логика сохранения в файл с использованием fileName и
                                // isDirectedGraph
                            }
                            StorageType.NEO4J -> {
                                // val repo = Neo4jRepository(uri.value, login.value, password.value)
                                // val handler = Neo4jHandler(repo)
                                // val wasGraphDirected = viewModel.graph.isDirected
                                // viewModel.graph.isDirected = isDirectedGraph.value
                                // handler.saveGraphToNeo4j(viewModel.graph)
                                // viewModel.graph.isDirected = wasGraphDirected
                            }
                            StorageType.SQLITE -> {
                                val fileAddress = "saves/sqlite/${viewModel.fileName.value}"
                                val dataBase: File = File(fileAddress)
                                val sqlHandler = SQLiteDBHandler()
                                sqlHandler.save(
                                    dataBase,
                                    viewModel.canvasViewModel.graph,
                                    viewModel.canvasViewModel.graphViewModel,
                                    isWeighted
                                )
                                viewModel.canvasViewModel.isOpenSaveGraph.value = false
                            }
                        }
                    }
                ) {
                    Text("Save")
                }
            }
        }
    }
}
