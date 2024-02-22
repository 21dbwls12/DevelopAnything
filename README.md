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

### 사용한 파일

- lotto
  - LottoActivity.kt

### 사진
https://github.com/21dbwls12/DevelopAnything/assets/139525941/5b28b261-8a80-481b-8e03-6970b9c4a100
