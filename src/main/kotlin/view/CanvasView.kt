package view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.io.File
import kotlinx.coroutines.launch
import model.databases.neo4j.Neo4jHandler
import model.databases.neo4j.Neo4jRepository
import model.databases.sqlite.SQLiteDBHandler
import view.graph.GraphView
import viewmodel.CanvasViewModel
import viewmodel.LoadGraphMenuViewModel

@Composable
fun Canvas(viewModel: CanvasViewModel) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val showDialog = remember { mutableStateOf(false) }
    val storageType = remember { mutableStateOf(StorageType.FILE) }
    val fileName = remember { mutableStateOf("") }
    val isDirectedGraph = remember { mutableStateOf(false) }
    val isWeighted = remember { mutableStateOf(false) }
    val uri = remember { mutableStateOf("") }
    val login = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    AnimatedVisibility(visible = showDialog.value) {
        Dialog(
            onDismissRequest = { showDialog.value = false },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Choose where to save the graph:")
                CustomRadioGroup(
                    selectedOption = storageType.value.toString(),
                    options =
                        listOf(
                            StorageType.FILE.toString(),
                            StorageType.NEO4J.toString(),
                            StorageType.SQLITE.toString()
                        ),
                    onOptionSelected = { storageType.value = StorageType.valueOf(it) }
                )

                when (storageType.value) {
                    StorageType.FILE -> {
                        TextField(
                            value = fileName.value,
                            onValueChange = { fileName.value = it },
                            label = { Text("File Name") }
                        )
                        Text("Ориентированный ли граф")
                        Checkbox(
                            checked = isDirectedGraph.value,
                            onCheckedChange = { isDirectedGraph.value = it },
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Взвешанные ли рёбра")
                        Checkbox(
                            checked = isWeighted.value,
                            onCheckedChange = { isWeighted.value = it },
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    StorageType.NEO4J -> {
                        TextField(
                            value = uri.value,
                            onValueChange = { uri.value = it },
                            label = { Text("URI") }
                        )
                        TextField(
                            value = login.value,
                            onValueChange = { login.value = it },
                            label = { Text("Login") }
                        )
                        TextField(
                            value = password.value,
                            onValueChange = { password.value = it },
                            label = { Text("Password") },
                            visualTransformation = PasswordVisualTransformation()
                        )
                        Text("Ориентированный ли граф")
                        Checkbox(
                            checked = isDirectedGraph.value,
                            onCheckedChange = { isDirectedGraph.value = it },
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Взвешанные ли рёбра")
                        Checkbox(
                            checked = isWeighted.value,
                            onCheckedChange = { isWeighted.value = it },
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    StorageType.SQLITE -> {
                        TextField(
                            value = fileName.value,
                            onValueChange = { fileName.value = it },
                            label = { Text("File Name") }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Взвешанные ли рёбра")
                        Checkbox(
                            checked = isWeighted.value,
                            onCheckedChange = { isWeighted.value = it },
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }

                Button(
                    onClick = {
                        when (storageType.value) {
                            StorageType.FILE -> {
                                // Логика сохранения в файл с использованием fileName и
                                // isDirectedGraph
                            }
                            StorageType.NEO4J -> {
                                // Логика сохранения в Neo4j
                                val repo = Neo4jRepository(uri.value, login.value, password.value)
                                val handler = Neo4jHandler(repo)
                                val wasGraphDirected = viewModel.graph.isDirected
                                viewModel.graph.isDirected = isDirectedGraph.value
                                handler.saveGraphToNeo4j(viewModel.graph)
                                viewModel.graph.isDirected = wasGraphDirected
                            }
                            StorageType.SQLITE -> {
                                val fileAddress = "saves/sqlite/${fileName.value}"
                                val dataBase = File(fileAddress)
                                val sqlHandler = SQLiteDBHandler()
                                sqlHandler.save(
                                    dataBase,
                                    viewModel.graph,
                                    viewModel.graphViewModel,
                                    isWeighted.value
                                )
                            }
                        }
                        showDialog.value = false
                    }
                ) {
                    Text("Save")
                }
            }
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        contentColor = Color.LightGray,
        color = Color.DarkGray
    ) {
        val showSubMenu = remember { mutableStateOf(false) }
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = true,
            drawerContent = {
                ModalDrawerSheet {
                    Row {
                        IconButton(onClick = { scope.launch { drawerState.close() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Меню")
                        }
                        Text("Menu", modifier = Modifier.padding(16.dp))
                    }
                    Divider()

                    NavigationDrawerItem(
                        label = { Text(text = "Доступные алгоритмы") },
                        icon = { Icon(Icons.Filled.List, contentDescription = null) },
                        selected = false,
                        onClick = { showSubMenu.value = !showSubMenu.value }
                    )
                    AnimatedVisibility(visible = showSubMenu.value) { AlgorithmSubMenu(viewModel) }
                    Button(onClick = { viewModel.isOpenLoadGraph = true }) { Text("Load graph") }
                    Button(enabled = true, onClick = { scope.launch { showDialog.value = true } }) {
                        Text(text = "Save Graph")
                    }
                }
            },
        ) {
            Scaffold(
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        text = { Text("Show drawer") },
                        icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                        onClick = {
                            scope.launch { drawerState.apply { if (isClosed) open() else close() } }
                        }
                    )
                }
            ) {
                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Меню")
                }
            }
            GraphView(viewModel.graphViewModel)
        }
    }

    if (viewModel.isOpenLoadGraph) {
        LoadGraph(LoadGraphMenuViewModel(viewModel))
    }
}
