package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomRadioGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var isBoxSelected1 by remember { mutableStateOf(false) }
    var isBoxSelected2 by remember { mutableStateOf(false) }
    var isBoxSelected3 by remember { mutableStateOf(false) }

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier =
                    Modifier.background(shape = CircleShape, color = Color.Gray)
                        .background(if (isBoxSelected1) Color.DarkGray else Color.Gray)
                        .height(50.dp)
                        .padding(8.dp)
                        .weight(1f)
            ) {
                Text("NEO4J", color = Color.Black)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    onOptionSelected(StorageType.NEO4J.name)
                    isBoxSelected3 = false
                    isBoxSelected2 = false
                    isBoxSelected1 = !isBoxSelected1
                }
            ) {
                Text("Select")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier =
                    Modifier.background(if (isBoxSelected2) Color.DarkGray else Color.Gray)
                        .padding(8.dp)
                        .weight(1f)
            ) {
                Text("FILE", color = Color.Black)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    onOptionSelected(StorageType.FILE.name)
                    isBoxSelected3 = false
                    isBoxSelected2 = !isBoxSelected2
                    isBoxSelected1 = false
                }
            ) {
                Text("Select")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier =
                    Modifier.background(if (isBoxSelected3) Color.DarkGray else Color.Gray)
                        .padding(8.dp)
                        .weight(1f)
            ) {
                Text("SQLITE", color = Color.Black)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    onOptionSelected(StorageType.SQLITE.name)
                    isBoxSelected3 = !isBoxSelected3
                    isBoxSelected2 = false
                    isBoxSelected1 = false
                }
            ) {
                Text("Select")
            }
        }
    }
}
