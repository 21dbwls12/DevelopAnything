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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
    val checkedRemoveIndices = remember { mutableStateListOf<Int>() }
    // 추가 버튼을 눌렀을 때 TextField를 나타나게 하기 위함
    var clickAdd by remember { mutableStateOf(false) }
    var clickDelete by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(clickAdd) {
        if (clickAdd) {
            focusRequester.requestFocus()
        }
    }

    fun handleAddClick() {
        clickAdd = true
        clickDelete = false
        checkedRemoveIndices.clear()
    }

    fun handleDeleteClick() {
        if (todoList.isNotEmpty()) {
            clickDelete = !clickDelete
        }
        if (!clickDelete) {
            checkedRemoveIndices.sortedDescending().forEach { todoList.removeAt(it) }
            checkedRemoveIndices.clear()
        }
    }

    Column(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
    ) {
        // clickAdd의 상태를 다른 스코프에서도 변경할 수 있음
        TopBar(
            setClickAdd = ::handleAddClick,
            setClickDelete = ::handleDeleteClick,
        )
        SpaceForPartition()
        PartitionLine()
        SpaceForPartition()

        TodoList(
            todoList,
            clickAdd,
            setClickAdd = { clickAdd = it },
            focusRequester,
            clickDelete,
            checkedRemoveIndices
        )
    }
}

@Composable
private fun TopBar(
    setClickAdd: () -> Unit,
    setClickDelete: () -> Unit,
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
                setClickDelete()
            }
        )
        Text(text = date.toString(), color = Color(0xFFF2C12E), fontSize = 40.sp)
        IconButtons(
            icon = Icons.Rounded.Add,
            color = Color.Blue,
            onClick = {
                setClickAdd()
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
    focusRequester: FocusRequester,
    clickDelete: Boolean,
    checkedRemoveIndices: MutableList<Int>
) {
    Row {
        if (clickDelete) {
            LazyColumn {
                items(todoList.size) {
                    ListContainer {
                        TodoWithCheckbox(
                            text = null,
                            clickDelete = clickDelete,
                            checkCondition = clickDelete,
                            checkedRemoveIndices = checkedRemoveIndices,
                            index = it
                        )
                    }
                }
            }
        }
        LazyColumn {
            itemsIndexed(todoList) { index, todo ->
                ListContainer {
                    TodoWithCheckbox(
                        text = todo,
                        clickDelete = clickDelete,
                        checkCondition = !clickDelete,
                        checkedRemoveIndices = checkedRemoveIndices,
                        index = index
                    )
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
            .padding(start = 5.dp, end = 5.dp)
    ) {
        content()
    }
}

@Composable
fun TodoWithCheckbox(
    text: String?,
    clickDelete: Boolean,
    checkCondition: Boolean,
    checkedRemoveIndices: MutableList<Int>,
    index: Int
) {
    var checked by remember { mutableStateOf(false) }

    Checkbox(
        checked = checked,
        onCheckedChange = { isChecked ->
            checked = isChecked
            if (clickDelete && isChecked) {
                checkedRemoveIndices.add(index)
            } else {
                checkedRemoveIndices.remove(index)
            }
        },
        colors = CheckboxDefaults.colors(
            uncheckedColor = if (clickDelete) Color.Red else Color(0xFF024959),
            checkedColor = if (clickDelete) Color.Red else Color(0xFF024959),
            checkmarkColor = Color.White
        ),
        enabled = checkCondition
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
        // 엔터키 사용하기 위한 설정
        singleLine = true,
        // text underline 제거
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        // 엔터키 누르면 밑의 check 아이콘 버튼 누른 것과 동일한 기능
        keyboardActions = KeyboardActions(onDone = {
            addTodoInList(
                text = text,
                todoList = todoList,
                setClickAdd = setClickAdd,
                setText = { text = it }
            )
        }),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .focusRequester(focusRequester)
    )
    IconButtons(icon = Icons.Rounded.Check, color = Color.Blue) {
        addTodoInList(
            text = text,
            todoList = todoList,
            setClickAdd = setClickAdd,
            setText = { text = it }
        )
    }
}

fun addTodoInList(
    text: String,
    todoList: MutableList<String>,
    setClickAdd: (Boolean) -> Unit,
    setText: (String) -> Unit
) {
    if (text.isNotBlank()) {
        todoList.add(text)
        setText("")
        setClickAdd(false)
    }
}
