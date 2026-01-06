package com.snehamarathe.snap2nest

data class SlotTransform(
    val scale: Float = 1f,
    val offsetX: Float = 0f, // [-1..1]
    val offsetY: Float = 0f  // [-1..1]
)
