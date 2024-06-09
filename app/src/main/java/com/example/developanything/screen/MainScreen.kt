package com.example.developanything.screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.developanything.viewModel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

@Composable
fun MainScreen(viewModel: MainViewModel) {
    var clickRetrofit by remember { mutableStateOf(true) }
    var clickKtor by remember { mutableStateOf(false) }
    var prompt by remember { mutableStateOf("") }
    val response by viewModel.response.observeAsState()
    var painter by remember { mutableStateOf<String?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(key1 = response?.images) {
        painter = response?.images?.getOrNull(0)?.image
//        bitmap = getBitmap(response?.images?.getOrNull(0)?.image ?: "")
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            ChoiceButton(weight = 0.5f, text = "retrofit2") {
                clickRetrofit = true
                clickKtor = false
            }
            ChoiceButton(weight = 1f, text = "ktor") {
                clickRetrofit = false
                clickKtor = true
            }
        }
        if (clickRetrofit) {
            PromptLine(value = prompt, onValueChange = { prompt = it }) {
                viewModel.generateImage(prompt)
                Log.d("MainScreen", "Generate Image button clicked with value: $prompt")
            }
//            if (response != null) {
//                val painter = rememberAsyncImagePainter(model = response?.images)

            painter?.let {
                Log.d("MainScreen", "Response Image: $it")
                ResponseImage(it)
            }
//            }
//            bitmap?.let { ResponseImage(painter = bitmap) }
        } else {
            KtorPage("ktor")
        }
    }
}

@Composable
fun ChoiceButton(weight: Float, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(weight)
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PromptLine(value: String, onValueChange: (String) -> Unit, onClick: () -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
//            keyboardActions = KeyboardActions(onDone = {
////                keyboardController?.hide()
//
//            }),
            Modifier
                .fillMaxWidth(0.85f)
        )
        Button(
            onClick = onClick,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .fillMaxWidth(1f)
        ) {
            Text(text = "생성")
        }
    }
}

@Composable
fun KtorPage(name: String) {
    Text(
        text = "Hello $name!",
    )
}

@Composable
fun ResponseImage(painter: String) {
    val context = LocalContext.current
    val imageLoader = ImageLoader(context)

    AsyncImage(
        model = painter,
        contentDescription = null,
        imageLoader = imageLoader,
    )
}

//@Composable
//fun ResponseImageBitmap(painter: Bitmap?) {
//    Image(bitmap = painter!!.asImageBitmap(), contentDescription = "null")
//}

suspend fun getBitmapFromURL(src: String): Bitmap {
    return withContext(Dispatchers.IO) {
        val url = URL(src)
        BitmapFactory.decodeStream(url.openConnection().getInputStream())
    }
}

//@Composable
//fun getBitmap(uri: Uri): Bitmap? {
//    val context = LocalContext.current
//
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//        ImageDecoder.decodeBitmap(
//            ImageDecoder.createSource(
//                context.contentResolver,
//                uri
//            )
//        )
//    } else {
//        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
//    }
//}