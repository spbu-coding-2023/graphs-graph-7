package view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import controller.GraphPainterByCommunity
import controller.GraphPainterByDjikstra
import controller.GraphPainterByKosaraju
import viewmodel.CanvasViewModel

@Composable
fun AlgorithmSubMenu(viewModel: CanvasViewModel) {
    val showDialog = remember { mutableStateOf(false) }
    var startIdx by remember { mutableStateOf(0) }
    var endIdx by remember { mutableStateOf(0) }

    Column(Modifier.padding(start = 16.dp, end = 0.dp, top = 15.dp)) {
        Button(
            onClick = { /*TODO*/ },
            enabled = true,
        ) {
            Text(
                text = "Выделение ключевых вершин",
            )
        }
        Button(
            onClick = {
                val graph = viewModel.graph
                val painter = GraphPainterByCommunity(graph, viewModel.graphViewModel)
                painter.paint()
            },
            enabled = true,
        ) {
            Text(
                text = "Поиск сообществ",
            )
        }
        Button(
            onClick = {
                val graph = viewModel.graph
                val painter = GraphPainterByKosaraju(graph, viewModel.graphViewModel)
                painter.paint()
            },
            enabled = true,
        ) {
            Text(
                text = "Выделение компонент сильной связности",
            )

            ShortestPathDialog(showDialog) { enteredStartIdx, enteredEndIdx ->
                startIdx = enteredStartIdx
                endIdx = enteredEndIdx
                showDialog.value = false

                viewModel.graph.let { graph ->
                    val painter = GraphPainterByDjikstra(graph, viewModel.graphViewModel, startIdx, endIdx)
                    painter.paint()
                }
            }
        }
        Button(
            onClick = { /*TODO*/ },
            enabled = true,
        ) {
            Text(
                text = "Поиск мостов",
            )
        }
        Button(
            onClick = { /*TODO*/ },
            enabled = true,
        ) {
            Text(
                text = "Построение минимального остовного дерева",
            )
        }
        Button(
            onClick = {
                showDialog.value = true
            },
            enabled = true,
            modifier = Modifier.padding(top = 3.dp)
        ) {
            Text(text = "Кратчайший путь алгоритмом Дейкстры")
        }
        Button(
            onClick = { /*TODO*/ },
            enabled = true,
            modifier = Modifier.padding(top = 3.dp),
        ) {
            Text(
                text = "Кратчайший путь алгоритмом Форда-Беллмана",
            )
        }
    }
}