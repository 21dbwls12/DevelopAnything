# 안드로이드


## 안드로이드 Todo List

### 목적
앱 기능 구현

### 제출 일자

2024년 3월 10일 21:06:28

### 문제 설명

 <p>한 화면으로 구성된 Todo List 를 앱을 구성하세요.</p>
 <p>앱 재실행 시 리스트 초기화 ( DB 미사용 )</p>

### 조건

 <p>todo 텍스트 입력창, 추가 버튼, 리스트 표시</p>

### 사용한 함수

```kotlin
// 현재 시간
val currentTime = LocalDate.now()
// 현재 달
val month = currentTime.dayOfYear
// 현재 날짜
val date = currentTime.dayOfMonth
// 현재 요일
val day = currentTime.dayOfWeek

// TextField 엔터 입력시 실행할 내용을 설정하는 파라미터
keyboardActions = KeyboardActions(onDone = { addTodoInList() })

// 체크 박스
var checked by remember { mutableStateOf(false) }

Checkbox(
    checked = checked,
    onCheckedChange = { checked = it }
)

// 취소섬
Text(
    textDecoration = TextDecoration.LineThrough
)
```

### 피드백

### 사진
