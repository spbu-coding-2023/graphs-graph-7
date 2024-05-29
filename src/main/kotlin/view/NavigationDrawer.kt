package view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import view.graph.GraphView
import viewmodel.CanvasViewModel

@Composable
fun NavigationDrawer(viewModel: CanvasViewModel) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val showSubMenu = remember {
        mutableStateOf(false)
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Row {
                    IconButton(onClick = { scope.launch { drawerState.close() } }
                    ) {
                        Icon(Icons.Filled.Menu, contentDescription = "Меню")
                    }
                    Text("TOP G GANG", modifier = Modifier.padding(16.dp))
                }
                Divider()
                NavigationDrawerItem(
                    label = { Text("Menu Item 1") },
                    modifier = Modifier.padding(10.dp),
                    onClick = { scope.launch { drawerState.close() } },
                    selected = false
                )
                NavigationDrawerItem(
                    label = { Text(text = "Настройки") },
                    icon = {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = null
                        )
                    },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Доступные алгоритмы") },
                    icon = {
                        Icon(
                            Icons.Filled.List,
                            contentDescription = null
                        )
                    },
                    selected = false,
                    onClick = { showSubMenu.value = !showSubMenu.value }
                )
                AnimatedVisibility(visible = showSubMenu.value) {
                    AlgorithmSubMenu()
                }
                Row {
                    Checkbox(checked = viewModel.showVerticesLabels.value, onCheckedChange = {
                        viewModel.showVerticesLabels.value = it
                    })
                    Text("Show vertices labels", fontSize = 20.sp, modifier = Modifier.padding(0.dp))
                }
                Row {
                    Checkbox(checked = viewModel.showEdgesLabels.value, onCheckedChange = {
                        viewModel.showEdgesLabels.value = it
                    })
                    Text("Show edges labels", fontSize = 20.sp, modifier = Modifier.padding(4.dp))
                }
            }
        },
    ) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Add graph") },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
            }
        ) { GraphView(viewModel.graphViewModel) }
        IconButton(onClick = { scope.launch { drawerState.open() } }) {
            Icon(Icons.Filled.Menu, contentDescription = "Меню")
        }
    }
}