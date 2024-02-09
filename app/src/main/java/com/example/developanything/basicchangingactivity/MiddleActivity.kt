package com.example.developanything.basicchangingactivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.developanything.MainActivity
import com.example.developanything.ui.theme.BlueGreen
import com.example.developanything.ui.theme.DevelopAnythingTheme

class MiddleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevelopAnythingTheme {
                val currentNumber = 1

                AllScaffold(
                    title = "1번",
                    iconButton = null,
                    content = {
                        Greeting(Icons.Filled.Home, currentNumber)
                    },
                    currentNumber = currentNumber
                )
            }

        }
    }
}

@Composable
fun Greeting(iconVector: ImageVector, currentNumber: Int) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            ChangingButton(
                onclick = {
                    onClickIntent(context = context, activity = MiddleActivity::class.java)
                },
                text = "1번",
                currentNumber = currentNumber,
                buttonNumber = 1
            )
            ChangingButton(
                onclick = {
                    onClickIntent(context = context, activity = LeftActivity::class.java)
                },
                text = "2번",
                currentNumber = currentNumber,
                buttonNumber = 0
            )
            ChangingButton(
                onclick = {
                    onClickIntent(context = context, activity = RightActivity::class.java)
                },
                text = "3번",
                currentNumber = currentNumber,
                buttonNumber = 2
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = iconVector,
            contentDescription = null,
            tint = Color(0xFF452C1C),
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun ChangingButton(onclick: () -> Unit, text: String, currentNumber: Int, buttonNumber: Int) {
    Button(
        onClick = { (if (currentNumber != buttonNumber) onclick()) },
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(Color.White),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 1.dp),
        modifier = Modifier.size(80.dp),
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = if (currentNumber == buttonNumber) BlueGreen else Color.Black
        )
    }
}

private fun onClickIntent(context: Context, activity: Class<out Activity>) {
    val intent = Intent(context, activity)
    context.startActivity(intent)
}

@Composable
fun AllScaffold(
    title: String,
    iconButton: @Composable (() -> Unit)?,
    content: @Composable (() -> Unit)?,
    currentNumber: Int,
) {
    Scaffold(
        topBar = { TopAppBar(title = title, iconButton = iconButton) },
        bottomBar = { BottomAppBar(currentNumber) },
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            color = Color.White,
        ) {
            if (content != null) {
                content()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(title: String, iconButton: @Composable (() -> Unit)?) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = BlueGreen
        ),
        title = {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 25.sp)
        },
        navigationIcon = {
            if (iconButton != null) {
                iconButton()
            }
        },
    )
}

@Composable
fun MoveBackIcon() {
    val context = LocalContext.current

    IconButton(
        onClick = {
            onClickIntent(context = context, activity = MainActivity::class.java)
        }
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowLeft,
            contentDescription = "Move back",
            tint = BlueGreen,
        )
    }
}

@Composable
private fun BottomAppBar(currentNumber: Int) {
    var selectedItem by remember { mutableIntStateOf(currentNumber) }
    val items = listOf("Lock", "Home", "Favorite")
    val context = LocalContext.current

    NavigationBar(
        containerColor = Color.White,

        ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    if (currentNumber != index) {
                        selectedItem = index
                        onClickIntent(context = context, activity = activityIndex(index))
                    }
                },
                icon = { Icon(imageVector = getIconForItem(item), contentDescription = null) },
                label = { Text(text = item) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BlueGreen,
                    selectedTextColor = BlueGreen,
                    indicatorColor = Color.White,
                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black,
                ),
            )
        }
    }
}

private fun getIconForItem(item: String): ImageVector {
    return when (item) {
        "Lock" -> Icons.Filled.Lock
        "Home" -> Icons.Filled.Home
        else -> Icons.Filled.Favorite
    }
}

private fun activityIndex(index: Int): Class<out Activity> {
    return when (index) {
        0 -> LeftActivity::class.java
        1 -> MainActivity::class.java
        else -> RightActivity::class.java
    }
}
