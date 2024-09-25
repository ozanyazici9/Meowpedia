package com.ozanyazici.meowpedia.presentation.breed_detail.views

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ozanyazici.meowpedia.R
import com.ozanyazici.meowpedia.presentation.breed_detail.BreedDetailArgumentState
import com.ozanyazici.meowpedia.presentation.breed_detail.BreedDetailImageState
import com.ozanyazici.meowpedia.presentation.breed_detail.BreedDetailViewModel
import com.ozanyazici.meowpedia.util.BreedReferenceImageStateHolder
import com.ozanyazici.meowpedia.util.Constants.BASE_URL_REFERENCE_IMAGE
@Composable
fun BreedDetailScreen(
    viewModel: BreedDetailViewModel = hiltViewModel()
) {
    val breedArgumentState = viewModel.breedArgumentState.value
    val breedImageState = viewModel.breedImageState.value

    MainBreedInfoScreen(
        breedArgumentState = breedArgumentState,
        breedImageState = breedImageState,
    )
}

@Composable
fun rememberBreedCardStateHolder() = remember {
    BreedReferenceImageStateHolder()
}

@Composable
fun CatBreedPicture(
    modifier: Modifier = Modifier,
    breedArgumentState: BreedDetailArgumentState,
) {
    val stateHolder = rememberBreedCardStateHolder()
    stateHolder.UpdateReferenceImage(breedArgumentState.breedName, breedArgumentState.breedReferenceImageId)

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ){
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.8429f),
            painter = stateHolder.referenceImage!!,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            )
    }
}

@Composable
fun BreedDescriptionText(breedArgumentState: BreedDetailArgumentState) {

    val context = LocalContext.current
    val annotatedText = buildAnnotatedString {

        append(breedArgumentState.breedDescription)

        pushStringAnnotation(tag = "more_info", annotation = breedArgumentState.breedWikipediaUrl)
        withStyle(style = SpanStyle(color = Color.Magenta)) {
            append(" More Info")
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        style = MaterialTheme.typography.labelMedium.copy(color = Color.Black),
        modifier = Modifier.padding(top = 8.dp),
        onClick = { offset ->
            // Kullanıcı "More Info"ya tıkladığında çalışacak
            annotatedText.getStringAnnotations(tag = "more_info", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    // Intent ile linke yönlendirme
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                    context.startActivity(intent)
                }
        }
    )
}

@Composable
fun BreedInfo(
    modifier: Modifier = Modifier,
    breedArgumentState: BreedDetailArgumentState
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 19.dp)
    )
    {
        Text(
            text = breedArgumentState.breedName,
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

        BreedDescriptionText(breedArgumentState = breedArgumentState)

        Text(
            text = "Temperament",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 18.dp)
        )

        Text(
            text = breedArgumentState.breedTemperament,
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
            text = breedArgumentState.breedOrigin,
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
            text = breedArgumentState.breedLifeSpan + " years",
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
                .padding(top = 18.dp, bottom = 8.dp)
        )
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
fun MainBreedInfoScreen(
    breedArgumentState: BreedDetailArgumentState,
    breedImageState: BreedDetailImageState,
) {
    Scaffold(
    ) { padding ->
        BreedInfoScreen(
            Modifier.padding(padding),
            breedArgumentState = breedArgumentState,
            breedImageState = breedImageState,
        )
    }
}

@Composable
fun BreedInfoScreen(
    modifier: Modifier = Modifier,
    breedArgumentState: BreedDetailArgumentState,
    breedImageState: BreedDetailImageState,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        item {
            BreedInfoSection {
                CatBreedPicture(
                    breedArgumentState = breedArgumentState,
                )
            }
        }
        item {
            BreedInfoSection {
                BreedInfo(
                    breedArgumentState = breedArgumentState
                )
            }
        }
        item {
            BreedImageGrid(
                breedImageState = breedImageState,
                modifier = Modifier
                    .fillMaxWidth() // veya ihtiyaç duyduğunuz dinamik yükseklik
            )
        }
    }
}

@Composable
fun BreedImageGrid(
    breedImageState: BreedDetailImageState,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .sizeIn(maxHeight = 790.dp, minHeight = 250.dp)
    ) {
        items(breedImageState.breedImage) { breed ->
            BreedImageCard(
                breedImage = breed.url,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            )
        }
    }
}

@Composable
fun BreedImageCard(
    modifier: Modifier = Modifier,
    breedImage: String
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(breedImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
            )
        }
    }
}