package com.ozanyazici.meowpedia.presentation.breeds.views

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ozanyazici.meowpedia.R
import com.ozanyazici.meowpedia.domain.model.Breed
import com.ozanyazici.meowpedia.presentation.breeds.BreedState
import com.ozanyazici.meowpedia.presentation.breeds.BreedViewModel
import com.ozanyazici.meowpedia.presentation.ui.theme.MeowpediaTheme
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.ozanyazici.meowpedia.presentation.MainActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ozanyazici.meowpedia.presentation.breeds.BreedsEvent
import com.ozanyazici.meowpedia.util.BreedReferenceImageStateHolder
import com.ozanyazici.meowpedia.util.ErrorAlertDialog
import com.ozanyazici.meowpedia.util.ProgressIndicator

@Composable
fun BreedScreen(
    onClickBreedCard: (Breed) -> Unit = {},
    viewModel: BreedViewModel = hiltViewModel()
) {

    val breedState = viewModel.breedState.value
    val context = LocalContext.current
    val navController = rememberNavController()

        if (breedState.error.isNotEmpty()) {
            ErrorAlertDialog(
                onConfirmation = {
                    (context as? MainActivity)?.finish()
                },
                dialogTitle = "Error",
                dialogText = "No network connection. Connect to the network and try again",
                icon = Icons.Default.Info
            )
        } else if (breedState.isLoading) {
            ProgressIndicator(
                modifier = Modifier.width(40.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.White
            )
        } else {
            MeowpediaApp(
                breedState = breedState,
                onClickBreedCard = onClickBreedCard,
                viewModel = viewModel,
                navController = navController
            )
        }
    }

@Composable
fun MeowpediaApp(
    breedState: BreedState,
    onClickBreedCard: (Breed) -> Unit = {},
    viewModel: BreedViewModel,
    navController: NavController
) {
    MeowpediaTheme {
        Scaffold(
        ) { padding ->
            HomeScreen(
                Modifier.padding(padding),
                breedState = breedState,
                onClickBreedCard = onClickBreedCard,
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    breedState: BreedState,
    onClickBreedCard: (Breed) -> Unit = {},
    viewModel: BreedViewModel,
    navController: NavController
) {
    Column(modifier) {
        HomeSection {
            AppTitle(title = R.string.app_name)
        }
        HomeSection {
            BreedSearchBar(
                viewModel = viewModel,
                navController = navController
            )
        }
        HomeSection {
            BreedCardGrid(
                breedState = breedState,
                onClickBreedCard = onClickBreedCard,
            )
        }
    }
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

class BreedSearchBarStateHolder {
    var text by mutableStateOf("")
    var active by mutableStateOf(false)
}

@Composable
fun rememberBreedSearchbarStateHolder() = remember {
    BreedSearchBarStateHolder()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedSearchBar(
    stateHolder: BreedSearchBarStateHolder = rememberBreedSearchbarStateHolder(),
    viewModel: BreedViewModel,
    navController: NavController
) {

    val gradient = listOf(Color(0xFFADD8E6), Color(0xFF800080), Color(0xFFFFC0CB))

    LaunchedEffect(navController) {
        val savedText = viewModel.searchText
        if (savedText.isNotEmpty()) {
            stateHolder.text = savedText
            viewModel.onEvent(BreedsEvent.Search(stateHolder.text))
        }
    }

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
                .height(55.dp)
                .fillMaxWidth()
                .semantics { traversalIndex = -1f }
                .border(
                    border = BorderStroke(
                        brush = Brush.linearGradient(gradient),
                        width = 2.dp,
                    ),
                    shape = CutCornerShape(14.dp)
                ),
            query = stateHolder.text,
            onQueryChange = { newText ->
                stateHolder.text = newText
                stateHolder.active = true
                viewModel.onEvent(BreedsEvent.Search(stateHolder.text))
            },
            onSearch = {
                stateHolder.active = false
                viewModel.onEvent(BreedsEvent.Search(it)) },
            active = stateHolder.active,
            onActiveChange = { stateHolder.active = it },
            placeholder = { Text(text = "Search") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        ) {}
    }
}

@Composable
fun BreedCardGrid(
    modifier: Modifier = Modifier,
    breedState: BreedState,
    onClickBreedCard: (Breed) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(breedState.breeds) { breed ->
            BreedCard(
                breed = breed,
                onCardClick = onClickBreedCard,
                modifier = Modifier
                    .padding(2.dp)
            )
        }
    }
}

@Composable
fun rememberBreedCardStateHolder() = remember {
    BreedReferenceImageStateHolder()
}

@Composable
fun BreedCard(
    breed: Breed,
    modifier: Modifier = Modifier,
    onCardClick: (Breed) -> Unit = {},
) {
    val stateHolder = rememberBreedCardStateHolder()
    stateHolder.UpdateReferenceImage(breed.name, breed.reference_image_id)

    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .clickable { onCardClick(breed) }
    ) {

        Box {
            stateHolder.referenceImage?.let {
                Image(
                    painter = it,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(1f)
                )
            }

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