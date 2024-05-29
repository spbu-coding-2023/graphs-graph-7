package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Magenta)
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text("NEO4J", color = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { onOptionSelected(StorageType.NEO4J.name) }
            ) {
                Text("Select")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Magenta)
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text("FILE", color = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { onOptionSelected(StorageType.FILE.name) }
            ) {
                Text("Select")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Magenta)
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text("SQLITE", color = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { onOptionSelected(StorageType.SQLITE.name) }
            ) {
                Text("Select")
            }
        }
    }
}