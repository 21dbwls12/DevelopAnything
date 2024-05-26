package com.example.developanything.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.developanything.Colors
import com.example.developanything.R
import com.example.developanything.ui.theme.DarkTree
import com.example.developanything.ui.theme.LightTree
import com.example.developanything.ui.theme.Sky
import com.example.developanything.viewmodel.HabitViewModel

@Composable
fun CertificationScreen(
    colors: Colors,
    deviceWidth: Float,
    viewModel: HabitViewModel = viewModel(),
    navController: NavController
) {
    val clickNaviIcon by remember { mutableStateOf(false) }
    val currentRoute = navController.currentDestination?.route

    ScaffoldBar(colors = colors, currentRoute = currentRoute, viewModel = viewModel) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .zIndex(1f),
        ) {
            HorizontalDivider(thickness = 1.dp, color = Sky)
            // 인증 목록
            LazyRow(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.998f)
            ) {
                items(2) {
                    HabitCard(color = LightTree, deviceWidth = deviceWidth) {

                    }
                    HabitCard(color = DarkTree, deviceWidth = deviceWidth) {

                    }
                }
            }
            HorizontalDivider(thickness = 1.dp, color = Sky)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldBar(
    colors: Colors,
    currentRoute: String?,
    viewModel: HabitViewModel,
    content: @Composable (PaddingValues) -> Unit
) {
    val clickAdd = viewModel.clickAdd

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "오늘의 진행도", color = colors.star) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colors.background
                ),
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = colors.background,
                contentColor = colors.text,
                tonalElevation = 12.dp,
            ) {
                NaviIconButton(
                    colors = colors,
                    currentRoute = currentRoute,
                    trueIcon = R.drawable.filled_premium,
                    falseIcon = R.drawable.outlined_premium,
                    text = "인증"
                ) {

                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.setAddClick()
                },
                containerColor = colors.background,
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                modifier = Modifier
                    .size(60.dp)
                    .zIndex(2f),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "인증 항목 추가",
                    tint = colors.text,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(0.dp)
                )
            }
        },
        containerColor = colors.background
    ) {
        content(it)
        if (clickAdd) {
            AddCBottomSheet(colors = colors, setClickAdd = { viewModel.clickAdd = it })
        }
    }
}

@Composable
fun HabitCard(color: Color, deviceWidth: Float, onclick: () -> Unit) {
    Card(
        onClick = onclick,
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(color),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width((deviceWidth - 40).dp)
            .fillMaxHeight()
            .padding(vertical = 15.dp, horizontal = 5.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "매일 인증해야하는 습관",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun NaviIconButton(
    colors: Colors,
    currentRoute: String?,
    trueIcon: Int,
    falseIcon: Int,
    text: String,
    onclick: () -> Unit
) {
    Button(
        onClick = onclick,
        colors = ButtonDefaults.buttonColors(colors.background),
        shape = RoundedCornerShape(5.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .padding(0.dp)
            .size(50.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),
        ) {
            Icon(
                painter = painterResource(id = if (currentRoute == text) trueIcon else falseIcon),
                contentDescription = "네비게이션 아이콘",
                tint = colors.text,
                modifier = Modifier
                    .size(30.dp)
                    .padding(0.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            Text(
                text = text,
                fontSize = 11.sp,
                color = colors.text,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCBottomSheet(colors: Colors, setClickAdd: (Boolean) -> Unit) {
    var habit by remember { mutableStateOf("") }
    var detail by remember { mutableStateOf("") }
    var clickImage by remember { mutableStateOf(true) }
    var clickVoice by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = {
            setClickAdd(false)
        },
        containerColor = colors.background,
        modifier = Modifier.heightIn(min = 500.dp, max = Int.MAX_VALUE.dp)
    ) {
        Button(
            onClick = {  },
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(colors.background),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "저장",
                color = Sky,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }
        LazyColumn(
            modifier = Modifier.padding(10.dp)
        ) {
            item {
                AddTextField(
                    value = habit,
                    onValueChange = { habit = it },
                    placeholder = "습관",
                    colors = colors,
                    height = null
                )
                AddTextField(
                    value = detail,
                    onValueChange = { detail = it },
                    placeholder = "세부 사항",
                    colors = colors,
                    height = 200,
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)
                ) {
                    AddButton(
                        colors = colors,
                        icon = R.drawable.round_image_24,
                        condition = clickImage,
                    ) {
                        clickImage = true
                        clickVoice = false
                    }
                    AddButton(
                        colors = colors,
                        icon = R.drawable.round_voicemail_24,
                        condition = clickVoice,
                    ) {
                        clickImage = false
                        clickVoice = true
                    }
                }
            }
        }
    }
}

@Composable
fun AddTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    colors: Colors,
    height: Int?
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder, color = Color.LightGray) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colors.background,
            unfocusedContainerColor = colors.background,
            focusedIndicatorColor = colors.background,
            unfocusedIndicatorColor = colors.background,
            cursorColor = colors.text
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height((height ?: 60).dp)
    )
}

@Composable
fun AddButton(colors: Colors, icon: Int, condition: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(colors.background),
        modifier = Modifier.size(60.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "추가 방법",
            tint = if (condition) colors.star else Color.LightGray,
            modifier = Modifier.size(40.dp)
        )
    }
}