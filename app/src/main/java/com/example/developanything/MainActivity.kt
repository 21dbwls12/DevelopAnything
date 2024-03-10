package com.example.developanything

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.developanything.ui.theme.DevelopAnythingTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevelopAnythingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    TodoListScreen()
                }
            }
        }
    }
}

@Composable
private fun TodoListScreen() {
    val todoList = remember { mutableStateListOf<String>() }
    // 추가 버튼을 눌렀을 때 TextField를 나타나게 하기 위함
    var clickAdd by remember { mutableStateOf(false) }
    var clickDelete by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(clickAdd) {
        if (clickAdd) {
            focusRequester.requestFocus()
        }
    }

    Column(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
    ) {
        // clickAdd의 상태를 다른 스코프에서도 변경할 수 있음
        TopBar(
            setClickAdd = { clickAdd = it },
            setClickDelete = { clickDelete = it },
            clickDelete = clickDelete
        )
        SpaceForPartition()
        PartitionLine()
        SpaceForPartition()

        TodoList(todoList, clickAdd, setClickAdd = { clickAdd = it }, focusRequester)
    }
}

@Composable
private fun TopBar(
    setClickAdd: (Boolean) -> Unit,
    setClickDelete: (Boolean) -> Unit,
    clickDelete: Boolean
) {
    val currentTime = LocalDate.now()
    // 날짜
    val date = currentTime.dayOfMonth

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        IconButtons(
            icon = Icons.Outlined.Delete,
            color = Color.Red,
            onClick = {
                setClickDelete(!clickDelete)
                if (clickDelete) {

                }
            }
        )
        Text(text = date.toString(), color = Color(0xFFF2C12E), fontSize = 40.sp)
        IconButtons(
            icon = Icons.Rounded.Add,
            color = Color.Blue,
            onClick = {
                setClickAdd(true)
            }
        )
    }
}

@Composable
private fun IconButtons(icon: ImageVector, color: Color, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(imageVector = icon, contentDescription = null, tint = color)
    }
}

@Composable
private fun SpaceForPartition() {
    Spacer(modifier = Modifier.height(5.dp))
}

@Composable
private fun PartitionLine() {
    Box(
        modifier = Modifier
            .height(2.dp)
            .fillMaxWidth()
            .background(Color(0xFF024959))
    )
}

@Composable
private fun TodoList(
    todoList: MutableList<String>,
    clickAdd: Boolean,
    setClickAdd: (Boolean) -> Unit,
    focusRequester: FocusRequester
) {


    Column {

        LazyColumn {
            items(todoList) { todo ->
                ListContainer {
                    TodoListWithCheckbox(todo)
                }
            }
            if (clickAdd) {
                item {
                    ListContainer {
                        AddTodo(
                            todoList = todoList,
                            setClickAdd = setClickAdd,
                            focusRequester = focusRequester
                        )
                    }
                }
            }
        }

    }
}

@Composable
private fun ListContainer(content: @Composable () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp)
    ) {
        content()
    }
}

@Composable
fun TodoListWithCheckbox(text: String?) {
    var checked by remember { mutableStateOf(false) }

    Checkbox(
        checked = checked,
        onCheckedChange = { checked = it },
        colors = CheckboxDefaults.colors(
            uncheckedColor = Color(0xFF024959),
            checkedColor = Color(0xFF024959),
            checkmarkColor = Color.White
        )
    )
    Text(
        text = text ?: "",
        // text 취소선(체크박스를 눌렀을 때만)
        textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None
    )
}

@Composable
private fun AddTodo(
    todoList: MutableList<String>,
    setClickAdd: (Boolean) -> Unit,
    focusRequester: FocusRequester
) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        shape = RoundedCornerShape(15.dp),
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            unfocusedTextColor = Color.Black,
            cursorColor = Color(0xFF024959),
            focusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedTextColor = Color.Black,
        ),
        maxLines = 2,
        // text underline 제거
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .focusRequester(focusRequester)
    )
    IconButtons(icon = Icons.Rounded.Check, color = Color.Blue) {
        if (text.isNotBlank()) {
            todoList.add(text)
            text = ""
            setClickAdd(false)
        }
    }
}
