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
fun MyNavigationDrawer(viewModel: CanvasViewModel) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            Column(modifier = Modifier.width(370.dp)) {
                IconButton(onClick = { scope.launch { drawerState.close() } }) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                }
                Text("Menu Item 1", modifier = Modifier.padding(10.dp).clickable { scope.launch { drawerState.close() } })
                Text("Menu Item 2", modifier = Modifier.padding(10.dp).clickable { scope.launch { drawerState.close() } })
                Row {
                    Checkbox(checked = viewModel.showVerticesLabels.value, onCheckedChange = {
                        viewModel.showVerticesLabels.value = it
                    })
                    Text("Show vertices labels", fontSize = 28.sp, modifier = Modifier.padding(4.dp))
                }
                Row {
                    Checkbox(checked = viewModel.showEdgesLabels.value, onCheckedChange = {
                        viewModel.showEdgesLabels.value = it
                    })
                    Text("Show edges labels", fontSize = 28.sp, modifier = Modifier.padding(4.dp))
                }
                Button(
                    onClick = viewModel::resetGraphView,
                    enabled = true,
                ) {
                    Text(
                        text = "Reset default settings",
                    )
                }
                Button(
                    onClick = viewModel::setVerticesColor,
                    enabled = true,
                ) {
                    Text(
                        text = "Set colors",
                    )
                }
            }
        },
        content = {
            Column(modifier = Modifier.width(370.dp)) {
                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }
            }
        }
    )
}