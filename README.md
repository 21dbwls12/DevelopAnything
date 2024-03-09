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

### 피드백

```kotlin
onClick = {
    var temp = (0..2).random()
    while (currentNumber == temp) {
        temp = (0..2).random()
    }
    currentNumber = temp
},
```

<p>while 위험도가 높음. 경우의 수가 많을 때, 최악의 경우(연속으로 계속 같은 숫자가 나올는 경우)에는 연산이 길어짐.</p>

```kotlin
onClick = {
    var temp = (0..2).minus(currentNumber).random()
    currentNumber = temp
},
```

 <p>위의 코드처럼 현재 값을 뺀 범위에서 랜덤 숫자를 뽑는 방식으로 진행하면 안정성과 가독성이 올라감</p>

### 사진

[crossfademp4.mp4](https://github.com/21dbwls12/DevelopAnything/assets/139525941/5e770bc3-bc7c-48c8-a5a5-a7d754d6be89)


![50crossfadegif](https://github.com/21dbwls12/DevelopAnything/assets/139525941/913c52de-fe6e-4f89-9479-5cb17574ee30)
