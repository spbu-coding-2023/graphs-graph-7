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
import model.databases.sqlite.SQLiteDBHandler
import model.graph.Graph
import viewmodel.LoadGraphMenuViewModel
import viewmodel.graph.GraphViewModel
import java.io.File

@Composable
fun LoadGraph(viewModel: LoadGraphMenuViewModel,) {
    val coroutineScope = rememberCoroutineScope()
    var isWeighted by remember { mutableStateOf(false) }
    var isDirected by remember { mutableStateOf(false) }


    val storageType = remember { mutableStateOf(StorageType.FILE) }
    var fileAddress by remember { mutableStateOf("") }
    var uri by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


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
                                "URI:",
                                modifier = Modifier.weight(0.5f),
                                textAlign = TextAlign.Center,

                                )
                            OutlinedTextField(
                                modifier = Modifier.weight(1f),
                                value = viewModel.graphName.value,
                                onValueChange = { newValue -> viewModel.graphName.value = newValue
                                                uri = newValue},
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
                                onValueChange = { newValue -> viewModel.graphName.value = newValue
                                                login = newValue},
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
                                onValueChange = { newValue -> viewModel.graphName.value = newValue
                                                password = newValue},
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
                                onValueChange = { newValue ->
                                    viewModel.graphName.value = newValue
                                                fileAddress=newValue},
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

            }
            Row(modifierRow, verticalAlignment = verticalRow) {
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { viewModel.canvasViewModel.isOpenLoadGraph = false }

                ) {
                    Text("Cancel")
                }
                Spacer(modifier = Modifier.weight(0.01f))
                Button(
                    onClick = {
                        when (storageType.value) {
                            StorageType.FILE -> {
                                // Логика сохранения в файл с использованием fileName и isDirectedGraph
                            }
                            StorageType.NEO4J -> {
                                val repository = Neo4jRepository(uri, login, password)
                                val handler = Neo4jHandler(repository)
                                val newGraph = handler.loadGraphFromNeo4j()
                                newGraph.isDirected = isDirected
                                viewModel.canvasViewModel.graph = newGraph
                                viewModel.canvasViewModel.graphViewModel = GraphViewModel(newGraph)
                                viewModel.canvasViewModel.isOpenLoadGraph = false
                        }
                            StorageType.SQLITE -> {
                                fileAddress = "examples/$fileAddress"
                                val dataBase: File = File(fileAddress)
                                val sqlHandler = SQLiteDBHandler()
                                sqlHandler.open(dataBase,isWeighted,isDirected)
                                viewModel.canvasViewModel.graph = sqlHandler.graph
                                if(sqlHandler.vertexViewModelFlag){
                                    viewModel.canvasViewModel.graphViewModel.graph = sqlHandler.graph
                                    viewModel.canvasViewModel.graphViewModel = sqlHandler.graphViewModel
                                }
                                else{
                                    viewModel.canvasViewModel.graphViewModel = GraphViewModel(sqlHandler.graph)
                                }
                                viewModel.canvasViewModel.representationStrategy.place(1280.0, 860.0,
                                    viewModel.canvasViewModel.graphViewModel)
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
