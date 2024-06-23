package com.ozanyazici.meowpedia.presentation.breeds.views

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ozanyazici.meowpedia.R
import com.ozanyazici.meowpedia.domain.model.Breed
import com.ozanyazici.meowpedia.presentation.Screen
import com.ozanyazici.meowpedia.presentation.breeds.BreedsState
import com.ozanyazici.meowpedia.presentation.breeds.BreedsViewModel
import com.ozanyazici.meowpedia.presentation.ui.theme.MeowpediaTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import com.ozanyazici.meowpedia.presentation.MainActivity
import com.ozanyazici.meowpedia.util.Constants.BASE_URL_REFERENCE_IMAGE
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import com.ozanyazici.meowpedia.presentation.breeds.BreedsEvent

@Composable
fun BreedScreen(navController: NavController, viewModel: BreedsViewModel = hiltViewModel()) {

    val state = viewModel.state.value
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val openAlertDialog = remember {
        mutableStateOf(false)
    }

    var loading by remember {
        mutableStateOf(false)
    }

    if (state.error.isNotEmpty()) {
        openAlertDialog.value = true
    } else if (state.isLoading) {
        loading = true
    } else {
        loading = false
        MeowpediaApp(state = state, navController = navController, screenWidth = screenWidth, viewModel = viewModel)
    }

    when {
        openAlertDialog.value -> {
            ErrorAlertDialog(
                onConfirmation = {
                    openAlertDialog.value = false
                    (context as? MainActivity)?.finish()
                },
                dialogTitle = "Error",
                dialogText = "No network connection. Connect to the network and try again",
                icon = Icons.Default.Info
            )
        }
        loading -> {
            ProgressIndicator(
                modifier = Modifier.width(40.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.White
            )
        }
    }
}

@Composable
fun BreedCard(
    breed: Breed,
    modifier: Modifier = Modifier,
    onCardClick: (Breed) -> Unit,
    screenWidth: Dp
) {
    var referenceImage by remember {
        mutableStateOf<Painter?>(null)
    }

    // Bu kod belki viewmodel a taşınabilir.
    referenceImage = if (breed.reference_image_id.isNullOrBlank()) {
        if (breed.name == "European Burmese") {
            painterResource(R.drawable.european_burmese_cat)
        } else {
            painterResource(R.drawable.malayan_cat)
        }
    } else if (breed.name == "Bengal" || breed.name == "Devon Rex" || breed.name == "Korat") {
        rememberAsyncImagePainter(model = BASE_URL_REFERENCE_IMAGE + breed.reference_image_id + ".png")
    } else {
        rememberAsyncImagePainter(model = BASE_URL_REFERENCE_IMAGE + breed.reference_image_id + ".jpg")
    }

    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .clickable {
                onCardClick(breed)
            }
    ) {
        Box {
            Image(
                painter = referenceImage!!,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(screenWidth * 0.4602f)
            )
            Text(
                text = breed.name,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .padding(horizontal = 0.dp, vertical = 0.dp)
                    .align(Alignment.BottomStart)
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                    .padding(3.dp)
                )
            }
        }
    }


@Composable
fun BreedCardGrid(
    state: BreedsState,
    navController: NavController,
    modifier: Modifier = Modifier,
    screenWidth: Dp
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = screenWidth * 0.0179f),
        horizontalArrangement = Arrangement.spacedBy(screenWidth * 0.04f),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.width(screenWidth)
    ) {
        items(state.breeds) {breed ->
            BreedCard(breed = breed, screenWidth = screenWidth, onCardClick = {
                navController.navigate(Screen.BreedDetailScreen.route+"/${breed.id}"+"/${breed.name}"+"/${breed.origin}"+"/${breed.country_code}"+"/${breed.description}"+"/${breed.life_span}"+"/${breed.reference_image_id}"+"/${breed.temperament}")
            })
        }
    }
}

@Composable
fun AppTitle(
    @StringRes title: Int
) {
    Text(
        stringResource(id = title),
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier
            .paddingFromBaseline(top = 30.dp, bottom = 2.dp)
            .padding(horizontal = 16.dp)
    )
}

@Composable
fun HomeSection(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        content()
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: BreedsState,
    navController: NavController,
    screenWidth: Dp,
    viewModel: BreedsViewModel
) {
    Column(modifier) {
        HomeSection {
            AppTitle(title = R.string.app_name)
        }
        HomeSection {
            BreedSearchBar(screenWidth = screenWidth, viewModel = viewModel)
        }
        HomeSection {
            BreedCardGrid(
                state = state,
                navController = navController,
                screenWidth = screenWidth
            )
        }
    }
}

@Composable
fun MeowpediaApp(
    state: BreedsState,
    navController: NavController,
    screenWidth: Dp,
    viewModel: BreedsViewModel
) {
    MeowpediaTheme {
        Scaffold { padding ->
            HomeScreen(
                Modifier.padding(padding),
                state = state,
                navController = navController,
                screenWidth = screenWidth,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun ErrorAlertDialog(
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(imageVector = icon, contentDescription = "Error AlertDialog Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {

        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                 Text("Confirm")
            }
        },
    )
}

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color,
    trackColor: Color
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = modifier,
            color = color,
            trackColor = trackColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedSearchBar(
    screenWidth: Dp,
    viewModel: BreedsViewModel
) {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .height(67.dp)
            .semantics { isTraversalGroup = true }) {
        DockedSearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp)
                .size(width = screenWidth * 0.97f, height = 55.dp) // farklı ekranlara uygun hale getir
                .semantics { traversalIndex = -1f },
            query = text,
            onQueryChange = { newText ->
                text = newText
                active = true
                viewModel.onEvent(BreedsEvent.Search(newText))
                 },
            onSearch = {
                active = false
                viewModel.onEvent(BreedsEvent.Search(it)) },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text(text = "Search") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        ) {}
        }
    }


// Meowpedia yazısının yanında küçük pati resimleri


















