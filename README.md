# 안드로이드 


## 안드로이드 Compose Animation

### 목적
Compose를 활용한 Animation 적용

### 제출 일자

2024년 2월 22일 16:34:28

### 문제 설명

 <p>'다음' 버튼을 누르면 이미지가 변경되는 동작을 Animation을 포함해 구현해주세요.</p>

### 조건

 <p>Kotlin, Compose, Crossfade</p>

### 사용한 함수

```kotlin
// 원래 있던 이미지는 opacity가 점진적으로 0이 되고 새로 나타날 이미지는 점진적으로 opacity가 100이 되면서 교차되면서 이미지가 교체됨
var currentPage by remember { mutableStateOf("A") }
Crossfade(targetState = currentPage) { screen ->
    when (screen) {
        "A" -> Text("Page A")
        "B" -> Text("Page B")
    }
}
```

### 사진




[crossfademp4.mp4](https://github.com/21dbwls12/DevelopAnything/assets/139525941/5e770bc3-bc7c-48c8-a5a5-a7d754d6be89)


