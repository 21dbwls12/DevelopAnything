package com.example.developanything.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.developanything.room.RoomDB
import com.example.developanything.room.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

// 전체 화면 틀 구성
@Composable
fun TodoListScreen() {
    // 기존의 리스트의 인덱스 값을 저장해 그 인덱스에 해당하는 값을 제거하는 방식에서 uid 값을 받아 그 uid를 가진 행을 지우는 방식으로 변경
    val checkedRemoveUids = remember { mutableStateListOf<Int>() }
    // 추가 버튼을 눌렀을 때 TextField를 나타나게 하기 위함
    var clickAdd by remember { mutableStateOf(false) }
    var clickDelete by remember { mutableStateOf(false) }
    var clickTodo by remember { mutableStateOf<Todo?>(null) }
    val focusToTextField = remember { FocusRequester() }

    val context = LocalContext.current
    val filteredList = RoomDB.getInstance(context).GetTodoList()
    val scope = rememberCoroutineScope()

    LaunchedEffect(clickAdd) {
        if (clickAdd) {
            focusToTextField.requestFocus()
        }
    }

    fun handleAddClick(isClicked: Boolean) {
        if (isClicked) {
            clickAdd = true
            clickDelete = false
            checkedRemoveUids.clear()
        } else {
            clickAdd = false
        }
    }

    fun handleDeleteClick() {
        if (filteredList.isNotEmpty()) {
            clickDelete = !clickDelete
        }
        if (!clickDelete) {
            scope.launch(Dispatchers.IO) {
                checkedRemoveUids.forEach { uid ->
                    val selectedTodo = filteredList.find { it.uid == uid }!!
                    RoomDB.getInstance(context).deleteTodo(selectedTodo)
                }
                checkedRemoveUids.clear()
            }
        }
    }

    fun handleTodoClick(todo: Todo?) {
        clickTodo = todo
    }

    Column(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
    ) {
        // clickAdd의 상태를 다른 스코프에서도 변경할 수 있음
        TopBar(
            setClickAdd = {
                handleAddClick(it)
                if (it) clickTodo = null
            },
            setClickDelete = ::handleDeleteClick,
            clickAdd = clickAdd,
        )

        Partition()

        TodoList(
            clickAdd,
            setClickAdd = {
                clickAdd = it
                if (it) clickTodo = null
            },
            setClickTodo = {
                if (it != null) {
                    handleTodoClick(it)
                }
            },
            focusToTextField,
            clickDelete,
            clickTodo,
            checkedRemoveUids
        )
    }
}

@Composable
private fun TopBar(
    setClickAdd: (Boolean) -> Unit,
    setClickDelete: () -> Unit,
    clickAdd: Boolean,
) {
    val currentDate = LocalDate.now()
    // 날짜
    val date = currentDate.dayOfMonth

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
        if (!clickAdd) {
            IconButtons(
                icon = Icons.Rounded.Add,
                color = Color.Blue,
                onClick = {
                    setClickAdd(true)
                }
            )
        } else {
            IconButtons(
                icon = Icons.Rounded.Close,
                color = Color.Red,
                onClick = {
                    setClickAdd(false)
                }
            )
        }
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
    clickAdd: Boolean,
    setClickAdd: (Boolean) -> Unit,
    setClickTodo: (Todo?) -> Unit,
    focusToTextField: FocusRequester,
    clickDelete: Boolean,
    clickTodo: Todo?,
    checkedRemoveUids: MutableList<Int>
) {
    val context = LocalContext.current
    val filteredList = RoomDB.getInstance(context).GetTodoList()
    val todoCount = RoomDB.getInstance(context).GetTodoList().size

    LazyColumn {
        items(filteredList) {todo ->
            ListContainer {
                TodoWithCheckbox(
                    todo = todo,
                    clickDelete = clickDelete,
                    checkCondition = !clickDelete,
                    checkedRemoveUids = checkedRemoveUids,
                    setClickAdd = setClickAdd,
                    setClickTodo = setClickTodo
                )
            }
        }
        if (clickAdd) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().fillParentMaxHeight(0.8f)
                ) {
                    ListContainer {
                        AddTodo(
                            setClickAdd = setClickAdd,
                            focusToTextField = focusToTextField,
                            todoCount = todoCount,
                            clickTodo = clickTodo
                        )
                    }
                }
            }
        }
    }
}