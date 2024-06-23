package com.ozanyazici.meowpedia.presentation.breed_detail.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ozanyazici.meowpedia.domain.model.CatBreedImage
import com.ozanyazici.meowpedia.presentation.breed_detail.BreedDetailImageState
import com.ozanyazici.meowpedia.presentation.breed_detail.BreedDetailInfoState
import com.ozanyazici.meowpedia.presentation.breed_detail.BreedDetailViewModel
import com.ozanyazici.meowpedia.util.Constants

@Composable
fun BreedDetailScreen(viewModel: BreedDetailViewModel = hiltViewModel()) {

    val state = viewModel.state.value
    val stateInfo = viewModel.stateInfo.value
    val screenWidt = LocalConfiguration.current.screenWidthDp.dp

    BreedInfoScreen(stateInfo = stateInfo, state = state, screenWidth = screenWidt)
}

@Composable
fun CatBreedPictures(
    modifier: Modifier = Modifier,
    catBreedImage: String?
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    )
    {
        Image(painter = rememberAsyncImagePainter(model = Constants.BASE_URL_REFERENCE_IMAGE + catBreedImage + ".jpg"),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(475.dp),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun BreedInfo(
    modifier: Modifier = Modifier,
    stateInfo: BreedDetailInfoState
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 19.dp)
    )
    {
        Text(
            text = stateInfo.breedName,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 16.dp)
            )

        Text(
            text = "Description",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 18.dp)
        )

        Text(
            text = stateInfo.breedDescription,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 8.dp)
        )

        Text(
            text = "Temperament",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 18.dp)
        )

        Text(
            text = stateInfo.breedTemperament,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 8.dp)
        )

        Text(
            text = "Origin",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 18.dp)
        )

        Text(
            text = stateInfo.breedOrigin,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 8.dp)
        )

        Text(
            text = "Life Span",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 18.dp)
        )

        Text(
            text = stateInfo.breedLifeSpan + " years",
            style = MaterialTheme.typography.labelMedium,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 8.dp)
        )

        Text(
            text = "More Pictures",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 18.dp, bottom = 8.dp))
    }
}

@Composable
fun BreedInfoSection(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        content()
    }
}

@Composable
fun BreedInfoScreen(
    modifier: Modifier = Modifier,
    stateInfo: BreedDetailInfoState,
    state: BreedDetailImageState,
    screenWidth: Dp
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        item {
            BreedInfoSection {
                CatBreedPictures(catBreedImage = stateInfo.breedReferenceImageId)
            }
        }
        item {
            BreedInfoSection {
                BreedInfo(stateInfo = stateInfo)
            }
        }
        item {
            BreedImageGrid(
                state = state,
                screenWidth = screenWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp) // veya ihtiyaç duyduğunuz dinamik yükseklik
            )
        }
    }
}
@Composable
fun BreedImageGrid(
    state: BreedDetailImageState,
    modifier: Modifier = Modifier,
    screenWidth: Dp
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = screenWidth * 0.0179f),
        horizontalArrangement = Arrangement.spacedBy(screenWidth * 0.04f),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(state.breedImage) { breedImage ->
            BreedImageCard(
                screenWidth = screenWidth,
                breedImage = breedImage,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
        }
    }
}
@Composable
fun BreedImageCard(
    modifier: Modifier = Modifier,
    screenWidth: Dp,
    breedImage: CatBreedImage
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(breedImage.url),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(screenWidth * 0.4602f)
            )
        }
    }
}