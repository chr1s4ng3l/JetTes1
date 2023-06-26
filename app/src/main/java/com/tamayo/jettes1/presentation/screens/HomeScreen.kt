package com.tamayo.jettes1.presentation.screens

import android.graphics.ImageDecoder
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.tamayo.jettes1.data.model.Original
import com.tamayo.jettes1.domain.DomainData
import com.tamayo.jettes1.presentation.MyViewModel
import com.tamayo.jettes1.utils.UIState

@Composable
fun HomeScreen(vm: MyViewModel = hiltViewModel()) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {

        SearchView(vm = vm)

        when (val dataGift = vm.data.collectAsState().value) {
            is UIState.ERROR -> {
                Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show()
            }

            is UIState.LOADING -> {
            }

            is UIState.SUCCESS -> {
                ListData(data = dataGift.data)
            }
        }
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListData(
    data: List<DomainData>
) {

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(data) {
                MyGiphy(data = it)
            }
        }, modifier = Modifier.fillMaxSize()
    )

}


@Composable
fun MyGiphy(data: DomainData) {

    Card(elevation = CardDefaults.cardElevation()) {


        val imageLoader = ImageLoader.Builder(LocalContext.current)
            .components { add(ImageDecoderDecoder.Factory()) }.build()

        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data.images?.original?.url ?: "")
                    .size(Size.ORIGINAL).build(), imageLoader = imageLoader
            ), contentDescription = "Gif"
        )

    }

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchView(vm: MyViewModel) {

    val context = LocalContext.current

    val query = vm.tag.collectAsState()

    var error by remember {
        mutableStateOf(false)
    }
    val keyboardController = LocalSoftwareKeyboardController.current


    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        isError = error,
        value = query.value,
        onValueChange = { vm.searchWithTag(it) },
        label = { Text("Search...") },

        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                if (query.value.isEmpty()) {
                    error = true
                } else {
                    error = false
                    vm.getData(query.value)
                    keyboardController?.hide()
                    Toast.makeText(context, "Wait a moment Loading gift", Toast.LENGTH_SHORT).show()
                }
            }
        )

    )

}