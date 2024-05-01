package com.ozanyazici.meowpedia.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ozanyazici.meowpedia.R
import com.ozanyazici.meowpedia.presentation.breed_detail.views.BreedDetailScreen
import com.ozanyazici.meowpedia.presentation.breeds.views.BreedCard
import com.ozanyazici.meowpedia.presentation.breeds.views.BreedScreen
import com.ozanyazici.meowpedia.presentation.ui.theme.MeowpediaTheme
import com.ozanyazici.meowpedia.util.Constants.BREED_ID
import com.ozanyazici.meowpedia.util.Constants.BREED_NAME
import com.ozanyazici.meowpedia.util.Constants.COUNTRY_CODE
import com.ozanyazici.meowpedia.util.Constants.DESCRIPTION
import com.ozanyazici.meowpedia.util.Constants.LIFE_SPAN
import com.ozanyazici.meowpedia.util.Constants.ORIGIN
import com.ozanyazici.meowpedia.util.Constants.REFERENCE_IMAGE_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeowpediaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.BreedScreen.route) {
                        composable(route = Screen.BreedScreen.route) {
                            BreedScreen(navController = navController)
                        }

                        composable(Screen.BreedDetailScreen.route+"/{${BREED_ID}}"+"/{${BREED_NAME}}"+"/{${ORIGIN}}"+"/{${COUNTRY_CODE}}"+"/{${DESCRIPTION}}"+"/{${LIFE_SPAN}}"+"/{${REFERENCE_IMAGE_ID}}") {
                            BreedDetailScreen()
                        }
                    }
                }
            }
        }
    }
}


