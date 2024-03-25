# 안드로이드

## Todo List 로컬 DB연동

### 목적
Live 프로젝트 관리 훈련

### 제출 일자

2024년 3월 24일 19:35:28

### 문제 설명

 <p>todo list를 db에서 관리해보세요.</p>
 <p>기존 프로젝트에 git을 활용하시고, 변경사항을 어떻게 공유할지도 한 번 고민해서 올려주세요.</p>

### 조건

 <p>db 구현</p>

### 사용한 함수

```kotlin
// 프로젝트 수준 그래들
id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false
// 앱 수준 그래들
plugins {
    id("com.google.devtools.ksp")
}
dependencies {
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    ksp("androidx.room:room-compiler:$room_version")

    implementation("androidx.room:room-ktx:$room_version")
}
```
[Room 관련 안드로이드 스튜디오 공식 사이트](https://developer.android.com/training/data-storage/room?hl=ko)   
[kapt에서 ksp로 이전 관련 안드로이드 스튜디오 공식 사이트](https://developer.android.com/build/migrate-to-ksp?hl=ko)  
[ksp 최신 버전 확인](https://github.com/google/ksp/releases)  
[Kotlin과 Compose와의 호환 버전 확인](https://developer.android.com/jetpack/androidx/releases/compose-kotlin?hl=ko)  

### 피드백

### 사진

https://github.com/21dbwls12/DevelopAnything/assets/139525941/3350bc47-a7ef-45b2-a1a2-b4c16536e9ba

----------------------
## Todo List

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

<p>Layout과 screen 파일 구분 기준에 대해서 설명을 대비하기</p>

### 사진

https://github.com/21dbwls12/DevelopAnything/assets/139525941/75ff6ae7-2c37-4e6d-98db-ad70b0662b04
