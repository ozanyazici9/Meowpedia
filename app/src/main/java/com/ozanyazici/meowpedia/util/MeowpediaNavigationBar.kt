package com.ozanyazici.meowpedia.util

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ozanyazici.meowpedia.presentation.ui.theme.MeowpediaTheme

@Composable
fun MeowpediaNavigationBar(onClickNavigationBar: () -> Unit = {}) {

    val selectedItem by remember { mutableStateOf(false) }
    MeowpediaTheme {
        NavigationBar(
            modifier = Modifier.height(40.dp)
        ) {
            NavigationBarItem(
                selected = selectedItem,
                onClick = { onClickNavigationBar() },
                icon = {Icon(Icons.Filled.Home, contentDescription = null)},
            )
        }
    }
}