package com.example.developanything.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.developanything.Colors
import com.example.developanything.R
import com.example.developanything.room.Habit
import com.example.developanything.ui.theme.BrightSky
import com.example.developanything.ui.theme.Sky
import com.example.developanything.viewmodel.HabitViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CertificationScreen(
    colors: Colors,
    viewModel: HabitViewModel,
    navController: NavController
) {
    val currentRoute = navController.currentDestination?.route
    val habitList by viewModel.allHabit.observeAsState(initial = emptyList())
    // paging 라이브러리 이용하여 무한 스크롤
//    val lazyPagingItems = viewModel.infiniteHabit().collectAsLazyPagingItems()
    val pagerState = rememberPagerState(pageCount = { habitList.size })
    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(10)
    )
    val audioPickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
            viewModel.selectedUri = it
        }

    ScaffoldBar(colors = colors, currentRoute = currentRoute, viewModel = viewModel) { it ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .zIndex(1f),
        ) {
            HorizontalDivider(thickness = 1.dp, color = Sky)
            // 인증 목록
//            LazyRow(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight(0.998f)
//            ) {
//                items(habitList) { habit ->
//                    HabitCard(habit = habit, deviceWidth = deviceWidth) {
//
//                    }
//                }
//                // paging 라이브러리 이용하여 무한 스크롤
////                items(lazyPagingItems.itemCount) {
////                    val item = lazyPagingItems[it]
////                    if (item != null) {
////                        HabitCard(habit = item, deviceWidth = deviceWidth) {
////
////                        }
////                    }
////                }
//            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                contentPadding = PaddingValues(horizontal = 40.dp),
                pageSpacing = 15.dp,
                flingBehavior = fling,
                userScrollEnabled = !viewModel.flipCard,
            ) {
                HabitCard(
                    colors = colors,
                    habit = habitList[it],
                    viewModel = viewModel,
                    pagerState = pagerState,
                    page = it,
                ) {
                    viewModel.habitId = it
                    viewModel.habit = habitList[it].habit
                    viewModel.typeText = habitList[it].type
                    if (habitList[it].type == "image") {
                        viewModel.flipCard = !viewModel.flipCard
                    } else {
                        audioPickerLauncher.launch(arrayOf("audio/*"))
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
    // skipPartiallyExpanded = true -> 모든 항목이 다 보일 수 있도록 설정(키보드 올라가면 전체화면 됨)
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

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
                    viewModel.clickAdd = true
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
    ) { paddingValues ->
        if (viewModel.clickAdd) {
            viewModel.cancelAddHabit()
            AddCBottomSheet(
                colors = colors,
                sheetState = sheetState,
                viewModel = viewModel,
            )
        }
        content(paddingValues)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HabitCard(
    colors: Colors,
    habit: Habit,
    viewModel: HabitViewModel,
    pagerState: PagerState,
    page: Int,
    onclick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val currentDate = Date(System.currentTimeMillis())
    val formatDate =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(currentDate.toString())
    val certificationList by viewModel.allCertification.observeAsState(initial = emptyList())
    val seletedCerti = certificationList.filter { it.date == formatDate && it.habit == habit.habit }
    val thisCerti = seletedCerti[0]

    Card(
        onClick = onclick,
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(colors.background),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, BrightSky),
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 15.dp)
            .graphicsLayer {
                // Calculate the absolute offset for the current page from the
                // scroll position. We use the absolute value which allows us to mirror
                // any effects for both directions
                val pageOffset =
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

                // We animate the alpha, between 50% and 100%
                alpha = lerp(
                    start = if (viewModel.flipCard) 0f else 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                )
                // 높이과 위치 변경
                lerp(
                    start = 1f,
                    stop = 0.8f,
                    fraction = pageOffset.absoluteValue.coerceIn(0f, 1f),
                ).let {
                    scaleX = it
                    scaleY = it
                    val sign = if (pageOffset > 0) 1 else -1
                    translationX = sign * size.width * (1 - it) / 2
                }
            }
    ) {
        if (viewModel.flipCard) {
            BackSurface(colors = colors, viewModel = viewModel)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(25.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = if (habit.type == "image") R.drawable.camera else R.drawable.voice),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = habit.habit,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colors.text
            )
            Spacer(modifier = Modifier.height(20.dp))
            habit.detail?.let {
                Text(
                    text = it,
                    fontSize = 15.sp,
                    color = colors.text,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .verticalScroll(scrollState)
                )
            }
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
fun AddCBottomSheet(
    colors: Colors,
    sheetState: SheetState,
    viewModel: HabitViewModel,
) {
    ModalBottomSheet(
        onDismissRequest = {
            viewModel.cancelAddHabit()
        },
        containerColor = colors.background,
        sheetState = sheetState,
    ) {
        Button(
            onClick = {
                viewModel.addHabit()
            },
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
                    value = viewModel.habit,
                    onValueChange = { viewModel.habit = it },
                    placeholder = "습관",
                    colors = colors,
                    height = null
                )
                AddTextField(
                    value = viewModel.detail,
                    onValueChange = { viewModel.detail = it },
                    placeholder = "세부 사항",
                    colors = colors,
                    height = 200,
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                ) {
                    AddButton(
                        colors = colors,
                        icon = R.drawable.round_image_24,
                        condition = viewModel.clickImage,
                    ) {
                        viewModel.clickImage = true
                        viewModel.clickVoice = false
                        viewModel.typeText = "image"
                    }
                    AddButton(
                        colors = colors,
                        icon = R.drawable.round_voicemail_24,
                        condition = viewModel.clickVoice,
                    ) {
                        viewModel.clickImage = false
                        viewModel.clickVoice = true
                        viewModel.typeText = "voice"
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

@Composable
fun BackSurface(colors: Colors, viewModel: HabitViewModel) {
    val context = LocalContext.current
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.selectedUri = uri
                viewModel.flipCard = false
                viewModel.addCertification()
            }
        }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            if (bitmap != null) {
                viewModel.selectedUri = getImageUriFromBitmap(context, bitmap)
                viewModel.flipCard = false
                viewModel.addCertification()
            }
        }
    )

    Surface(
        color = colors.opBackground,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            SelectButton(text = "카메라", colors = colors) {
                cameraLauncher.launch(null)
            }
            SelectButton(text = "앨범", colors = colors) {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
            }
        }
    }
}

@Composable
fun SelectButton(text: String, colors: Colors, onclick: () -> Unit) {
    Button(
        onClick = onclick,
        border = BorderStroke(1.dp, BrightSky),
        colors = ButtonDefaults.buttonColors(colors.opBackground),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(120.dp)
            .height(60.dp)
    ) {
        Text(text = text, color = colors.opText, fontSize = 20.sp)
    }
}