package view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import controller.GraphPainter
import viewmodel.CanvasViewModel

@Composable
fun AlgorithmSubMenu(viewModel: CanvasViewModel) {
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
                val painter = GraphPainter(graph, viewModel.graphViewModel)
                painter.paint()
            },
            enabled = true,
        ) {
            Text(
                text = "Поиск сообществ",
            )
        }
        Button(
            onClick = { /*TODO*/ },
            enabled = true,
        ) {
            Text(
                text = "Выделение компонент сильной связности",
            )
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
            onClick = { /*TODO*/ },
            enabled = true,
            modifier = Modifier.padding(top = 3.dp),
        ) {
            Text(
                text = "Кратчайший путь алгоритмом Дейкстры",
            )
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