package controller

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun generateRandomColor(base: Int): Color {
    val mRandom = Random(base)
    val red: Int = (base + mRandom.nextInt(256)) / 2
    val green: Int = (base + mRandom.nextInt(256)) / 2
    val blue: Int = (base + mRandom.nextInt(256)) / 2
    return Color(red, green, blue)
}
