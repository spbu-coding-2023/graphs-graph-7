package view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShortestPathDialog(
    showDialog: MutableState<Boolean>,
    onPathSelected: (Int, Int) -> Unit
) {
    var startIdx by remember { mutableStateOf(0) }
    var endIdx by remember { mutableStateOf(0) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            buttons = {
                Button(onClick = {
                    onPathSelected(startIdx, endIdx)
                    showDialog.value = false
                }) {
                    Text("Найти кратчайший путь")
                }
            },
            text = {
                Column {
                    TextField(
                        value = startIdx.toString(),
                        onValueChange = { startIdx = it.toIntOrNull() ?: 0 },
                        label = { Text("Введите точку отправления") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = endIdx.toString(),
                        onValueChange = { endIdx = it.toIntOrNull() ?: 0 },
                        label = { Text("Введите точку назначения") }
                    )
                }
            }
        )
    }
}