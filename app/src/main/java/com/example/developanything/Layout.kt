package com.example.developanything

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
fun AddTodo(
    todoList: MutableList<String>,
    setClickAdd: (Boolean) -> Unit,
    focusToTextField: FocusRequester
) {
    var text by remember { mutableStateOf("") }

    fun addTodoInList() {
        if (text.isNotBlank()) {
            todoList.add(text)
            text = ""
            setClickAdd(false)
        }
    }

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
    )
    IconButtons(icon = Icons.Rounded.Check, color = Color.Blue) {
        addTodoInList()
    }
}
