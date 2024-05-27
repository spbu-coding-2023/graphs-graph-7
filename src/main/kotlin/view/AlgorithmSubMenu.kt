package view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AlgorithmSubMenu() {
    Column(Modifier.padding(start = 16.dp, end = 0.dp, top = 15.dp)) {
        Text(text = "Выделение ключевых вершин")
        Text(text = "Поиск сообществ")
        Text(text = "Выделение компонент сильной связности")
        Text(text = "Поиск мостов")
        Text(text = "Поиск циклов для заданной вершины")
        Text(text = "Построение минимального остовного дерева")
        Text(text = "Кратчайший путь алгоритмом Дейкстры")
        Text(text = "Кратчайший путь алгоритмом Форда-Беллмана")
    }
}