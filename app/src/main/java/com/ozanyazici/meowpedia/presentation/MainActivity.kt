package com.ozanyazici.meowpedia.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ozanyazici.meowpedia.domain.model.Breed
import com.ozanyazici.meowpedia.presentation.breed_detail.views.BreedDetailScreen
import com.ozanyazici.meowpedia.presentation.breeds.views.BreedScreen
import com.ozanyazici.meowpedia.presentation.ui.theme.MeowpediaTheme
import com.ozanyazici.meowpedia.util.Constants.BREED_ID
import com.ozanyazici.meowpedia.util.Constants.BREED_NAME
import com.ozanyazici.meowpedia.util.Constants.COUNTRY_CODE
import com.ozanyazici.meowpedia.util.Constants.DESCRIPTION
import com.ozanyazici.meowpedia.util.Constants.EUROPEAN_BURMESE_URL
import com.ozanyazici.meowpedia.util.Constants.LIFE_SPAN
import com.ozanyazici.meowpedia.util.Constants.ORIGIN
import com.ozanyazici.meowpedia.util.Constants.REFERENCE_IMAGE_ID
import com.ozanyazici.meowpedia.util.Constants.TEMPERAMENT
import com.ozanyazici.meowpedia.util.Constants.WIKIPEDIA_URL
import com.ozanyazici.meowpedia.util.MeowpediaNavigationBar
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = false
        }
        launchSingleTop = true
        restoreState = true
    }

private fun NavHostController.navigateBreedDetail(breed: Breed) {

    val encodedUrl = URLEncoder.encode(breed.wikipediaUrl ?: EUROPEAN_BURMESE_URL, "UTF-8")
    this.navigateSingleTopTo(
        "${Screen.BreedDetailScreen.route}/${breed.id}/"+
                "${breed.name}/${breed.temperament}/"+
                "${breed.origin}/${breed.country_code}/"+
                "${breed.description}/${breed.life_span}/"+
                "${breed.reference_image_id}/${encodedUrl}"
    )
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeowpediaTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        MeowpediaNavigationBar(
                            onClickNavigationBar = {
                                navController.navigateSingleTopTo(Screen.BreedScreen.route)
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.BreedScreen.route,
                        modifier = Modifier.padding(innerPadding)  // Scaffold padding
                    ) {
                        composable(route = Screen.BreedScreen.route) {
                            BreedScreen(
                                onClickBreedCard = { breed ->
                                    navController.navigateBreedDetail(breed)
                                }
                            )
                        }

                        composable(
                            Screen.BreedDetailScreen.route +
                                    "/{${BREED_ID}}" + "/{${BREED_NAME}}" +
                                    "/{${TEMPERAMENT}}" + "/{${ORIGIN}}" +
                                    "/{${COUNTRY_CODE}}" + "/{${DESCRIPTION}}" +
                                    "/{${LIFE_SPAN}}" + "/{${REFERENCE_IMAGE_ID}}" +
                                    "/{${WIKIPEDIA_URL}}"
                        ) {
                            BreedDetailScreen()
                        }
                    }
                }
            }
        }
    }
}