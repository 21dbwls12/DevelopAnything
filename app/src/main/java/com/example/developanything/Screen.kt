package com.example.developanything

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

// 전체 화면 틀 구성
@Composable
fun TodoListScreen() {
    val todoList = remember { mutableStateListOf<String>() }
    val checkedRemoveIndices = remember { mutableStateListOf<Int>() }
    // 추가 버튼을 눌렀을 때 TextField를 나타나게 하기 위함
    var clickAdd by remember { mutableStateOf(false) }
    var clickDelete by remember { mutableStateOf(false) }
    val focusToTextField = remember { FocusRequester() }

    LaunchedEffect(clickAdd) {
        if (clickAdd) {
            focusToTextField.requestFocus()
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

        Partition()

        TodoList(
            todoList,
            clickAdd,
            setClickAdd = { clickAdd = it },
            focusToTextField,
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
private fun Partition() {
    SpaceForPartition()
    PartitionLine()
    SpaceForPartition()
}

@Composable
private fun TodoList(
    todoList: MutableList<String>,
    clickAdd: Boolean,
    setClickAdd: (Boolean) -> Unit,
    focusToTextField: FocusRequester,
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
                            focusToTextField = focusToTextField
                        )
                    }
                }
            }
        }
    }
}