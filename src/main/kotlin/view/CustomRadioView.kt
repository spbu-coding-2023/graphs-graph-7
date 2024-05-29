package view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
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
                    .background(Color.Gray)
                    .height(50.dp)
                    .padding(8.dp)
                    .weight(1f)
                    //.border(2.dp, Color.Black, CircleShape),
            ) {
                Text("NEO4J", color = Color.Black)
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
                    .background(Color.Gray)
                    .padding(8.dp)
                    .weight(1f)
                    //.border(2.dp, Color.Black, CircleShape),
            ) {
                Text("FILE", color = Color.Black)
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
                    .background(Color.Gray)
                    .padding(8.dp)
                    .weight(1f)
                   // .border(2.dp, Color.Black, CircleShape),
            ) {
                Text("SQLITE", color = Color.Black)
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