package com.ozanyazici.meowpedia.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color,
    trackColor: Color
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        CircularProgressIndicator(
            modifier = modifier
                .align(Alignment.Center),
            color = color,
            trackColor = trackColor
        )
    }
}