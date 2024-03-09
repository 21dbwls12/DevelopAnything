# 안드로이드 


## 로또번호 생성기

### 목적
기능 요구사항 수행

### 제출 일자

2024년 2월 10일 16:12:28

### 문제 설명

 <p>버튼을 누르면 매번 새로운 로또 번호 조합을 생성해주는 동작을 구현해주세요.</p>

### 조건

 <p>Kotlin, Compose, 애니메이션</p>

### 사용한 함수

```kotlin
val imageLoader = ImageLoader.Builder(context)
    .components {
        if (SDK_INT >= 28) {
            add(ImageDecoderDecoder.Factory())
        } else {
            add(GifDecoder.Factory())
        }
    }
    .build()
AsyncImage(
    model = R.raw.colorballbump,
    imageLoader = imageLoader,
    contentDescription = null,
    modifier = Modifier
        .fillMaxSize()
        .alpha(0.5f),
    contentScale = ContentScale.FillHeight
)

LaunchedEffect(key1 = true) {)

val random = (1..45).random()
Random.nextInt(20, 100)
```

### 피드백

 <p>액티비티 시작이 MainActivity로 한 이유가 따로 없다면 LottoActivity에서 시작하기</p>
 <p>LottoScreen()이 너무 길어서 가독성이 낮아서 함수화하면서 가독성 고민하기</p>

  - 뷰와 로직을 구분하는 동작부터 함수화 
  - 함수화로 관계를 독립적으로 가져가는 작업이 '모듈화'의 첫 시작!
  - 큰 개념을 2~3개로 나누고 다시 하위를 2~3개로 나누기

### 사용한 파일

- lotto
  - LottoActivity.kt

### 사진
https://github.com/21dbwls12/DevelopAnything/assets/139525941/5b28b261-8a80-481b-8e03-6970b9c4a100
