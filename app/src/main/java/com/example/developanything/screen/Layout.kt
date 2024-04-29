package com.example.developanything.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.developanything.room.RoomDB
import com.example.developanything.room.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter.BASIC_ISO_DATE

// 화면 구성 요소
@Composable
fun IconButtons(icon: ImageVector, color: Color, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(imageVector = icon, contentDescription = null, tint = color)
    }
}

@Composable
fun SpaceForPartition() {
    Spacer(modifier = Modifier.height(5.dp))
}

@Composable
fun PartitionLine() {
    Box(
        modifier = Modifier
            .height(2.dp)
            .fillMaxWidth()
            .background(Color(0xFF024959))
    )
}


@Composable
fun ListContainer(content: @Composable () -> Unit) {
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
    // db 추가하면서 기존에 index와 text값을 따로 받아오던 것을 db table의 행 하나를 통째로 받아오는 것으로 수정
    todo: Todo,
    clickDelete: Boolean,
    checkCondition: Boolean,
    checkedRemoveUids: MutableList<Int>,
    setClickAdd: (Boolean) -> Unit,
    setClickTodo: (Todo?) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var checked by remember { mutableStateOf(false) }
    // 지울 내용을 체크하는 기능이랑 일반 체크리스트 기능이 동시에 이 함수를 이용하고 있어서 휴지통 아이콘을 눌렀을 때 db에 저장되어있는 정보가 표현되는 문제를 해결하기 위함
    if (!clickDelete) {
        checked = todo.isFinished ?: false
    }
    var checkedDelete by remember { mutableStateOf(false) }
    // 기존에 text를 따로 받아오던 것도 위의 checked와 같은 오류가 생김 (clickDelete가 true가 되면 빨간색 체크 박스에만 없어야하는데 원래 화면에 있던 text가 같이 사라짐)
    var text by remember { mutableStateOf("") }
    var memo by remember { mutableStateOf("") }
    var finishDate by remember { mutableStateOf("") }
    if (!clickDelete) {
        text = todo.todo
        memo = todo.memo ?: ""
        finishDate = todo.finishDate ?: ""
    }

    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .clickable {
                setClickAdd(true)
                setClickTodo(todo)
            }
            .fillMaxWidth()
    ) {
        if (clickDelete) {
            Checkbox(
                checked = checkedDelete,
                onCheckedChange = { isChecked ->
                    checkedDelete = isChecked
                    if (isChecked) {
                        checkedRemoveUids.add(todo.uid)
                    } else {
                        checkedRemoveUids.remove(todo.uid)
                    }
                },
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Color.Red,
                    checkedColor = Color.Red,
                    checkmarkColor = Color.White
                )
            )
        }
        Checkbox(
            checked = checked,
            onCheckedChange = { isChecked ->
                checked = isChecked
                // 체크 박스 눌렀을 때 db에 있는 Boolean 값이 바뀌도록 설정
                if (!clickDelete) {
                    scope.launch(Dispatchers.IO) {
                        todo.isFinished = isChecked
                        RoomDB.getInstance(context).updateTodo(todo)
                    }
                }
            },
            colors = CheckboxDefaults.colors(
                uncheckedColor = Color(0xFF024959),
                checkedColor = Color(0xFF024959),
                checkmarkColor = Color.White
            ),
            enabled = checkCondition
        )
        Text(
            text = todo.priority.toString(),
            color = Color(0xFFF2C12E),
            modifier = Modifier.padding(top = 12.dp, end = 12.dp)
        )
        Column(
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text(
                text = text,
                // text 취소선(체크박스를 눌렀을 때만)
                textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None
            )
            if (memo.isNotBlank()) {
                Text(
                    text = memo,
                    // text 취소선(체크박스를 눌렀을 때만)
                    textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None,
                )
            }
            if (finishDate.isNotBlank()) {
                Text(
                    text = finishDate,
                    // text 취소선(체크박스를 눌렀을 때만)
                    textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None,
                )
            }
        }
    }
}

@Composable
fun AddTodo(
    setClickAdd: (Boolean) -> Unit,
    focusToTextField: FocusRequester,
    todoCount: Int,
    clickTodo: Todo?
) {
    var text by remember { mutableStateOf(clickTodo?.todo ?: "") }
    var memo by remember { mutableStateOf(clickTodo?.memo ?: "") }
    var finishDate by remember { mutableStateOf(clickTodo?.finishDate ?: "") }
    var priority by remember { mutableIntStateOf(clickTodo?.priority ?: (todoCount + 1)) }
    val context = LocalContext.current
    val todayTodo = RoomDB.getInstance(context).GetTodoList()
    var reorderTodo = listOf<Todo?>()
    var isBigger = false
    val filteredTodo = clickTodo?.let { RoomDB.getInstance(context).GetTodo(uid = it.uid) }
    val savedDate = currentTimeFormat()
    val scope = rememberCoroutineScope()

    fun addTodoInList() {
        // 원래 선언해둔 list에 추가하던 방식말고 roomdb에 직접 넣어주는 방식으로 변경
        if (text.isNotBlank() && (finishDate.isBlank() || (finishDate.length == 8 && finishDate.isDigitsOnly()))) {
            // db에 추가할 객체 생성
            if (clickTodo != null) {
                filteredTodo!!.todo = text
                filteredTodo.date = savedDate
                filteredTodo.memo = memo
                filteredTodo.finishDate = finishDate
                filteredTodo.priority = priority

                if (clickTodo.priority != priority) {
                    when (clickTodo.priority < priority) {
                        true -> {
                            reorderTodo = todayTodo.filter { it.priority > clickTodo.priority && it.priority <= priority }
                            isBigger = true
                        }
                        else -> {
                            reorderTodo = todayTodo.filter { it.priority < clickTodo.priority && it.priority >= priority }
                            isBigger = false
                        }
                    }
                }
                scope.launch(Dispatchers.IO) {
                    RoomDB.getInstance(context).updateTodo(filteredTodo)
                    RoomDB.getInstance(context).pushPriority(reorderTodo, isBigger)
                }
            } else {
                val newTodo = Todo(
                    todo = text,
                    date = savedDate,
                    memo = memo,
                    finishDate = finishDate,
                    priority = priority
                )
                // 위에서 생성한 객체 db에 추가
                scope.launch(Dispatchers.IO) {
                    RoomDB.getInstance(context).insertTodo(newTodo)
                }
            }
            text = ""
            memo = ""
            finishDate = ""
            setClickAdd(false)
        }
    }

    Column {
        Row {
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
                keyboardActions = KeyboardActions(onDone = { addTodoInList() }),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .focusRequester(focusToTextField)
                    .padding(0.dp)
            )
            IconButtons(icon = Icons.Rounded.Check, color = Color.Blue) {
                addTodoInList()
            }
        }
        AddTextField(
            value = memo,
            onValueChange = { memo = it }
        ) {
            Text(text = "메모")
        }
        AddTextField(
            value = finishDate,
            onValueChange = { finishDate = it }
        ) {
            Text(text = "마감일자(20240407)")
        }
        if (clickTodo != null) {
            AddTextField(
                value = priority.toString(),
                onValueChange = { priority = it.toInt() }
            ) {

            }
        }
    }
}

@Composable
fun AddTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: @Composable (() -> Unit)
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
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
        placeholder = placeholder,
        modifier = Modifier.padding(0.dp),
    )
}


private fun currentTimeFormat(): String {
    val currentDate = LocalDate.now()
    // 현재 년, 월, 일을 20240323 이런식으로 출력
    return currentDate.format(BASIC_ISO_DATE)
}

fun stringToDate(date: String): LocalDate {
    return LocalDate.parse(date, BASIC_ISO_DATE)
}