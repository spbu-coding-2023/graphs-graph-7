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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import view.graph.GraphView
import viewmodel.CanvasViewModel
import viewmodel.LoadGraphMenuViewModel
import viewmodel.SaveGraphMenuViewModel
import viewmodel.layouts.CircularLayout
import viewmodel.layouts.ForceAtlas2Layout

@ExperimentalStdlibApi
@Composable
fun Canvas(viewModel: CanvasViewModel) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val saveGraphMenuViewModel = remember { SaveGraphMenuViewModel(viewModel) }

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
                    HorizontalDivider()
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = { viewModel.switchLayout(ForceAtlas2Layout()) }) {
                            Text("Force Atlas 2")
                        }
                        Button(onClick = { viewModel.switchLayout(CircularLayout()) }) {
                            Text("Circular Layout")
                        }
                    }
                    NavigationDrawerItem(
                        label = { Text(text = "Доступные алгоритмы") },
                        icon = { Icon(Icons.Filled.List, contentDescription = null) },
                        selected = false,
                        onClick = { showSubMenu.value = !showSubMenu.value }
                    )
                    AnimatedVisibility(visible = showSubMenu.value) { AlgorithmSubMenu(viewModel) }
                    Button(onClick = { viewModel.isOpenLoadGraph = true }) { Text("Load graph") }
                    Button(onClick = { viewModel.isOpenSaveGraph.value = true }) {
                        Text(text = "Save Graph")
                    }
                }
            },
        ) {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(Icons.Filled.Menu, contentDescription = "Меню")
            }
            GraphView(viewModel.graphViewModel)
        }
    }

    if (viewModel.isOpenLoadGraph) {
        LoadGraph(LoadGraphMenuViewModel(viewModel))
    }

    if (viewModel.isOpenSaveGraph.value) {
        SaveGraph(saveGraphMenuViewModel)
    }
}
