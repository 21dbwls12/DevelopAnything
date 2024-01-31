# 안드로이드 


## 기본 화면 전환

### 목적

Activity를 이용한 화면 전환

### 제출 일자

2024년 1월 31일 11:50:28

### 문제 설명

<p>버튼을 누르면 화면을 이동하는 동작을 구현해주세요.</p>

### 조건 

 <p>Kotlin, Compose, 최소 3개의 Activity</p>

### 사용한 함수

```kotlin
val context = LocalContext.current
val intent = Intent(context, MainAcitivity::class.java)
context.startActivity(intent)
```
